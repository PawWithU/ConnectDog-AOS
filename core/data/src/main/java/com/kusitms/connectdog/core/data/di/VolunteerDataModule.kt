package com.kusitms.connectdog.core.data.di

import com.kusitms.connectdog.core.data.api.ApiService
import com.kusitms.connectdog.core.data.api.InterApiService
import com.kusitms.connectdog.core.data.repository.ApplyRepository
import com.kusitms.connectdog.core.data.repository.ApplyRepositoryImpl
import com.kusitms.connectdog.core.data.repository.AuthRepositoryImpl
import com.kusitms.connectdog.core.data.repository.DetailRepository
import com.kusitms.connectdog.core.data.repository.DetailRepositoryImpl
import com.kusitms.connectdog.core.data.repository.HomeRepository
import com.kusitms.connectdog.core.data.repository.HomeRepositoryImpl
import com.kusitms.connectdog.core.data.repository.LoginRepositoryImpl
import com.kusitms.connectdog.core.data.repository.ManagementRepository
import com.kusitms.connectdog.core.data.repository.ManagementRepositoryImpl
import com.kusitms.connectdog.core.data.repository.MyPageRepository
import com.kusitms.connectdog.core.data.repository.MyPageRepositoryImpl
import com.kusitms.connectdog.core.data.repository.SignUpRepositoryImpl
import com.kusitms.connectdog.domain.repository.AuthRepository
import com.kusitms.connectdog.domain.repository.LoginRepository
import com.kusitms.connectdog.domain.repository.SignUpRepository
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
    fun provideLoginRepository(
        apiService: ApiService,
        intermediatorApi: InterApiService
    ): LoginRepository {
        return LoginRepositoryImpl(apiService, intermediatorApi)
    }

    @Provides
    @Singleton
    fun provideAuthRepository(
        apiService: ApiService,
        intermediatorApi: InterApiService
    ): AuthRepository {
        return AuthRepositoryImpl(apiService, intermediatorApi)
    }

    @Provides
    @Singleton
    fun provideSignUpRepository(
        volunteerApi: ApiService,
        intermediatorApi: InterApiService
    ): SignUpRepository {
        return SignUpRepositoryImpl(volunteerApi, intermediatorApi)
    }

    @Provides
    @Singleton
    fun provideTestRepository(
        volunteerApi: ApiService,
        intermediatorApi: InterApiService
    ): com.kusitms.connectdog.core.data.repository.SignUpRepository {
        return SignUpRepositoryImpl(volunteerApi, intermediatorApi)
    }

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

    @Provides
    @Singleton
    fun provideMyPageRepository(
        apiService: ApiService,
        intermediatorApi: InterApiService
    ): MyPageRepository {
        return MyPageRepositoryImpl(apiService, intermediatorApi)
    }

    @Provides
    @Singleton
    fun provideNoticeRepository(apiService: ApiService): DetailRepository {
        return DetailRepositoryImpl(apiService)
    }

    @Provides
    @Singleton
    fun provideApplyRepository(apiService: ApiService): ApplyRepository {
        return ApplyRepositoryImpl(apiService)
    }
}
