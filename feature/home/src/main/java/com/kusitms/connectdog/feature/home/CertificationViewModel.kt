package com.kusitms.connectdog.feature.home

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kusitms.connectdog.core.data.api.model.AdditionalAuthBody
import com.kusitms.connectdog.core.data.repository.ApplyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CertificationViewModel @Inject constructor(
    private val applyRepository: ApplyRepository
) : ViewModel() {
    private val _isCertified: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isCertified: StateFlow<Boolean> = _isCertified

    private val _isSendNumber: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isSendNumber: StateFlow<Boolean> = _isSendNumber

    private val _name: MutableState<String> = mutableStateOf("")
    val name: String
        get() = _name.value

    private val _phoneNumber: MutableState<String> = mutableStateOf("")
    val phoneNumber: String
        get() = _phoneNumber.value

    private val _certificationNumber: MutableState<String> = mutableStateOf("")
    val certificationNumber: String
        get() = _certificationNumber.value

    fun updateName(name: String) {
        _name.value = name
    }

    fun updatePhoneNumber(phoneNumber: String) {
        _phoneNumber.value = phoneNumber
    }

    fun updateCertificationNumber(certificationNumber: String) {
        _certificationNumber.value = certificationNumber
    }

    fun updateIsCertified(isCertified: Boolean) {
        _isCertified.value = isCertified
    }

    fun updateIsSendNumber(value: Boolean) {
        _isSendNumber.value = value
    }

    fun postAdditionalAuth() {
        val body = AdditionalAuthBody(name = _name.value, phone = _phoneNumber.value)
        viewModelScope.launch {
            try {
                val response = applyRepository.postAdditionalAuth(body)
                Log.d("testtts", response.toString())
            } catch (e: Exception) {
                Log.d("testttserror", e.message.toString())
            }
        }
    }
}
