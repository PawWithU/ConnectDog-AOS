package com.kusitms.connectdog.feature.mypage.state

import com.kusitms.connectdog.core.model.notification.Notification

data class NotificationUiState(
    val notificationList: List<Notification>?
) {
    companion object {
        fun empty() = NotificationUiState(
            notificationList = null
        )
    }
}

sealed class NotificationSideEffect
