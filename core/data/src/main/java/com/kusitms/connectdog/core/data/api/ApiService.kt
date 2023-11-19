package com.kusitms.connectdog.core.data.api

import com.kusitms.connectdog.core.data.api.model.AnnouncementHomeResponseItem
import com.kusitms.connectdog.core.data.api.model.ReviewResponseItem
import retrofit2.http.GET
import retrofit2.http.Query

internal interface ApiService {

    @GET("/volunteers/posts/home")
    suspend fun getAnnouncementPostsHome(): List<AnnouncementHomeResponseItem>

    @GET("/volunteers/reviews")
    suspend fun getReviewsHome(
        @Query("page") page: Int,
        @Query("size") size: Int
    ): List<ReviewResponseItem>
}
