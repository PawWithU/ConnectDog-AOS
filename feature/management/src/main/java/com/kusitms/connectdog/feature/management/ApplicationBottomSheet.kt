package com.kusitms.connectdog.feature.management

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kusitms.connectdog.core.designsystem.component.ConnectDogBottomSheet
import com.kusitms.connectdog.core.designsystem.component.ConnectDogTopAppBar
import com.kusitms.connectdog.core.designsystem.component.ListForUserItem
import com.kusitms.connectdog.core.designsystem.component.TopAppBarNavigationType
import com.kusitms.connectdog.core.designsystem.theme.Gray3
import com.kusitms.connectdog.core.designsystem.theme.Gray6
import com.kusitms.connectdog.core.model.Application
import com.kusitms.connectdog.core.model.Volunteer
import org.w3c.dom.Comment

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun ApplicationBottomSheet(
    modifier: Modifier = Modifier,
    application: Application,
    volunteer: Volunteer
) {
    val sheetState = rememberModalBottomSheetState()
    var isSheetOpen by rememberSaveable {
        mutableStateOf(false)
    }
    ConnectDogBottomSheet(
        sheetState = sheetState,
        onDismissRequest = { isSheetOpen = false }) {
        Column(modifier = modifier) {
            TopAppBar {
                isSheetOpen = false
            }
            ApplicationContent(application = application)
            Divider(thickness = 1.dp, color = Gray6, modifier = Modifier.padding(vertical = 40.dp))
            InformationContent(volunteer = volunteer)
        }
    }
}

@Composable
private fun TopAppBar(
    onBackClick: () -> Unit
) {
    ConnectDogTopAppBar(
        titleRes = R.string.check_my_appliance,
        navigationType = TopAppBarNavigationType.CLOSE,
        navigationIconContentDescription = "close",
        onNavigationClick = onBackClick
    )
}

@Composable
private fun ApplicationContent(application: Application) {
    ListForUserItem(
        imageUrl = application.imageUrl,
        location = application.location,
        date = application.date,
        organization = application.organization,
        hasKennel = application.hasKennel
    )
}

@Composable
private fun InformationContent(volunteer: Volunteer){
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Information(titleRes = R.string.name, inform = volunteer.name)
        Information(titleRes = R.string.phone, inform = volunteer.phoneNumber)
        Information(titleRes = R.string.vehicle, inform = volunteer.vehicle)
        Spacer(modifier = Modifier.size(20.dp))
        CommentContent(comment = volunteer.comment)
    }
}

@Composable
private fun Information(
    @StringRes titleRes: Int,
    inform: String
) {
    Row {
        Text(text = stringResource(id = titleRes), style = MaterialTheme.typography.bodyLarge, color = Gray3)
        Spacer(modifier = Modifier.size(12.dp))
        Text(text = inform, style = MaterialTheme.typography.bodyLarge)
    }
}

@Composable
private fun CommentContent(comment: String){
    Column(modifier = Modifier
        .fillMaxWidth()
        .wrapContentSize()
        .padding(10.dp)
    ) {
        Text(text = stringResource(id = R.string.volunteer_comment), style = MaterialTheme.typography.titleSmall, fontSize = 14.sp)
        Text(text = comment, style = MaterialTheme.typography.bodyMedium)
    }
}