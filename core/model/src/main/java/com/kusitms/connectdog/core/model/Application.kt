package com.kusitms.connectdog.core.model

import kotlinx.serialization.Serializable

@Serializable
data class Application(
    val imageUrl: String,
    val location: String,
    val date: String,
    val organization: String? = null,
    val hasKennel: Boolean,
    val postId: Long,
    val applicationId: Long ? = null,
    val reviewId: Long? = null,
    val dogSize: String? = null,
    val dogName: String? = null,
    val pickUpTime: String? = null
)
