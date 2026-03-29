package com.kusitms.connectdog.domain.usecase.notification

import com.kusitms.connectdog.domain.repository.NotificationRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetNotificationUseCase @Inject constructor(
    val repository: NotificationRepository
) {
    suspend operator fun invoke() = repository.getNotification()
}