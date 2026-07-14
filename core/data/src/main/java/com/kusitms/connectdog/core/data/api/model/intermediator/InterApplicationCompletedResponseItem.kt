package com.kusitms.connectdog.core.data.api.model.intermediator

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class InterApplicationCompletedResponseItem(
    val arrivalLoc: String,
    val departureLoc: String,
    val dogName: String,
    val endDate: String,
    val mainImage: String,
    val postId: Long,
    val reviewId: Long?,
    val startDate: String,
    val isKennel: Boolean,
    val pickUpTime: String,
    val dogSize: String,
)
