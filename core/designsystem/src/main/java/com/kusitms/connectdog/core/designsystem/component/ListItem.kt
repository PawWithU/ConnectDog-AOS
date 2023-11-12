package com.kusitms.connectdog.core.designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kusitms.connectdog.core.designsystem.R
import com.kusitms.connectdog.core.designsystem.theme.ConnectDogTheme
import com.kusitms.connectdog.core.designsystem.theme.Gray2
import com.kusitms.connectdog.core.model.Announcement
import com.kusitms.connectdog.core.model.Applicant

@Composable
fun ListItem(
    modifier: Modifier = Modifier,
    imageUrl: String,
    title: String,
    isValid: Boolean = true,
    content: @Composable () -> Unit,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight(Alignment.CenterVertically)
    ) {
        Box {
            NetworkImage(
                imageUrl = imageUrl,
                modifier = Modifier
                    .size(85.dp)
                    .clip(shape = RoundedCornerShape(12.dp))
                    .alpha(if (!isValid) 0.4F else 1.0F),
                placeholder = ColorPainter(MaterialTheme.colorScheme.primaryContainer)
            )
            if (!isValid) DescriptionTag(
                text = stringResource(id = R.string.end_recruit),
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(4.dp)
            )
        }
        Spacer(modifier = Modifier.size(16.dp))
        Column {
            Title(text = title)
            Spacer(modifier = Modifier.height(9.dp))
            content()
        }
    }
}

@Composable
fun ListForUserItem(
    modifier: Modifier = Modifier,
    imageUrl: String,
    announcement: Announcement,
    isValid: Boolean = true,
) {
    ListItem(modifier = modifier, imageUrl = imageUrl, title = announcement.location, isValid = isValid) {
        AnnouncementContent(
            date = announcement.date,
            organization = announcement.organization,
            hasKennel = announcement.hasKennel
        )
    }
}

@Composable
fun ListForOrganizationItem(
    modifier: Modifier = Modifier,
    imageUrl: String,
    applicant: Applicant,
    isValid: Boolean = true,
) {
    ListItem(modifier = modifier, imageUrl = imageUrl, title = applicant.dogName, isValid = isValid) {
        ApplicantContent(
            date = applicant.date,
            location = applicant.location,
            volunteer = applicant.volunteer
        )
    }
}

@Composable
private fun DescriptionTag(
    text: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = text,
        style = MaterialTheme.typography.labelSmall,
        fontWeight = FontWeight.SemiBold,
        fontSize = 8.sp,
        color = MaterialTheme.colorScheme.onPrimary,
        modifier = modifier
            .background(shape = RoundedCornerShape(2.dp), color = Gray2)
            .padding(horizontal = 6.dp, vertical = 2.dp)
    )
}

@Composable
private fun Title(
    text: String
) {
    Text(
        text = text,
        style = MaterialTheme.typography.titleSmall,
        fontSize = 14.sp,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis
    )
}

@Preview
@Composable
private fun ListForUserItemPreivew() {
    ConnectDogTheme {
        ListForUserItem(
            imageUrl = "",
            announcement = Announcement("", "이동봉사 위치", "YY.mm.dd(요일)", "단체이름", false),
            isValid = false
        )
    }
}