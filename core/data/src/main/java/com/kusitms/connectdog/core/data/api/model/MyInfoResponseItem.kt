package com.kusitms.connectdog.core.data.api.model

data class MyInfoResponseItem(
    val profileImageNum: Int,
    val nickname: String,
    val waitingCount: Int,
    val completedCount: Int,
    val progressingCount: Int,
    val reviewCount: Int,
)
