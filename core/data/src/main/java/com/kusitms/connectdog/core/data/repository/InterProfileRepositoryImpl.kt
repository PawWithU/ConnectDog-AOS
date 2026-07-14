package com.kusitms.connectdog.core.data.repository

import com.kusitms.connectdog.core.data.api.InterApiService
import com.kusitms.connectdog.core.data.api.model.intermediator.InterProfileInfoResponse
import com.kusitms.connectdog.core.data.mapper.intermediator.toData
import com.kusitms.connectdog.core.data.mapper.toData
import com.kusitms.connectdog.core.model.Announcement
import com.kusitms.connectdog.core.model.Review
import javax.inject.Inject

internal class InterProfileRepositoryImpl
    @Inject
    constructor(
        private val apiService: InterApiService,
    ) : InterProfileRepository {
        override suspend fun getInterReview(
            page: Int?,
            size: Int?,
        ): List<Review> {
            return apiService.getIntermediatorReview(page, size).map { it.toData() }
        }

        override suspend fun getInterFinding(
            page: Int,
            size: Int?,
        ): List<Announcement> {
            return apiService.getFindingApplication(page, size).map { it.toData() }
        }

        override suspend fun getInterProfileInfo(): InterProfileInfoResponse {
            return apiService.getIntermediatorInfo()
        }
    }
