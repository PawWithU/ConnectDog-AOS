package com.kusitms.connectdog.core.data.api.model.volunteer

import com.kusitms.connectdog.core.model.Announcement

data class BookmarkResponseItem(
    val dogName: String,
    val pickUpTime: String,
    val dogSize: String,
    val arrivalLoc: String,
    val departureLoc: String,
    val endDate: String,
    val mainImage: String,
    val postId: Long,
    val startDate: String,
    val isKennel: Boolean,
) {
    fun toData() =
        Announcement(
            imageUrl = mainImage,
            location = "$departureLoc → $arrivalLoc",
            date = startDate,
            postId = postId.toInt(),
            dogName = dogName,
            dogSize = dogSize,
            isKennel = isKennel,
            pickUpTime = pickUpTime,
        )
}
