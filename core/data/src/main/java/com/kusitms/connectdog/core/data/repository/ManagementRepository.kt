package com.kusitms.connectdog.core.data.repository

import com.kusitms.connectdog.core.data.api.model.volunteer.ReviewBody
import com.kusitms.connectdog.core.data.api.model.volunteer.ReviewDetailResponse
import com.kusitms.connectdog.core.model.Application
import com.kusitms.connectdog.core.model.ConnectDogResult
import com.kusitms.connectdog.core.model.Volunteer
import java.io.File

interface ManagementRepository {
    suspend fun getApplicationWaiting(
        page: Int? = 0,
        size: Int? = 5,
    ): List<Application>

    suspend fun getApplicationInProgress(
        page: Int? = 0,
        size: Int? = 5,
    ): List<Application>

    suspend fun getApplicationCompleted(
        page: Int? = 0,
        size: Int? = 5,
    ): List<Application>

    suspend fun getMyApplication(applicationId: Long): Volunteer

    suspend fun deleteMyApplication(applicationId: Long): ConnectDogResult

    suspend fun getReview(reviewId: Long): ReviewDetailResponse

    suspend fun postReview(
        postId: Long,
        body: ReviewBody,
        images: List<File>,
    )
}
