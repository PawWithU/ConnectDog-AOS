package com.kusitms.connectdog.core.data.di

import com.kusitms.connectdog.core.data.api.ApiService
import com.kusitms.connectdog.core.data.repository.DetailRepository
import com.kusitms.connectdog.core.data.repository.DetailRepositoryImpl
import com.kusitms.connectdog.core.data.repository.HomeRepository
import com.kusitms.connectdog.core.data.repository.HomeRepositoryImpl
import com.kusitms.connectdog.core.data.repository.LoginRepository
import com.kusitms.connectdog.core.data.repository.LoginRepositoryImpl
import com.kusitms.connectdog.core.data.repository.ManagementRepository
import com.kusitms.connectdog.core.data.repository.ManagementRepositoryImpl
import com.kusitms.connectdog.core.data.repository.MyPageRepository
import com.kusitms.connectdog.core.data.repository.MyPageRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
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
    fun provideLoginRepository(apiService: ApiService): LoginRepository {
        return LoginRepositoryImpl(apiService)
    }

    @Provides
    @Singleton
    fun provideManagementRepository(apiService: ApiService): ManagementRepository {
        return ManagementRepositoryImpl(apiService)
    }

    @Provides
    @Singleton
    fun provideMyPageRepository(apiService: ApiService): MyPageRepository {
        return MyPageRepositoryImpl(apiService)
    }

    @Provides
    @Singleton
    fun provideNoticeRepository(apiService: ApiService): DetailRepository {
        return DetailRepositoryImpl(apiService)
    }
}
