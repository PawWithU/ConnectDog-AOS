package com.kusitms.connectdog.core.data.api

import com.kusitms.connectdog.core.data.api.model.VolunteerResponse
import com.kusitms.connectdog.core.data.api.model.intermediator.InterApplicationCompletedResponseItem
import com.kusitms.connectdog.core.data.api.model.intermediator.InterApplicationInProgressResponseItem
import com.kusitms.connectdog.core.data.api.model.intermediator.InterApplicationRecruitingResponseItem
import com.kusitms.connectdog.core.data.api.model.intermediator.InterApplicationWaitingResponseItem
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.Path
import retrofit2.http.Query

internal interface InterApiService {
    /**
     * 봉사관리
     */
    @GET("/intermediaries/posts/recruiting")
    suspend fun getApplicationRecruiting(
        @Query("page") page: Int?,
        @Query("size") size: Int?
    ): List<InterApplicationRecruitingResponseItem>

    @GET("/intermediaries/applications/waiting")
    suspend fun getApplicationWaiting(
        @Query("page") page: Int?,
        @Query("size") size: Int?
    ): List<InterApplicationWaitingResponseItem>

    @GET("/intermediaries/applications/progressing")
    suspend fun getApplicationProgressing(
        @Query("page") page: Int?,
        @Query("size") size: Int?
    ): List<InterApplicationInProgressResponseItem>

    @GET("/intermediaries/applications/completed")
    suspend fun getApplicationCompleted(
        @Query("page") page: Int?,
        @Query("size") size: Int?
    ): List<InterApplicationCompletedResponseItem>

    @GET("/intermediaries/applications/{applicationId}")
    suspend fun getApplicationVolunteer(
        @Path("applicationId") applicationId: Long
    ): VolunteerResponse

    @PATCH("/intermediaries/applications/{applicationId}")
    suspend fun patchApplicationVolunteer(
        @Path("applicationId") applicationId: Long
    )

    @DELETE("/intermediaries/applications/{applicationId}")
    suspend fun deleteApplicationVolunteer(
        @Path("applicationId") applicationId: Long
    )
}
