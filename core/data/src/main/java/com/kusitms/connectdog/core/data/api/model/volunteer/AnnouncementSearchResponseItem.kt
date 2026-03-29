package com.kusitms.connectdog.core.data.api.model.volunteer

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AnnouncementSearchResponseItem(
    val postId: Int,
    val departureLoc: String,
    val arrivalLoc: String,
    val startDate: String,
    val endDate: String,
    val isKennel: Boolean,
    val mainImage: String,
    val dogSize: String,
    val pickUpTime: String?,
    val dogName: String
)
