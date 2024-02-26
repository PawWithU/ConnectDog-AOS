package com.kusitms.connectdog.core.data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.kusitms.connectdog.core.util.AppMode
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import javax.inject.Inject

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "TokenDataStore")

@ActivityRetainedScoped
class DataStoreRepository @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private object PreferenceKeys {
        val accessToken = stringPreferencesKey("access_token")
        val refreshToken = stringPreferencesKey("refresh_token")
        val appMode = stringPreferencesKey("app_mode")
    }

    suspend fun saveAccessToken(accessToken: String) {
        context.dataStore.edit { preferences ->
            preferences[PreferenceKeys.accessToken] = accessToken
        }
    }

    suspend fun saveRefreshToken(refreshToken: String) {
        context.dataStore.edit { preferences ->
            preferences[PreferenceKeys.refreshToken] = refreshToken
        }
    }

    suspend fun saveAppMode(appMode: AppMode) {
        context.dataStore.edit { preferences ->
            preferences[PreferenceKeys.appMode] = appMode.name
        }
    }

    val accessTokenFlow: Flow<String?> = context.dataStore.data
        .map { preferences ->
            preferences[PreferenceKeys.accessToken]
        }

    val appModeFlow: Flow<AppMode> = context.dataStore.data
        .map { preferences ->
            AppMode.valueOf(preferences[PreferenceKeys.appMode].toString())
        }
        .catch {
            emit(AppMode.LOGIN)
        }

    val refreshTokenFlow: Flow<String?> = context.dataStore.data
        .map { preferences ->
            preferences[PreferenceKeys.accessToken]
        }
}
