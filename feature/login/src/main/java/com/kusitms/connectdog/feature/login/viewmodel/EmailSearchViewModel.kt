package com.kusitms.connectdog.feature.login.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kusitms.connectdog.core.util.UserType
import com.kusitms.connectdog.domain.usecase.auth.SearchIntermediatorEmailUseCase
import com.kusitms.connectdog.domain.usecase.auth.SearchVolunteerEmailUseCase
import com.kusitms.connectdog.feature.login.state.EmailSearchSideEffect
import com.kusitms.connectdog.feature.login.state.EmailSearchUiState
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
class EmailSearchViewModel
    @Inject
    constructor(
        private val searchVolunteerEmailUseCase: SearchVolunteerEmailUseCase,
        private val searchIntermediatorEmailUseCase: SearchIntermediatorEmailUseCase,
    ) : ContainerHost<EmailSearchUiState, EmailSearchSideEffect>, ViewModel() {
        override val container: Container<EmailSearchUiState, EmailSearchSideEffect> = container(EmailSearchUiState.empty())
        private val state: EmailSearchUiState
            get() = container.stateFlow.value

        fun onPhoneNumberChanged(phoneNumber: String) =
            intent {
                if (phoneNumber.length <= 11) reduce { state.copy(phoneNumber = phoneNumber) }
                enableNextButton()
            }

        fun onAuthCodeChanged(authCode: String) =
            intent {
                if (authCode.length <= 6) reduce { state.copy(authCode = authCode) }
                enableNextButton()
            }

        private fun enableNextButton() =
            intent {
                if (!state.isSendAuthCode && state.phoneNumber.length == 11) {
                    reduce { state.copy(enableNext = true) }
                } else if (state.isSendAuthCode && state.authCode.length == 6) {
                    reduce { state.copy(enableNext = true) }
                } else {
                    reduce { state.copy(enableNext = false) }
                }
            }

        fun onNextClick(
            userType: UserType,
            onSendMessageClick: (String) -> Unit,
            onVerifyCodeClick: (String, (Boolean) -> Unit) -> Unit,
        ) {
            if (!state.isSendAuthCode) {
                updateBottomButtonText()
                onSendMessageClick(state.phoneNumber)
                disableNextButton()
                intent { reduce { state.copy(isSendAuthCode = true) } }
            } else {
                onVerifyCodeClick(state.authCode) {
                    if (it) {
                        intent {
                            when (userType) {
                                UserType.INTERMEDIATOR -> searchIntermediatorEmail()
                                else -> searchVolunteerEmail()
                            }
                            postSideEffect(EmailSearchSideEffect.NavigateToEmailSearchResult)
                        }
                    } else {
                        intent { reduce { state.copy(isAuthCodeError = true) } }
                    }
                }
            }
        }

        private fun updateBottomButtonText() = intent { reduce { state.copy(bottomButtonText = "인증 확인") } }

        private fun disableNextButton() = intent { reduce { state.copy(enableNext = false) } }

        private fun searchIntermediatorEmail() =
            viewModelScope.launch {
                searchIntermediatorEmailUseCase(state.phoneNumber).onSuccess {
                    intent { reduce { state.copy(email = it.email) } }
                }.onFailure {
                }
            }

        private fun searchVolunteerEmail() =
            viewModelScope.launch {
                searchVolunteerEmailUseCase(state.phoneNumber).onSuccess {
                    intent { reduce { state.copy(email = it.email) } }
                }.onFailure {
                }
            }
    }
