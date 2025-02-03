package com.kusitms.connectdog.feature.mypage.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kusitms.connectdog.core.data.api.model.volunteer.IsDuplicateNicknameBody
import com.kusitms.connectdog.core.data.api.model.volunteer.UserInfoResponse
import com.kusitms.connectdog.core.data.repository.MyPageRepository
import com.kusitms.connectdog.core.model.signup.Nickname
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditProfileViewModel @Inject constructor(
    private val myPageRepository: MyPageRepository
) : ViewModel() {
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

    fun fetchProfileInformation(profileImageId: Int, nickName: String) {
        updateProfileImageIndex(profileImageId)
        updateNickname(nickName)
    }

    fun updateNicknameAvailability() {
        val body = Nickname(nickname = _nickname.value)
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
