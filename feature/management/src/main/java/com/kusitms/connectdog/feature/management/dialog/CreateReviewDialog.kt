package com.kusitms.connectdog.feature.management.dialog

import androidx.compose.runtime.Composable
import com.kusitms.connectdog.core.designsystem.component.ConnectDogAlertDialog
import com.kusitms.connectdog.feature.management.R

@Composable
internal fun CreateReviewDialog(
    onConfirmClick: () -> Unit,
    onDismiss: () -> Unit,
) {
    ConnectDogAlertDialog(
        onDismissRequest = onDismiss,
        titleRes = R.string.dialog_title,
        descriptionRes = R.string.dialog_description,
        okText = R.string.dialog_confirm,
        cancelText = R.string.cancel_back,
    ) {
        onConfirmClick()
    }
}
