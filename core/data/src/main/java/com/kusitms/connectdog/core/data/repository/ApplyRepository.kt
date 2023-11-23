package com.kusitms.connectdog.core.data.repository

import com.kusitms.connectdog.core.data.api.model.volunteer.ApplyBody

interface ApplyRepository {
    suspend fun postApplyVolunteer(postId: Long, applyBody: ApplyBody)
}
