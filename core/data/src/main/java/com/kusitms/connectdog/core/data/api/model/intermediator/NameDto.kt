package com.kusitms.connectdog.core.data.api.model.intermediator

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class NameDto(
    val name: String,
)
