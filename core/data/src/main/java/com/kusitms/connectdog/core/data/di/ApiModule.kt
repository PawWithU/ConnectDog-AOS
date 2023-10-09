package com.kusitms.connectdog.core.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Converter
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApiModule {
    @Provides
    @Singleton
    fun provideOkhttpClient(): OkHttpClient = OkHttpClient.Builder().build()

//    @Provides
//    @Singleton
//    fun provideConverterFactory(
//        json: Json,
//    ): Converter.Factory {
//        return json.asConverterFactory("application/json".toMediaType())
//    }


}