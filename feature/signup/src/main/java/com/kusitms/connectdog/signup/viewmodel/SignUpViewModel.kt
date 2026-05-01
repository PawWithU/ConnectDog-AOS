package com.kusitms.connectdog.signup.viewmodel

import android.util.Log
import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kusitms.connectdog.core.util.UserType
import com.kusitms.connectdog.domain.usecase.auth.GetEmailAuthCodeUseCase
import com.kusitms.connectdog.domain.usecase.auth.GetIntermediatorPhoneNumberDuplicationUseCase
import com.kusitms.connectdog.domain.usecase.auth.GetVolunteerPhoneNumberDuplicationUseCase
import com.kusitms.connectdog.domain.usecase.login.AppMode
import com.kusitms.connectdog.domain.usecase.login.SocialLoginUseCase
import com.kusitms.connectdog.domain.usecase.login.UpdateAccessTokenUseCase
import com.kusitms.connectdog.domain.usecase.login.UpdateAppModeUseCase
import com.kusitms.connectdog.domain.usecase.login.UpdateRefreshTokenUseCase
import com.kusitms.connectdog.domain.usecase.login.VolunteerLoginUseCase
import com.kusitms.connectdog.domain.usecase.signup.GetIntermediatorNameDuplication
import com.kusitms.connectdog.domain.usecase.signup.GetSocialProviderUseCase
import com.kusitms.connectdog.domain.usecase.signup.GetSocialTokenUseCase
import com.kusitms.connectdog.domain.usecase.signup.GetVolunteerNicknameDuplication
import com.kusitms.connectdog.domain.usecase.signup.InitNormalVolunteerSignUpUseCase
import com.kusitms.connectdog.domain.usecase.signup.InitSocialVolunteerSignUpUseCase
import com.kusitms.connectdog.signup.state.SignUpSideEffect
import com.kusitms.connectdog.signup.state.SignUpUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

private const val TAG = "SignUpViewModel"

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val getVolunteerPhoneNumberDuplicationUseCase: GetVolunteerPhoneNumberDuplicationUseCase,
    private val getIntermediatorPhoneNumberDuplicationUseCase: GetIntermediatorPhoneNumberDuplicationUseCase,
    private val getEmailAuthCodeUseCase: GetEmailAuthCodeUseCase,
    private val getNicknameDuplicationUseCase: GetVolunteerNicknameDuplication,
    private val getIntermediatorNameDuplicationUseCase: GetIntermediatorNameDuplication,
    private val initNormalVolunteerSignUpUseCase: InitNormalVolunteerSignUpUseCase,
    private val initSocialVolunteerSignUpUseCase: InitSocialVolunteerSignUpUseCase,
    private val volunteerLoginUseCase: VolunteerLoginUseCase,
    private val socialLoginUseCase: SocialLoginUseCase,
    private val updateAccessTokenUseCase: UpdateAccessTokenUseCase,
    private val updateRefreshTokenUseCase: UpdateRefreshTokenUseCase,
    private val updateAppModeUseCase: UpdateAppModeUseCase,
    private val getSocialTokenUseCase: GetSocialTokenUseCase,
    private val getSocialProviderUseCase: GetSocialProviderUseCase,
) : ContainerHost<SignUpUiState, SignUpSideEffect>, ViewModel() {
    override val container: Container<SignUpUiState, SignUpSideEffect> =
        container(SignUpUiState.empty())
    private val state: SignUpUiState
        get() = container.stateFlow.value

    fun updateUserType(userType: UserType) = intent {
        reduce { state.copy(userType = userType) }
    }

    /*이름, 휴대폰 번호 인증*/
    fun onNameChanged(name: String) = intent {
        reduce { state.copy(name = name) }
        enableSendPhoneAuthCodeButton()
    }

    fun onPhoneNumberChanged(phoneNumber: String) = intent {
        if (phoneNumber.length <= 11) reduce { state.copy(phoneNumber = phoneNumber) }
        enableSendPhoneAuthCodeButton()
    }

    fun onPhoneAuthCodeChanged(authCode: String) {
        if (authCode.length <= 6) intent { reduce { state.copy(phoneAuthCode = authCode) } }
        enableSendPhoneAuthCodeButton()
    }

    fun onPhoneCertificationButtonClick(
        onSendMessageClick: (String) -> Unit,
        onVerifyCodeClick: (String, (Boolean) -> Unit) -> Unit,
    ) {
        if (!state.isSendPhoneAuthCode) {
            when (state.userType) {
                UserType.INTERMEDIATOR -> getIntermediatorPhoneNumberDuplication()
                else -> getVolunteerPhoneNumberDuplication()
            }
            if (!state.isDuplicatedPhoneNumber) sendPhoneAuthCode(onSendMessageClick)
            else intent { postSideEffect(SignUpSideEffect.NavigateTo) }
        } else checkPhoneAuthCode(onVerifyCodeClick, state.userType)
    }

    private fun getVolunteerPhoneNumberDuplication() = viewModelScope.launch {
        getVolunteerPhoneNumberDuplicationUseCase(
            phone = state.phoneNumber
        ).onSuccess {
            intent { reduce { state.copy(isDuplicatedPhoneNumber = it.isDuplicated) } }
        }.onFailure {

        }
    }

    private fun getIntermediatorPhoneNumberDuplication() = viewModelScope.launch {
        getIntermediatorPhoneNumberDuplicationUseCase(
            phone = state.phoneNumber
        ).onSuccess {
            intent { reduce { state.copy(isDuplicatedPhoneNumber = it.isDuplicated) } }
        }.onFailure {

        }
    }

    private fun sendPhoneAuthCode(sendMessage: (String) -> Unit) {
        sendMessage(state.phoneNumber)
        intent {
            reduce {
                state.copy(
                    isSendPhoneAuthCode = true,
                    enablePhoneCertification = false
                )
            }
        }
        updatePhoneCertificationButtonText()
    }

    private fun enableSendPhoneAuthCodeButton() = intent {
        if (!state.isSendPhoneAuthCode && state.phoneNumber.length == 11 && state.name.isNotEmpty()) {
            reduce { state.copy(enablePhoneCertification = true) }
        } else if (state.isSendPhoneAuthCode && state.phoneAuthCode.length == 6) {
            reduce { state.copy(enablePhoneCertification = true) }
        } else {
            reduce { state.copy(enablePhoneCertification = false) }
        }
    }

    private fun checkPhoneAuthCode(
        onVerifyCodeClick: (String, (Boolean) -> Unit) -> Unit,
        userType: UserType,
    ) {
//        onVerifyCodeClick(state.phoneAuthCode) { isCertified ->
//            intent { reduce { state.copy(isPhoneNumberCertified = isCertified) } }
//            if (isCertified) intent {
//                when(userType) {
//                    UserType.SOCIAL_VOLUNTEER -> postSideEffect(SignUpSideEffect.NavigateToProfile)
//                    else -> postSideEffect(SignUpSideEffect.NavigateToEmailRegister)
//                }
//            }
//        }
        intent {
            when (userType) {
                UserType.SOCIAL_VOLUNTEER -> postSideEffect(SignUpSideEffect.NavigateToProfile)
                else -> postSideEffect(SignUpSideEffect.NavigateToEmailRegister)
            }
        }
    }

    private fun updatePhoneCertificationButtonText() =
        intent { reduce { state.copy(phoneCertificationButtonText = "인증 확인") } }

    /*이메일 인증*/
    fun onEmailChanged(email: String) = intent {
        reduce { state.copy(email = email) }
        checkValidEmail()
        enableEmailCertification()
    }

    fun onEmailAuthCodeChanged(authCode: String) = intent {
        if (authCode.length <= 8) reduce { state.copy(inputEmailAuthCode = authCode) }
        enableEmailCertification()
    }

    private fun enableEmailCertification() = intent {
        if (!state.isSendEmailAuthCode && state.isValidEmail == true) reduce {
            state.copy(enableEmailCertification = true)
        }
        else if (state.isSendEmailAuthCode && state.inputEmailAuthCode.length == 8) reduce {
            state.copy(enableEmailCertification = true)
        }
        else reduce { state.copy(enableEmailCertification = false) }
    }

    fun onEmailCertificationButtonClick() {
        if (!state.isSendEmailAuthCode) getEmailAuthCode()
        else checkEmailAuthCode()
    }

    private fun checkEmailAuthCode() = intent {
        reduce { state.copy(isEmailAuthCodeError = state.inputEmailAuthCode != state.emailAuthCode) }
        if (state.isEmailAuthCodeError == false) postSideEffect(SignUpSideEffect.NavigateToPasswordRegister)
    }

    private fun checkValidEmail() = intent {
        reduce { state.copy(isValidEmail = Patterns.EMAIL_ADDRESS.matcher(state.email).matches()) }
    }

    private fun getEmailAuthCode() = viewModelScope.launch {
        intent { reduce { state.copy(isEmailLoading = true, enableEmailCertification = false) } }
        getEmailAuthCodeUseCase(
            email = state.email
        ).onSuccess {
            intent {
                reduce {
                    state.copy(
                        emailAuthCode = it.authCode,
                        isSendEmailAuthCode = true,
                        enableEmailCertification = false,
                        isEmailLoading = false
                    )
                }
            }
            updateEmailCertificationButtonText()
        }.onFailure {
            intent { reduce { state.copy(isEmailLoading = false, enableEmailCertification = true) } }
        }
    }

    private fun updateEmailCertificationButtonText() =
        intent { reduce { state.copy(emailCertificationButtonText = "인증 확인") } }

    /*비밀번호 등록*/
    fun onPasswordChanged(password: String) = intent {
        reduce { state.copy(password = password) }
        checkPasswordValidity()
        enablePasswordRegisterButton()
    }

    fun onConfirmPasswordChanged(password: String) = intent {
        reduce { state.copy(confirmPassword = password) }
        checkConfirmPasswordValidity()
        enablePasswordRegisterButton()
    }

    fun onPasswordRegisterNextButtonClick() = intent {
        when (state.userType) {
            UserType.INTERMEDIATOR -> Unit
            else -> postSideEffect(SignUpSideEffect.NavigateToProfile)
        }
    }

    private fun enablePasswordRegisterButton() = intent {
        reduce { state.copy(enablePasswordRegister = (state.isValidPassword == true && state.isValidConfirmPassword == true)) }
    }

    private fun checkPasswordValidity() = intent {
        reduce {
            state.copy(
                isValidPassword = state.englishAndNumberRegex.matches(state.password)
                        || state.englishNumberSpecialRegex.matches(state.password)
            )
        }
    }

    private fun checkConfirmPasswordValidity() = intent {
        reduce { state.copy(isValidConfirmPassword = state.password == state.confirmPassword) }
    }

    /*닉네임*/
    fun onNickNameChanged(nickname: String) = intent {
        if (nickname.length <= 12) reduce { state.copy(nickname = nickname) }
        isAvailableNickname()
        enableNicknameDuplicationButton()
    }

    private fun isAvailableNickname() {
        intent {
            reduce { state.copy(isAvailableNickName = !state.nicknameRegex.matches(state.nickname)) }
        }
    }

    fun onCheckNicknameDuplicationButtonClick() = viewModelScope.launch {
        getNicknameDuplicationUseCase(state.nickname).onSuccess {
            intent { reduce { state.copy(isDuplicatedNickname = it.isDuplicated) } }
            when (state.userType) {
                UserType.NORMAL_VOLUNTEER -> initNormalVolunteerSignUp()
                UserType.SOCIAL_VOLUNTEER -> initSocialVolunteerSignUp()
                else -> Unit
            }
        }
    }

    fun updateProfileImageIndex(index: Int) =
        intent { reduce { state.copy(profileImageId = index) } }

    private fun enableNicknameDuplicationButton() = intent {
        if (state.nickname.length >= 2 && state.isAvailableNickName) reduce {
            state.copy(
                enableNicknameDuplication = true
            )
        }
        else reduce { state.copy(enableNicknameDuplication = false) }
    }

    private fun getNameDuplication() = viewModelScope.launch {
        getIntermediatorNameDuplicationUseCase(state.name).onSuccess {

        }
    }

    private fun initNormalVolunteerSignUp() = viewModelScope.launch {
        initNormalVolunteerSignUpUseCase(
            email = state.email,
            password = state.password,
            nickname = state.nickname,
            profileImageNum = state.profileImageId,
            phone = state.phoneNumber,
            name = state.name
        ).onSuccess {
            intent { postSideEffect(SignUpSideEffect.NavigateToSignUpComplete) }
        }.onFailure {
            Log.d("asdf", it.toString())
        }
    }

    private fun initSocialVolunteerSignUp() = viewModelScope.launch {
        initSocialVolunteerSignUpUseCase(
            email = state.email,
            password = state.password,
            nickname = state.nickname,
            profileImageNum = state.profileImageId,
            phone = state.phoneNumber,
            name = state.name
        ).onSuccess {
            setSocialVolunteerLogin()
        }.onFailure {

        }
    }

    fun initIntermediatorSignUp() = viewModelScope.launch {

    }

    fun onStartClick() = when (state.userType) {
        UserType.NORMAL_VOLUNTEER -> setNormalVolunteerLogin()
        UserType.SOCIAL_VOLUNTEER -> setSocialVolunteerLogin()
        UserType.INTERMEDIATOR -> setSocialVolunteerLogin()
    }

    private fun setNormalVolunteerLogin() = viewModelScope.launch {
        volunteerLoginUseCase(state.email, state.password).onSuccess {
            updateAccessTokenUseCase(it.accessToken)
            updateRefreshTokenUseCase(it.refreshToken)
            updateAppModeUseCase(AppMode.VOLUNTEER)
            intent { postSideEffect(SignUpSideEffect.NavigateToSignUpComplete) }
        }
    }

    private fun setSocialVolunteerLogin() = viewModelScope.launch {
//
    }
}
