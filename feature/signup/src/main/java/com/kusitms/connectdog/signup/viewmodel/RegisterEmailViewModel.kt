package com.kusitms.connectdog.signup.viewmodel

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kusitms.connectdog.core.data.api.model.volunteer.EmailCertificationBody
import com.kusitms.connectdog.core.data.repository.SignUpRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterEmailViewModel @Inject constructor(
    private val signUpRepository: SignUpRepository
) : ViewModel() {
    private val _isEmailVerified: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isEmailVerified: StateFlow<Boolean>
        get() = _isEmailVerified

    private val _isEmailDuplicated: MutableStateFlow<Boolean?> = MutableStateFlow(null)
    val isEmailDuplicated: StateFlow<Boolean?>
        get() = _isEmailDuplicated

    private val _isValidEmail: MutableStateFlow<Boolean?> = MutableStateFlow(null)
    val isValidEmail: StateFlow<Boolean?>
        get() = _isValidEmail

    private val _email: MutableState<String> = mutableStateOf("")
    val email: String
        get() = _email.value

    private var postNumber: String = ""

    private val _certificationNumber: MutableState<String> = mutableStateOf("")
    val certificationNumber: String
        get() = _certificationNumber.value

    fun updateEmail(email: String) {
        _email.value = email
    }

    fun updateCertificationNumber(certificationNumber: String) {
        _certificationNumber.value = certificationNumber
    }

    fun updateEmailValidity() {
        _isValidEmail.value = !android.util.Patterns.EMAIL_ADDRESS.matcher(_email.value).matches()
    }

    fun postEmail() {
        viewModelScope.launch {
            val body = EmailCertificationBody(_email.value)
            try {
                val response = signUpRepository.postEmail(body)
                _isEmailDuplicated.value = false
                postNumber = response.authCode
            } catch (e: Exception) {
                _isEmailDuplicated.value = true
            }
        }
    }

    fun checkCertificationNumber() {
        _isEmailVerified.value = _certificationNumber.value == postNumber
        Log.d("asdfa", _isEmailVerified.value.toString())
    }
}
