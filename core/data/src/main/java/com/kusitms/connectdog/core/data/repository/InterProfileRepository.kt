package com.kusitms.connectdog.core.data.repository

import com.kusitms.connectdog.core.data.api.model.intermediator.InterProfileInfoResponse
import com.kusitms.connectdog.core.model.Announcement
import com.kusitms.connectdog.core.model.Review

interface InterProfileRepository {
    suspend fun getInterReview(
        page: Int?,
        size: Int?,
    ): List<Review>

    suspend fun getInterFinding(
        page: Int,
        size: Int?,
    ): List<Announcement>

    suspend fun getInterProfileInfo(): InterProfileInfoResponse
}
