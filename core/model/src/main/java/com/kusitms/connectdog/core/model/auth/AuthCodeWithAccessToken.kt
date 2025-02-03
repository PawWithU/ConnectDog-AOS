package com.kusitms.connectdog.core.model.auth

data class AuthCodeWithAccessToken(
    val authCode: String,
    val accessToken: String
)
