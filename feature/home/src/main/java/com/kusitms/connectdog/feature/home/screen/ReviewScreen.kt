package com.kusitms.connectdog.feature.home.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kusitms.connectdog.core.designsystem.component.ConnectDogReview
import com.kusitms.connectdog.core.designsystem.component.ConnectDogTopAppBar
import com.kusitms.connectdog.core.designsystem.component.TopAppBarNavigationType
import com.kusitms.connectdog.core.designsystem.theme.ConnectDogTheme
import com.kusitms.connectdog.core.designsystem.theme.Gray7
import com.kusitms.connectdog.core.model.Review
import com.kusitms.connectdog.feature.home.HomeViewModel
import com.kusitms.connectdog.feature.home.R
import com.kusitms.connectdog.feature.home.state.ReviewUiState

@Composable
fun ReviewScreen(
    onBackClick: () -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val scrollState = rememberScrollState()
    val reviewUiState by viewModel.reviewUiState.collectAsStateWithLifecycle()

    Column {
        TopAppBar {
            onBackClick()
        }
        ReviewContent(uiState = reviewUiState)
    }
}

@Composable
private fun TopAppBar(
    onBackClick: () -> Unit
) {
    ConnectDogTopAppBar(
        titleRes = R.string.review_top_app_bar_title,
        navigationType = TopAppBarNavigationType.BACK,
        navigationIconContentDescription = "Back",
        onNavigationClick = { onBackClick() }
    )
}

@Composable
private fun ReviewContent(uiState: ReviewUiState) {
    val modifier = Modifier.padding(horizontal = 0.dp)
    when (uiState) {
        is ReviewUiState.Reviews -> {
            ReviewListContent(
                list = uiState.reviews,
                modifier = modifier
            )
        }

        else -> ReviewLoading(modifier = modifier)
    }
}

@Composable
private fun ReviewListContent(
    list: List<Review>,
    modifier: Modifier
) {
    LazyColumn(modifier = modifier) {
        items(list.take(30)) {
            ReviewItemContent(review = it)
        }
    }
}

@Composable
fun ReviewLoading(modifier: Modifier) {
    val list = List(10) {
        Review(
            profileNum = 0,
            dogName = "멍멍이",
            userName = "츄",
            contentUrl = "",
            date = "23.10.19(목)",
            location = "서울 강남구 -> 서울 도봉구",
            organization = "단체이름",
            content = "진짜 천사같은 아기와 하루를 함께해서 행복했습니다 너무 감사드려요 봉사 또 해야징 ><"
        )
    }
    LazyColumn(modifier = modifier) {
        items(list) {
            ReviewItemContent(review = it)
        }
    }
}

@Composable
private fun ReviewItemContent(
    review: Review,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxWidth().wrapContentSize()) {
        ConnectDogReview(review = review)
        Divider(Modifier.height(8.dp).fillMaxWidth(), color = Gray7)
    }
}

@Preview
@Composable
private fun ReviewScreenPreview() {
    ConnectDogTheme {
        ReviewContent(uiState = ReviewUiState.Empty)
    }
}
