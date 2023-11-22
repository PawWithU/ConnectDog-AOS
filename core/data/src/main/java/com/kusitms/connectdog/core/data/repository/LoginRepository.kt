package com.kusitms.connectdog.core.data.repository

import com.kusitms.connectdog.core.data.api.model.LoginResponseItem
import com.kusitms.connectdog.core.data.api.model.NormalLoginBody
import com.kusitms.connectdog.core.data.api.model.SocialLoginBody

interface LoginRepository {
    suspend fun postLoginData(
        loginBody: NormalLoginBody
    ): LoginResponseItem

    suspend fun postSocialLoginData(
        socialLoginBody: SocialLoginBody
    ): LoginResponseItem
}
