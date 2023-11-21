package com.kusitms.connectdog.feature.management

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kusitms.connectdog.core.data.repository.ManagementRepository
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
class ManagementViewModel @Inject constructor(
    managementRepository: ManagementRepository
): ViewModel(){
    private val _errorFlow = MutableSharedFlow<Throwable>()
    val errorFlow: SharedFlow<Throwable> get() = _errorFlow

    val waitingUiState: StateFlow<ApplicationUiState> =
        flow{
            emit(managementRepository.getApplicationWaiting())
        }.map {
            if (it.isNotEmpty()){
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
        flow{
            emit(managementRepository.getApplicationInProgress())
        }.map {
            if (it.isNotEmpty()){
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
        flow{
            emit(managementRepository.getApplicationCompleted())
        }.map {
            Log.d("ManagementViewModel", "completedUiState = $it")
            if (it.isNotEmpty()){
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

    private fun getMyApplication(applicationId: Long){
        viewModelScope.launch {

        }
    }
}