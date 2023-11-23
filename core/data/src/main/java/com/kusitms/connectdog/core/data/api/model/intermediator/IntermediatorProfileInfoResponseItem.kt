package com.kusitms.connectdog.core.data.api.model.intermediator

data class IntermediatorProfileInfoResponseItem(
    val completedCount: Long,
    val intermediaryName: String,
    val intro: String,
    val profileImage: String,
    val progressingCount: Long,
    val recruitingCount: Long,
    val waitingCount: Long
)
