package com.kusitms.connectdog.feature.management.viewmodel

import android.net.Uri
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class CreateReviewViewModel
    @Inject
    constructor() : ViewModel() {
        private val _uriList = MutableStateFlow<MutableList<Uri>>(mutableListOf())
        val uriList: StateFlow<List<Uri>>
            get() = _uriList

        private val _review: MutableState<String> = mutableStateOf("")
        val review: String
            get() = _review.value

        fun updateReview(review: String) {
            _review.value = review
        }

        fun updateUriList(uri: Uri) {
            _uriList.value = _uriList.value.toMutableList().apply { add(uri) }
        }

        fun removeUriList(uri: Uri) {
            _uriList.value = _uriList.value.toMutableList().apply { remove(uri) }
        }
    }
