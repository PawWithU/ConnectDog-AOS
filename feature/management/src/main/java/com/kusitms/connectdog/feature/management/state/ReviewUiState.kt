package com.kusitms.connectdog.feature.management.state

import com.kusitms.connectdog.core.model.Review

sealed interface ReviewUiState {
    object Loading : ReviewUiState

    data class Reviews(
        val review: Review,
    ) : ReviewUiState
}
