package com.kusitms.connectdog.feature.management

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kusitms.connectdog.core.data.repository.ManagementRepository
import com.kusitms.connectdog.core.model.Application
import com.kusitms.connectdog.core.model.Volunteer
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
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
class ManagementViewModel @Inject constructor(
    private val managementRepository: ManagementRepository
) : ViewModel() {
    private val TAG = "ManagementViewModel"

    private val _errorFlow = MutableSharedFlow<Throwable>()
    val errorFlow: SharedFlow<Throwable> get() = _errorFlow

    val waitingUiState: StateFlow<ApplicationUiState> =
        flow {
            emit(managementRepository.getApplicationWaiting())
        }.map {
            if (it.isNotEmpty()) {
                ApplicationUiState.Applications(it)
            } else {
                ApplicationUiState.Empty
            }
        }.catch {
            _errorFlow.emit(it)
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = ApplicationUiState.Loading
        )

    val progressUiState: StateFlow<ApplicationUiState> =
        flow {
            emit(managementRepository.getApplicationInProgress())
        }.map {
            if (it.isNotEmpty()) {
                ApplicationUiState.Applications(it)
            } else {
                ApplicationUiState.Empty
            }
        }.catch {
            _errorFlow.emit(it)
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = ApplicationUiState.Loading
        )

    val completedUiState: StateFlow<ApplicationUiState> =
        flow {
            emit(managementRepository.getApplicationCompleted())
        }.map {
            Log.d("ManagementViewModel", "completedUiState = $it")
            if (it.isNotEmpty()) {
                ApplicationUiState.Applications(it)
            } else {
                ApplicationUiState.Empty
            }
        }.catch {
            Log.e("ManagementViewModel", "completedUiState = $it")
            _errorFlow.emit(it)
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = ApplicationUiState.Loading
        )

    private val _volunteerResponse = MutableLiveData<Volunteer>()
    val volunteerResponse: LiveData<Volunteer> get() = _volunteerResponse

    var selectedApplication: Application? = null

    fun getMyApplication(applicationId: Long) {
        viewModelScope.launch {
            try {
                _volunteerResponse.value = managementRepository.getMyApplication(applicationId)
            } catch (e: Exception) {
                Log.e(TAG, "getMyApplication ${e.stackTrace}")
            }
        }
    }
}
