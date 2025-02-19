package com.kusitms.connectdog.core.designsystem.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kusitms.connectdog.core.designsystem.R
import com.kusitms.connectdog.core.designsystem.theme.Gray2
import com.kusitms.connectdog.core.designsystem.theme.Orange10
import com.kusitms.connectdog.core.model.Review
import com.kusitms.connectdog.core.util.getProfileImageId

enum class ReviewType {
    HOME, REVIEW
}

@Composable
fun ConnectDogReview(
    modifier: Modifier = Modifier,
    review: Review,
    type: ReviewType
) {
    ConnectDogCommunityContent(
        modifier = modifier,
        profile = {
            ProfileContent(
                profileNum = review.profileNum,
                dogName = review.dogName,
                userName = review.userName
            )
        },
        informationContent = {
            if (type == ReviewType.HOME) {
                ReviewContent(
                    date = review.date,
                    location = review.location,
                    organization = review.organization
                )
            }
        },
        contentUrl = review.mainImage,
        reviewUrl = review.contentImages,
        content = review.content,
        type = type
    )
}

@Composable
fun ConnectDogCommunityContent(
    modifier: Modifier = Modifier,
    profile: @Composable () -> Unit,
    informationContent: @Composable () -> Unit,
    contentUrl: String,
    reviewUrl: List<String>?,
    content: String,
    type: ReviewType
) {
    Column(modifier = modifier.padding(20.dp)) {


        when (type) {
            ReviewType.HOME -> {
                NetworkImage(
                    imageUrl = contentUrl,
                    placeholder = ColorPainter(MaterialTheme.colorScheme.primaryContainer),
                    modifier = Modifier
                        .clip(shape = RoundedCornerShape(12.dp))
                        .fillMaxWidth()
                        .height(250.dp)
                )
            }

            ReviewType.REVIEW -> {
                val image = listOf(contentUrl)
                val list = if (reviewUrl != null) image + reviewUrl else image
                profile()
                Spacer(modifier = Modifier.height(20.dp))
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(list.size) {
                        NetworkImage(
                            imageUrl = list[it],
                            placeholder = ColorPainter(MaterialTheme.colorScheme.primaryContainer),
                            modifier = Modifier
                                .clip(shape = RoundedCornerShape(12.dp))
                                .size(120.dp)
                        )
                    }
                }
            }
        }
        Spacer(
            modifier = Modifier.height(
                when (type) {
                    ReviewType.HOME -> 12.dp
                    ReviewType.REVIEW -> 6.dp
                }
            )
        )
        informationContent()
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = content,
            style = MaterialTheme.typography.bodyMedium,
            maxLines = when (type) {
                ReviewType.HOME -> 2
                ReviewType.REVIEW -> 100
            },
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Preview
@Composable
private fun ReviewContentPreview() {
    ConnectDogReview(
        review = Review(
            profileNum = 0,
            dogName = "멍멍이",
            userName = "츄",
            mainImage = "",
            date = "23.10.19(목)",
            location = "서울 강남구 -> 서울 도봉구",
            organization = "단체이름",
            content = "진짜 천사같은 아기와 하루를 함께해서 행복했습니다 너무 감사드려요 봉사 또 해야징 ><",
            contentImages = null
        ),
        type = ReviewType.HOME
    )
}
