package com.kusitms.connectdog.signup.viewmodel

import android.net.Uri
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class IntermediatorProfileViewModel @Inject constructor() : ViewModel() {
    private val _introduce: MutableState<String> = mutableStateOf("")
    val introduce: String
        get() = _introduce.value

    fun updateIntroduce(introduce: String) {
        _introduce.value = introduce
    }

    private val _uri: MutableState<Uri?> = mutableStateOf(null)
    val uri: Uri?
        get() = _uri.value
}
