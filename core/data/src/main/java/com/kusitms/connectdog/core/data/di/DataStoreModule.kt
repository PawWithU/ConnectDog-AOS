package com.kusitms.connectdog.core.data.di

import android.content.Context
import com.kusitms.connectdog.core.data.repository.DataStoreRepositoryImpl
import com.kusitms.connectdog.domain.repository.DataStoreRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
internal object DataStoreModule {
    @Provides
    @Singleton
    fun provideDataStoreRepository(
        @ApplicationContext context: Context,
    ): DataStoreRepository {
        return DataStoreRepositoryImpl(context)
    }
}
