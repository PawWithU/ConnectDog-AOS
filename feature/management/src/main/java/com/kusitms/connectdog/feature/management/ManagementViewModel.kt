package com.kusitms.connectdog.feature.management

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kusitms.connectdog.core.data.repository.ManagementRepository
import com.kusitms.connectdog.core.model.Application
import com.kusitms.connectdog.core.model.DataUiState
import com.kusitms.connectdog.core.model.Volunteer
import dagger.hilt.android.lifecycle.HiltViewModel
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
class ManagementViewModel @Inject constructor(
    private val managementRepository: ManagementRepository
) : ViewModel() {
    private val TAG = "ManagementViewModel"

    private val _errorFlow = MutableSharedFlow<Throwable>()
    val errorFlow: SharedFlow<Throwable> get() = _errorFlow

    private val _waitingUiState = MutableStateFlow<ApplicationUiState>(ApplicationUiState.Loading)
    val waitingUiState: StateFlow<ApplicationUiState> = _waitingUiState

    val progressUiState: StateFlow<ApplicationUiState> =
        createUiStateFlow { managementRepository.getApplicationInProgress() }

    val completedUiState: StateFlow<ApplicationUiState> =
        createUiStateFlow { managementRepository.getApplicationCompleted() }

    private val _volunteerResponse = MutableLiveData<Volunteer>()
    val volunteerResponse: LiveData<Volunteer> get() = _volunteerResponse

    var selectedApplication: Application? = null

    private val _deleteDataState = MutableStateFlow<DataUiState>(DataUiState.Yet)
    val deleteDataUiState = _deleteDataState.asStateFlow()

    init {
        refreshWaitingApplications()
    }

    fun refreshWaitingApplications() {
        viewModelScope.launch {
            try {
                val applications = managementRepository.getApplicationWaiting()
                _waitingUiState.value = if (applications.isNotEmpty()) {
                    ApplicationUiState.Applications(applications)
                } else {
                    ApplicationUiState.Empty
                }
            } catch (e: Exception) {
                _errorFlow.emit(e)
                Log.e(TAG, "refreshWaitingApplications: ${e.message}")
            }
        }
    }

    fun getMyApplication(applicationId: Long) {
        viewModelScope.launch {
            try {
                _volunteerResponse.value = managementRepository.getMyApplication(applicationId)
            } catch (e: Exception) {
                Log.e(TAG, "getMyApplication ${e.stackTrace}")
            }
        }
    }

    fun deleteMyApplication(applicationId: Long) {
        viewModelScope.launch {
            try {
                managementRepository.deleteMyApplication(applicationId).let {
                    if (it.isSuccess) _deleteDataState.value = DataUiState.Success
                }
            } catch (e: Exception) {
                Log.e(TAG, "deleteMyApplication ${e.message}")
            }
        }
    }

    private fun createUiStateFlow(getApplication: suspend () -> List<Application>): StateFlow<ApplicationUiState> =
        flow {
            emit(getApplication())
        }.map {
            if (it.isNotEmpty()) {
                ApplicationUiState.Applications(it)
            } else {
                ApplicationUiState.Empty
            }
        }.catch {
            _errorFlow.emit(it)
            Log.e("InterManagementViewModel", "${it.message}")
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = ApplicationUiState.Loading
        )
}
