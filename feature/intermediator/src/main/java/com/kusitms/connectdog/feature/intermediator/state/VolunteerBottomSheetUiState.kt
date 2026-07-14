package com.kusitms.connectdog.feature.intermediator.state

import com.kusitms.connectdog.core.model.InterApplication
import com.kusitms.connectdog.core.model.Volunteer

sealed interface VolunteerBottomSheetUiState {
    object Loading : VolunteerBottomSheetUiState

    data class VolunteerInfo(
        val application: InterApplication,
        val volunteer: Volunteer,
    ) : VolunteerBottomSheetUiState
}
