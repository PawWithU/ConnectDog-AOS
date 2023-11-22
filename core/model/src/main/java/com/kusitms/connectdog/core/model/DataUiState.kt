package com.kusitms.connectdog.core.model

sealed interface DataUiState {
    object Yet : DataUiState
    object Loading : DataUiState
    object Success : DataUiState
}