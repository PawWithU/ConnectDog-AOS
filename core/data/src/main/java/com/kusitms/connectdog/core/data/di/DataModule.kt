package com.kusitms.connectdog.core.data.di

import com.kusitms.connectdog.core.data.repository.DefaultExampleRepository
import com.kusitms.connectdog.core.data.repository.ExampleRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
internal abstract class DataModule {
    @Binds
    abstract fun bindsExampleRepository(repository: DefaultExampleRepository): ExampleRepository
}
