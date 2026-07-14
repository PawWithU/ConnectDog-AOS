package com.kusitms.connectdog.feature.login.viewmodel

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kusitms.connectdog.core.util.UserType
import com.kusitms.connectdog.domain.usecase.auth.EmailAuthForIntermediatorPasswordResetUseCase
import com.kusitms.connectdog.domain.usecase.auth.EmailAuthForVolunteerPasswordResetUseCase
import com.kusitms.connectdog.domain.usecase.login.UpdateAccessTokenUseCase
import com.kusitms.connectdog.feature.login.state.EmailAuthForPasswordResetSideEffect
import com.kusitms.connectdog.feature.login.state.EmailAuthForPasswordResetUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class EmailAuthForPasswordResetViewModel
    @Inject
    constructor(
        private val emailAuthForVolunteerPasswordResetUseCase: EmailAuthForVolunteerPasswordResetUseCase,
        private val emailAuthForIntermediatorPasswordResetUseCase: EmailAuthForIntermediatorPasswordResetUseCase,
        private val updateAccessTokenUseCase: UpdateAccessTokenUseCase,
    ) : ContainerHost<EmailAuthForPasswordResetUiState, EmailAuthForPasswordResetSideEffect>,
        ViewModel() {
        override val container: Container<EmailAuthForPasswordResetUiState, EmailAuthForPasswordResetSideEffect> =
            container(EmailAuthForPasswordResetUiState.empty())
        private val state: EmailAuthForPasswordResetUiState
            get() = container.stateFlow.value

        fun onEmailChanged(email: String) {
            intent { reduce { state.copy(email = email) } }
            checkValidEmail()
            enableNextButton()
        }

        fun onInputAuthCodeChanged(inputAuthCode: String) {
            if (inputAuthCode.length <= 8) intent { reduce { state.copy(inputAuthCode = inputAuthCode) } }
            enableNextButton()
        }

        fun onNextButtonClick(userType: UserType) {
            if (!state.isSendAuthCode) {
                sendAuthCode(userType)
                intent { reduce { state.copy(enableNext = false) } }
                intent { reduce { state.copy(bottomButtonText = "인증 확인") } }
            } else if (state.inputAuthCode != state.authCode) {
                intent { reduce { state.copy(isAuthCodeError = true) } }
            } else {
                intent { postSideEffect(EmailAuthForPasswordResetSideEffect.NavigateToPasswordReset) }
            }
        }

        private fun sendAuthCode(userType: UserType) =
            when (userType) {
                UserType.INTERMEDIATOR -> getIntermediatorEmailAuthCode()
                else -> getVolunteerEmailAuthCode()
            }

        private fun getVolunteerEmailAuthCode() =
            viewModelScope.launch {
                emailAuthForVolunteerPasswordResetUseCase(state.email).onSuccess {
                    intent { reduce { state.copy(authCode = it.authCode, isSendAuthCode = true) } }
                    updateAccessTokenUseCase(it.accessToken)
                }.onFailure {
                    intent { postSideEffect(EmailAuthForPasswordResetSideEffect.NavigateToFail) }
                }
            }

        private fun getIntermediatorEmailAuthCode() =
            viewModelScope.launch {
                emailAuthForIntermediatorPasswordResetUseCase(state.email).onSuccess {
                    intent { reduce { state.copy(authCode = it.authCode, isSendAuthCode = true) } }
                    updateAccessTokenUseCase(it.accessToken)
                }.onFailure {
                    intent { postSideEffect(EmailAuthForPasswordResetSideEffect.NavigateToFail) }
                }
            }

        private fun enableNextButton() =
            intent {
                if (!state.isSendAuthCode && state.isValidEmail) {
                    reduce { state.copy(enableNext = true) }
                } else if (state.isSendAuthCode && state.inputAuthCode.length == 8) {
                    reduce { state.copy(enableNext = true) }
                } else {
                    reduce { state.copy(enableNext = false) }
                }
            }

        private fun checkValidEmail() =
            intent {
                reduce { state.copy(isValidEmail = Patterns.EMAIL_ADDRESS.matcher(state.email).matches()) }
            }
    }
