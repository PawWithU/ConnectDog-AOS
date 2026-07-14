package com.kusitms.connectdog.core.data.repository

import com.kusitms.connectdog.core.data.api.model.volunteer.PasswordCheckResponse

interface SignUpRepository {
    suspend fun checkVolunteerPassword(password: String): PasswordCheckResponse

    suspend fun checkInterPassword(password: String): PasswordCheckResponse

    suspend fun changeVolunteerPassword(password: String)

    suspend fun changeInterPassword(password: String)
}
