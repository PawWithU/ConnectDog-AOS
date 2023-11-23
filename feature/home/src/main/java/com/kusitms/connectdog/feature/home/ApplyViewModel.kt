package com.kusitms.connectdog.feature.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kusitms.connectdog.core.data.api.model.volunteer.ApplyBody
import com.kusitms.connectdog.core.data.repository.ApplyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ApplyViewModel @Inject constructor(
    private val applyRepository: ApplyRepository
): ViewModel() {
    private val _isCertified = MutableLiveData<Boolean>()
    val isCertified: LiveData<Boolean> =_isCertified

    fun postApplyVolunteer(postId: Long, applyBody: ApplyBody) {
        viewModelScope.launch {
            try {
                val response = applyRepository.postApplyVolunteer(postId, applyBody)
                Log.d("testtts", response.toString())
            } catch (e: Exception) {
                Log.d("testttserror", e.message.toString())
            }
        }
    }

    fun updateIsCertified(isCertified: Boolean) {
        _isCertified.postValue(isCertified)
    }
}