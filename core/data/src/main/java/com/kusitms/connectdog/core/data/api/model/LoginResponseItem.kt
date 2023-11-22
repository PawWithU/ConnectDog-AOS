package com.kusitms.connectdog.core.data.api.model

data class LoginResponseItem(
    val accessToken: String,
    val refreshToken: String,
    val roleName: String
)
