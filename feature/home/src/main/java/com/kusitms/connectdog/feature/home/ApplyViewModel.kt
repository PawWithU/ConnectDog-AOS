package com.kusitms.connectdog.feature.home

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kusitms.connectdog.core.data.api.model.volunteer.ApplyBody
import com.kusitms.connectdog.core.data.repository.ApplyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "ApplyViewModel"

@HiltViewModel
class ApplyViewModel
    @Inject
    constructor(
        private val applyRepository: ApplyRepository,
    ) : ViewModel() {
        init {
            getBasicInformation()
        }

        private val _basicName: MutableState<String> = mutableStateOf("")
        private val _basicPhoneNumber: MutableState<String> = mutableStateOf("")

        private val _name: MutableState<String> = mutableStateOf("")
        val name: String
            get() = _name.value

        private val _phoneNumber: MutableState<String> = mutableStateOf("")
        val phoneNumber: String
            get() = _phoneNumber.value

        private val _isChecked = MutableStateFlow(true)
        val isChecked: StateFlow<Boolean> = _isChecked

        private val _transportation: MutableState<String> = mutableStateOf("")
        val transportation: String
            get() = _transportation.value

        private val _isAvailableName: MutableState<Boolean?> = mutableStateOf(null)
        val isAvailableName: Boolean?
            get() = _isAvailableName.value

        private val _isAvailablePhoneNumber: MutableState<Boolean?> = mutableStateOf(null)
        val isAvailablePhoneNumber: Boolean?
            get() = _isAvailablePhoneNumber.value

        private val _content: MutableState<String> = mutableStateOf("")
        val content: String
            get() = _content.value

        private val _isNextEnabled: MutableState<Boolean> = mutableStateOf(false)
        val isNextEnabled: Boolean
            get() = _isNextEnabled.value

        fun updateContent(content: String) {
            _content.value = content
        }

        fun updateIsChecked() {
            _isChecked.value = !_isChecked.value
            if (_isChecked.value) {
                _name.value = _basicName.value
                _phoneNumber.value = _basicPhoneNumber.value
            } else {
                _name.value = ""
                _phoneNumber.value = ""
            }
        }

        fun updateName(name: String) {
            _name.value = name
            updateIsAvailableName()
        }

        fun updatePhoneNumber(phoneNumber: String) {
            _phoneNumber.value = phoneNumber
        }

        fun postApplyVolunteer(postId: Long) {
            val body =
                ApplyBody(
                    content = content,
                    name = name,
                    phone = phoneNumber,
                )
            viewModelScope.launch {
                try {
                    applyRepository.postApplyVolunteer(postId, body)
                } catch (e: Exception) {
                    Log.d(TAG, e.message.toString())
                }
            }
        }

        private fun updateIsAvailableName() {
            val koreanRegex = Regex("[가-힣]+")
            _isAvailableName.value = koreanRegex.matches(_name.value)
        }

        private fun getBasicInformation() =
            viewModelScope.launch {
                try {
                    val response = applyRepository.getBasicInformation()
                    _basicName.value = response.name
                    _basicPhoneNumber.value = response.phone
                    _name.value = response.name
                    _phoneNumber.value = response.phone
                } catch (e: Exception) {
                    Log.d(TAG, e.message.toString())
                }
            }
    }
