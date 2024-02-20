package com.kusitms.connectdog.signup

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class RegisterEmailViewModel : ViewModel() {
    private val _isEmailVerified: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isEmailVerified: StateFlow<Boolean>
        get() = _isEmailVerified
}
