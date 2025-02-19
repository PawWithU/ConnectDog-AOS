package com.kusitms.connectdog.core.designsystem.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.kusitms.connectdog.core.designsystem.R
import com.kusitms.connectdog.core.designsystem.theme.Gray2
import com.kusitms.connectdog.core.designsystem.theme.Orange10
import com.kusitms.connectdog.core.model.Review
import com.kusitms.connectdog.core.util.getProfileImageId

@Composable
fun ReviewHome(
    onClick: (Long) -> Unit,
    review: Review
) {
    Column(
        modifier = Modifier
            .height(394.dp)
            .width(240.dp)
            .clip(RoundedCornerShape(12.dp))
            .clickable { review.reviewId?.let { onClick(it) } }
    ) {
        NetworkImage(
            imageUrl = review.mainImage,
            placeholder = ColorPainter(MaterialTheme.colorScheme.primaryContainer),
            modifier = Modifier
                .clip(shape = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp))
                .fillMaxWidth()
                .height(250.dp)
        )
        ReviewHomeContent(
            profileNum = review.profileNum,
            dogName = review.dogName,
            userName = review.userName,
            text = review.content
        )
    }
}

@Composable
private fun ReviewHomeContent(
    profileNum: Int,
    dogName: String,
    userName: String,
    text: String
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        ProfileContent(
            profileNum = profileNum,
            dogName = dogName,
            userName = userName
        )
        Spacer(modifier = Modifier.height(20.dp))
        ReviewHomeText(text = text)
    }
}

@Composable
private fun ReviewHomeText(
    text: String
) {
    Row(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_quote),
            contentDescription = null
        )
        Text(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 5.dp),
            style = MaterialTheme.typography.bodyMedium,
            maxLines = 3,
            text = text,
            overflow = TextOverflow.Ellipsis
        )
        Image(
            painter = painterResource(id = R.drawable.ic_quote),
            contentDescription = null,
            modifier = Modifier
                .graphicsLayer(scaleX = -1f, scaleY = -1f)
                .align(Alignment.Bottom)
        )
    }
}

@Composable
fun  ReviewCommunity(
    review: Review
) {
    Column(
        modifier = Modifier.padding(20.dp)
    ) {
        NetworkImage(
            imageUrl = review.mainImage,
            placeholder = ColorPainter(MaterialTheme.colorScheme.primaryContainer),
            modifier = Modifier
                .clip(shape = RoundedCornerShape(12.dp))
                .fillMaxWidth()
                .height(250.dp)
        )
    }
}

@Composable
fun ProfileContent(
    profileNum: Int,
    dogName: String,
    userName: String
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Image(
            painter = painterResource(id = getProfileImageId(profileNum)),
            contentDescription = null,
            modifier = Modifier.size(30.dp)
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
                        color = Orange10
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
