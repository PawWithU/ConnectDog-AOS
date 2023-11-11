package com.kusitms.connectdog.feature.home.state

import com.kusitms.connectdog.core.model.Announcement

sealed interface AnnouncementUiState {
    object Loading : AnnouncementUiState
    object Empty : AnnouncementUiState
    data class Announcements(
        val announcements: List<Announcement>
    ) : AnnouncementUiState
}
