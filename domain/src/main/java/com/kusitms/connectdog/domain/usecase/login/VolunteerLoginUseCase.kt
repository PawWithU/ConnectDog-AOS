package com.kusitms.connectdog.domain.usecase.login

import com.kusitms.connectdog.domain.repository.LoginRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class VolunteerLoginUseCase @Inject constructor(
    val repository: LoginRepository
) {
    suspend operator fun invoke(
        email: String,
        password: String
    ) = repository.volunteerNormalLogin(email, password)
}