package com.kusitms.connectdog.core.data.api

import com.kusitms.connectdog.core.data.api.model.AnnouncementHomeResponseItem
import com.kusitms.connectdog.core.data.api.model.AnnouncementSearchResponseItem
import com.kusitms.connectdog.core.data.api.model.LoginResponseItem
import com.kusitms.connectdog.core.data.api.model.NormalLoginBody
import com.kusitms.connectdog.core.data.api.model.ReviewResponseItem
import com.kusitms.connectdog.core.data.api.model.SocialLoginBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query

internal interface ApiService {

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

    @Headers("Content-Type: application/json")
    @POST("/volunteers/login")
    suspend fun postLoginData(
        @Body loginBody: NormalLoginBody,
    ): LoginResponseItem

    @POST("/volunteers/login/social")
    suspend fun postSocialLoginData(
        @Body socialLoginBody: SocialLoginBody
    ): LoginResponseItem
}
