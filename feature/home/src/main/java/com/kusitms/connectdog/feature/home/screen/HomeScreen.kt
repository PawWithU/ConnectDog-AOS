package com.kusitms.connectdog.feature.home.screen

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kusitms.connectdog.core.designsystem.component.BannerGuideline
import com.kusitms.connectdog.core.designsystem.component.ConnectDogReview
import com.kusitms.connectdog.core.designsystem.component.ConnectDogTopAppBar
import com.kusitms.connectdog.core.designsystem.component.NetworkImage
import com.kusitms.connectdog.core.designsystem.component.ReviewType
import com.kusitms.connectdog.core.designsystem.component.SearchBar
import com.kusitms.connectdog.core.designsystem.component.TopAppBarNavigationType
import com.kusitms.connectdog.core.designsystem.component.text.TextWithIcon
import com.kusitms.connectdog.core.designsystem.theme.Gray2
import com.kusitms.connectdog.core.designsystem.theme.Gray3
import com.kusitms.connectdog.core.model.AnnouncementHome
import com.kusitms.connectdog.core.model.Review
import com.kusitms.connectdog.feature.home.HomeViewModel
import com.kusitms.connectdog.feature.home.R
import com.kusitms.connectdog.feature.home.state.AnnouncementUiState
import com.kusitms.connectdog.feature.home.state.ReviewUiState
import kotlinx.coroutines.flow.collectLatest

@Composable
internal fun HomeRoute(
    onNavigateToSearch: () -> Unit,
    onNavigateToFilterSearch: () -> Unit,
    onNavigateToReview: () -> Unit,
    onNavigateToDetail: (Long) -> Unit,
    onNavigateToNotification: () -> Unit,
    onShowErrorSnackBar: (throwable: Throwable?) -> Unit,
    onNavigateToReviewDetail: (Long) -> Unit,
    onNavigateToGuide: () -> Unit,
    finish: () -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val announcementUiState by viewModel.announcementUiState.collectAsStateWithLifecycle()
    val reviewUiState by viewModel.homeReviewUiState.collectAsStateWithLifecycle()

    BackHandler { finish() }

    // 에러 발생할 때마다 에러 스낵바 표시
    LaunchedEffect(true) {
        viewModel.errorFlow.collectLatest { throwable -> onShowErrorSnackBar(throwable) }
    }

    HomeScreen(
        announcementUiState = announcementUiState,
        reviewUiState = reviewUiState,
        onNavigateToFilterSearch = onNavigateToFilterSearch,
        onNavigateToSearch = onNavigateToSearch,
        onNavigateToReview = onNavigateToReview,
        onNavigateToDetail = onNavigateToDetail,
        onNavigateToGuide = onNavigateToGuide,
        onNavigateToReviewDetail = onNavigateToReviewDetail
    )
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
private fun HomeScreen(
    announcementUiState: AnnouncementUiState,
    reviewUiState: ReviewUiState,
    onNavigateToFilterSearch: () -> Unit,
    onNavigateToSearch: () -> Unit,
    onNavigateToReview: () -> Unit,
    onNavigateToDetail: (Long) -> Unit,
    onNavigateToGuide: () -> Unit,
    onNavigateToReviewDetail: (Long) -> Unit
) {
    val scrollState = rememberScrollState()

    Scaffold(
        topBar = {
            ConnectDogTopAppBar(
                titleRes = null,
                navigationType = TopAppBarNavigationType.HOME,
                actionButtons = {
                    IconButton(onClick = {}) {
                        Icon(
                            imageVector = Icons.Outlined.Notifications,
                            contentDescription = "Navigate to Search",
                            modifier = Modifier.clickable {  }
                        )
                    }
                }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .verticalScroll(scrollState)
                .fillMaxSize()
                .padding(top = 48.dp, bottom = 90.dp)
        ) {
            SearchBar(onClick = onNavigateToFilterSearch)
            BannerGuideline(onNavigateToGuide)
            MoveContent(onClick = { onNavigateToSearch() }, titleRes = R.string.home_navigate_search)
            AnnouncementContent(announcementUiState, onClick = onNavigateToDetail)
            MoveContent(onClick = { onNavigateToReview() }, titleRes = R.string.home_navigate_review)
            ReviewContent(uiState = reviewUiState, onClick = onNavigateToReviewDetail)
        }
    }
}


@Composable
fun MoveContent(
    onClick: () -> Unit,
    titleRes: Int
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(horizontal = 20.dp, vertical = 20.dp)
            .fillMaxWidth()
    ) {
        Text(
            text = stringResource(id = titleRes),
            style = MaterialTheme.typography.titleMedium,
            fontSize = 18.sp
        )
        IconButton(onClick = { onClick() }) {
            Icon(
                painter = painterResource(id = R.drawable.ic_right_arrow),
                contentDescription = "move to another screen",
                modifier = Modifier.size(24.dp),
                tint = Gray2
            )
        }
    }
}

@Composable
private fun AnnouncementContent(uiState: AnnouncementUiState, onClick: (Long) -> Unit) {
    val modifier = Modifier.padding(horizontal = 20.dp)
    when (uiState) {
        is AnnouncementUiState.Announcements -> {
            AnnouncementListContent(
                list = uiState.announcementHomes,
                modifier = modifier,
                arrangement = Arrangement.spacedBy(12.dp),
                onClick = onClick
            )
        }

        else -> AnnouncementLoading(
            modifier = modifier,
            arrangement = Arrangement.spacedBy(12.dp)
        )
    }
}

@Composable
private fun ReviewContent(
    uiState: ReviewUiState,
    onClick: (Long) -> Unit
) {
    val modifier = Modifier.padding(horizontal = 20.dp)
    when (uiState) {
        is ReviewUiState.Reviews -> {
            ReviewListContent(
                list = uiState.reviews,
                modifier = modifier,
                arrangement = Arrangement.spacedBy(12.dp),
                onClick = onClick
            )
        }

        else -> ReviewLoading(modifier = modifier, arrangement = Arrangement.spacedBy(12.dp))
    }
}

@Composable
fun AnnouncementListContent(
    list: List<AnnouncementHome>,
    modifier: Modifier,
    arrangement: Arrangement.Horizontal,
    onClick: (Long) -> Unit
) {
    LazyRow(horizontalArrangement = arrangement, modifier = modifier) {
        items(list.take(10)) {
            AnnouncementCardContent(
                announcementHome = it,
                onClick = { onClick(it.postId.toLong()) }
            )
        }
    }
}

@Composable
fun AnnouncementLoading(
    modifier: Modifier,
    arrangement: Arrangement.Horizontal
) {
    val list = List(4) { AnnouncementHome.loading() }
    LazyRow(horizontalArrangement = arrangement, modifier = modifier) {
        items(list) {
            AnnouncementCardContent(announcementHome = it, onClick = {})
        }
    }
}

@Composable
private fun ReviewListContent(
    list: List<Review>,
    modifier: Modifier,
    arrangement: Arrangement.Horizontal,
    onClick: (Long) -> Unit
) {
    LazyRow(horizontalArrangement = arrangement, modifier = modifier) {
        items(list.take(10)) {
            ReviewCardContent(
                review = it,
                onClick = onClick
            )
        }
    }
}

@Composable
private fun ReviewLoading(modifier: Modifier, arrangement: Arrangement.Horizontal) {
    val list = List(4) {
        Review(
            profileNum = 0,
            dogName = "멍멍이",
            userName = "츄",
            date = "23.10.19(목)",
            location = "서울 강남구 -> 서울 도봉구",
            organization = "단체이름",
            content = "진짜 천사같은 아기와 하루를 함께해서 행복했습니다 너무 감사드려요 봉사 또 해야징 ><",
            contentImages = null,
            mainImage = ""
        )
    }
    LazyRow(horizontalArrangement = arrangement, modifier = modifier) {
        items(list) {
            ReviewCardContent(review = it, onClick = { })
        }
    }
}

@Composable
private fun AnnouncementCardContent(
    announcementHome: AnnouncementHome,
    onClick: () -> Unit = {}
) {
    Column(
        horizontalAlignment = Alignment.Start,
        modifier = Modifier
            .width(150.dp)
            .clickable { onClick() }
    ) {
        NetworkImage(
            imageUrl = announcementHome.imageUrl,
            placeholder = ColorPainter(MaterialTheme.colorScheme.primaryContainer),
            modifier = Modifier
                .size(150.dp)
                .shadow(shape = RoundedCornerShape(12.dp), elevation = 1.dp)
        )
        Text(
            text = announcementHome.dogName,
            maxLines = 2,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(start = 1.dp, top = 10.dp, bottom = 8.dp)
        )
        Text(
            text = announcementHome.location,
            color = Gray3,
            fontSize = 12.sp,
            fontWeight = FontWeight.Normal
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextWithIcon(
            text = announcementHome.date.substringBefore(" "),
            iconId = R.drawable.ic_calendar
        )
        Spacer(modifier = Modifier.height(5.dp))
        TextWithIcon(text = announcementHome.pickUpTime, iconId = R.drawable.ic_clock)
    }
}

@Composable
private fun ReviewCardContent(
    review: Review,
    onClick: (Long) -> Unit
) {
    Surface(
        color = MaterialTheme.colorScheme.surface,
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(width = 1.dp, color = MaterialTheme.colorScheme.outline),
        modifier = Modifier.clickable { review.reviewId?.let { onClick(it) } }
    ) {
        ConnectDogReview(review = review, modifier = Modifier.width(272.dp), type = ReviewType.HOME)
    }
}
