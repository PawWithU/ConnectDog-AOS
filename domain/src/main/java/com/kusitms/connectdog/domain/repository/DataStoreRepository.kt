package com.kusitms.connectdog.domain.repository

import com.kusitms.connectdog.domain.usecase.login.AppMode
import com.kusitms.connectdog.domain.usecase.login.SocialLoginProvider
import kotlinx.coroutines.flow.Flow

interface DataStoreRepository {
    suspend fun updateAccessToken(accessToken: String)
    suspend fun updateRefreshToken(refreshToken: String)
    suspend fun updateAppMode(appMode: AppMode)
    suspend fun updateFcmToken(fcmToken: String)
    suspend fun updateSocialToken(token: String)
    suspend fun updateSocialLoginProvider(provider: SocialLoginProvider)

    suspend fun getAccessToken(): Flow<String?>
    suspend fun getRefreshToken(): Flow<String?>
    suspend fun getFcmToken(): Flow<String?>
    suspend fun getAppMode(): Flow<AppMode>
    suspend fun getSocialToken(): Flow<String?>
    suspend fun getSocialLoginProvider(): Flow<SocialLoginProvider?>

    suspend fun deleteAccessToken()
}