package com.kusitms.connectdog.core.model.login

data class LoginResult(
    val accessToken: String,
    val refreshToken: String,
    val roleName: String
)
