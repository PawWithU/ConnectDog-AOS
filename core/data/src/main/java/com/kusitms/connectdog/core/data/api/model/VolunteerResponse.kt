package com.kusitms.connectdog.core.data.api.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class VolunteerResponse(
    val content: String,
    val id: Long,
    val phone: String,
    val transportation: String,
    val volunteerName: String
)
