package com.kusitms.connectdog.domain.usecase.login

import com.kusitms.connectdog.domain.repository.DataStoreRepository
import javax.inject.Inject
import javax.inject.Singleton

enum class AppMode {
    LOGIN,
    VOLUNTEER,
    INTERMEDIATOR
}

@Singleton
class UpdateAppModeUseCase @Inject constructor(
    val repository: DataStoreRepository
) {
    suspend operator fun invoke(appMode: AppMode) = repository.updateAppMode(appMode)
}