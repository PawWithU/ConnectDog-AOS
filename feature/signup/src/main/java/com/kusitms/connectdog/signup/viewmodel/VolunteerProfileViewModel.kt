package com.kusitms.connectdog.signup.viewmodel

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kusitms.connectdog.core.data.api.model.volunteer.IsDuplicateNicknameBody
import com.kusitms.connectdog.core.data.repository.SignUpRepository
import com.kusitms.connectdog.core.util.getProfileImageId
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VolunteerProfileViewModel @Inject constructor(
    private val signupRepository: SignUpRepository
) : ViewModel() {
    private val _selectedImageIndex = MutableStateFlow(-1)
    val selectedImageIndex: StateFlow<Int>
        get() = _selectedImageIndex

    private val _isNicknameAvailable: MutableStateFlow<Boolean?> = MutableStateFlow(null)
    val isAvailableNickname: StateFlow<Boolean?>
        get() = _isNicknameAvailable

    private val _nickname: MutableState<String> = mutableStateOf("")
    val nickname: String
        get() = _nickname.value

    fun updateNicknameAvailability(nickname: String) {
        val nicknameBody = IsDuplicateNicknameBody(nickname = nickname)
        viewModelScope.launch {
            try {
                _isNicknameAvailable.value = signupRepository.postNickname(nicknameBody).isDuplicated
            } catch (e: Exception) {
                Log.d("error", e.message.toString())
            }
        }
    }

    fun updateProfileImageIndex(imageIndex: Int) {
        _selectedImageIndex.value = imageIndex
    }

    fun updateNickname(nickname: String) {
        _nickname.value = nickname
    }

    fun getProfileImageId(): Int {
        return if (_selectedImageIndex.value == -1) {
            com.kusitms.connectdog.core.designsystem.R.drawable.ic_circle
        } else {
            getProfileImageId(_selectedImageIndex.value)
        }
    }
}
