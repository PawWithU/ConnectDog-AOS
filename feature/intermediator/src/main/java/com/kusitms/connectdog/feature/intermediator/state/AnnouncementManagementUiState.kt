package com.kusitms.connectdog.feature.intermediator.state

import com.kusitms.connectdog.core.data.api.model.volunteer.NoticeDetailResponseItem

sealed interface AnnouncementManagementUiState {
    object Loading : AnnouncementManagementUiState

    data class AnnouncementDetail(
        val announcement: NoticeDetailResponseItem,
    ) : AnnouncementManagementUiState
}
