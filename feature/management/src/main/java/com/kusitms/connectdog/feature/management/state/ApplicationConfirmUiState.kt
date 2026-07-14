package com.kusitms.connectdog.feature.management.state

import com.kusitms.connectdog.core.model.Application
import com.kusitms.connectdog.core.model.Volunteer

sealed interface ApplicationConfirmUiState {
    object Loading : ApplicationConfirmUiState

    data class ApplicationConfirm(
        val application: Application,
        val volunteer: Volunteer,
    ) : ApplicationConfirmUiState
}
