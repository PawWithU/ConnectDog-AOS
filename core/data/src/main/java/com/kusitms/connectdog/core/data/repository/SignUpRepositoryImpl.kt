package com.kusitms.connectdog.core.data.repository

import com.kusitms.connectdog.core.data.api.ApiService
import com.kusitms.connectdog.core.data.api.model.IsDuplicateNicknameResponse
import com.kusitms.connectdog.core.data.api.model.volunteer.EmailCertificationBody
import com.kusitms.connectdog.core.data.api.model.volunteer.EmailCertificationResponseItem
import com.kusitms.connectdog.core.data.api.model.volunteer.IsDuplicateNicknameBody
import com.kusitms.connectdog.core.data.api.model.volunteer.NormalVolunteerSignUpBody
import com.kusitms.connectdog.core.data.api.model.volunteer.SocialVolunteerSignUpBody
import javax.inject.Inject

internal class SignUpRepositoryImpl @Inject constructor(
    private val api: ApiService
) : SignUpRepository {
    override suspend fun postNickname(nickname: IsDuplicateNicknameBody): IsDuplicateNicknameResponse {
        return api.postNickname(nickname)
    }

    override suspend fun postEmail(email: EmailCertificationBody): EmailCertificationResponseItem {
        return api.postEmail(email)
    }

    override suspend fun postNormalVolunteerSignUp(signUp: NormalVolunteerSignUpBody) {
        return api.postNormalVolunteerSignUp(signUp)
    }

    override suspend fun postSocialVolunteerSignUp(signUp: SocialVolunteerSignUpBody) {
        return api.postSocialVolunteerSignUp(signUp)
    }
}
