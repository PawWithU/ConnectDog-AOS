package com.kusitms.connectdog.feature.mypage.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kusitms.connectdog.core.data.api.model.volunteer.IsDuplicateNicknameBody
import com.kusitms.connectdog.core.data.api.model.volunteer.UserInfoResponse
import com.kusitms.connectdog.core.data.repository.MyPageRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditProfileViewModel @Inject constructor(
    private val myPageRepository: MyPageRepository
) : ViewModel() {
    init {
        fetchProfileInformation()
    }

    private val _isDuplicatedNickname: MutableStateFlow<Boolean?> = MutableStateFlow(null)
    val isDuplicatedNickname: StateFlow<Boolean?>
        get() = _isDuplicatedNickname

    private val _selectedImageIndex: MutableStateFlow<Int> = MutableStateFlow(0)
    val selectedImageIndex: StateFlow<Int>
        get() = _selectedImageIndex

    private val _nickname: MutableStateFlow<String> = MutableStateFlow("")
    val nickname: StateFlow<String>
        get() = _nickname

    fun updateNickname(value: String) {
        _nickname.value = value
    }

    fun updateProfileImageIndex(imageIndex: Int) {
        _selectedImageIndex.value = imageIndex
    }

    private fun fetchProfileInformation() {
        viewModelScope.launch {
            try {
                val response = myPageRepository.getUserInfo()
                updateProfileImageIndex(response.profileImageNum)
                updateNickname(response.nickname)
            } catch (e: Exception) {
                Log.d("Asdf", e.message.toString())
            }
        }
    }

    fun updateNicknameAvailability() {
        val body = IsDuplicateNicknameBody(nickname = _nickname.value)
        viewModelScope.launch {
            try {
                val response = myPageRepository.postNickname(body)
                _isDuplicatedNickname.value = response.isDuplicated
            } catch (e: Exception) {
                Log.d("asdf", e.message.toString())
            }
        }
    }

    fun updateUserInfo() {
        val body = UserInfoResponse(
            nickname = _nickname.value,
            profileImageNum = _selectedImageIndex.value
        )
        viewModelScope.launch {
            try {
                myPageRepository.updateUserInfo(body)
            } catch (e: Exception) {
                Log.d("asdf", e.message.toString())
            }
        }
    }
}
