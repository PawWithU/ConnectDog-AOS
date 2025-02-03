package com.kusitms.connectdog.domain.usecase.login

import com.kusitms.connectdog.domain.repository.DataStoreRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UpdateRefreshTokenUseCase @Inject constructor(
    val repository: DataStoreRepository
) {
    suspend operator fun invoke(refreshToken: String) = repository.updateRefreshToken(refreshToken)
}