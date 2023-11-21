package com.kusitms.connectdog.core.data.api.model.intermediator


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class InterApplicationCompletedResponseItem(
    val applicationId: Int,
    val arrivalLoc: String,
    val departureLoc: String,
    val dogName: String,
    val dogStatusId: Int,
    val endDate: String,
    val mainImage: String,
    val postId: Int,
    val reviewId: Int,
    val startDate: String,
    val volunteerName: String
)