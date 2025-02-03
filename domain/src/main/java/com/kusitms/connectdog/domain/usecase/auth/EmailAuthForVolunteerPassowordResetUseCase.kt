package com.kusitms.connectdog.domain.usecase.auth

import com.kusitms.connectdog.domain.repository.AuthRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EmailAuthForVolunteerPasswordResetUseCase @Inject constructor(
    val repository: AuthRepository
) {
    suspend operator fun invoke(email: String) = repository.emailAuthForVolunteerPasswordReset(email)
}