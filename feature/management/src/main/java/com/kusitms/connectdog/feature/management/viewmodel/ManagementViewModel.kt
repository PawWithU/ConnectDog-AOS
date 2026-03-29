package com.kusitms.connectdog.feature.management.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kusitms.connectdog.core.data.repository.ManagementRepository
import com.kusitms.connectdog.core.model.Application
import com.kusitms.connectdog.core.model.DataUiState
import com.kusitms.connectdog.core.model.Volunteer
import com.kusitms.connectdog.feature.management.state.ApplicationConfirmUiState
import com.kusitms.connectdog.feature.management.state.ApplicationUiState
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

    init {
        refreshWaitingApplications()
        refreshCompletedApplications()
    }

    private val _errorFlow = MutableSharedFlow<Throwable>()
    val errorFlow: SharedFlow<Throwable> get() = _errorFlow

    private val _waitingUiState = MutableStateFlow<ApplicationUiState>(ApplicationUiState.Loading)
    val waitingUiState: StateFlow<ApplicationUiState> = _waitingUiState

    val progressUiState: StateFlow<ApplicationUiState> =
        createUiStateFlow { managementRepository.getApplicationInProgress() }

    private val _completedUiState = MutableStateFlow<ApplicationUiState>(ApplicationUiState.Loading)
    val completedUiState: StateFlow<ApplicationUiState> = _completedUiState.asStateFlow()

    private val _volunteer = MutableStateFlow<Volunteer?>(null)
    val volunteer: StateFlow<Volunteer?> get() = _volunteer

    private val _selectedApplication = MutableStateFlow<Application?>(null)
    val selectedApplication: StateFlow<Application?> get() = _selectedApplication

    private val _deleteDataState = MutableStateFlow<DataUiState>(DataUiState.Yet)
    val deleteDataUiState = _deleteDataState.asStateFlow()

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

    private fun refreshCompletedApplications() {
        viewModelScope.launch {
            try {
                val applications = managementRepository.getApplicationCompleted()
                Log.d("asdfasdf", applications.toString())
                _completedUiState.value = if (applications.isNotEmpty()) {
                    ApplicationUiState.Applications(applications)
                } else {
                    ApplicationUiState.Empty
                }
            } catch (e: Exception) {
                _errorFlow.emit(e)
                Log.e(TAG, "refreshCompletedApplications: ${e.message}")
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

    private fun createUiStateFlow(
        selectedApplication: () -> Application,
        getVolunteer: suspend (Long) -> Volunteer
    ): StateFlow<ApplicationConfirmUiState> =
        flow<ApplicationConfirmUiState> {
            val application = selectedApplication()
            val volunteer = getVolunteer(application.applicationId!!)
            emit(ApplicationConfirmUiState.ApplicationConfirm(application, volunteer))
        }.catch {
            _errorFlow.emit(it)
            Log.e("InterManagementViewModel", "${it.message}")
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = ApplicationConfirmUiState.Loading
        )

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
            started = SharingStarted.Eagerly,
            initialValue = ApplicationUiState.Loading
        )
}
