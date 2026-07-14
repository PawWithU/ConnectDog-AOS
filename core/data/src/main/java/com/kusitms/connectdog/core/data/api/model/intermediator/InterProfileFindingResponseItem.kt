package com.kusitms.connectdog.core.data.api.model.intermediator

data class InterProfileFindingResponseItem(
    val arrivalLoc: String,
    val departureLoc: String,
    val startDate: String,
    val endDate: String,
    val isKennel: Boolean,
    val mainImage: String,
    val postId: Long,
    val dogName: String,
    val pickUpTime: String,
    val dogSize: String,
    val postStatus: String,
)
