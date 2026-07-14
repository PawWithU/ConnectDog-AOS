package com.kusitms.connectdog.core.data.api.model.volunteer

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AnnouncementHomeResponseItem(
    val arrivalLoc: String,
    val departureLoc: String,
    val endDate: String,
    val mainImage: String,
    val postId: Int,
    val startDate: String,
    val dogName: String,
    val pickUpTime: String,
)
