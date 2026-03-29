package com.kusitms.connectdog.feature.management.component

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kusitms.connectdog.core.designsystem.component.AnnouncementItem
import com.kusitms.connectdog.core.designsystem.component.Empty
import com.kusitms.connectdog.core.designsystem.theme.Gray1
import com.kusitms.connectdog.core.designsystem.theme.Gray7
import com.kusitms.connectdog.core.model.Application
import com.kusitms.connectdog.core.util.UserType
import com.kusitms.connectdog.feature.management.R
import com.kusitms.connectdog.feature.management.screen.Loading
import com.kusitms.connectdog.feature.management.state.ApplicationUiState

@Composable
fun Completed(
    uiState: ApplicationUiState,
    onCreateReviewClick: (Application) -> Unit,
    onCheckReviewClick: (Long, UserType) -> Unit
) {
    when (uiState) {
        is ApplicationUiState.Applications -> {
            LazyColumn(
                verticalArrangement = Arrangement.Top,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 80.dp)
            ) {
                items(uiState.applications) {
                    CompletedContent(
                        application = it,
                        onCreateReviewClick = { onCreateReviewClick(it) },
                        onCheckReviewClick = {
                            onCheckReviewClick(
                                it.reviewId!!,
                                UserType.NORMAL_VOLUNTEER
                            )
                        }
                    )
                }
            }
        }

        is ApplicationUiState.Empty -> {
            Empty(titleRes = R.string.no_completed, descriptionRes = R.string.no_description)
        }

        is ApplicationUiState.Loading -> Loading()
    }
}

@Composable
private fun CompletedContent(
    application: Application,
    onCreateReviewClick: () -> Unit,
    onCheckReviewClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        Column(
            modifier = Modifier
                .padding(20.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Top
        ) {
            AnnouncementItem(
                imageUrl = application.imageUrl,
                dogName = application.dogName!!,
                location = application.location,
                isKennel = application.hasKennel,
                dogSize = application.dogSize!!,
                date = application.date,
                pickUpTime = application.pickUpTime ?: ""
            )
            Spacer(modifier = Modifier.size(20.dp))
            ReviewButton(
                modifier = Modifier.height(40.dp),
                hasReview = application.reviewId != null,
                onCreateReviewClick = onCreateReviewClick,
                onCheckReviewClick = onCheckReviewClick
            )
        }
        Divider(thickness = 8.dp, color = Gray7)
    }
}

@Composable
private fun ReviewButton(
    modifier: Modifier = Modifier,
    hasReview: Boolean,
    onCreateReviewClick: () -> Unit,
    onCheckReviewClick: () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .border(
                shape = RoundedCornerShape(6.dp),
                color = MaterialTheme.colorScheme.outline,
                width = 1.dp
            )
            .clickable { if (hasReview) onCheckReviewClick() else onCreateReviewClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = stringResource(id = if (hasReview) R.string.check_review else R.string.create_review),
            style = MaterialTheme.typography.titleSmall,
            fontSize = 12.sp,
            textAlign = TextAlign.Center,
            color = Gray1
        )
    }
}
