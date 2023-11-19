package com.kusitms.connectdog.core.data.api.model


import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AnnouncementSearchResponseItem(
    val postId: Int,
    val departureLoc: String,
    val arrivalLoc: String,
    val startDate: String,
    val endDate: String,
    val intermediaryName: String,
    val isKennel: Boolean,
    val mainImage: String,
)