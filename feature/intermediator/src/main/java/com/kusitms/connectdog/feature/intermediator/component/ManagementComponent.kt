package com.kusitms.connectdog.feature.intermediator.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.kusitms.connectdog.core.designsystem.component.AnnouncementItem
import com.kusitms.connectdog.core.designsystem.component.ConnectDogBottomButton
import com.kusitms.connectdog.core.designsystem.component.ConnectDogSecondaryButton
import com.kusitms.connectdog.core.designsystem.component.ListForOrganizationItem
import com.kusitms.connectdog.core.designsystem.theme.Gray1
import com.kusitms.connectdog.core.designsystem.theme.Gray3
import com.kusitms.connectdog.core.designsystem.theme.Gray4
import com.kusitms.connectdog.core.designsystem.theme.Gray5
import com.kusitms.connectdog.core.designsystem.theme.Gray7
import com.kusitms.connectdog.core.designsystem.theme.PetOrange
import com.kusitms.connectdog.core.designsystem.theme.Red2
import com.kusitms.connectdog.core.model.InterApplication
import com.kusitms.connectdog.core.util.calDateTimeDifference
import com.kusitms.connectdog.feature.intermediator.R

@Composable
internal fun RecruitingContent(application: InterApplication) {
    ListForOrganizationItem(
        modifier = Modifier.padding(20.dp),
        imageUrl = application.imageUrl,
        dogName = application.dogName,
        date = application.date,
        location = application.location,
        volunteerName = application.volunteerName,
        isValid = !(application.postStatus != null && application.postStatus == "모집 마감"),
    )
    Divider(thickness = 8.dp, color = Gray7)
}

@Composable
internal fun PendingContent(
    application: InterApplication,
    onClick: () -> Unit,
) {
    val diffTime = application.applicationTime?.calDateTimeDifference()
    Column(
        modifier =
            Modifier
                .padding(20.dp)
                .fillMaxWidth()
                .wrapContentSize(),
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .height(40.dp)
                .background(shape = RoundedCornerShape(6.dp), color = Red2)
                .padding(horizontal = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_info),
                contentDescription = "info",
                tint = MaterialTheme.colorScheme.error,
            )
            Spacer(modifier = Modifier.size(5.dp))
            Text(
                text = stringResource(id = R.string.will_be_canceled, "$diffTime"),
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.error,
            )
        }
        AnnouncementItem(
            modifier = Modifier.padding(vertical = 20.dp),
            imageUrl = application.imageUrl,
            dogName = application.dogName,
            location = application.location,
            isKennel = application.isKennel ?: false,
            dogSize = application.dogSize ?: "",
            date = application.date,
            pickUpTime = application.pickUpTime ?: "",
        )
        ConnectDogSecondaryButton(
            contentRes = R.string.check_volunteer,
        ) { onClick() }
    }
    Divider(thickness = 8.dp, color = Gray7)
}

@Composable
internal fun InProgressContent(
    application: InterApplication,
    onCheckVolunteerClick: () -> Unit,
    onCompleteClick: () -> Unit,
) {
    Column(
        modifier =
            Modifier
                .fillMaxWidth()
                .wrapContentSize(),
    ) {
        ListForOrganizationItem(
            modifier = Modifier.padding(20.dp),
            imageUrl = application.imageUrl,
            dogName = application.dogName,
            date = application.date,
            location = application.location,
            volunteerName = application.volunteerName,
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
        ) {
            ConnectDogSecondaryButton(
                modifier = Modifier.padding(start = 20.dp, bottom = 20.dp).weight(1f),
                contentRes = R.string.check_volunteer,
                onClick = onCheckVolunteerClick,
            )
            Spacer(modifier = Modifier.width(8.dp))
            ConnectDogSecondaryButton(
                color = PetOrange,
                textColor = Color.White,
                modifier = Modifier.padding(end = 20.dp, bottom = 20.dp).weight(1f),
                contentRes = R.string.make_complete,
                onClick = onCompleteClick,
            )
        }
    }
    Divider(thickness = 8.dp, color = Gray7)
}

@Composable
internal fun CompletedContent(
    application: InterApplication,
    onClickReview: () -> Unit,
) {
    Column(
        modifier =
            Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            AnnouncementItem(
                modifier = Modifier.padding(bottom = 20.dp),
                imageUrl = application.imageUrl,
                dogName = application.dogName,
                location = application.location,
                isKennel = application.isKennel ?: false,
                dogSize = application.dogSize ?: "",
                date = application.date,
                pickUpTime = application.pickUpTime ?: "",
            )
            ConnectDogBottomButton(
                height = 40,
                onClick = onClickReview,
                content = "받은 후기 확인",
                enabled = application.reviewId != null,
                enabledColor = Color.White,
                disabledColor = Gray7,
                textColor = if (application.reviewId == null) Gray3 else Gray1,
                border = BorderStroke(1.dp, Gray5),
                fontSize = 12,
                paddingValues = PaddingValues(vertical = 11.dp),
                radius = 6,
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
    onClickRecent: () -> Unit,
) {
    Box(
        modifier =
            modifier
                .fillMaxWidth()
                .border(
                    shape = RoundedCornerShape(6.dp),
                    color = MaterialTheme.colorScheme.outline,
                    width = 1.dp,
                ),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly,
        ) {
            Box(
                modifier =
                    modifier
                        .fillMaxHeight()
                        .weight(0.5f)
                        .clickable(enabled = hasReview) { onClickReview() },
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = stringResource(id = R.string.create_review),
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Center,
                    color = if (!hasReview) Gray4 else Gray1,
                )
            }
            Box(
                modifier =
                    modifier
                        .fillMaxHeight()
                        .weight(0.5f)
                        .clickable(enabled = hasRecent) { onClickRecent() },
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = stringResource(id = R.string.check_recent),
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Center,
                    color = if (!hasRecent) Gray4 else Gray1,
                )
            }
        }
        Divider(
            color = MaterialTheme.colorScheme.outline,
            modifier =
                modifier
                    .align(Alignment.Center)
                    .width(1.dp)
                    .fillMaxHeight()
                    .padding(vertical = 8.dp),
        )
    }
}
