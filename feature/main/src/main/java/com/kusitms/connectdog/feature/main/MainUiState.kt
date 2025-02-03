package com.kusitms.connectdog.feature.main

import com.kusitms.connectdog.domain.usecase.login.AppMode

data class MainUiState(
    val appMode: AppMode?
) {
    companion object {
        fun empty() = MainUiState(
            appMode = null
        )
    }
}

sealed class MainSideEffect