package com.kusitms.connectdog.core.data.repository

import com.kusitms.connectdog.core.data.api.ApiService
import com.kusitms.connectdog.core.data.api.model.LoginResponseItem
import com.kusitms.connectdog.core.data.api.model.NormalLoginBody
import com.kusitms.connectdog.core.data.api.model.SocialLoginBody
import javax.inject.Inject

internal class LoginRepositoryImpl @Inject constructor(
    private val api: ApiService
) : LoginRepository {
    override suspend fun postLoginData(
        loginBody: NormalLoginBody
    ): LoginResponseItem {
        return api.postLoginData(
            loginBody
        )
    }

    override suspend fun postSocialLoginData(
        socialLoginBody: SocialLoginBody
    ): LoginResponseItem {
        return api.postSocialLoginData(
            socialLoginBody
        )
    }

    override suspend fun postIntermediatorLoginData(loginBody: NormalLoginBody): LoginResponseItem {
        return api.postIntermediatorLoginData(loginBody)
    }
}
