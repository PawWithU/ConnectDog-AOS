package com.kusitms.connectdog.feature.mypage.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kusitms.connectdog.domain.usecase.notification.GetNotificationUseCase
import com.kusitms.connectdog.feature.mypage.state.NotificationSideEffect
import com.kusitms.connectdog.feature.mypage.state.NotificationUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class NotificationViewModel @Inject constructor (
    private val getNotificationUseCase: GetNotificationUseCase
): ContainerHost<NotificationUiState, NotificationSideEffect>, ViewModel() {
    override val container: Container<NotificationUiState, NotificationSideEffect> = container(NotificationUiState.empty())
    private val state: NotificationUiState
        get() = container.stateFlow.value

    init {
        getNotificationList()
    }

    private fun getNotificationList() = viewModelScope.launch {
        getNotificationUseCase().onSuccess {
            intent { reduce { state.copy(notificationList = it) }}
        }.onFailure {

        }
    }
}