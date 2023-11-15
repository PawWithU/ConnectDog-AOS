package com.kusitms.connectdog.feature.signup.volunteer

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SelectProfileImageViewModel : ViewModel() {
    private val _selectedImageIndex = MutableLiveData<Int?>()
    val selectedImageIndex: LiveData<Int?> = _selectedImageIndex

    private val _isSelected = MutableLiveData<Boolean>()
    val isSelected: LiveData<Boolean> = _isSelected

    fun updateSelectedImageIndex(index: Int) {
        _selectedImageIndex.value = index
    }
}
