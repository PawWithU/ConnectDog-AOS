package com.kusitms.connectdog.feature.management.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kusitms.connectdog.core.designsystem.component.AnnouncementItem
import com.kusitms.connectdog.core.designsystem.component.Empty
import com.kusitms.connectdog.core.designsystem.theme.Gray7
import com.kusitms.connectdog.core.model.Application
import com.kusitms.connectdog.feature.management.R
import com.kusitms.connectdog.feature.management.screen.Loading
import com.kusitms.connectdog.feature.management.screen.OutlinedButton
import com.kusitms.connectdog.feature.management.state.ApplicationUiState

@Composable
fun PendingApproval(
    uiState: ApplicationUiState,
    onClick: (Application) -> Unit
) {
    when (uiState) {
        is ApplicationUiState.Applications -> {
            LazyColumn(
                verticalArrangement = Arrangement.Top,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 80.dp)
            ) {
                items(uiState.applications) {
                    PendingContent(application = it, onClick = onClick)
                }
            }
            Spacer(modifier = Modifier.height(80.dp))
        }

        is ApplicationUiState.Empty -> {
            Empty(titleRes = R.string.no_pending, descriptionRes = R.string.pending_approval_empty)
        }

        is ApplicationUiState.Loading -> Loading()
    }
}

@Composable
private fun PendingContent(application: Application, onClick: (Application) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        Column(
            modifier = Modifier
                .padding(20.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Top
        ) {
            AnnouncementItem(
                imageUrl = application.imageUrl,
                dogName = application.dogName!!,
                location = application.location,
                isKennel = application.hasKennel,
                dogSize = application.dogSize!!,
                date = application.date,
                pickUpTime = application.pickUpTime ?: ""
            )
            OutlinedButton(modifier = Modifier.padding(top = 20.dp)) {
                onClick(application)
            }
        }
        Divider(thickness = 8.dp, color = Gray7)
    }
}
