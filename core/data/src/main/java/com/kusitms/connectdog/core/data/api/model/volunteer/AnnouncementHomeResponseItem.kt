package com.kusitms.connectdog.core.data.api.model.volunteer

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AnnouncementHomeResponseItem(
    val arrivalLoc: String,
    val departureLoc: String,
    val endDate: String,
    val intermediaryName: String,
    val isKennel: Boolean,
    val mainImage: String,
    val postId: Int,
    val startDate: String
)
