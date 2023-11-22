package com.kusitms.connectdog.core.data.api.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ReviewResponseItem(
    val arrivalLoc: String,
    val content: String,
    val departureLoc: String,
    val dogName: String,
    val endDate: String,
    val images: List<String>,
    val intermediaryName: String,
    val mainImage: String,
    val startDate: String,
    val volunteerNickname: String,
    val profileImageNum: Int
)
