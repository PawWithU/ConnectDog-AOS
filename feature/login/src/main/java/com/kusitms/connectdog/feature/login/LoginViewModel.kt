package com.kusitms.connectdog.feature.login

import android.content.Context
import androidx.lifecycle.ViewModel
import com.kusitms.connectdog.core.data.repository.LoginRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel
@Inject
constructor(private val loginRepository: LoginRepository) : ViewModel() {
    fun initNaverLogin(context: Context) {
        loginRepository.initNaverLogin(context)
    }

    fun initKakaoLogin(context: Context) {
        loginRepository.initKakaoLogin(context)
    }
}
