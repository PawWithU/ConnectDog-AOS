package com.kusitms.connectdog.domain.usecase.signup

import com.kusitms.connectdog.domain.repository.DataStoreRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetSocialProviderUseCase @Inject constructor(
    val repository: DataStoreRepository
) {
    suspend operator fun invoke() = repository.getSocialLoginProvider()
}