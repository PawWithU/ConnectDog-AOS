package com.kusitms.connectdog.domain.repository

import com.kusitms.connectdog.core.model.notification.Notification

interface NotificationRepository {
    suspend fun getNotification(): Result<List<Notification>>
}