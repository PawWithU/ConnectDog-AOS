package com.kusitms.connectdog.feature.home

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kusitms.connectdog.core.data.api.model.volunteer.ApplyBody
import com.kusitms.connectdog.core.data.repository.ApplyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ApplyViewModel @Inject constructor(
    private val applyRepository: ApplyRepository
) : ViewModel() {
    private val _isCertified = MutableLiveData<Boolean>()
    val isCertified: LiveData<Boolean> = _isCertified

    private val _name: MutableState<String> = mutableStateOf("")
    val name: String
        get() = _name.value

    private val _phoneNumber: MutableState<String> = mutableStateOf("")
    val phoneNumber: String
        get() = _phoneNumber.value

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

    fun updateTransportation(transportation: String) {
        _transportation.value = transportation
    }

    fun updateContent(content: String) {
        _content.value = content
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

    fun updateIsCertified(isCertified: Boolean) {
        _isCertified.postValue(isCertified)
    }
}
