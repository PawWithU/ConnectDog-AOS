package com.kusitms.connectdog.feature.intermediator.state

import com.kusitms.connectdog.core.data.api.model.intermediator.InterProfileInfoResponse

sealed interface InterProfileInfoUiState {
    object Loading : InterProfileInfoUiState

    data class InterProfile(
        val data: InterProfileInfoResponse,
    ) : InterProfileInfoUiState
}
