package com.kusitms.connectdog.feature.intermediator

import com.kusitms.connectdog.core.model.InterApplication

sealed interface IntermediatorApplicationUiState {
    object Loading : IntermediatorApplicationUiState
    object Empty : IntermediatorApplicationUiState
    data class IntermediatorApplications(
        val applications: List<InterApplication>
    ) : IntermediatorApplicationUiState
}
