package com.kusitms.connectdog.feature.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kusitms.connectdog.core.data.api.model.volunteer.NoticeDetailResponseItem
import com.kusitms.connectdog.core.data.repository.DetailRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "DetailViewModel"

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val detailRepository: DetailRepository
) : ViewModel() {
    private val _detail = MutableLiveData<NoticeDetailResponseItem>()
    val detail: LiveData<NoticeDetailResponseItem> = _detail

    fun initNoticeDetail(postId: Long) {
        viewModelScope.launch {
            val response = detailRepository.getNoticeDetail(postId)
            _detail.postValue(response)
        }
    }

    fun postBookmark(postId: Long) {
        viewModelScope.launch {
            try {
                val response = detailRepository.postBookmark(postId)
                Log.d(TAG, response.toString())
            } catch (e: Exception) {
                Log.d(TAG, e.message.toString())
            }
        }
    }

    fun deleteBookmark(postId: Long) {
        viewModelScope.launch {
            try {
                val response = detailRepository.deleteBookmark(postId)
                Log.d(TAG, response.toString())
            } catch (e: Exception) {
                Log.d(TAG, e.message.toString())
            }
        }
    }
}
