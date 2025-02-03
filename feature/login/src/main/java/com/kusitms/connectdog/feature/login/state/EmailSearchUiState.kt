package com.kusitms.connectdog.feature.login.state

import com.kusitms.connectdog.core.util.emptyString

data class EmailSearchUiState(
    val phoneNumber: String,
    val isSendAuthCode: Boolean,
    val authCode: String,
    val email: String?,
    val enableNext: Boolean,
    val bottomButtonText: String,
    val isAuthCodeError: Boolean?
) {
    companion object {
        fun empty() = EmailSearchUiState(
            phoneNumber = emptyString(),
            authCode = emptyString(),
            isSendAuthCode = false,
            email = null,
            enableNext = false,
            bottomButtonText = "인증 요청",
            isAuthCodeError = null
        )
    }
}

sealed class EmailSearchSideEffect {
    object NavigateToEmailSearchResult: EmailSearchSideEffect()
}