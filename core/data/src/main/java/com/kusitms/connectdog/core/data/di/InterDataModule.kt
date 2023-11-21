package com.kusitms.connectdog.core.data.di

import com.kusitms.connectdog.core.data.api.InterApiService
import com.kusitms.connectdog.core.data.repository.InterManagementRepository
import com.kusitms.connectdog.core.data.repository.InterManagementRepositoryImpl
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
    fun provideInterManagementRepository(apiService: InterApiService): InterManagementRepository {
        return InterManagementRepositoryImpl(apiService)
    }
}
