package com.kusitms.connectdog.feature.mypage.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kusitms.connectdog.core.data.api.model.MyInfoResponseItem
import com.kusitms.connectdog.core.data.api.model.volunteer.BadgeResponse
import com.kusitms.connectdog.core.data.api.model.volunteer.BookmarkResponseItem
import com.kusitms.connectdog.core.data.api.model.volunteer.UserInfoResponse
import com.kusitms.connectdog.core.data.repository.MyPageRepository
import com.kusitms.connectdog.feature.mypage.screen.BadgeItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "MyPageViewModel"

@HiltViewModel
class MyPageViewModel @Inject constructor(
    private val myPageRepository: MyPageRepository
) : ViewModel() {
    private val _myInfo = MutableLiveData<MyInfoResponseItem?>()
    val myInfo: LiveData<MyInfoResponseItem?> = _myInfo

    private val _userInfo = MutableLiveData<UserInfoResponse?>()
    val userInfo: LiveData<UserInfoResponse?> = _userInfo

    private val _badge = MutableLiveData<List<BadgeResponse>>()
    val badge: LiveData<List<BadgeResponse>> = _badge

    private val _bookmark = MutableLiveData<List<BookmarkResponseItem>>()
    val bookmark: LiveData<List<BookmarkResponseItem>> = _bookmark

    private val _showBottomSheet = MutableLiveData(false)
    val showBottomSheet: LiveData<Boolean> = _showBottomSheet

    private val _badgeItem = MutableLiveData<BadgeItem>()
    val badgeItem: LiveData<BadgeItem> = _badgeItem

    fun fetchUserInfo() {
        viewModelScope.launch {
            try {
                val myInfoResponse = myPageRepository.getMyInfo()
                val userInfoResponse = myPageRepository.getUserInfo()

                _myInfo.postValue(myInfoResponse)
                _userInfo.postValue(userInfoResponse)
            } catch (e: Exception) {
                Log.d(TAG, e.message.toString())
            }
        }
    }

    fun fetchBookmark() {
        viewModelScope.launch {
            try {
                val response = myPageRepository.getBadge()
                _badge.postValue(response)
            } catch (e: Exception) {
                Log.d(TAG, e.message.toString())
            }
        }
    }

    fun fetchBadge() {
        viewModelScope.launch {
            try {
                val response = myPageRepository.getBookmarkData()
                _bookmark.postValue(response)
            } catch (e: Exception) {
                Log.d(TAG, e.message.toString())
            }
        }
    }

    fun updateBottomSheet() {
        _showBottomSheet.value = !_showBottomSheet.value!!
    }

    fun updateBottomSheetData(badgeItem: BadgeItem) {
        _badgeItem.value = badgeItem
    }
}
