package com.kusitms.connectdog.core.data.repository

import com.kusitms.connectdog.core.data.api.model.AdditionalAuthBody
import com.kusitms.connectdog.core.data.api.model.volunteer.ApplyBody

interface ApplyRepository {
    suspend fun postApplyVolunteer(postId: Long, applyBody: ApplyBody)
    suspend fun postAdditionalAuth(additionalAuthBody: AdditionalAuthBody)
    suspend fun getAdditionalAuth(): AdditionalAuthBody
}
