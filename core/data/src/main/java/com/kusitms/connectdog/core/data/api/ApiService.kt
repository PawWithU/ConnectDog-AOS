package com.kusitms.connectdog.core.data.api

import com.kusitms.connectdog.core.data.api.model.AnnouncementHomeResponseItem
import com.kusitms.connectdog.core.data.api.model.AnnouncementSearchResponseItem
import com.kusitms.connectdog.core.data.api.model.ApplicationCompletedResponseItem
import com.kusitms.connectdog.core.data.api.model.ApplicationInProgressResponseItem
import com.kusitms.connectdog.core.data.api.model.ApplicationWaitingResponseItem
import com.kusitms.connectdog.core.data.api.model.LoginResponseItem
import com.kusitms.connectdog.core.data.api.model.MyInfoResponseItem
import com.kusitms.connectdog.core.data.api.model.NormalLoginBody
import com.kusitms.connectdog.core.data.api.model.ReviewResponseItem
import com.kusitms.connectdog.core.data.api.model.SocialLoginBody
import com.kusitms.connectdog.core.data.api.model.VolunteerResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

internal interface ApiService {

    /**
     * 이동봉사자 > 홈
     */
    @GET("/volunteers/posts/home")
    suspend fun getAnnouncementPostsHome(): List<AnnouncementHomeResponseItem>

    @GET("/volunteers/posts")
    suspend fun getAnnouncementFilterPosts(
        @Query("postStatus") postStatus: String?,
        @Query("departureLoc") departureLoc: String?,
        @Query("arrivalLoc") arrivalLoc: String?,
        @Query("startDate") startDate: String?,
        @Query("endDate") endDate: String?,
        @Query("dogSize") dogSize: String?,
        @Query("isKennel") isKennel: Boolean?,
        @Query("intermediaryName") intermediaryName: String?,
        @Query("orderCondition") orderCondition: String?,
        @Query("page") page: Int,
        @Query("size") size: Int
    ): List<AnnouncementSearchResponseItem>

    @GET("/volunteers/reviews")
    suspend fun getReviewsHome(
        @Query("page") page: Int,
        @Query("size") size: Int
    ): List<ReviewResponseItem>

    /**
     * 이동봉사자 > 봉사관리
     */
    @GET("/volunteers/applications/waiting")
    suspend fun getApplicationWaiting(
        @Query("page") page: Int?,
        @Query("size") size: Int?
    ): List<ApplicationWaitingResponseItem>

    @GET("/volunteers/applications/progressing")
    suspend fun getApplicationInProgress(
        @Query("page") page: Int?,
        @Query("size") size: Int?
    ): List<ApplicationInProgressResponseItem>

    @GET("/volunteers/applications/completed")
    suspend fun getApplicationCompleted(
        @Query("page") page: Int?,
        @Query("size") size: Int?
    ): List<ApplicationCompletedResponseItem>

    @GET("/volunteers/applications/{applicationId}")
    suspend fun getMyApplication(
        @Path("applicationId") applicationId: Long
    ): VolunteerResponse

    @Headers("Content-Type: application/json")
    @POST("/volunteers/login")
    suspend fun postLoginData(
        @Body loginBody: NormalLoginBody
    ): LoginResponseItem

    @POST("/volunteers/login/social")
    suspend fun postSocialLoginData(
        @Body socialLoginBody: SocialLoginBody
    ): LoginResponseItem

    @GET("/volunteers/my/info")
    suspend fun getMyInfo(): MyInfoResponseItem
}
