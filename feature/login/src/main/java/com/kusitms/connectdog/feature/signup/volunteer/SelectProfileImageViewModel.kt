package com.kusitms.connectdog.feature.signup.volunteer

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kusitms.connectdog.feature.login.R
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class SelectProfileImageViewModel @Inject constructor() : ViewModel() {
    private val _selectedImageIndex = MutableStateFlow(com.kusitms.connectdog.core.designsystem.R.drawable.ic_circle)
    val selectedImageIndex: StateFlow<Int>
        get() = _selectedImageIndex.asStateFlow()

    private val _isSelected = MutableLiveData<Boolean>()
    val isSelected: LiveData<Boolean> = _isSelected

    fun updateSelectedImageIndex(index: Int) {
        _selectedImageIndex.value = index
    }
}
