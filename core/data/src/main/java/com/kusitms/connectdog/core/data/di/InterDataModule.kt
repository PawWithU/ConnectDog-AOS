package com.kusitms.connectdog.core.data.di

import com.kusitms.connectdog.core.data.api.InterApiService
import com.kusitms.connectdog.core.data.repository.InterHomeRepository
import com.kusitms.connectdog.core.data.repository.InterHomeRepositoryImpl
import com.kusitms.connectdog.core.data.repository.InterManagementRepository
import com.kusitms.connectdog.core.data.repository.InterManagementRepositoryImpl
import com.kusitms.connectdog.core.data.repository.InterProfileRepository
import com.kusitms.connectdog.core.data.repository.InterProfileRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
internal class InterDataModule {
    @Provides
    @Singleton
    fun provideInterHomeRepository(apiService: InterApiService): InterHomeRepository {
        return InterHomeRepositoryImpl(apiService)
    }

    @Provides
    @Singleton
    fun provideInterManagementRepository(apiService: InterApiService): InterManagementRepository {
        return InterManagementRepositoryImpl(apiService)
    }

    @Provides
    @Singleton
    fun provideInterProfileRepository(apiService: InterApiService): InterProfileRepository {
        return InterProfileRepositoryImpl(apiService)
    }
}
