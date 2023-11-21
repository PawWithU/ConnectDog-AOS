package com.kusitms.connectdog.feature.intermediator.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kusitms.connectdog.core.designsystem.component.ConnectDogSecondaryButton
import com.kusitms.connectdog.core.designsystem.component.ListForUserItem
import com.kusitms.connectdog.core.designsystem.theme.Gray1
import com.kusitms.connectdog.core.designsystem.theme.Gray4
import com.kusitms.connectdog.core.designsystem.theme.Gray7
import com.kusitms.connectdog.core.designsystem.theme.Red2
import com.kusitms.connectdog.core.model.InterApplication
import com.kusitms.connectdog.feature.intermediator.R


@Composable
internal fun RecruitingContent(application: InterApplication) {
    ListForUserItem(
        modifier = Modifier.padding(20.dp),
        imageUrl = application.imageUrl,
        location = application.location,
        date = application.date,
        organization = application.organization,
        hasKennel = application.hasKennel
    )
    Divider(thickness = 8.dp, color = Gray7)
}

@Composable
internal fun PendingContent(application: InterApplication, onClick: () -> Unit) {
    // todo 시간 계산 필요
    Column(
        modifier = Modifier
            .padding(20.dp)
            .fillMaxWidth()
            .wrapContentSize()
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .height(40.dp)
                .background(shape = RoundedCornerShape(6.dp), color = Red2),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(painter = painterResource(id = R.drawable.ic_info), contentDescription = "info")
            Spacer(modifier = Modifier.size(5.dp))
            Text(text = stringResource(id = R.string.will_be_canceled), style = MaterialTheme.typography.labelLarge)
        }
        ListForUserItem(
            modifier = Modifier.padding(20.dp),
            imageUrl = application.imageUrl,
            location = application.location,
            date = application.date,
            organization = application.organization,
            hasKennel = application.hasKennel
        )
        ConnectDogSecondaryButton(
            contentRes = R.string.check_volunteer
        ) { onClick() }
    }
    Divider(thickness = 8.dp, color = Gray7)
}



@Composable
internal fun InProgressContent(
    application: InterApplication,
    onClick: () -> Unit
){
    Column(
        modifier = Modifier
            .padding(20.dp)
            .fillMaxWidth()
            .wrapContentSize()
    ) {
        ListForUserItem(
            modifier = Modifier.padding(20.dp),
            imageUrl = application.imageUrl,
            location = application.location,
            date = application.date,
            organization = application.organization,
            hasKennel = application.hasKennel
        )
        ConnectDogSecondaryButton(
            contentRes = R.string.make_complete
        ) { onClick() }
    }
    Divider(thickness = 8.dp, color = Gray7)
}

@Composable
internal fun CompletedContent(
    application: InterApplication,
    onClickReview: () -> Unit,
    onClickRecent: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            ListForUserItem(
                imageUrl = application.imageUrl,
                location = application.location,
                date = application.date,
                organization = application.organization,
                hasKennel = application.hasKennel
            )
            Spacer(modifier = Modifier.size(20.dp))
            ReviewRecentButton(
                modifier = Modifier.height(40.dp),
                hasReview = application.reviewId != null,
                hasRecent = application.dogStatusId != null,
                onClickReview = onClickReview,
                onClickRecent = onClickRecent
            )
        }
        Divider(thickness = 8.dp, color = Gray7)
    }
}

@Composable
private fun ReviewRecentButton(
    modifier: Modifier = Modifier,
    hasReview: Boolean,
    hasRecent: Boolean,
    onClickReview: () -> Unit,
    onClickRecent: () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .border(
                shape = RoundedCornerShape(6.dp),
                color = MaterialTheme.colorScheme.outline,
                width = 1.dp
            )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Box(
                modifier = modifier
                    .fillMaxHeight()
                    .weight(0.5f)
                    .clickable(enabled = hasReview) { onClickReview() },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(id = R.string.create_review),
                    style = MaterialTheme.typography.titleSmall,
                    fontSize = 12.sp,
                    textAlign = TextAlign.Center,
                    color = if (!hasReview) Gray4 else Gray1
                )
            }
            Box(
                modifier = modifier
                    .fillMaxHeight()
                    .weight(0.5f)
                    .clickable(enabled = hasRecent) { onClickRecent() },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(id = R.string.check_recent),
                    style = MaterialTheme.typography.titleSmall,
                    fontSize = 12.sp,
                    textAlign = TextAlign.Center,
                    color = if (!hasRecent) Gray4 else Gray1
                )
            }
        }
        Divider(
            color = MaterialTheme.colorScheme.outline,
            modifier = modifier
                .align(Alignment.Center)
                .width(1.dp)
                .fillMaxHeight()
                .padding(vertical = 8.dp)
        )
    }
}


@Composable
internal fun Loading() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}