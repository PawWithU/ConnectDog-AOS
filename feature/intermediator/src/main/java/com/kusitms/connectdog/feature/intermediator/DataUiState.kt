package com.kusitms.connectdog.feature.intermediator

sealed interface DataUiState {
    object Yet : DataUiState
    object Loading : DataUiState
    object Success : DataUiState
}