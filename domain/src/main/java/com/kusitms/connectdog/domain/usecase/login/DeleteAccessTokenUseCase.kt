package com.kusitms.connectdog.domain.usecase.login

import com.kusitms.connectdog.domain.repository.DataStoreRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DeleteAccessTokenUseCase @Inject constructor(
    val repository: DataStoreRepository
) {
    suspend operator fun invoke() = repository.deleteAccessToken()
}