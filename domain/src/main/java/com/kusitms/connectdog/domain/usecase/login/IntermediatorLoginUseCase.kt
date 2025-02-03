package com.kusitms.connectdog.domain.usecase.login

import com.kusitms.connectdog.domain.repository.LoginRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class IntermediatorLoginUseCase @Inject constructor(
    val repository: LoginRepository
) {
    suspend operator fun invoke(
        email: String,
        password: String
    ) = repository.intermediatorNormalLogin(email, password)
}