package com.kusitms.connectdog.signup.state

import android.net.Uri
import com.kusitms.connectdog.core.util.UserType
import com.kusitms.connectdog.core.util.emptyString

data class SignUpUiState(
    val userType: UserType,
    val email: String,
    val password: String,
    val confirmPassword: String,
    val isValidPassword: Boolean?,
    val isValidConfirmPassword: Boolean?,
    val nickname: String,
    val nicknameRegex: Regex,
    val nickNameErrorMessage: String,
    val profileImageId: Int,
    val phoneNumber: String,
    val phoneAuthCode: String,
    val isSendPhoneAuthCode: Boolean,
    val isDuplicatedPhoneNumber: Boolean,
    val contact: String,
    val intro: String,
    val url: String,
    val name: String,
    val intermediatorProfileImage: Uri?,
    val phoneCertificationButtonText: String,
    val emailCertificationButtonText: String,
    val enablePhoneCertification: Boolean,
    val enableEmailCertification: Boolean,
    val enablePasswordRegister: Boolean,
    val enableNicknameDuplication: Boolean,
    val isPhoneNumberCertified: Boolean?,
    val isSendEmailAuthCode: Boolean,
    val inputEmailAuthCode: String,
    val emailAuthCode: String,
    val isEmailError: Boolean?,
    val isEmailAuthCodeError: Boolean?,
    val isValidEmail: Boolean?,
    val isAvailableNickName: Boolean,
    val isDuplicatedNickname: Boolean?,
    val isDuplicatedEmail: Boolean,
    val englishAndNumberRegex: Regex,
    val englishNumberSpecialRegex: Regex
) {
    companion object {
        fun empty() = SignUpUiState(
            userType = UserType.INTERMEDIATOR,
            email = emptyString(),
            password = emptyString(),
            confirmPassword = emptyString(),
            isValidPassword = null,
            isValidConfirmPassword = null,
            nickname = emptyString(),
            nicknameRegex = "^[ㄱ-ㅎ]+$".toRegex(),
            nickNameErrorMessage = emptyString(),
            profileImageId = 0,
            phoneNumber = emptyString(),
            phoneAuthCode = emptyString(),
            isSendPhoneAuthCode = false,
            contact = emptyString(),
            intro = emptyString(),
            url = emptyString(),
            name = emptyString(),
            intermediatorProfileImage = null,
            phoneCertificationButtonText = "인증 요청",
            emailCertificationButtonText = "인증 요청",
            enablePhoneCertification = false,
            enableEmailCertification = false,
            enablePasswordRegister = false,
            enableNicknameDuplication = false,
            isPhoneNumberCertified = null,
            isSendEmailAuthCode = false,
            inputEmailAuthCode = emptyString(),
            emailAuthCode = emptyString(),
            isEmailAuthCodeError = null,
            isEmailError = null,
            isValidEmail = null,
            isAvailableNickName = false,
            isDuplicatedNickname = null,
            isDuplicatedEmail = false,
            isDuplicatedPhoneNumber = false,
            englishAndNumberRegex = Regex("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{10,}$"),
            englishNumberSpecialRegex = Regex("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@#\$%^&+=!])[A-Za-z\\d@#\$%^&+=!]{8,}$")
        )
    }
}

sealed class SignUpSideEffect {
    object NavigateToProfile : SignUpSideEffect()
    object NavigateToEmailRegister: SignUpSideEffect()
    object NavigateToPasswordRegister: SignUpSideEffect()
    object NavigateToSignUpComplete: SignUpSideEffect()
    object NavigateToVolunteerHome: SignUpSideEffect()
    object NavigateToIntermediatorHome: SignUpSideEffect()
    object NavigateTo: SignUpSideEffect()
}