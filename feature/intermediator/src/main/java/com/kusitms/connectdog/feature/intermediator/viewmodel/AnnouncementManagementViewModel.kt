package com.kusitms.connectdog.feature.intermediator.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kusitms.connectdog.core.data.repository.InterManagementRepository
import com.kusitms.connectdog.feature.intermediator.state.AnnouncementManagementUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "AnnouncementManagementViewModel"

@HiltViewModel
class AnnouncementManagementViewModel
    @Inject
    constructor(
        private val repository: InterManagementRepository,
    ) : ViewModel() {
        private val _postId = MutableStateFlow<Long?>(null)
        private val _errorFlow = MutableSharedFlow<Throwable>()

        fun initializePostId(postId: Long) =
            viewModelScope.launch {
                _postId.emit(postId)
            }

        val announcementDetailState: StateFlow<AnnouncementManagementUiState> =
            flow {
                _postId.collect {
                    it?.let { emit(repository.getAnnouncementDetail(it)) }
                }
            }.map {
                AnnouncementManagementUiState.AnnouncementDetail(it)
            }.catch {
                _errorFlow.emit(it)
            }.stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = AnnouncementManagementUiState.Loading,
            )

        fun deleteAnnouncement() =
            viewModelScope.launch {
                try {
                    _postId.value?.let { repository.deleteAnnouncement(it) }
                } catch (e: Exception) {
                    Log.d(TAG, e.message.toString())
                }
            }
    }
