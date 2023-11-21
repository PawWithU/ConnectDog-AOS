package com.kusitms.connectdog.feature.intermediator

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kusitms.connectdog.core.data.repository.InterManagementRepository
import com.kusitms.connectdog.core.model.Application
import com.kusitms.connectdog.core.model.InterApplication
import com.kusitms.connectdog.core.model.Volunteer
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@HiltViewModel
class InterManagementViewModel @Inject constructor(
    private val managementRepository: InterManagementRepository
): ViewModel(){
    private val _errorFlow = MutableSharedFlow<Throwable>()
    val errorFlow: SharedFlow<Throwable> get() = _errorFlow

    val recruitingUiState: StateFlow<InterApplicationUiState> =
        createUiStateFlow { managementRepository.getApplicationRecruiting() }

    val waitingUiState: StateFlow<InterApplicationUiState> =
        createUiStateFlow { managementRepository.getApplicationWaiting() }

    val progressUiState: StateFlow<InterApplicationUiState> =
        createUiStateFlow { managementRepository.getApplicationInProgress() }

    val completedUiState: StateFlow<InterApplicationUiState> =
        createUiStateFlow { managementRepository.getApplicationCompleted() }

    private val _volunteerResponse = MutableLiveData<Volunteer>()
    val volunteerResponse: LiveData<Volunteer> get() = _volunteerResponse

    var selectedApplication: InterApplication? = null

    fun getVolunteer(applicationId: Long) {
        viewModelScope.launch {
            try {
                _volunteerResponse.value = managementRepository.getApplicationVolunteer(applicationId)
            } catch (e: Exception) {
                Log.e("InterManagementViewModel", "getVolunteer ${e.stackTrace}")
            }
        }
    }

    private fun createUiStateFlow(getApplication: suspend () -> List<InterApplication>) =
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