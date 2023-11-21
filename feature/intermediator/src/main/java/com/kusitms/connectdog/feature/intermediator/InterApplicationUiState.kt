package com.kusitms.connectdog.feature.intermediator

import com.kusitms.connectdog.core.model.InterApplication

sealed interface InterApplicationUiState {
    object Loading : InterApplicationUiState
    object Empty : InterApplicationUiState
    data class InterApplications(
        val applications: List<InterApplication>
    ) : InterApplicationUiState
}
