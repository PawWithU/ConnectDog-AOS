package com.kusitms.connectdog.core.designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kusitms.connectdog.core.designsystem.R
import com.kusitms.connectdog.core.designsystem.component.text.TextWithIcon
import com.kusitms.connectdog.core.designsystem.theme.Gray1
import com.kusitms.connectdog.core.designsystem.theme.Gray2
import com.kusitms.connectdog.core.designsystem.theme.Gray3
import com.kusitms.connectdog.core.designsystem.theme.Gray7
import com.kusitms.connectdog.core.model.AnnouncementHome
import com.kusitms.connectdog.core.model.InterApplication
import com.kusitms.connectdog.core.model.Review
import com.kusitms.connectdog.core.util.UserType

@Composable
fun ListForUserItem(
    modifier: Modifier = Modifier,
    imageUrl: String,
    announcementHome: AnnouncementHome,
    isValid: Boolean = true
) {
    ListItem(
        modifier = modifier,
        imageUrl = imageUrl,
        title = announcementHome.dogName,
        isValid = isValid
    ) {
        AnnouncementContent(
            date = announcementHome.date,
            organization = announcementHome.date,
            hasKennel = true
        )
    }
}

@Composable
fun AnnouncementContent(
    onClick: (Long) -> Unit,
    postId: Int,
    imageUrl: String,
    dogName: String,
    location: String,
    isKennel: Boolean,
    dogSize: String,
    date: String,
    pickUpTime: String?
) {
    Column(
        modifier = Modifier.clickable { onClick(postId.toLong()) }
    ) {
        AnnouncementItem(
            modifier = Modifier.padding(20.dp),
            imageUrl = imageUrl,
            dogName = dogName,
            location = location,
            isKennel = isKennel,
            dogSize = dogSize,
            date = date,
            pickUpTime = pickUpTime
        )
        Divider(
            thickness = 1.dp,
            color = MaterialTheme.colorScheme.outline,
            modifier = Modifier.padding(horizontal = 20.dp)
        )
    }
}

@Composable
fun AnnouncementItem(
    modifier: Modifier = Modifier,
    imageUrl: String,
    dogName: String,
    location: String,
    isKennel: Boolean,
    dogSize: String,
    date: String,
    pickUpTime: String?,
    isValid: Boolean = true
) {
    ListItem(
        modifier = modifier,
        imageUrl = imageUrl,
        title = dogName,
        isValid = isValid
    ) {
        Column {
            Text(
                text = location,
                fontSize = 12.sp,
                color = Gray3,
                fontWeight = FontWeight.Normal
            )
            Spacer(modifier = Modifier.height(12.dp))
            TextWithIcon(text = date, iconId = R.drawable.ic_calendar)
            Spacer(modifier = Modifier.height(6.dp))
            TextWithIcon(text = pickUpTime ?: "", iconId = R.drawable.ic_clock)
            Spacer(modifier = Modifier.height(12.dp))
            Row {
                ConnectDogTagWithIcon(
                    iconId = R.drawable.ic_dog_size,
                    text = dogSize,
                    contentColor = Gray3,
                    backgroundColor = Gray7
                )
                Spacer(modifier = Modifier.width(4.dp))
                ConnectDogTagWithIcon(
                    iconId = R.drawable.ic_kennel,
                    text = if (isKennel) {
                        stringResource(id = R.string.has_kennel)
                    } else {
                        stringResource(id = R.string.has_not_kennel)
                    },
                    contentColor = Gray3,
                    backgroundColor = Gray7
                )
            }
        }
    }
}

@Composable
fun ReviewAnnouncement(
    imageUrl: String,
    dogName: String,
    location: String,
    date: String,
    isValid: Boolean = true
) {
    Row(
        modifier = Modifier.fillMaxWidth().wrapContentHeight()
    ) {
        NetworkImage(
            imageUrl = imageUrl,
            modifier = Modifier
                .size(50.dp)
                .clip(shape = RoundedCornerShape(12.dp))
                .alpha(if (!isValid) 0.4F else 1.0F),
            placeholder = ColorPainter(MaterialTheme.colorScheme.primaryContainer)
        )
        Spacer(modifier = Modifier.width(12.dp))
        Column(
            modifier = Modifier.wrapContentHeight().align(Alignment.CenterVertically)
        ) {
            Row {
                Text(
                    text = dogName,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Gray1
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = location,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Normal,
                    color = Gray3
                )
            }
            Spacer(modifier = Modifier.width(6.dp))
            TextWithIcon(text = date, iconId = R.drawable.ic_clock)
        }
    }
}

@Composable
fun ListForUserItem(
    modifier: Modifier = Modifier,
    imageUrl: String,
    location: String,
    date: String,
    organization: String,
    hasKennel: Boolean,
    isValid: Boolean = true
) {
    ListItem(modifier = modifier, imageUrl = imageUrl, title = location, isValid = isValid) {
        AnnouncementContent(
            date = date,
            organization = organization,
            hasKennel = hasKennel
        )
    }
}

@Composable
fun ListForOrganizationItem(
    modifier: Modifier = Modifier,
    imageUrl: String,
    applicant: InterApplication,
    isValid: Boolean = true
) {
    ListItem(
        modifier = modifier,
        imageUrl = imageUrl,
        title = applicant.dogName,
        isValid = isValid
    ) {
        ApplicantContent(
            date = applicant.date,
            location = applicant.location,
            volunteer = applicant.volunteerName
        )
    }
}

@Composable
fun ListForOrganizationItem(
    modifier: Modifier = Modifier,
    imageUrl: String,
    dogName: String,
    date: String,
    location: String,
    volunteerName: String,
    isValid: Boolean = true
) {
    ListItem(modifier = modifier, imageUrl = imageUrl, title = dogName, isValid = isValid) {
        ApplicantContent(
            date = date,
            location = location,
            volunteer = volunteerName
        )
    }
}

@Composable
fun ListItem(
    modifier: Modifier = Modifier,
    imageUrl: String,
    title: String,
    isValid: Boolean = true,
    content: @Composable () -> Unit
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
                    .size(100.dp)
                    .clip(shape = RoundedCornerShape(12.dp))
                    .alpha(if (!isValid) 0.4F else 1.0F),
                placeholder = ColorPainter(MaterialTheme.colorScheme.primaryContainer)
            )
            if (!isValid) {
                DescriptionTag(
                    text = stringResource(id = R.string.end_recruit),
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(4.dp)
                )
            }
        }
        Spacer(modifier = Modifier.size(16.dp))
        Column {
            Title(text = title)
            Spacer(modifier = Modifier.height(4.dp))
            content()
        }
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

@Composable
fun ReviewItemContent(
    review: Review,
    modifier: Modifier = Modifier,
    reviewType: ReviewType = ReviewType.REVIEW,
    userType: UserType = UserType.NORMAL_VOLUNTEER,
    onInterProfileClick: (Long) -> Unit = {}
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentSize()
    ) {
        ConnectDogReview(review = review, type = reviewType)
        Divider(
            Modifier
                .height(1.dp)
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            color = Gray7
        )
        Spacer(modifier = Modifier.height(16.dp))
        IntermediatorInfo(userType, review) {
            review.intermediaryId?.let { onInterProfileClick(it) }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Divider(
            Modifier
                .height(8.dp)
                .fillMaxWidth(),
            color = Gray7
        )
    }
}

@Composable
private fun IntermediatorInfo(
    userType: UserType,
    review: Review,
    onInterProfileClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
    ) {
        NetworkImage(
            imageUrl = review.postMainImage,
            modifier = Modifier.size(50.dp)
        )
        Spacer(modifier = Modifier.width(12.dp))
        Column {
            Row {
                Text(
                    text = review.organization,
                    fontSize = 12.sp,
                    color = Gray1,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.width(6.dp))
                if (userType != UserType.INTERMEDIATOR) {
                    Text(
                        text = "프로필 보기",
                        fontSize = 12.sp,
                        color = Gray3,
                        fontWeight = FontWeight.Normal,
                        modifier = Modifier.clickable { onInterProfileClick() }
                    )
                }
            }
            Spacer(modifier = Modifier.height(6.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = review.dogName,
                    fontSize = 14.sp,
                    color = Gray1,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = review.location,
                    fontSize = 12.sp,
                    color = Gray3,
                    fontWeight = FontWeight.Normal
                )
            }
            Spacer(modifier = Modifier.height(7.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_clock),
                    contentDescription = null,
                    tint = Gray2
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = review.date,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    color = Gray2
                )
            }
        }
    }
}
