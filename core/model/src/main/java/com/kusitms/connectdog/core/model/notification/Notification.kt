package com.kusitms.connectdog.core.model.notification

data class Notification(
    val id: Long,
    val notificationType: String,
    val title: String,
    val body: String,
    val isRead: Boolean,
    val volunteerId: String,
    val createdDate: String
)
