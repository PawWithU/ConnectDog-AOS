package com.kusitms.connectdog.core.data.api.model.volunteer

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ApplicationWaitingResponseItem(
    val arrivalLoc: String,
    val departureLoc: String,
    val startDate: String,
    val endDate: String,
    val isKennel: Boolean,
    val mainImage: String,
    val postId: Long,
    val dogName: String,
    val pickUpTime: String?,
    val dogSize: String,
    val applicationId: Long
)
