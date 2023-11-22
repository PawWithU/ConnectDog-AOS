package com.kusitms.connectdog.feature.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kusitms.connectdog.core.data.api.model.volunteer.NoticeDetailResponseItem
import com.kusitms.connectdog.core.data.repository.DetailRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val detailRepository: DetailRepository
): ViewModel() {
    private val _detail = MutableLiveData<NoticeDetailResponseItem>()
    val detail: LiveData<NoticeDetailResponseItem> = _detail

    fun initNoticeDetail(postId: Long) {
        viewModelScope.launch {
            val response = detailRepository.getNoticeDetail(postId)
            _detail.postValue(response)
        }
    }
}