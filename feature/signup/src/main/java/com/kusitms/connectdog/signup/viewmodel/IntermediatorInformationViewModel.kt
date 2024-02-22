package com.kusitms.connectdog.signup.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class IntermediatorInformationViewModel @Inject constructor() : ViewModel() {
    private val _name: MutableState<String> = mutableStateOf("")
    val name: String
        get() = _name.value

    fun updateName(name: String) {
        _name.value = name
    }
}
