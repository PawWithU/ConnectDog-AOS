package com.kusitms.connectdog.core.data.repository

import com.kusitms.connectdog.core.data.api.model.IsDuplicateNicknameResponse
import com.kusitms.connectdog.core.data.api.model.volunteer.EmailCertificationBody
import com.kusitms.connectdog.core.data.api.model.volunteer.EmailCertificationResponseItem
import com.kusitms.connectdog.core.data.api.model.volunteer.IsDuplicateNicknameBody

interface SignUpRepository {
    suspend fun postNickname(nickname: IsDuplicateNicknameBody): IsDuplicateNicknameResponse
    suspend fun postEmail(email: EmailCertificationBody): EmailCertificationResponseItem
}
