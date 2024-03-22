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

private const val TAG = "VolunteerProfileViewModel"

@HiltViewModel
class VolunteerProfileViewModel @Inject constructor(
    private val signupRepository: SignUpRepository
) : ViewModel() {
    private val _selectedImageIndex = MutableStateFlow(-1)
    val selectedImageIndex: StateFlow<Int>
        get() = _selectedImageIndex

    private val _isDuplicatedNickname: MutableStateFlow<Boolean?> = MutableStateFlow(null)
    val isDuplicatedNickname: StateFlow<Boolean?>
        get() = _isDuplicatedNickname

    private val _isAvailableNickname: MutableStateFlow<Boolean?> = MutableStateFlow(null)
    val isAvailableNickname: StateFlow<Boolean?>
        get() = _isAvailableNickname

    private val _nickname: MutableState<String> = mutableStateOf("")
    val nickname: String
        get() = _nickname.value

    fun isAvailableNickname() {
        val regex = Regex("[가-힣0-9]+")
        _isAvailableNickname.value = !regex.matches(_nickname.value)
        Log.d("taswa", _isAvailableNickname.value.toString())
    }

    fun updateNicknameAvailability(nickname: String) {
        val nicknameBody = IsDuplicateNicknameBody(nickname = nickname)
        viewModelScope.launch {
            try {
                val response = signupRepository.postNickname(nicknameBody)
                Log.d(TAG, response.toString())
                _isDuplicatedNickname.value = !response.isDuplicated
            } catch (e: Exception) {
                Log.d(TAG, e.message.toString())
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
