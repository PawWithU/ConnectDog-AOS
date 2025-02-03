package com.kusitms.connectdog.domain.usecase.auth

import com.kusitms.connectdog.domain.repository.AuthRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetIntermediatorPhoneNumberDuplicationUseCase @Inject constructor(
    val repository: AuthRepository
) {
    suspend operator fun invoke(phone: String) = repository.getIntermediatorPhoneNumberDuplication(phone)
}