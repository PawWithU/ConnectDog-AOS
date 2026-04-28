package com.kusitms.connectdog.feature.management.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kusitms.connectdog.core.data.repository.ManagementRepository
import com.kusitms.connectdog.core.model.Application
import com.kusitms.connectdog.core.model.DataUiState
import com.kusitms.connectdog.core.model.Volunteer
import com.kusitms.connectdog.feature.management.state.ApplicationUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ManagementViewModel @Inject constructor(
    private val managementRepository: ManagementRepository
) : ViewModel() {
    private val TAG = "ManagementViewModel"

    init {
        refreshAllApplications()
    }

    private val _errorFlow = MutableSharedFlow<Throwable>()
    val errorFlow: SharedFlow<Throwable> get() = _errorFlow

    private val _waitingUiState = MutableStateFlow<ApplicationUiState>(ApplicationUiState.Loading)
    val waitingUiState: StateFlow<ApplicationUiState> = _waitingUiState

    private val _progressUiState = MutableStateFlow<ApplicationUiState>(ApplicationUiState.Loading)
    val progressUiState: StateFlow<ApplicationUiState> = _progressUiState

    private val _completedUiState = MutableStateFlow<ApplicationUiState>(ApplicationUiState.Loading)
    val completedUiState: StateFlow<ApplicationUiState> = _completedUiState

    private val _volunteer = MutableStateFlow<Volunteer?>(null)
    val volunteer: StateFlow<Volunteer?> get() = _volunteer

    private val _selectedApplication = MutableStateFlow<Application?>(null)
    val selectedApplication: StateFlow<Application?> get() = _selectedApplication

    private val _deleteDataState = MutableStateFlow<DataUiState>(DataUiState.Yet)
    val deleteDataUiState = _deleteDataState.asStateFlow()

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean> = _isRefreshing

    fun refreshAllApplications() {
        refreshWaitingApplications()
        refreshProgressApplications()
        refreshCompletedApplications()
    }

    fun refreshByTabIndex(tabIndex: Int) {
        when (tabIndex) {
            0 -> refreshWaitingApplications()
            1 -> refreshProgressApplications()
            2 -> refreshCompletedApplications()
        }
    }

    fun refreshCurrentTab(tabIndex: Int) {
        viewModelScope.launch {
            _isRefreshing.value = true
            try {
                when (tabIndex) {
                    0 -> {
                        _waitingUiState.value = ApplicationUiState.Loading
                        refreshWaitingApplications()
                    }
                    1 -> {
                        _progressUiState.value = ApplicationUiState.Loading
                        refreshProgressApplications()
                    }
                    2 -> {
                        _completedUiState.value = ApplicationUiState.Loading
                        refreshCompletedApplications()
                    }
                }
            } finally {
                _isRefreshing.value = false
            }
        }
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
                _waitingUiState.value = ApplicationUiState.Empty
                Log.e(TAG, "refreshWaitingApplications: ${e.message}")
            } finally {
                _isRefreshing.value = false
            }
        }
    }

    fun refreshProgressApplications() {
        viewModelScope.launch {
            try {
                val applications = managementRepository.getApplicationInProgress()
                _progressUiState.value = if (applications.isNotEmpty()) {
                    ApplicationUiState.Applications(applications)
                } else {
                    ApplicationUiState.Empty
                }
            } catch (e: Exception) {
                _errorFlow.emit(e)
                _progressUiState.value = ApplicationUiState.Empty
                Log.e(TAG, "refreshProgressApplications: ${e.message}")
            } finally {
                _isRefreshing.value = false
            }
        }
    }

    fun refreshCompletedApplications() {
        viewModelScope.launch {
            try {
                val applications = managementRepository.getApplicationCompleted()
                _completedUiState.value = if (applications.isNotEmpty()) {
                    ApplicationUiState.Applications(applications)
                } else {
                    ApplicationUiState.Empty
                }
            } catch (e: Exception) {
                _errorFlow.emit(e)
                _completedUiState.value = ApplicationUiState.Empty
                Log.e(TAG, "refreshCompletedApplications: ${e.message}")
            } finally {
                _isRefreshing.value = false
            }
        }
    }

    fun updateSelectedApplication(application: Application) {
        _selectedApplication.value = application
    }

    fun getVolunteerInfo(applicationId: Long) {
        viewModelScope.launch {
            try {
                _volunteer.value = managementRepository.getMyApplication(applicationId)
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
}
