package com.kusitms.connectdog.core.data.di

import com.kusitms.connectdog.core.data.api.ApiService
import com.kusitms.connectdog.core.data.api.InterApiService
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Protocol
import okhttp3.Response
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object ApiModule {
    private const val BASE_URL = "https://dev-api.connectdog.site"

    private val networkInterceptor: Interceptor = Interceptor { chain ->
        val request = chain.request()
        val jwt = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJBY2Nlc3NUb2tlbiIsInJvbGVOYW1lIjoiVk9MVU5URUVSIiwiaWQiOjEsImV4cCI6MTcwMTY4OTU0NX0.Icz7auxDCkiUSaCaEQYzMibizObIrOniP5WmjX8cn54T56kA_i2bfG9IxUne3exWdFGq7DPWp7i-prpq5LgfXw"
        try {
            chain.proceed(
                request.newBuilder()
                    .addHeader(
                        "Authorization",
                        "Bearer $jwt"
                    ) // todo
                    .build()
            )
        } catch (e: Exception) {
            Response.Builder()
                .request(request)
                .protocol(Protocol.HTTP_2)
                .code(1001)
                .message(e.message ?: "")
                .body(ResponseBody.create(null, e.message ?: ""))
                .build()
        }
    }

    @Provides
    @Singleton
    fun provideOkhttpClient(): OkHttpClient {
        val httpLoggingInterceptor =
            HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }
        return OkHttpClient.Builder()
            .addInterceptor(networkInterceptor)
            .addNetworkInterceptor(httpLoggingInterceptor)
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun provideMoshi(): Moshi {
        return Moshi.Builder()
            .addLast(KotlinJsonAdapterFactory())
            .build()
    }

    @Provides
    @Singleton
    fun provideApiService(
        okHttpClient: OkHttpClient,
        moshi: Moshi
    ): ApiService {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(okHttpClient).build()
            .create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideIntermediatorApiService(
        okHttpClient: OkHttpClient,
        moshi: Moshi
    ): InterApiService {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(okHttpClient).build()
            .create(InterApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideJson(): Json =
        Json {
            ignoreUnknownKeys = true
        }
}
