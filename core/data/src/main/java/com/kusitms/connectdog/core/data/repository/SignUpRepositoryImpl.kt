package com.kusitms.connectdog.core.data.repository

import com.kusitms.connectdog.core.data.api.ApiService
import com.kusitms.connectdog.core.data.api.InterApiService
import com.kusitms.connectdog.core.data.api.model.volunteer.PasswordCheckResponse
import com.kusitms.connectdog.core.model.signup.IsDuplicated
import com.kusitms.connectdog.core.model.signup.Name
import com.kusitms.connectdog.core.model.signup.Nickname
import com.kusitms.connectdog.core.model.signup.NormalVolunteerDetail
import com.kusitms.connectdog.core.model.signup.SocialVolunteerDetail
import com.kusitms.connectdog.domain.repository.SignUpRepository
import javax.inject.Inject

internal class SignUpRepositoryImpl @Inject constructor(
    private val volunteerApi: ApiService,
    private val intermediatorApi: InterApiService
) : SignUpRepository, com.kusitms.connectdog.core.data.repository.SignUpRepository {
    override suspend fun getVolunteerNicknameDuplication(
        nickname: String
    ): Result<IsDuplicated> = runCatching {
        val body = Nickname(nickname)
        return@runCatching volunteerApi.postNickname(body)
    }

    override suspend fun getIntermediatorNicknameDuplication(
        name: String
    ): Result<IsDuplicated> = runCatching {
        val body = Name(name)
        return@runCatching intermediatorApi.checkIsDuplicateName(body)
    }

    override suspend fun initNormalVolunteerSignUp(
        email: String,
        password: String,
        nickname: String,
        profileImageNum: Int,
        isOptionAgr: Boolean,
        phone: String,
        name: String
    ): Result<Unit> = runCatching {
        val body = NormalVolunteerDetail(
            email = email,
            password = password,
            nickname = nickname,
            profileImageNum = profileImageNum,
            isOptionAgr = isOptionAgr,
            phone = phone,
            name = name
        )
        volunteerApi.postNormalVolunteerSignUp(body)
    }

    override suspend fun initSocialVolunteerSignUp(
        nickname: String,
        profileImageNum: Int,
        isOptionAgr: Boolean,
        phone: String,
        name: String
    ): Result<Unit> = runCatching {
        val body = SocialVolunteerDetail(
            nickname = nickname,
            profileImageNum = profileImageNum,
            isOptionAgr = isOptionAgr,
            phone = phone,
            name = name
        )
        return@runCatching volunteerApi.postSocialVolunteerSignUp(body)
    }

//    override suspend fun postIntermediatorSignUp(signUp: IntermediatorSignUpBody, image: File) {
//        val jsonBody = RequestBody.create(
//            "application/json; charset=utf-8".toMediaTypeOrNull(),
//            Gson().toJson(signUp)
//        )
//
//        val fileBody = RequestBody.create("multipart/form-data".toMediaTypeOrNull(), image)
//        val file = MultipartBody.Part.createFormData("profileImage", image.name, fileBody)
//
//        return intermediatorApi.intermediatorSignUp(jsonBody, file)
//    }
//
//    override suspend fun getVolunteerPhoneNumberDuplicated(body: IsDuplicatePhoneNumberBody): IsDuplicatePhoneNumberResponse {
//        return volunteerApi.getIsDuplicatePhoneNumber(body)
//    }
//
//    override suspend fun getInterMediatorPhoneNumberDuplicated(body: IsDuplicatePhoneNumberBody): IsDuplicatePhoneNumberResponse {
//        return intermediatorApi.getIsDuplicatePhoneNumber(body)
//    }
//
//    override suspend fun checkVolunteerPassword(password: String): PasswordCheckResponse {
//        val body = PasswordDto(password)
//        return volunteerApi.checkVolunteerPassword(body)
//    }
//
//    override suspend fun checkInterPassword(password: String): PasswordCheckResponse {
//        val body = PasswordDto(password)
//        return intermediatorApi.checkInterPassword(body)
//    }
//
//    override suspend fun changeVolunteerPassword(password: String) {
//        val body = PasswordDto(password)
//        volunteerApi.changeVolunteerPassword(body)
//    }
//
//    override suspend fun changeInterPassword(password: String) {
//        val body = PasswordDto(password)
//        intermediatorApi.changeInterPassword(body)
//    }

    override suspend fun checkVolunteerPassword(password: String): PasswordCheckResponse {
        TODO("Not yet implemented")
    }

    override suspend fun checkInterPassword(password: String): PasswordCheckResponse {
        TODO("Not yet implemented")
    }

    override suspend fun changeVolunteerPassword(password: String) {
        TODO("Not yet implemented")
    }

    override suspend fun changeInterPassword(password: String) {
        TODO("Not yet implemented")
    }
}
