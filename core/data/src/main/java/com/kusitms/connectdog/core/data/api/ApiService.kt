package com.kusitms.connectdog.core.data.api

import com.kusitms.connectdog.core.data.api.model.ExampleResponse
import retrofit2.http.GET

internal interface ApiService {
    @GET("/droidknights/DroidKnights2023_App/main/core/data/src/main/assets/sponsors.json")
    suspend fun getExample(): List<ExampleResponse>

}