package com.kusitms.connectdog.core.designsystem.component

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kusitms.connectdog.core.designsystem.R
import com.kusitms.connectdog.core.designsystem.theme.ConnectDogTheme
import com.kusitms.connectdog.core.designsystem.theme.Gray3
import com.kusitms.connectdog.core.designsystem.theme.Gray6
import com.kusitms.connectdog.core.designsystem.theme.Orange20
import com.kusitms.connectdog.core.model.Application
import com.kusitms.connectdog.core.model.InterApplication
import com.kusitms.connectdog.core.model.Volunteer

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ApplicationBottomSheet(
    modifier: Modifier = Modifier,
    @StringRes titleRes: Int,
    application: Application,
    volunteer: Volunteer,
    sheetState: SheetState,
    onDismissRequest: () -> Unit,
    bottomButton: @Composable () -> Unit
) {
    Content(
        modifier = modifier,
        titleRes = titleRes,
        volunteer = volunteer,
        sheetState = sheetState,
        onDismissRequest = onDismissRequest,
        bottomButton = bottomButton
    ) {
        ApplicationContent(application = application)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InterApplicationBottomSheet(
    modifier: Modifier = Modifier,
    @StringRes titleRes: Int,
    application: InterApplication,
    volunteer: Volunteer,
    sheetState: SheetState,
    onDismissRequest: () -> Unit,
    bottomButton: @Composable () -> Unit
) {
    Content(
        modifier = modifier,
        titleRes = titleRes,
        volunteer = volunteer,
        sheetState = sheetState,
        onDismissRequest = onDismissRequest,
        bottomButton = bottomButton
    ) {
        InterApplicationContent(application = application)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Content(
    modifier: Modifier = Modifier,
    @StringRes titleRes: Int,
    volunteer: Volunteer,
    sheetState: SheetState,
    onDismissRequest: () -> Unit,
    bottomButton: @Composable () -> Unit,
    informContent: @Composable () -> Unit
) {
    ConnectDogBottomSheet(
        sheetState = sheetState,
        onDismissRequest = onDismissRequest
    ) {
        Column(
            modifier = Modifier.wrapContentSize()
        ) {
            BottomSheetTopAppBar(titleRes = titleRes) { onDismissRequest() }
            Column(
                modifier = Modifier.padding(20.dp)
            ) {
                informContent()
                Divider(
                    thickness = 1.dp,
                    color = Gray6,
                    modifier = Modifier.padding(vertical = 20.dp)
                )
                InformationContent(volunteer = volunteer)
            }
            bottomButton()
        }
    }
}

@Composable
private fun BottomSheetTopAppBar(
    @StringRes titleRes: Int,
    onBackClick: () -> Unit
) {
    ConnectDogTopAppBar(
        titleRes = titleRes,
        navigationType = TopAppBarNavigationType.CLOSE,
        navigationIconContentDescription = "close",
        onNavigationClick = onBackClick
    )
}

@Composable
private fun ApplicationContent(application: Application) {
    AnnouncementItem(
        imageUrl = application.imageUrl,
        dogName = application.dogName!!,
        location = application.location,
        isKennel = application.hasKennel,
        dogSize = application.dogSize!!,
        date = application.date,
        pickUpTime = application.pickUpTime ?: ""
    )
}

@Composable
private fun InterApplicationContent(application: InterApplication) {
    ListForOrganizationItem(
        imageUrl = application.imageUrl,
        dogName = application.dogName,
        date = application.date,
        location = application.location,
        volunteerName = application.volunteerName
    )
}

@Composable
private fun InformationContent(volunteer: Volunteer) {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Information(titleRes = R.string.name, inform = volunteer.volunteerName)
        Information(titleRes = R.string.phone, inform = volunteer.phone)
        Spacer(modifier = Modifier.size(20.dp))
        CommentContent(comment = volunteer.content)
    }
}

@Composable
private fun Information(
    @StringRes titleRes: Int,
    inform: String
) {
    Row {
        Text(
            text = stringResource(id = titleRes),
            style = MaterialTheme.typography.bodyLarge,
            color = Gray3
        )
        Spacer(modifier = Modifier.size(12.dp))
        Text(text = inform, style = MaterialTheme.typography.bodyLarge)
    }
}

@Composable
private fun CommentContent(comment: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Orange20, shape = RoundedCornerShape(4.dp))
            .padding(10.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = stringResource(id = R.string.inquiry),
            style = MaterialTheme.typography.titleSmall,
            fontSize = 14.sp
        )
        Text(text = comment, style = MaterialTheme.typography.bodyMedium)
    }
}

@Preview
@Composable
private fun CommentContentPreview() {
    ConnectDogTheme {
        CommentContent(comment = "이동봉사 신청합니다!")
    }
}
