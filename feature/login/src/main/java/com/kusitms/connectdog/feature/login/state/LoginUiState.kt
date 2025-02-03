package com.kusitms.connectdog.feature.login.state

import com.kusitms.connectdog.core.util.SocialType
import com.kusitms.connectdog.core.util.UserType
import com.kusitms.connectdog.core.util.emptyString

data class LoginUiState(
    val email: String,
    val password: String,
    val isLoginSuccessful: Boolean?,
    val socialType: SocialType?,
    val socialToken: String
) {
    companion object {
        fun empty() = LoginUiState(
            email = emptyString(),
            password = emptyString(),
            isLoginSuccessful = null,
            socialType = null,
            socialToken = emptyString()
        )
    }
}

sealed class LoginSideEffect {
    object NavigateToHome: LoginSideEffect()
    data class NavigateToSignUp(val userType: UserType): LoginSideEffect()
}