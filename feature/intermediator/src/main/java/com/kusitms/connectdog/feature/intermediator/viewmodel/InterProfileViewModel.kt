package com.kusitms.connectdog.feature.intermediator.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kusitms.connectdog.core.data.api.model.intermediator.InterProfileInfoResponse
import com.kusitms.connectdog.core.data.repository.InterProfileRepository
import com.kusitms.connectdog.core.model.Announcement
import com.kusitms.connectdog.core.model.Review
import com.kusitms.connectdog.feature.intermediator.state.InterProfileFindingUiState
import com.kusitms.connectdog.feature.intermediator.state.InterProfileInfoUiState
import com.kusitms.connectdog.feature.intermediator.state.InterProfileReviewUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

private const val TAG = "InterProfileViewModel"

@HiltViewModel
class InterProfileViewModel
    @Inject
    constructor(
        private val repository: InterProfileRepository,
    ) : ViewModel() {
        private val _errorFlow = MutableSharedFlow<Throwable>()

        val interProfileInfoUiState: StateFlow<InterProfileInfoUiState> =
            createProfileInfoUiStateFlow {
                repository.getInterProfileInfo()
            }

        val interProfileReviewUiState: StateFlow<InterProfileReviewUiState> =
            createUiStateFlow {
                repository.getInterReview(0, 20)
            }

        val interProfileFindingUiState: StateFlow<InterProfileFindingUiState> =
            createFindingUiStateFlow {
                repository.getInterFinding(0, 20)
            }

        private fun createProfileInfoUiStateFlow(getData: suspend () -> InterProfileInfoResponse): StateFlow<InterProfileInfoUiState> =
            flow {
                emit(getData())
            }.map {
                InterProfileInfoUiState.InterProfile(it)
            }.stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = InterProfileInfoUiState.Loading,
            )

        private fun createFindingUiStateFlow(getData: suspend () -> List<Announcement>): StateFlow<InterProfileFindingUiState> =
            flow {
                emit(getData())
            }.map {
                if (it.isNotEmpty()) {
                    InterProfileFindingUiState.InterProfileFinding(it)
                } else {
                    InterProfileFindingUiState.Empty
                }
            }.stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = InterProfileFindingUiState.Loading,
            )

        private fun createUiStateFlow(getApplication: suspend () -> List<Review>): StateFlow<InterProfileReviewUiState> =
            flow {
                emit(getApplication())
            }.map {
                if (it.isNotEmpty()) {
                    InterProfileReviewUiState.InterProfileReview(it)
                } else {
                    InterProfileReviewUiState.Empty
                }
            }.catch {
                _errorFlow.emit(it)
                Log.e(TAG, "${it.message}")
            }.stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = InterProfileReviewUiState.Loading,
            )
    }
