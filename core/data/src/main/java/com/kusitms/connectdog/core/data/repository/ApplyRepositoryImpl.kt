package com.kusitms.connectdog.core.data.repository

import com.kusitms.connectdog.core.data.api.ApiService
import com.kusitms.connectdog.core.data.api.model.AdditionalAuthBody
import com.kusitms.connectdog.core.data.api.model.volunteer.ApplyBody
import javax.inject.Inject

internal class ApplyRepositoryImpl @Inject constructor(
    private val api: ApiService
) : ApplyRepository {
    override suspend fun postApplyVolunteer(postId: Long, applyBody: ApplyBody) {
        api.postApplyVolunteer(postId, applyBody)
    }

    override suspend fun postAdditionalAuth(additionalAuthBody: AdditionalAuthBody) {
        api.postAdditionalAuth(additionalAuthBody)
    }

    override suspend fun getAdditionalAuth(): AdditionalAuthBody {
        return api.getAdditionalAuth()
    }
}
