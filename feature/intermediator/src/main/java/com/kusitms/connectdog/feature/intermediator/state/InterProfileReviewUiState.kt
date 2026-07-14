package com.kusitms.connectdog.feature.intermediator.state

import com.kusitms.connectdog.core.model.Review

sealed interface InterProfileReviewUiState {
    object Loading : InterProfileReviewUiState

    object Empty : InterProfileReviewUiState

    data class InterProfileReview(
        val review: List<Review>,
    ) : InterProfileReviewUiState
}
