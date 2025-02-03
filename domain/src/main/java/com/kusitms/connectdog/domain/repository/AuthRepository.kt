package com.kusitms.connectdog.domain.repository

import com.kusitms.connectdog.core.model.auth.AuthCodeWithAccessToken
import com.kusitms.connectdog.core.model.auth.Email
import com.kusitms.connectdog.core.model.auth.EmailAuthCode
import com.kusitms.connectdog.core.model.auth.PhoneNumberDuplication


interface AuthRepository {
    suspend fun getEmailAuthCode(email: String): Result<EmailAuthCode>
    suspend fun searchVolunteerEmail(phone: String): Result<Email>
    suspend fun searchIntermediatorEmail(phone: String): Result<Email>

    suspend fun emailAuthForVolunteerPasswordReset(email: String): Result<AuthCodeWithAccessToken>
    suspend fun emailAuthForIntermediatorPasswordReset(email: String): Result<AuthCodeWithAccessToken>

    suspend fun getVolunteerPhoneNumberDuplication(phone: String): Result<PhoneNumberDuplication>
    suspend fun getIntermediatorPhoneNumberDuplication(phone: String): Result<PhoneNumberDuplication>
}