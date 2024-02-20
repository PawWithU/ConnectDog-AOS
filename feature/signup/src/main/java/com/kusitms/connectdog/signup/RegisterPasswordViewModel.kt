package com.kusitms.connectdog.signup

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class RegisterPasswordViewModel @Inject constructor() : ViewModel() {
    private var _password = mutableStateOf("")
    val password: String
        get() = _password.value

    private var _confirmPassword = mutableStateOf("")
    val confirmPassword: String
        get() = _confirmPassword.value

    private val _isValidPassword: MutableStateFlow<Boolean?> = MutableStateFlow(null)
    val isValidPassword: StateFlow<Boolean?>
        get() = _isValidPassword

    private val _isValidConfirmPassword: MutableStateFlow<Boolean?> = MutableStateFlow(null)
    val isValidConfirmPassword: StateFlow<Boolean?>
        get() = _isValidConfirmPassword

    fun updatePassword(password: String) {
        _password.value = password
    }

    fun updateConfirmPassword(confirmPassword: String) {
        _confirmPassword.value = confirmPassword
    }

    fun checkPasswordValidity(password: String) {
        val pattern1 = "^[a-zA-Z0-9]{10,}$"
        val pattern2 = "^[a-zA-Z0-9!@#$%^&*()_+}{\":?><,./;'\\[\\]]{8,}$"
        _isValidPassword.value = !Regex(pattern1).matches(password) || !Regex(pattern2).matches(password)
    }

    fun checkConfirmPasswordValidity(confirmPassword: String) {
        _isValidConfirmPassword.value = confirmPassword != _password.value
    }
}
