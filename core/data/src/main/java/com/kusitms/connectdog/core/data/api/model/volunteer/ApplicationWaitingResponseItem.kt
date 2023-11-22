package com.kusitms.connectdog.core.data.api.model.volunteer

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ApplicationWaitingResponseItem(
    val arrivalLoc: String,
    val departureLoc: String,
    val endDate: String,
    val intermediaryName: String,
    val isKennel: Boolean,
    val mainImage: String,
    val applicationId: Long,
    val postId: Long,
    val startDate: String
)
