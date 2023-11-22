package com.kusitms.connectdog.feature.mypage

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

    init {
        viewModelScope.launch {
            try {
                val myInfoResponse = myPageRepository.getMyInfo()
                val userInfoResponse = myPageRepository.getUserInfo()
                val badgeResponse = myPageRepository.getBadge()
                val bookmarkResponse = myPageRepository.getBookmarkData()

                _myInfo.postValue(myInfoResponse)
                _userInfo.postValue(userInfoResponse)
                _badge.postValue(badgeResponse)
                _bookmark.postValue(bookmarkResponse)

                Log.d(TAG, badgeResponse.toString())
                Log.d(TAG, myInfoResponse.toString())
                Log.d(TAG, userInfoResponse.toString())
                Log.d(TAG, bookmarkResponse.toString())
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
