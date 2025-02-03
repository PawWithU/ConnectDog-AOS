package com.kusitms.connectdog.feature.mypage.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kusitms.connectdog.core.data.repository.InterManagementRepository
import com.kusitms.connectdog.core.data.repository.MyPageRepository
import com.kusitms.connectdog.core.util.UserType
import com.kusitms.connectdog.domain.usecase.login.AppMode
import com.kusitms.connectdog.domain.usecase.login.DeleteAccessTokenUseCase
import com.kusitms.connectdog.domain.usecase.login.LogoutUseCase
import com.kusitms.connectdog.domain.usecase.login.UpdateAppModeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "MyPageViewModel"

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val myPageRepository: MyPageRepository,
    private val interRepository: InterManagementRepository,
    private val updateAppModeUseCase: UpdateAppModeUseCase,
    private val deleteAccessTokenUseCase: DeleteAccessTokenUseCase,
    private val logoutUseCase: LogoutUseCase
) : ViewModel() {
    private val _isAbleWithdraw = MutableSharedFlow<Boolean?>()
    val isAbleWithdraw: SharedFlow<Boolean?>
        get() = _isAbleWithdraw

    fun initLogout() = viewModelScope.launch {
        updateAppModeUseCase(AppMode.LOGIN)
        deleteAccessTokenUseCase()
        logoutUseCase()
    }

    fun deleteAccount(userType: UserType) = viewModelScope.launch {
        try {
            updateAppModeUseCase(AppMode.LOGIN)
            when (userType) {
                UserType.INTERMEDIATOR -> myPageRepository.interWithdraw()
                else -> myPageRepository.volunteerWithdraw()
            }
            deleteAccessTokenUseCase()
        } catch (e: Exception) {
            Log.d(TAG, e.message.toString())
        }
    }

    fun updateNotification() = viewModelScope.launch {
        try {
            myPageRepository.updateNotification()
        } catch (e: Exception) {
            Log.d(TAG, e.message.toString())
        }
    }

    fun updateIsAbleWithdraw(userType: UserType) = viewModelScope.launch {
        when (userType) {
            UserType.INTERMEDIATOR -> {
                val response = interRepository.getIntermediatorProfileInfo()
                _isAbleWithdraw.emit(!(response.recruitingCount > 0 || response.waitingCount > 0 || response.progressingCount > 0))
            }

            else -> {
                val response = myPageRepository.getMyInfo()
                _isAbleWithdraw.emit(!(response.progressingCount > 0 || response.waitingCount > 0))
            }
        }
    }
}
