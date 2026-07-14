package com.kusitms.connectdog.core.data.api.model.intermediator

import com.kusitms.connectdog.core.data.api.model.volunteer.NoticeDetailResponseItem

data class InterAnnouncementDetailResponse(
    val arrivalLoc: String,
    val content: String,
    val departureLoc: String,
    val dogName: String,
    val dogSize: String,
    val endDate: String,
    val images: List<String>,
    val intermediaryId: Int,
    val intermediaryName: String,
    val intermediaryProfileImage: String,
    val isKennel: Boolean,
    val mainImage: String,
    val pickUpTime: String,
    val postId: Int,
    val postStatus: String,
    val specifics: String?,
    val startDate: String,
) {
    fun toData() =
        NoticeDetailResponseItem(
            arrivalLoc,
            content,
            departureLoc,
            dogName,
            dogSize,
            endDate,
            images,
            intermediaryId,
            intermediaryName,
            intermediaryProfileImage,
            isBookmark = false,
            isKennel,
            mainImage,
            pickUpTime,
            postId,
            postStatus,
            specifics,
            startDate,
        )
}
