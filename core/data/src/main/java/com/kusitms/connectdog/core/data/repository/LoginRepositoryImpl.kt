package com.kusitms.connectdog.core.data.repository

import com.kusitms.connectdog.core.data.api.ApiService
import com.kusitms.connectdog.core.data.api.InterApiService
import com.kusitms.connectdog.core.model.login.LoginResult
import com.kusitms.connectdog.core.model.login.NormalLogin
import com.kusitms.connectdog.core.model.login.SocialLogin
import com.kusitms.connectdog.domain.repository.LoginRepository
import javax.inject.Inject

internal class LoginRepositoryImpl @Inject constructor(
    private val volunteerApi: ApiService,
    private val intermediatorApi: InterApiService
) : LoginRepository {
    override suspend fun volunteerNormalLogin(
        email: String,
        password: String
    ): Result<LoginResult> = runCatching {
        val body = NormalLogin(email, password)
        return@runCatching volunteerApi.normalLogin(body)
    }

    override suspend fun intermediatorNormalLogin(
        email: String,
        password: String
    ): Result<LoginResult> = runCatching {
        val body = NormalLogin(email, password)
        return@runCatching intermediatorApi.normalLogin(body)
    }

    override suspend fun socialLogin(
        accessToken: String,
        provider: String
    ): Result<LoginResult> = runCatching{
        val body = SocialLogin(accessToken, provider)
        return@runCatching volunteerApi.postSocialLoginData(body)
    }

    override suspend fun logout() = runCatching {
        return@runCatching volunteerApi.logout()
    }
}
