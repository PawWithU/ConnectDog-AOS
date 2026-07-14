package com.kusitms.connectdog.signup.state

import com.kusitms.connectdog.core.util.emptyString

data class CertificationUiState(
    val name: String,
    val phoneNumber: String,
    val certificationNumber: String,
    val isSendCertificationNumber: Boolean,
    val isCertified: Boolean,
    val enableNext: Boolean,
    val bottomButtonText: String,
    val enableCertification: Boolean,
) {
    companion object {
        fun empty() =
            CertificationUiState(
                name = emptyString(),
                phoneNumber = emptyString(),
                isSendCertificationNumber = false,
                isCertified = false,
                enableNext = false,
                bottomButtonText = "다음",
                enableCertification = false,
                certificationNumber = emptyString(),
            )
    }
}

sealed class CertificationSideEffect {
    object NavigateToProfile : CertificationSideEffect()
}
