package com.kusitms.connectdog.feature.intermediator

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kusitms.connectdog.core.data.repository.InterManagementRepository
import com.kusitms.connectdog.core.model.DataUiState
import com.kusitms.connectdog.core.model.InterApplication
import com.kusitms.connectdog.core.model.Volunteer
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InterManagementViewModel @Inject constructor(
    private val managementRepository: InterManagementRepository
) : ViewModel() {
    private val TAG = "InterManagementViewModel"

    private val _errorFlow = MutableSharedFlow<Throwable>()
    val errorFlow: SharedFlow<Throwable> get() = _errorFlow

    val recruitingUiState: StateFlow<InterApplicationUiState> =
        createUiStateFlow { managementRepository.getApplicationRecruiting() }

    private val _waitingUiState =
        MutableStateFlow<InterApplicationUiState>(InterApplicationUiState.Loading)
    val waitingUiState: StateFlow<InterApplicationUiState> = _waitingUiState

    private val _progressUiState =
        MutableStateFlow<InterApplicationUiState>(InterApplicationUiState.Loading)
    val progressUiState: StateFlow<InterApplicationUiState> = _progressUiState

    private val _completedUiState = MutableStateFlow<InterApplicationUiState>(InterApplicationUiState.Loading)
    val completedUiState: StateFlow<InterApplicationUiState> = _completedUiState

    private val _volunteerResponse = MutableLiveData<Volunteer>()
    val volunteerResponse: LiveData<Volunteer> get() = _volunteerResponse

    var selectedApplication: InterApplication? = null

    private val _pendingDataState = MutableStateFlow<DataUiState>(DataUiState.Yet)
    val pendingDataState = _pendingDataState.asStateFlow()

    private val _progressDataState = MutableStateFlow<DataUiState>(DataUiState.Yet)
    val progressDataState = _progressDataState.asStateFlow()

    init {
        refreshWaitingUiState()
        refreshInProgressUiState()
        refreshCompletedUiState()
    }

    fun getVolunteer(applicationId: Long) {
        viewModelScope.launch {
            try {
                _volunteerResponse.value =
                    managementRepository.getApplicationVolunteer(applicationId)
            } catch (e: Exception) {
                Log.e(TAG, "getVolunteer ${e.stackTrace}")
            }
        }
    }

    fun confirmVolunteer(applicationId: Long) {
        _pendingDataState.value = DataUiState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            try {
                managementRepository.confirmApplicationVolunteer(applicationId).let {
                    if (it.isSuccess) _pendingDataState.value = DataUiState.Success
                }
            } catch (e: Exception) {
                Log.e(TAG, "confirmVolunteer ${e.message}")
            }
        }
    }

    fun rejectVolunteer(applicationId: Long) {
        _pendingDataState.value = DataUiState.Loading
        viewModelScope.launch {
            try {
                managementRepository.rejectApplicationVolunteer(applicationId).let {
                    if (it.isSuccess) _pendingDataState.value = DataUiState.Success
                }
            } catch (e: Exception) {
                Log.e(TAG, "rejectVolunteer ${e.message}")
            }
        }
    }

    fun completeApplication(applicationId: Long) {
        _progressDataState.value = DataUiState.Loading
        viewModelScope.launch {
            try {
                managementRepository.completeApllication(applicationId).let {
                    if (it.isSuccess) _progressDataState.value = DataUiState.Success
                }
            } catch (e: Exception) {
                Log.e(TAG, "completeApplication ${e.message}")
            }
        }
    }

    fun refreshWaitingUiState() {
        viewModelScope.launch {
            refreshUiState(
                getApplications = { managementRepository.getApplicationWaiting() },
                uiState = _waitingUiState,
                tag = "refreshWaitingUiState"
            )
        }
    }

    fun refreshInProgressUiState() {
        viewModelScope.launch {
            refreshUiState(
                getApplications = { managementRepository.getApplicationInProgress() },
                uiState = _progressUiState,
                tag = "refreshCompletedUiState"
            )
        }
    }

    fun refreshCompletedUiState() {
        viewModelScope.launch {
            refreshUiState(
                getApplications = { managementRepository.getApplicationCompleted() },
                uiState = _completedUiState,
                tag = "refreshCompletedUiState"
            )
        }
    }

    private suspend fun refreshUiState(
        getApplications: suspend () -> List<InterApplication>,
        uiState: MutableStateFlow<InterApplicationUiState>,
        tag: String
    ) {
        try {
            val applications = getApplications()
            uiState.value = if (applications.isNotEmpty()) {
                InterApplicationUiState.InterApplications(applications)
            } else {
                InterApplicationUiState.Empty
            }
            Log.d(TAG, "$tag = $applications")
        } catch (e: Exception) {
            _errorFlow.emit(e)
            Log.e("InterManagementViewModel", "${e.message}")
        }
    }

    private fun createUiStateFlow(getApplication: suspend () -> List<InterApplication>): StateFlow<InterApplicationUiState> =
        flow {
            emit(getApplication())
        }.map {
            if (it.isNotEmpty()) {
                InterApplicationUiState.InterApplications(it)
            } else {
                InterApplicationUiState.Empty
            }
        }.catch {
            _errorFlow.emit(it)
            Log.e("InterManagementViewModel", "${it.message}")
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = InterApplicationUiState.Loading
        )
}
