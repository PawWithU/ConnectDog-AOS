package com.kusitms.connectdog.feature.management

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.kusitms.connectdog.core.designsystem.component.ApplicationBottomSheet
import com.kusitms.connectdog.core.designsystem.component.ConnectDogBottomButton
import com.kusitms.connectdog.core.model.Application
import com.kusitms.connectdog.core.model.Volunteer

@Composable
internal fun MyApplicationBottomSheet(
    application: Application,
    volunteer: Volunteer,
) {
    ApplicationBottomSheet(
        titleRes = R.string.check_my_appliance,
        application = application,
        volunteer = volunteer
    ) {
        ConnectDogBottomButton(
            onClick = { /*TODO*/ },
            content = stringResource(id = R.string.cancel_appliance),
            textColor = MaterialTheme.colorScheme.error,
            color = MaterialTheme.colorScheme.surface,
            border = BorderStroke(width = 1.dp, color = MaterialTheme.colorScheme.error),
            modifier = Modifier.padding(start = 20.dp, end = 20.dp, top = 24.dp, bottom = 32.dp)
        )
    }
}