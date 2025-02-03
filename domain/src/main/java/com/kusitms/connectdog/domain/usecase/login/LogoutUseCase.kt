package com.kusitms.connectdog.domain.usecase.login

import com.kusitms.connectdog.domain.repository.LoginRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LogoutUseCase @Inject constructor(
    val repository: LoginRepository
) {
    suspend operator fun invoke() = repository.logout()
}