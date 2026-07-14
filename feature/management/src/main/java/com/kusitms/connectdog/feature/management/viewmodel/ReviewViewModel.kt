package com.kusitms.connectdog.feature.management.viewmodel

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kusitms.connectdog.core.data.api.model.volunteer.ReviewBody
import com.kusitms.connectdog.core.data.mapper.toData
import com.kusitms.connectdog.core.data.repository.ManagementRepository
import com.kusitms.connectdog.core.util.ImageConverter.uriToFile
import com.kusitms.connectdog.core.util.UserType
import com.kusitms.connectdog.feature.management.state.ReviewUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReviewViewModel
    @Inject
    constructor(
        private val repository: ManagementRepository,
    ) : ViewModel() {
        private val _errorFlow = MutableSharedFlow<Throwable>()
        val errorFlow: SharedFlow<Throwable> get() = _errorFlow

        private val _uriList = MutableStateFlow<MutableList<Uri>>(mutableListOf())
        val uriList: StateFlow<List<Uri>>
            get() = _uriList

        private val _review: MutableState<String> = mutableStateOf("")
        val review: String
            get() = _review.value

        private val _postId = MutableStateFlow<Long?>(null)

        private val _reviewId = MutableStateFlow<Long?>(null)
        private val _userType = MutableStateFlow<UserType?>(null)

        fun updateReview(review: String) {
            _review.value = review
        }

        fun updateUriList(uri: Uri) {
            _uriList.value = _uriList.value.toMutableList().apply { add(uri) }
        }

        fun removeUriList(uri: Uri) {
            _uriList.value = _uriList.value.toMutableList().apply { remove(uri) }
        }

        fun updateReviewId(reviewId: Long) =
            viewModelScope.launch {
                _reviewId.emit(reviewId)
            }

        fun updatePostId(postId: Long) =
            viewModelScope.launch {
                _postId.value = postId
            }

        val reviewUiState: StateFlow<ReviewUiState> =
            flow {
                _reviewId.collect {
                    it?.let { emit(repository.getReview(it).toData()) }
                }
            }.map {
                ReviewUiState.Reviews(it)
            }.catch {
                _errorFlow.emit(it)
            }.stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = ReviewUiState.Loading,
            )

        fun createReview(context: Context) =
            viewModelScope.launch {
                val files =
                    _uriList.value.mapNotNull { uri ->
                        uriToFile(context, uri, 80)
                    }

                val body =
                    ReviewBody(
                        content = _review.value,
                    )

                try {
                    repository.postReview(_postId.value!!, body, files)
                } catch (e: CancellationException) {
                    Log.d("createReview", "Coroutine was cancelled", e)
                } catch (e: Exception) {
                    Log.d("createReview", "An error occurred", e)
                }
            }
    }
