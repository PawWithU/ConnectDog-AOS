package com.kusitms.connectdog.feature.intermediator

import com.kusitms.connectdog.core.model.IntermediatorApplication

sealed interface IntermediatorApplicationUiState {
    object Loading : IntermediatorApplicationUiState
    object Empty : IntermediatorApplicationUiState
    data class IntermediatorApplications(
        val applications: List<IntermediatorApplication>
    ) : IntermediatorApplicationUiState
}
