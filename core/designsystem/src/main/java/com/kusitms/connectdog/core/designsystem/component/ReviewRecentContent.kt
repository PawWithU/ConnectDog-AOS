package com.kusitms.connectdog.core.designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.kusitms.connectdog.core.designsystem.R
import com.kusitms.connectdog.core.designsystem.theme.Gray2
import com.kusitms.connectdog.core.model.Recent
import com.kusitms.connectdog.core.model.Review


@Composable
fun ConnectDogCommunityContent(
    modifier: Modifier = Modifier,
    profile: @Composable () -> Unit,
    informationContent: @Composable () -> Unit,
    contentUrl: String,
    content: String
) {
    Column(modifier = modifier.padding(20.dp)) {
        profile()
        Spacer(
            modifier = Modifier
                .height(20.dp)
                .fillMaxWidth()
        )
        NetworkImage(
            imageUrl = contentUrl,
            placeholder = ColorPainter(MaterialTheme.colorScheme.primaryContainer),
            modifier = Modifier
                .clip(shape = RoundedCornerShape(12.dp))
                .fillMaxWidth()
                .height(250.dp)
        )
        Spacer(
            modifier = Modifier
                .height(12.dp)
                .fillMaxWidth()
        )
        informationContent()
        Spacer(
            modifier = Modifier
                .height(20.dp)
                .fillMaxWidth()
        )
        Text(text = content, style = MaterialTheme.typography.bodyMedium, maxLines = 2)
    }
}

@Composable
fun ConnectDogReview(
    modifier: Modifier = Modifier,
    review: Review
) {
    ConnectDogCommunityContent(
        modifier = modifier,
        profile = {
            ProfileContent(
                profileUrl = review.profileUrl,
                dogName = review.dogName,
                userName = review.userName
            )
        },
        informationContent = {
            ReviewContent(
                date = review.date,
                location = review.location,
                organization = review.organization
            )
        },
        contentUrl = review.contentUrl,
        content = review.content
    )
}

@Composable
fun ConnectDogRecent(
    modifier: Modifier = Modifier,
    recent: Recent
) {
    ConnectDogCommunityContent(
        modifier = modifier,
        profile = {
            Text(
                text = recent.dogName + stringResource(id = R.string.dog_recent),
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.SemiBold
            )
        },
        informationContent = {
                             RecentContent(date = recent.date, location = recent.location, volunteer = recent.volunteer)
        },
        contentUrl =recent.contentUrl,
        content = recent.content
    )
}

@Composable
fun ProfileContent(
    profileUrl: String,
    dogName: String,
    userName: String
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        NetworkImage(
            imageUrl = profileUrl, modifier = Modifier
                .size(30.dp)
                .clip(CircleShape),
            placeholder = ColorPainter(MaterialTheme.colorScheme.primary)
        )
        Spacer(modifier = Modifier.width(width = 12.dp))
        Column {
            Text(
                text = dogName + stringResource(id = R.string.who_connected),
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .background(
                        shape = RoundedCornerShape(4.dp),
                        color = MaterialTheme.colorScheme.primaryContainer
                    )
                    .padding(horizontal = 7.dp, vertical = 2.dp)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = userName + stringResource(id = R.string.who_review),
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.SemiBold,
                color = Gray2
            )
        }
    }
}

@Preview
@Composable
private fun ReviewContentPreview() {
    ConnectDogReview(
        review = Review(
            profileUrl = "",
            dogName = "멍멍이",
            userName = "츄",
            contentUrl = "",
            date = "23.10.19(목)",
            location = "서울 강남구 -> 서울 도봉구",
            organization = "단체이름",
            content = "진짜 천사같은 아기와 하루를 함께해서 행복했습니다 너무 감사드려요 봉사 또 해야징 ><"
        )
    )
}