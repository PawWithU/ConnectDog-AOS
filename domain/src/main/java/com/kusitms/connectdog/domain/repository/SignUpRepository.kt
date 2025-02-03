package com.kusitms.connectdog.domain.repository

import com.kusitms.connectdog.core.model.signup.IsDuplicated

interface SignUpRepository {
    suspend fun getVolunteerNicknameDuplication(nickname: String): Result<IsDuplicated>
    suspend fun getIntermediatorNicknameDuplication(nickname: String): Result<IsDuplicated>

    suspend fun initNormalVolunteerSignUp(
        email: String,
        password: String,
        nickname: String,
        profileImageNum: Int,
        isOptionAgr: Boolean = true,
        phone: String,
        name: String
    ): Result<Unit>

    suspend fun initSocialVolunteerSignUp(
        nickname: String,
        profileImageNum: Int,
        isOptionAgr: Boolean = true,
        phone: String,
        name: String
    ): Result<Unit>
}