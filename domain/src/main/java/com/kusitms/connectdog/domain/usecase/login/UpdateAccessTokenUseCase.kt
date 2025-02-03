package com.kusitms.connectdog.domain.usecase.login

import com.kusitms.connectdog.domain.repository.DataStoreRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UpdateAccessTokenUseCase @Inject constructor(
    val repository: DataStoreRepository
) {
    suspend operator fun invoke(accessToken: String) = repository.updateAccessToken(accessToken)
}