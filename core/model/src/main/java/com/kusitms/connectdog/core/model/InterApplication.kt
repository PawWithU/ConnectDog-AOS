package com.kusitms.connectdog.core.model

data class InterApplication(
    val imageUrl: String,
    val dogName: String,
    val location: String,
    val date: String,
    val volunteerName: String = "",
    val postId: Long,
    val postStatus: String? = null,
    val applicationTime: String? = null,
    val applicationId: Long ? = null,
    val reviewId: Long? = null,
    val dogStatusId: Long? = null
)
