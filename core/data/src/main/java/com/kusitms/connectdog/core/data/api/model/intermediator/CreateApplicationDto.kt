package com.kusitms.connectdog.core.data.api.model.intermediator

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CreateApplicationDto(
    val departureLoc: String,
    val arrivalLoc: String,
    val startDate: String,
    val endDate: String,
    val pickUpTime: String,
    val isKennel: Boolean,
    val content: String,
    val dogName: String,
    val dogSize: String,
    val specifics: String,
)
