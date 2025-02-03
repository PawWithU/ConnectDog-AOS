package com.kusitms.connectdog.feature.login.state

import com.kusitms.connectdog.core.util.emptyString

data class EmailAuthForPasswordResetUiState(
    val email: String,
    val inputAuthCode: String,
    val authCode: String,
    val isSendAuthCode: Boolean,
    val isValidEmail: Boolean,
    val isEmailError: Boolean,
    val accessToken: String,
    val bottomButtonText: String,
    val enableNext: Boolean,
    val isAuthCodeError: Boolean,
) {
    companion object {
        fun empty() = EmailAuthForPasswordResetUiState(
            email = emptyString(),
            inputAuthCode = emptyString(),
            authCode = emptyString(),
            accessToken = emptyString(),
            isSendAuthCode = false,
            isValidEmail = false,
            bottomButtonText = "인증 요청",
            enableNext = false,
            isEmailError = false,
            isAuthCodeError = false,
        )
    }
}

sealed class EmailAuthForPasswordResetSideEffect {
    object NavigateToFail: EmailAuthForPasswordResetSideEffect()
    object NavigateToPasswordReset: EmailAuthForPasswordResetSideEffect()
}