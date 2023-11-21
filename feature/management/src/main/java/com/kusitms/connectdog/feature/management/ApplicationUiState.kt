package com.kusitms.connectdog.feature.management

import com.kusitms.connectdog.core.model.Application

sealed interface ApplicationUiState {
    object Loading : ApplicationUiState
    object Empty : ApplicationUiState
    data class Applications(
        val applications: List<Application>
    ) : ApplicationUiState
}
