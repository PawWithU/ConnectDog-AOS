package com.kusitms.connectdog.feature.mypage.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kusitms.connectdog.core.data.repository.SignUpRepository
import com.kusitms.connectdog.core.util.UserType
import com.kusitms.connectdog.domain.usecase.login.DeleteAccessTokenUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PasswordChangeViewModel @Inject constructor(
    private val repository: SignUpRepository,
    private val deleteAccessTokenUseCase: DeleteAccessTokenUseCase
) : ViewModel() {
    private val _previousPassword: MutableState<String> = mutableStateOf("")
    val previousPassword: String
        get() = _previousPassword.value

    private val _newPassword: MutableState<String> = mutableStateOf("")
    val newPassword: String
        get() = _newPassword.value

    private val _checkPassword: MutableState<String> = mutableStateOf("")
    val checkPassword: String
        get() = _checkPassword.value

    private val _isRightPassword = MutableStateFlow<Boolean?>(null)
    val isRightPassword: StateFlow<Boolean?>
        get() = _isRightPassword

    private val _isValidPassword: MutableStateFlow<Boolean?> = MutableStateFlow(null)
    val isValidPassword: StateFlow<Boolean?>
        get() = _isValidPassword

    private val _isValidConfirmPassword: MutableStateFlow<Boolean?> = MutableStateFlow(null)
    val isValidConfirmPassword: StateFlow<Boolean?>
        get() = _isValidConfirmPassword

    fun updatePreviousPassword(value: String) {
        _previousPassword.value = value
    }

    fun updateNewPassword(value: String) {
        _newPassword.value = value
    }

    fun updateCheckPassword(value: String) {
        _checkPassword.value = value
    }

    fun checkPreviousPassword(userType: UserType) = viewModelScope.launch {
        try {
            val response = when (userType) {
                UserType.INTERMEDIATOR -> repository.checkInterPassword(_previousPassword.value)
                else -> repository.checkVolunteerPassword(_previousPassword.value)
            }
            _isRightPassword.value = response.isChecked
        } catch (e: Exception) {
        }
    }

    fun changePassword(userType: UserType) = viewModelScope.launch {
        try {
            when (userType) {
                UserType.INTERMEDIATOR -> repository.changeInterPassword(_newPassword.value)
                else -> repository.changeVolunteerPassword(_newPassword.value)
            }
            deleteAccessTokenUseCase()
        } catch (e: Exception) {
        }
    }

    fun checkPasswordValidity(password: String) {
        val pattern1 = "^[a-zA-Z0-9]{10,}$"
        val pattern2 = "^[a-zA-Z0-9!@#$%^&*()_+}{\":?><,./;'\\[\\]]{8,}$"
        _isValidPassword.value = !Regex(pattern1).matches(password) || !Regex(pattern2).matches(password)
    }

    fun checkConfirmPasswordValidity(confirmPassword: String) {
        _isValidConfirmPassword.value = confirmPassword != _newPassword.value
    }
}
