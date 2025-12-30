package com.kusitms.connectdog.core.data.repository

import com.kusitms.connectdog.core.data.api.ApiService
import com.kusitms.connectdog.core.data.api.InterApiService
import com.kusitms.connectdog.core.model.notification.Notification
import com.kusitms.connectdog.domain.repository.NotificationRepository
import javax.inject.Inject

internal class NotificationRepositoryImpl @Inject constructor(
    private val volunteerApi: ApiService,
    private val intermediatorApi: InterApiService
): NotificationRepository {
    override suspend fun getNotification(): Result<List<Notification>> = runCatching {
        return@runCatching volunteerApi.getNotifications(0, 5)
    }
}