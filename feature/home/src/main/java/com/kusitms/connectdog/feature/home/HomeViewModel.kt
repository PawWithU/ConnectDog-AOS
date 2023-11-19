package com.kusitms.connectdog.feature.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kusitms.connectdog.core.data.repository.HomeRepository
import com.kusitms.connectdog.feature.home.state.AnnouncementUiState
import com.kusitms.connectdog.feature.home.state.ReviewUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

private val TAG = "HomeViewModel"

@HiltViewModel
class HomeViewModel
@Inject
constructor(
    homeRepository: HomeRepository
) : ViewModel() {
    private val _errorFlow = MutableSharedFlow<Throwable>()
    val errorFlow: SharedFlow<Throwable> get() = _errorFlow

    val announcementUiState: StateFlow<AnnouncementUiState> =
        flow {
            emit(homeRepository.getAnnouncementList())
        }.map {
            Log.d(TAG, "announcementUiState = ${it.size}")
            if (it.isNotEmpty()) {
                AnnouncementUiState.Announcements(it)
            } else {
                AnnouncementUiState.Empty
            }
        }.catch {
            _errorFlow.emit(it)
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = AnnouncementUiState.Loading
        )

    val reviewUiState: StateFlow<ReviewUiState> =
        flow {
            emit(homeRepository.getReviewList())
        }.map {
            Log.d(TAG, "reviewUiState = ${it.size}")
            if (it.isNotEmpty()) {
                ReviewUiState.Reviews(it)
            } else {
                ReviewUiState.Empty
            }
        }.catch {
            _errorFlow.emit(it)
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = ReviewUiState.Loading
        )
}
