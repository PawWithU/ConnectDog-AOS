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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kusitms.connectdog.core.designsystem.component.ConnectDogAlertDialog
import com.kusitms.connectdog.core.designsystem.component.ConnectDogBottomButton
import com.kusitms.connectdog.core.designsystem.component.InterApplicationBottomSheet
import com.kusitms.connectdog.core.designsystem.component.Loading
import com.kusitms.connectdog.core.designsystem.theme.Gray1
import com.kusitms.connectdog.core.designsystem.theme.Gray6
import com.kusitms.connectdog.core.model.InterApplication
import com.kusitms.connectdog.core.model.Volunteer
import com.kusitms.connectdog.feature.intermediator.R
import com.kusitms.connectdog.feature.intermediator.state.VolunteerBottomSheetUiState
import com.kusitms.connectdog.feature.intermediator.viewmodel.InterManagementViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun VolunteerBottomSheet(
    interApplication: InterApplication,
    sheetState: SheetState,
    onDismissRequest: () -> Unit,
    viewModel: InterManagementViewModel,
) {
    val data by viewModel.getVolunteerUiState.collectAsStateWithLifecycle()

    var isConfirmDialogVisible by remember { mutableStateOf(false) }
    var isRejectDialogVisible by remember { mutableStateOf(false) }

    when (data) {
        is VolunteerBottomSheetUiState.Loading -> Loading()
        is VolunteerBottomSheetUiState.VolunteerInfo -> {
            InterApplicationBottomSheet(
                titleRes = R.string.check_volunteer_top_title,
                application = (data as VolunteerBottomSheetUiState.VolunteerInfo).application,
                volunteer = (data as VolunteerBottomSheetUiState.VolunteerInfo).volunteer,
                sheetState = sheetState,
                onDismissRequest = onDismissRequest,
            ) {
                Row(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .padding(start = 20.dp, end = 20.dp, top = 24.dp, bottom = 52.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    ConnectDogBottomButton(
                        onClick = { isRejectDialogVisible = true },
                        content = stringResource(id = R.string.reject),
                        textColor = Gray1,
                        enabledColor = Gray6,
                        modifier = Modifier.weight(0.5f),
                    )
                    ConnectDogBottomButton(
                        onClick = { isConfirmDialogVisible = true },
                        content = stringResource(id = R.string.confirm),
                        textColor = MaterialTheme.colorScheme.onPrimary,
                        enabledColor = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.weight(0.5f),
                    )
                }
            }
        }
    }

    if (isConfirmDialogVisible) {
        ConfirmDialog(onDismiss = { isConfirmDialogVisible = false }) {
            interApplication.applicationId?.let { viewModel.confirmVolunteer(it) }
            onDismissRequest()
        }
    }

    if (isRejectDialogVisible) {
        RejectDialog(onDismiss = { isRejectDialogVisible = false }) {
            viewModel.rejectVolunteer(interApplication.applicationId!!)
            onDismissRequest()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Content(
    volunteer: Volunteer,
    application: InterApplication,
    sheetState: SheetState,
    onDismissRequest: () -> Unit,
) {
}

@Composable
private fun RejectDialog(
    onDismiss: () -> Unit,
    onOkClick: () -> Unit,
) {
    ConnectDogAlertDialog(
        onDismissRequest = onDismiss,
        titleRes = R.string.question_reject,
        descriptionRes = R.string.question_reject_description,
        okText = R.string.ok_reject,
        cancelText = R.string.cancel_back,
    ) {
        onOkClick()
    }
}

@Composable
private fun ConfirmDialog(
    onDismiss: () -> Unit,
    onOkClick: () -> Unit,
) {
    ConnectDogAlertDialog(
        onDismissRequest = onDismiss,
        titleRes = R.string.question_confirm,
        descriptionRes = R.string.question_confirm_description,
        okText = R.string.ok_confirm,
        cancelText = R.string.cancel_back,
    ) {
        onOkClick()
    }
}
