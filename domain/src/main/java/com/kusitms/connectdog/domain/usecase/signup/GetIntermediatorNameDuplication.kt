package com.kusitms.connectdog.domain.usecase.signup

import com.kusitms.connectdog.domain.repository.SignUpRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetIntermediatorNameDuplication @Inject constructor(
    val repository: SignUpRepository
) {
    suspend operator fun invoke(name: String) = repository.getIntermediatorNicknameDuplication(name)
}