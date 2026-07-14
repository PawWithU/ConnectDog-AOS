package com.kusitms.connectdog.core.data.api.model.volunteer

data class ReviewDetailWithId(
    val profileImageNum: Int,
    val dogName: String,
    val volunteerNickname: String,
    val createdDate: String,
    val mainImage: String,
    val images: List<String>,
    val content: String,
    val postId: Long,
    val postMainImage: String,
    val startDate: String,
    val endDate: String,
    val departureLoc: String,
    val arrivalLoc: String,
    val intermediaryId: Long,
    val intermediaryName: String,
    val reviewId: Long,
)
