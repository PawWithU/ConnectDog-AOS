package com.kusitms.connectdog.domain.usecase.signup

import com.kusitms.connectdog.domain.repository.SignUpRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class InitSocialVolunteerSignUpUseCase @Inject constructor(
    val repository: SignUpRepository
) {
    suspend operator fun invoke(
        email: String,
        password: String,
        nickname: String,
        profileImageNum: Int,
        isOptionAgr: Boolean = true,
        phone: String,
        name: String
    ) = repository.initNormalVolunteerSignUp(
        email = email,
        password = password,
        nickname = nickname,
        profileImageNum = profileImageNum,
        isOptionAgr = isOptionAgr,
        phone = phone,
        name = name
    )
}