package com.kusitms.connectdog.domain.usecase.signup

import com.kusitms.connectdog.domain.repository.DataStoreRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetSocialTokenUseCase @Inject constructor(
    val repository: DataStoreRepository
) {
    suspend operator fun invoke() = repository.getSocialToken()
}