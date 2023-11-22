package com.kusitms.connectdog.core.data.api.model.intermediator

data class IntermediatorInfoResponseItem(
    val completedPostCount: Long,
    val contact: String,
    val dogStatusCount: Long,
    val guide: String,
    val intro: String,
    val name: String,
    val profileImage: String,
    val reviewCount: Long,
    val url: String
)
