package com.kusitms.connectdog.core.data.di

import android.content.Context
import com.kusitms.connectdog.core.data.api.ApiService
import com.kusitms.connectdog.core.data.repository.HomeRepository
import com.kusitms.connectdog.core.data.repository.HomeRepositoryImpl
import com.kusitms.connectdog.core.data.repository.LoginRepository
import com.kusitms.connectdog.core.data.repository.ManagementRepository
import com.kusitms.connectdog.core.data.repository.ManagementRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
internal class VolunteerDataModule {

    @Provides
    @Singleton
    fun provideHomeRepository(apiService: ApiService): HomeRepository {
        return HomeRepositoryImpl(apiService)
    }

    @Provides
    @Singleton
    fun provideManagementRepository(apiService: ApiService): ManagementRepository {
        return ManagementRepositoryImpl(apiService)
    }

    companion object {
        @Provides
        fun provideLoginRepository(
            @ApplicationContext context: Context
        ): LoginRepository {
            return LoginRepository(context)
        }
    }
}
