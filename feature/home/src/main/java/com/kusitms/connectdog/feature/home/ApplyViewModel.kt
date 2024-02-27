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

@HiltViewModel
class ApplyViewModel @Inject constructor(
    private val applyRepository: ApplyRepository
) : ViewModel() {
    private val _isCertified: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isCertified: StateFlow<Boolean> = _isCertified

    private val _isSendNumber: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isSendNumber: StateFlow<Boolean> = _isSendNumber

    private val _isChecked = MutableStateFlow(true)
    val isChecked: StateFlow<Boolean> = _isChecked

    private val _name: MutableState<String> = mutableStateOf("")
    val name: String
        get() = if(_isChecked.value) { _name.value } else { "" }

    private val _phoneNumber: MutableState<String> = mutableStateOf("")
    val phoneNumber: String
        get() = if(_isChecked.value) { _phoneNumber.value } else { "" }

    private val _certificationNumber: MutableState<String> = mutableStateOf("")
    val certificationNumber: String
        get() = _certificationNumber.value

    private val _transportation: MutableState<String> = mutableStateOf("")
    val transportation: String
        get() = _transportation.value

    private val _content: MutableState<String> = mutableStateOf("")
    val content: String
        get() = _content.value

    fun updateName(name: String) {
        _name.value = name
    }

    fun updatePhoneNumber(phoneNumber: String) {
        _phoneNumber.value = phoneNumber
    }

    fun updateCertificationNumber(certificationNumber: String) {
        _certificationNumber.value = certificationNumber
    }

    fun updateTransportation(transportation: String) {
        _transportation.value = transportation
    }

    fun updateContent(content: String) {
        _content.value = content
    }

    fun updateIsCertified(isCertified: Boolean) {
        _isCertified.value = isCertified
    }

    fun updateIsSendNumber(value: Boolean) {
        _isSendNumber.value = value
    }

    fun updateIsChecked() {
        _isChecked.value = !_isChecked.value
    }

    fun postApplyVolunteer(postId: Long, applyBody: ApplyBody) {
        viewModelScope.launch {
            try {
                val response = applyRepository.postApplyVolunteer(postId, applyBody)
                Log.d("testtts", response.toString())
            } catch (e: Exception) {
                Log.d("testttserror", e.message.toString())
            }
        }
    }
}
