package com.kusitms.connectdog.feature.intermediator.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
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
import com.kusitms.connectdog.core.designsystem.component.ConnectDogBottomButton
import com.kusitms.connectdog.core.designsystem.component.InterApplicationBottomSheet
import com.kusitms.connectdog.core.designsystem.theme.Gray1
import com.kusitms.connectdog.core.designsystem.theme.Gray6
import com.kusitms.connectdog.core.model.InterApplication
import com.kusitms.connectdog.feature.intermediator.InterManagementViewModel
import com.kusitms.connectdog.feature.intermediator.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun VolunteerBottomSheet(
    interApplication: InterApplication,
    sheetState: SheetState,
    onDismissRequest: () -> Unit,
    viewModel: InterManagementViewModel
) {
    val volunteer by viewModel.volunteerResponse.observeAsState(null)
    interApplication.applicationId?.let { id -> viewModel.getVolunteer(id) }

    volunteer?.let { vol ->
        InterApplicationBottomSheet(
            titleRes = R.string.check_volunteer_top_title,
            interApplication = interApplication,
            volunteer = vol,
            sheetState = sheetState,
            onDismissRequest = onDismissRequest
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, end = 20.dp, top = 24.dp, bottom = 52.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                ConnectDogBottomButton(
                    onClick = { /*TODO*/ },
                    content = stringResource(id = R.string.reject),
                    textColor = Gray1,
                    color = Gray6,
                    modifier = Modifier.weight(0.5f)
                )
                ConnectDogBottomButton(
                    onClick = { /*TODO*/ },
                    content = stringResource(id = R.string.confirm),
                    textColor = MaterialTheme.colorScheme.onPrimary,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.weight(0.5f)
                )
            }
        }
    }
}
