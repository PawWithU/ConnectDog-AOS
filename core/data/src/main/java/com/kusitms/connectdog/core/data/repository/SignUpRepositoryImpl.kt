package com.kusitms.connectdog.core.data.repository

import com.kusitms.connectdog.core.data.api.ApiService
import com.kusitms.connectdog.core.data.api.model.IsDuplicateNicknameResponse
import com.kusitms.connectdog.core.data.api.model.volunteer.IsDuplicateNicknameBody
import javax.inject.Inject

internal class SignUpRepositoryImpl @Inject constructor(
    private val api: ApiService
) : SignUpRepository {
    override suspend fun postNickname(nickname: IsDuplicateNicknameBody): IsDuplicateNicknameResponse {
        return api.postNickname(nickname)
    }
}
