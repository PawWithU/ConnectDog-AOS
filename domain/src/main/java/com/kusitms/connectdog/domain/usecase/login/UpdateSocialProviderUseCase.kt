package com.kusitms.connectdog.domain.usecase.login

import com.kusitms.connectdog.domain.repository.DataStoreRepository
import javax.inject.Inject
import javax.inject.Singleton

enum class SocialLoginProvider {
    KAKAO, NAVER
}

@Singleton
class UpdateSocialProviderUseCase @Inject constructor(
    val repository: DataStoreRepository
) {
    suspend operator fun invoke(provider: SocialLoginProvider) = repository.updateSocialLoginProvider(provider)
}