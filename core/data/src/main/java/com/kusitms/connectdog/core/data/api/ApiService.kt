package com.kusitms.connectdog.core.data.api

import com.kusitms.connectdog.core.data.api.model.AnnouncementHomeResponseItem
import retrofit2.http.GET

internal interface ApiService {

    @GET("/volunteers/posts/home")
    suspend fun getAnnouncementPostsHome(): List<AnnouncementHomeResponseItem>

}
