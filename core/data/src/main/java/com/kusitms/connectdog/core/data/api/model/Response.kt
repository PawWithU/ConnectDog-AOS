package com.kusitms.connectdog.core.data.api.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Response(
    val isSuccess: Boolean
)
