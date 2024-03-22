package com.kusitms.connectdog.feature.mypage.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kusitms.connectdog.core.data.repository.DataStoreRepository
import com.kusitms.connectdog.core.data.repository.MyPageRepository
import com.kusitms.connectdog.core.util.AppMode
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val dataStoreRepository: DataStoreRepository,
    private val myPageRepository: MyPageRepository
) : ViewModel() {
    fun initLogout() {
        viewModelScope.launch {
            dataStoreRepository.saveAppMode(AppMode.LOGIN)
        }
    }

    fun deleteAccount() {
        viewModelScope.launch {
            try {
                myPageRepository.deleteAccount()
            } catch (e: Exception) {
                Log.d("tesaq", e.message.toString())
            }
        }
    }
}
