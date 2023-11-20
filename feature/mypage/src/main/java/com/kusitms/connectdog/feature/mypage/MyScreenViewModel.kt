package com.kusitms.connectdog.feature.mypage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MyScreenViewModel: ViewModel() {
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
}