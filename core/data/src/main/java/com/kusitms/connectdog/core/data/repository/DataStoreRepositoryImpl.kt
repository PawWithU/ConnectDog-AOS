package com.kusitms.connectdog.core.data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.kusitms.connectdog.domain.repository.DataStoreRepository
import com.kusitms.connectdog.domain.usecase.login.AppMode
import com.kusitms.connectdog.domain.usecase.login.SocialLoginProvider
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import javax.inject.Inject

object PreferenceKeys {
    val accessToken = stringPreferencesKey("access_token")
    val refreshToken = stringPreferencesKey("refresh_token")
    val socialToken = stringPreferencesKey("social_token")
    val fcmToken = stringPreferencesKey("fcm_token")
    val appMode = stringPreferencesKey("app_mode")
    val socialProvider = stringPreferencesKey("social_provider")
}

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "ConnectDogDataStore")

class DataStoreRepositoryImpl
    @Inject
    constructor(
        @ApplicationContext private val context: Context,
    ) : DataStoreRepository {
        override suspend fun updateAccessToken(accessToken: String) {
            context.dataStore.edit { preferences ->
                preferences[PreferenceKeys.accessToken] = accessToken
            }
        }

        override suspend fun updateRefreshToken(refreshToken: String) {
            context.dataStore.edit { preferences ->
                preferences[PreferenceKeys.accessToken] = refreshToken
            }
        }

        override suspend fun updateAppMode(appMode: AppMode) {
            context.dataStore.edit { preferences ->
                preferences[PreferenceKeys.appMode] = appMode.toString()
            }
        }

        override suspend fun updateFcmToken(fcmToken: String) {
            context.dataStore.edit { preferences ->
                preferences[PreferenceKeys.fcmToken] = fcmToken
            }
        }

        override suspend fun updateSocialToken(token: String) {
            context.dataStore.edit { preferences ->
                preferences[PreferenceKeys.socialToken] = token
            }
        }

        override suspend fun updateSocialLoginProvider(provider: SocialLoginProvider) {
            context.dataStore.edit { preferences ->
                preferences[PreferenceKeys.socialProvider] = provider.toString()
            }
        }

        override suspend fun getFcmToken(): Flow<String?> =
            context.dataStore.data.map { preferences ->
                preferences[PreferenceKeys.fcmToken]
            }

        override suspend fun getRefreshToken(): Flow<String?> =
            context.dataStore.data.map { preferences ->
                preferences[PreferenceKeys.refreshToken]
            }

        override suspend fun getAccessToken(): Flow<String?> =
            context.dataStore.data.map { preferences ->
                preferences[PreferenceKeys.accessToken]
            }

        override suspend fun getAppMode(): Flow<AppMode> =
            context.dataStore.data
                .map { preferences ->
                    AppMode.valueOf(preferences[PreferenceKeys.appMode].toString())
                }
                .catch {
                    emit(AppMode.LOGIN)
                }

        override suspend fun getSocialToken(): Flow<String?> =
            context.dataStore.data.map { preferences ->
                preferences[PreferenceKeys.socialToken]
            }

        override suspend fun getSocialLoginProvider(): Flow<SocialLoginProvider?> {
            TODO("Not yet implemented")
        }

        override suspend fun deleteAccessToken() {
            context.dataStore.edit { preferences ->
                preferences[PreferenceKeys.fcmToken] = ""
            }
        }
    }
