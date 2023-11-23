package com.kusitms.connectdog.feature.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kusitms.connectdog.core.data.api.model.intermediator.IntermediatorInfoResponseItem
import com.kusitms.connectdog.core.data.api.model.volunteer.BookmarkResponseItem
import com.kusitms.connectdog.core.data.repository.DetailRepository
import com.kusitms.connectdog.core.model.Review
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class IntermediatorProfileViewModel @Inject constructor(
    private val detailRepository: DetailRepository
) : ViewModel() {
    private val _intermediator = MutableLiveData<IntermediatorInfoResponseItem>()
    val intermediator: LiveData<IntermediatorInfoResponseItem> = _intermediator

    private val _notice = MutableLiveData<List<BookmarkResponseItem>>()
    val notice: LiveData<List<BookmarkResponseItem>> = _notice

    private val _review = MutableLiveData<List<Review>>()
    val review: LiveData<List<Review>> = _review

    fun initIntermediatorProfile(intermediaryId: Long) {
        viewModelScope.launch {
            val response = detailRepository.getIntermediatorInfo(intermediaryId)
            _intermediator.postValue(response)
        }
    }

    fun initIntermediatorNotice(intermediaryId: Long) {
        viewModelScope.launch {
            val response = detailRepository.getIntermediatorNotice(intermediaryId)
            _notice.postValue(response)
        }
    }

    fun initIntermediatorReview(intermediaryId: Long) {
        viewModelScope.launch {
            val response = detailRepository.getIntermediatorReview(0, 20, intermediaryId)
            _review.postValue(response)
        }
    }
}
