package com.kusitms.connectdog.feature.home.state

import com.kusitms.connectdog.core.model.Review

sealed interface ReviewUiState {
    object Loading : ReviewUiState
    object Empty : ReviewUiState
    data class Reviews(
        val reviews: List<Review>
    ) : ReviewUiState
}
