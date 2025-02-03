package com.kusitms.connectdog.feature.login.state

import com.kusitms.connectdog.core.util.emptyString

data class PasswordResetUiState(
    val password: String,
    val passwordCheck: String,
    val isValidPassword: Boolean?
) {
    companion object {
        fun empty() = PasswordResetUiState(
            password = emptyString(),
            passwordCheck = emptyString(),
            isValidPassword = null
        )
    }
}

sealed class PasswordResetSideEffect