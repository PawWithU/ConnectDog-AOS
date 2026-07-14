package com.kusitms.connectdog.feature.home.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kusitms.connectdog.core.designsystem.component.ConnectDogTopAppBar
import com.kusitms.connectdog.core.designsystem.component.ReviewItemContent
import com.kusitms.connectdog.core.designsystem.component.ReviewType
import com.kusitms.connectdog.core.designsystem.component.TopAppBarNavigationType
import com.kusitms.connectdog.core.designsystem.theme.ConnectDogTheme
import com.kusitms.connectdog.core.model.Review
import com.kusitms.connectdog.feature.home.HomeViewModel
import com.kusitms.connectdog.feature.home.R
import com.kusitms.connectdog.feature.home.state.ReviewUiState

@Composable
fun ReviewScreen(
    onBackClick: () -> Unit,
    onInterProfileClick: (Long) -> Unit,
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val reviewUiState by viewModel.reviewUiState.collectAsStateWithLifecycle()

    Column {
        TopAppBar {
            onBackClick()
        }
        ReviewContent(
            uiState = reviewUiState,
            onInterProfileClick = onInterProfileClick,
        )
    }
}

@Composable
private fun TopAppBar(onBackClick: () -> Unit) {
    ConnectDogTopAppBar(
        titleRes = R.string.review_top_app_bar_title,
        navigationType = TopAppBarNavigationType.BACK,
        navigationIconContentDescription = "Back",
        onNavigationClick = { onBackClick() },
    )
}

@Composable
private fun ReviewContent(
    onInterProfileClick: (Long) -> Unit,
    uiState: ReviewUiState,
) {
    val modifier = Modifier.padding(horizontal = 0.dp)
    when (uiState) {
        is ReviewUiState.Reviews -> {
            ReviewListContent(
                list = uiState.reviews,
                modifier = modifier,
                onInterProfileClick = onInterProfileClick,
            )
        }

        else -> ReviewLoading(modifier = modifier)
    }
}

@Composable
fun ReviewListContent(
    list: List<Review>,
    onInterProfileClick: (Long) -> Unit,
    modifier: Modifier,
) {
    LazyColumn(modifier = modifier) {
        items(list.take(30)) {
            ReviewItemContent(
                review = it,
                onInterProfileClick = onInterProfileClick,
            )
        }
    }
}

@Composable
fun ReviewLoading(modifier: Modifier) {
    val list =
        List(10) {
            Review(
                profileNum = 0,
                dogName = "멍멍이",
                userName = "츄",
                mainImage = "",
                date = "23.10.19(목)",
                location = "서울 강남구 -> 서울 도봉구",
                organization = "단체이름",
                content = "진짜 천사같은 아기와 하루를 함께해서 행복했습니다 너무 감사드려요 봉사 또 해야징 ><",
                contentImages = null,
            )
        }

    LazyColumn(modifier = modifier) {
        items(list) {
            ReviewItemContent(
                review = it,
                reviewType = ReviewType.REVIEW,
            )
        }
    }
}

@Preview
@Composable
private fun ReviewScreenPreview() {
    ConnectDogTheme {
        ReviewContent(uiState = ReviewUiState.Empty, onInterProfileClick = {})
    }
}
