package com.kusitms.connectdog.core.data.di

import android.content.Context
import com.kusitms.connectdog.core.data.repository.DefaultExampleRepository
import com.kusitms.connectdog.core.data.repository.ExampleRepository
import com.kusitms.connectdog.core.data.repository.HomeRepository
import com.kusitms.connectdog.core.data.repository.HomeRepositoryImpl
import com.kusitms.connectdog.core.data.repository.LoginRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
internal abstract class DataModule {
    @Binds
    abstract fun bindsExampleRepository(repository: DefaultExampleRepository): ExampleRepository

    @Binds
    abstract fun bindsHomeRepository(repository: HomeRepositoryImpl): HomeRepository

    companion object {
        @Provides
        fun provideLoginRepository(
            @ApplicationContext context: Context
        ): LoginRepository {
            return LoginRepository(context)
        }
    }
}
