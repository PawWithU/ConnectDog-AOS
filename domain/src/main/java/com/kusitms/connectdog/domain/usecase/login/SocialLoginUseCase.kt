package com.kusitms.connectdog.domain.usecase.login

import com.kusitms.connectdog.domain.repository.LoginRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SocialLoginUseCase @Inject constructor(
    val repository: LoginRepository
) {
    suspend operator fun invoke(
        accessToken: String,
        provider: String
    ) = repository.socialLogin(accessToken, provider)
}