package com.kusitms.connectdog.feature.intermediator

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kusitms.connectdog.core.data.repository.InterManagementRepository
import com.kusitms.connectdog.core.model.InterApplication
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

@HiltViewModel
class InterManagementViewModel @Inject constructor(
    managementRepository: InterManagementRepository
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
        createUiStateFlow {
            val applications = managementRepository.getApplicationCompleted()
            Log.d("ManagementViewModel", "completedUiState = $applications")
            applications
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
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = InterApplicationUiState.Loading
        )
}