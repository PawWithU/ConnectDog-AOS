package com.kusitms.connectdog.core.data.api.model.volunteer

data class BookmarkResponseItem(
    val arrivalLoc: String,
    val departureLoc: String,
    val endDate: String,
    val intermediaryName: String,
    val mainImage: String,
    val postId: Long,
    val startDate: String,
    val isKennel: Boolean
)
