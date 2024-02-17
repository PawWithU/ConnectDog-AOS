package com.kusitms.connectdog.feature.signup.volunteer

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class VolunteerProfileViewModel @Inject constructor() : ViewModel() {
    private val _isProfileImageSelected: MutableState<Boolean> = mutableStateOf(false)
    private val _isNicknameAvailable: MutableState<Boolean> = mutableStateOf(false)

    val isProfileImageSelected get() = _isProfileImageSelected.value
    val isNicknameAvailable get() = _isNicknameAvailable.value

    fun updateProfileImageSelected(selected: Boolean) {
        _isProfileImageSelected.value = selected
    }

    fun updateNicknameAvailability(available: Boolean) {
        _isNicknameAvailable.value = available
    }
}
