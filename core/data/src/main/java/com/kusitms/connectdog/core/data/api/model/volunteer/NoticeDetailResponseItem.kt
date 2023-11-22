package com.kusitms.connectdog.core.data.api.model.volunteer

data class NoticeDetailResponseItem(
    val arrivalLoc: String,
    val content: String,
    val departureLoc: String,
    val dogGender: String,
    val dogName: String,
    val dogSize: String,
    val dogWeight: Double,
    val endDate: String,
    val images: List<String>,
    val intermediaryId: Int,
    val intermediaryName: String,
    val intermediaryProfileImage: String,
    val isBookmark: Boolean,
    val isKennel: Boolean,
    val mainImage: String,
    val pickUpTime: String,
    val postId: Int,
    val postStatus: String,
    val specifics: String?,
    val startDate: String
)