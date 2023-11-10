package com.kusitms.connectdog.core.model

data class Announcement(
    val imageUrl: String,
    val location: String,
    val date: String,
    val organization: String,
    val hasKennel: Boolean
)
