package com.kusitms.connectdog.feature.management

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.kusitms.connectdog.core.designsystem.component.ApplicationBottomSheet
import com.kusitms.connectdog.core.designsystem.component.ConnectDogBottomButton
import com.kusitms.connectdog.core.model.Application

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun MyApplicationBottomSheet(
    application: Application,
    sheetState: SheetState,
    onDismissRequest: () -> Unit,
    viewModel: ManagementViewModel
) {
    val volunteer by viewModel.volunteerResponse.observeAsState(null)
    application.applicationId?.let { id -> viewModel.getMyApplication(id) }

    volunteer?.let {
        ApplicationBottomSheet(
            titleRes = R.string.check_my_appliance,
            application = application,
            volunteer = it,
            sheetState = sheetState,
            onDismissRequest = onDismissRequest
        ) {
            ConnectDogBottomButton(
                onClick = { /*TODO*/ },
                content = stringResource(id = R.string.cancel_appliance),
                textColor = MaterialTheme.colorScheme.error,
                color = MaterialTheme.colorScheme.surface,
                border = BorderStroke(width = 1.dp, color = MaterialTheme.colorScheme.error),
                modifier = Modifier.padding(start = 20.dp, end = 20.dp, top = 24.dp, bottom = 52.dp)
            )
        }
    }
}
