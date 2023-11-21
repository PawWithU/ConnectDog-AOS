package com.kusitms.connectdog.core.data.api.model.intermediator


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class InterApplicationCompletedResponseItem(
    val applicationId: Long,
    val arrivalLoc: String,
    val departureLoc: String,
    val dogName: String,
    val endDate: String,
    val mainImage: String,
    val postId: Long,
    val reviewId: Long?,
    val dogStatusId: Long?,
    val startDate: String,
    val volunteerName: String
)