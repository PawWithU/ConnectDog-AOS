package com.kusitms.connectdog.core.data.api.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class VolunteerResponse(
    val volunteerName: String,
    val phone: String,
    val createdDate: String,
    val content: String,
    val id: Long,
)
