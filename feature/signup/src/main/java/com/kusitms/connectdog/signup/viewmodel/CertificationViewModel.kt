package com.kusitms.connectdog.signup.viewmodel

import androidx.lifecycle.ViewModel
import com.kusitms.connectdog.signup.state.CertificationSideEffect
import com.kusitms.connectdog.signup.state.CertificationUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class CertificationViewModel @Inject constructor(

) : ContainerHost<CertificationUiState, CertificationSideEffect>, ViewModel() {
    override val container = container<CertificationUiState, CertificationSideEffect>(CertificationUiState.empty())
    private val state: CertificationUiState
        get() = container.stateFlow.value

    fun onNameChanged(name: String) = intent {
        reduce { state.copy(name = name) }
        enableNextButton()
    }

    fun onPhoneNumberChanged(phoneNumber: String) = intent {
        if (phoneNumber.length <= 11) reduce { state.copy(phoneNumber = phoneNumber) }
        enableNextButton()
    }

    fun onChangeCertificationNumber(certificationNumber: String) = intent {
        if (certificationNumber.length <= 6) reduce { state.copy(certificationNumber = certificationNumber) }
        enableCertificationButton()
    }

    fun onNextClick(
        onSendMessageClick: (String) -> Unit,
        onVerifyCodeClick: (String, (Boolean) -> Unit) -> Unit
    ) {
        if (!state.isSendCertificationNumber) {
            sendCertificationNumber(onSendMessageClick)
        } else {
            checkCertificationNumber(onVerifyCodeClick)
        }
    }

    private fun enableNextButton() = intent {
        if (state.phoneNumber.length == 11 && state.name.isNotEmpty()) {
            reduce { state.copy(enableNext = true) }
        } else {
            reduce { state.copy(enableNext = false) }
        }
    }

    private fun enableCertificationButton() = intent {
        if (state.certificationNumber.length == 6) {
            reduce { state.copy(enableCertification = true) }
        } else {
            reduce { state.copy(enableCertification = false) }
        }
    }

    private fun checkCertificationNumber(onVerifyCodeClick: (String, (Boolean) -> Unit) -> Unit) {
        onVerifyCodeClick(state.certificationNumber) { isCertified ->
            if (isCertified) intent { postSideEffect(CertificationSideEffect.NavigateToProfile) }
            intent { reduce { state.copy(isCertified = isCertified) } }
        }
    }

    private fun sendCertificationNumber(sendMessage: (String) -> Unit) {
        sendMessage(state.phoneNumber)
        intent { reduce { state.copy(isSendCertificationNumber = true) } }
        updateBottomButtonText()
    }

    private fun updateBottomButtonText() = intent { reduce { state.copy(bottomButtonText = "인증 확인") } }
}
