package com.kusitms.connectdog.signup.viewmodel

import android.net.Uri
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kusitms.connectdog.core.data.api.model.intermediator.NameDto
import com.kusitms.connectdog.core.data.repository.SignUpRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class IntermediatorProfileViewModel @Inject constructor(
    private val signUpRepository: SignUpRepository
) : ViewModel() {
    private val _name: MutableState<String> = mutableStateOf("")
    val name: String
        get() = _name.value

    private val _introduce: MutableState<String> = mutableStateOf("")
    val introduce: String
        get() = _introduce.value

    private val _isDuplicateName = MutableStateFlow<Boolean?>(null)
    val isDuplicateName: StateFlow<Boolean?>
        get() = _isDuplicateName

    private val _uri: MutableState<Uri?> = mutableStateOf(null)
    val uri: Uri?
        get() = _uri.value

    fun updateName(name: String) {
        _name.value = name
    }

    fun updateIntroduce(introduce: String) {
        _introduce.value = introduce
    }

    fun updateUri(uri: Uri) {
        _uri.value = uri
    }

    fun checkNickNameDuplicate() = viewModelScope.launch {
        val body = NameDto(
            name = _name.value
        )
        try {
//            val response = signUpRepository.isDuplicateInterNickName(body)
//            _isDuplicateName.value = response.isDuplicated
        } catch (e: Exception) {
        }
    }
}
