package com.kusitms.connectdog.core.data.repository

import com.google.gson.Gson
import com.kusitms.connectdog.core.data.api.ApiService
import com.kusitms.connectdog.core.data.api.model.volunteer.ReviewBody
import com.kusitms.connectdog.core.data.api.model.volunteer.ReviewDetailResponse
import com.kusitms.connectdog.core.data.mapper.toData
import com.kusitms.connectdog.core.data.mapper.volunteer.toData
import com.kusitms.connectdog.core.model.Application
import com.kusitms.connectdog.core.model.ConnectDogResult
import com.kusitms.connectdog.core.model.Volunteer
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import javax.inject.Inject

internal class ManagementRepositoryImpl @Inject constructor(
    private val api: ApiService
) : ManagementRepository {
    override suspend fun getApplicationWaiting(page: Int?, size: Int?): List<Application> {
        return api.getApplicationWaiting(page, size).map { it.toData() }
    }

    override suspend fun getApplicationInProgress(page: Int?, size: Int?, sort: String): List<Application> {
        return api.getApplicationInProgress(page, size, sort).map { it.toData() }
    }

    override suspend fun getApplicationCompleted(page: Int?, size: Int?): List<Application> {
        return api.getApplicationCompleted(page, size).map { it.toData() }
    }

    override suspend fun getMyApplication(applicationId: Long): Volunteer {
        return api.getMyApplication(applicationId).toData()
    }

    override suspend fun deleteMyApplication(applicationId: Long): ConnectDogResult {
        return api.deleteMyApplication(applicationId).toData()
    }

    override suspend fun getReview(reviewId: Long): ReviewDetailResponse {
        return api.getReviewDetail(reviewId)
    }

    override suspend fun postReview(postId: Long, body: ReviewBody, images: List<File>) {
        val jsonBody = RequestBody.create(
            "application/json; charset=utf-8".toMediaTypeOrNull(),
            Gson().toJson(body)
        )

        val files = images.map { file ->
            val fileBody = RequestBody.create("multipart/form-data".toMediaTypeOrNull(), file)
            MultipartBody.Part.createFormData("files", file.name, fileBody)
        }

        return api.postReview(postId, jsonBody, files)
    }
}
