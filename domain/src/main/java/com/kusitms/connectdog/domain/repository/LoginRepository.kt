package com.kusitms.connectdog.domain.repository

import com.kusitms.connectdog.core.model.login.LoginResult

interface LoginRepository {
    suspend fun volunteerNormalLogin(
        email: String,
        password: String
    ): Result<LoginResult>

    suspend fun socialLogin(
        accessToken: String,
        provider: String
    ): Result<LoginResult>

    suspend fun intermediatorNormalLogin(
        email: String,
        password: String
    ): Result<LoginResult>

    suspend fun logout(): Result<Unit>
}
