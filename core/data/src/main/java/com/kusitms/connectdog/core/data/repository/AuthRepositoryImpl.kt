package com.kusitms.connectdog.core.data.repository

import com.kusitms.connectdog.core.data.api.ApiService
import com.kusitms.connectdog.core.data.api.InterApiService
import com.kusitms.connectdog.core.model.auth.Email
import com.kusitms.connectdog.core.model.auth.Phone
import com.kusitms.connectdog.core.model.auth.AuthCodeWithAccessToken
import com.kusitms.connectdog.core.model.auth.EmailAuthCode
import com.kusitms.connectdog.core.model.auth.PhoneNumberDuplication
import com.kusitms.connectdog.domain.repository.AuthRepository
import javax.inject.Inject

internal class AuthRepositoryImpl @Inject constructor(
    private val volunteerApi: ApiService,
    private val intermediatorApi: InterApiService
) : AuthRepository {
    override suspend fun getEmailAuthCode(
        email: String
    ): Result<EmailAuthCode> = runCatching {
        val body = Email(email)
        return@runCatching volunteerApi.getEmailAuthCode(body)
    }

    override suspend fun searchVolunteerEmail(
        phone: String
    ): Result<Email> = runCatching {
        val body = Phone(phone = phone)
        return@runCatching volunteerApi.searchVolunteerEmail(body)
    }

    override suspend fun searchIntermediatorEmail(
        phone: String
    ): Result<Email> = runCatching {
        val body = Phone(phone = phone)
        return@runCatching intermediatorApi.searchIntermediatorEmail(body)
    }

    override suspend fun emailAuthForVolunteerPasswordReset(
        email: String
    ): Result<AuthCodeWithAccessToken> = runCatching {
        val body = Email(email = email)
        return@runCatching volunteerApi.volunteerPasswordSearchAuth(body)
    }

    override suspend fun emailAuthForIntermediatorPasswordReset(
        email: String
    ): Result<AuthCodeWithAccessToken> = runCatching {
        val body = Email(email = email)
        return@runCatching intermediatorApi.emailAuthForPasswordReset(body)
    }

    override suspend fun getVolunteerPhoneNumberDuplication(
        phone: String
    ): Result<PhoneNumberDuplication> = runCatching {
        val body = Phone(phone = phone)
        return@runCatching volunteerApi.getPhoneNumberDuplication(body)
    }

    override suspend fun getIntermediatorPhoneNumberDuplication(
        phone: String
    ): Result<PhoneNumberDuplication> = runCatching {
        val body = Phone(phone = phone)
        return@runCatching intermediatorApi.getPhoneNumberDuplication(body)
    }
}