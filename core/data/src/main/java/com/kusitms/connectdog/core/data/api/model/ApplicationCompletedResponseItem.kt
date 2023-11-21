package com.kusitms.connectdog.core.data.api.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ApplicationCompletedResponseItem(
    val arrivalLoc: String,
    val departureLoc: String,
    val startDate: String,
    val endDate: String,
    val intermediaryName: String,
    val isKennel: Boolean,
    val mainImage: String,
    val postId: Long,
    val reviewId: Long?,
    val dogStatusId: Long?
)
