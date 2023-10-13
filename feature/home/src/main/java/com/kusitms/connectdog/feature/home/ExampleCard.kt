package com.kusitms.connectdog.feature.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kusitms.connectdog.core.designsystem.component.ConnectDogCard

@Composable
internal fun ExampleCard(uiState: ExampleUiState) {
    when (uiState) {
        ExampleUiState.Empty -> Unit
        ExampleUiState.Loading -> ExampleCardSkeleton()
        is ExampleUiState.Examples -> ExampleCardContent(uiState = uiState)
    }
}

@Composable
private fun ExampleCardContent(uiState: ExampleUiState.Examples) {
    ConnectDogCard {
        Column(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(horizontal = 24.dp)) {
            }
        }
    }
}

@Composable
private fun ExampleCardSkeleton() {
}
