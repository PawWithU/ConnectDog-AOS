package com.kusitms.connectdog.core.data.api.model.intermediator

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class InterApplicationRecruitingResponseItem(
    val postId: Long,
    val arrivalLoc: String,
    val departureLoc: String,
    val dogName: String,
    val dogSize: String,
    val isKennel: Boolean,
    val pickUpTime: String,
    val endDate: String,
    val mainImage: String,
    val postStatus: String,
    val startDate: String,
)
