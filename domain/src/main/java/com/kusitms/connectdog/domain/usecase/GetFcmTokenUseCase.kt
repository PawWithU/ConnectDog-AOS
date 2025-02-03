package com.kusitms.connectdog.domain.usecase

import com.kusitms.connectdog.domain.repository.DataStoreRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetFcmTokenUseCase @Inject constructor(
    val repository: DataStoreRepository
) {
    suspend operator fun invoke() = repository.getFcmToken()
}