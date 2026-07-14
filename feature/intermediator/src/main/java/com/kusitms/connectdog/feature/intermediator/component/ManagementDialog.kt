package com.kusitms.connectdog.feature.intermediator.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.kusitms.connectdog.core.designsystem.component.ConnectDogAlertDialog
import com.kusitms.connectdog.core.designsystem.component.ConnectDogBottomButton
import com.kusitms.connectdog.core.designsystem.theme.Gray2
import com.kusitms.connectdog.feature.intermediator.R

@Composable
internal fun CompletedDialog(onDismissRequest: () -> Unit) {
    ConnectDogAlertDialog(onDismissRequest = onDismissRequest) {
        Column(
            modifier =
                Modifier
                    .background(
                        color = MaterialTheme.colorScheme.surface,
                        shape = RoundedCornerShape(20.dp),
                    )
                    .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Image(
                painter = painterResource(id = R.drawable.img_dog_running),
                contentDescription = stringResource(id = R.string.dialog_completed_volunteer),
            )
            Spacer(modifier = Modifier.size(30.dp))
            Column(
                verticalArrangement = Arrangement.spacedBy(9.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = stringResource(id = R.string.dialog_completed_volunteer),
                    style = MaterialTheme.typography.headlineSmall,
                )
                Text(
                    text = stringResource(id = R.string.dialog_completed_description),
                    color = Gray2,
                    style = MaterialTheme.typography.bodyMedium,
                )
            }
            Spacer(modifier = Modifier.size(40.dp))
            ConnectDogBottomButton(
                onClick = onDismissRequest,
                content = stringResource(id = R.string.ok),
            )
        }
    }
}

@Composable
internal fun CompletedCheckDialog(
    onCompleteClick: () -> Unit,
    onDismissRequest: () -> Unit,
) {
    ConnectDogAlertDialog(
        onDismissRequest = onDismissRequest,
        titleRes = com.kusitms.connectdog.core.designsystem.R.string.complete_check_dialog_title,
        descriptionRes = com.kusitms.connectdog.core.designsystem.R.string.complete_check_dialog_description,
        okText = com.kusitms.connectdog.core.designsystem.R.string.complete,
        cancelText = com.kusitms.connectdog.core.designsystem.R.string.back,
        onClickOk = {
            onCompleteClick()
            onDismissRequest()
        },
    )
}
