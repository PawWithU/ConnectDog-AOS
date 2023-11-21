package com.kusitms.connectdog.core.data.api.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ApplicationInProgressResponseItem(
    val arrivalLoc: String,
    val departureLoc: String,
    val endDate: String,
    val intermediaryName: String,
    val isKennel: Boolean,
    val mainImage: String,
    val postId: Long,
    val startDate: String
)
