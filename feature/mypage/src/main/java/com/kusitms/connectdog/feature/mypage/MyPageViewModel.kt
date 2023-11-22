package com.kusitms.connectdog.feature.mypage

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kusitms.connectdog.core.data.api.model.MyInfoResponseItem
import com.kusitms.connectdog.core.data.repository.MyPageRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyPageViewModel @Inject constructor(
    private val myPageRepository: MyPageRepository
) : ViewModel() {
    private val _myInfo = MutableLiveData<MyInfoResponseItem?>()
    val myInfo: LiveData<MyInfoResponseItem?> = _myInfo

    private val _showBottomSheet = MutableLiveData(false)
    val showBottomSheet: LiveData<Boolean> = _showBottomSheet

    private val _badgeItem = MutableLiveData<BadgeItem>()
    val badgeItem: LiveData<BadgeItem> = _badgeItem

    fun updateBottomSheet() {
        _showBottomSheet.value = !_showBottomSheet.value!!
    }

    fun updateBottomSheetData(badgeItem: BadgeItem) {
        _badgeItem.value = badgeItem
    }

    init {
        viewModelScope.launch {
            try {
                val response = myPageRepository.getMyInfo()
                _myInfo.postValue(response)

                Log.d("testtss", response.toString())
            } catch (e: Exception) {
            }
        }
    }
}
