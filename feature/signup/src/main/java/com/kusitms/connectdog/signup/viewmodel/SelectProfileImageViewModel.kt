package com.kusitms.connectdog.signup.viewmodel

import androidx.lifecycle.ViewModel
import com.kusitms.connectdog.signup.state.SelectProfileImageSideEffect
import com.kusitms.connectdog.signup.state.SelectProfileImageUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

private const val TAG = "VolunteerProfileViewModel"

@HiltViewModel
class SelectProfileImageViewModel @Inject constructor(): ContainerHost<SelectProfileImageUiState, SelectProfileImageSideEffect>, ViewModel() {
    override val container: Container<SelectProfileImageUiState, SelectProfileImageSideEffect> = container(SelectProfileImageUiState.empty())
    private val state: SelectProfileImageUiState
        get() = container.stateFlow.value

    fun updateProfileImageIndex(imageIndex: Int) = intent { reduce { state.copy(selectedImageId = imageIndex) } }
}
