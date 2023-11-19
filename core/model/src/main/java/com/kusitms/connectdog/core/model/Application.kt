package com.kusitms.connectdog.core.model

data class Application(
    val postId: Long,
    val imageUrl: String,
    val location: String,
    val date: String,
    val organization: String,
    val hasKennel: Boolean
)
