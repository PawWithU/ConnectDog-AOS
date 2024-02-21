package com.kusitms.connectdog.core.data.repository

import com.kusitms.connectdog.core.data.api.model.IsDuplicateNicknameResponse
import com.kusitms.connectdog.core.data.api.model.volunteer.IsDuplicateNicknameBody

interface SignUpRepository {
    suspend fun postNickname(nickname: IsDuplicateNicknameBody): IsDuplicateNicknameResponse
}