package com.kusitms.connectdog.feature.mypage.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kusitms.connectdog.core.data.repository.MyPageRepository
import com.kusitms.connectdog.core.util.UserType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyAccountViewModel
    @Inject
    constructor(
        private val repository: MyPageRepository,
    ) : ViewModel() {
        private val _name = MutableStateFlow<String>("")
        val name: StateFlow<String>
            get() = _name

        private val _email = MutableStateFlow<String>("")
        val email: StateFlow<String>
            get() = _email

        private val _phoneNumber = MutableStateFlow<String>("")
        val phoneNumber: StateFlow<String>
            get() = _phoneNumber

        private val _socialType = MutableStateFlow<String?>(null)
        val socialType: StateFlow<String?>
            get() = _socialType

        fun fetchAccountInfo(userType: UserType) =
            viewModelScope.launch {
                when (userType) {
                    UserType.INTERMEDIATOR -> {
                        val response = repository.getInterAccountInfo()
                        _name.value = response.realName
                        _email.value = response.email
                        _phoneNumber.value = response.phone
                    }
                    else -> {
                        val response = repository.getVolunteerAccountInfo()
                        _name.value = response.name
                        _email.value = response.email ?: ""
                        _phoneNumber.value = response.phone
                        _socialType.value = response.socialType
                    }
                }
            }
    }
