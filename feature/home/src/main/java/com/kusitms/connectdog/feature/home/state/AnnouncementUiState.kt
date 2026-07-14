package com.kusitms.connectdog.feature.home.state

import com.kusitms.connectdog.core.model.AnnouncementHome

sealed interface AnnouncementUiState {
    object Loading : AnnouncementUiState

    object Empty : AnnouncementUiState

    data class Announcements(
        val announcementHomes: List<AnnouncementHome>,
    ) : AnnouncementUiState
}
