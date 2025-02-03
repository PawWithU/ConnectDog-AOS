package com.kusitms.connectdog.feature.main

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kusitms.connectdog.domain.usecase.GetAppModeUseCase
import com.kusitms.connectdog.domain.usecase.UpdateFcmTokenUseCase
import com.kusitms.connectdog.feature.login.state.LoginSideEffect
import com.kusitms.connectdog.feature.login.state.LoginUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

private const val TAG = "MAIN_VIEWMODEL"

@HiltViewModel
class MainViewModel @Inject constructor(
    private val updateFcmTokenUseCase: UpdateFcmTokenUseCase,
    private val getAppModeUseCase: GetAppModeUseCase
): ContainerHost<MainUiState, MainSideEffect>, ViewModel() {
    override val container: Container<MainUiState, MainSideEffect> = container(MainUiState.empty())

    init {
        initAppMode()
    }

    private fun initAppMode() = viewModelScope.launch {
        getAppModeUseCase().collect { appMode ->
            intent { reduce { state.copy(appMode = appMode) } }
        }
    }

    fun updateFcmToken(token: String) = viewModelScope.launch {
        updateFcmTokenUseCase(token)
    }
}
