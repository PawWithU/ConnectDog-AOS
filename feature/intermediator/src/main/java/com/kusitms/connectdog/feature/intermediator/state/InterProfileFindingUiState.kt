package com.kusitms.connectdog.feature.intermediator.state

import com.kusitms.connectdog.core.model.Announcement

sealed interface InterProfileFindingUiState {
    object Loading : InterProfileFindingUiState

    object Empty : InterProfileFindingUiState

    data class InterProfileFinding(
        val data: List<Announcement>,
    ) : InterProfileFindingUiState
}
