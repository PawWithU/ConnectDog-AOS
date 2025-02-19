package com.kusitms.connectdog.feature.home.screen

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kusitms.connectdog.core.designsystem.component.BannerGuideline
import com.kusitms.connectdog.core.designsystem.component.ConnectDogTopAppBar
import com.kusitms.connectdog.core.designsystem.component.NetworkImage
import com.kusitms.connectdog.core.designsystem.component.ReviewHome
import com.kusitms.connectdog.core.designsystem.component.TitleWithIcon
import com.kusitms.connectdog.core.designsystem.component.TopAppBarNavigationType
import com.kusitms.connectdog.core.designsystem.component.text.TextWithIcon
import com.kusitms.connectdog.core.designsystem.theme.ConnectDogTheme
import com.kusitms.connectdog.core.designsystem.theme.Gray1
import com.kusitms.connectdog.core.designsystem.theme.Gray3
import com.kusitms.connectdog.core.designsystem.theme.Gray5
import com.kusitms.connectdog.core.designsystem.theme.Gray7
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

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        ConnectDogTopAppBar(
            titleRes = null,
            navigationType = TopAppBarNavigationType.HOME,
            navigationIconContentDescription = "HOME",
            actionButtons = {
                IconButton(onClick = {}) {
                    Icon(
                        imageVector = Icons.Outlined.Notifications,
                        contentDescription = "Navigate to Search",
                        modifier = Modifier.clickable { onNavigateToNotification() }
                    )
                }
            }
        )
        HomeScreen(
            announcementUiState = announcementUiState,
            reviewUiState = reviewUiState,
            onNavigateToSearch = onNavigateToSearch,
            onNavigateToReview = onNavigateToReview,
            onNavigateToDetail = onNavigateToDetail,
            onNavigateToGuide = onNavigateToGuide,
            onNavigateToReviewDetail = onNavigateToReviewDetail,
            onNavigateToFilterSearch = onNavigateToFilterSearch
        )
    }
}

@Composable
private fun HomeScreen(
    announcementUiState: AnnouncementUiState,
    reviewUiState: ReviewUiState,
    onNavigateToSearch: () -> Unit,
    onNavigateToReview: () -> Unit,
    onNavigateToDetail: (Long) -> Unit,
    onNavigateToGuide: () -> Unit,
    onNavigateToReviewDetail: (Long) -> Unit,
    onNavigateToFilterSearch: () -> Unit
) {
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .verticalScroll(scrollState)
            .fillMaxSize()
    ) {
        Spacer(modifier = Modifier.height(12.dp))
        SearchBar(onClick = onNavigateToFilterSearch)
        Spacer(modifier = Modifier.height(24.dp))
        BannerGuideline(onNavigateToGuide)
        TitleWithIcon(onClick = { onNavigateToSearch() }, titleRes = R.string.home_navigate_search)
        AnnouncementContent(announcementUiState, onClick = onNavigateToDetail)
        VerticalDivider(modifier = Modifier.height(8.dp).fillMaxWidth(), color = Gray7)
        TitleWithIcon(onClick = { onNavigateToReview() }, titleRes = R.string.home_navigate_review)
        ReviewContent(uiState = reviewUiState, onClick = onNavigateToReviewDetail)
        Spacer(modifier = Modifier.height(90.dp))
    }
}

@Composable
private fun SearchBar(
    onClick: () -> Unit,
) {
    Box(
        modifier = Modifier
            .height(50.dp)
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .border(
                width = 1.dp,
                color = Gray5,
                shape = RoundedCornerShape(90.dp)
            )
            .clickable { onClick() },
        contentAlignment = Alignment.CenterStart
    ) {
        Icon(
            modifier = Modifier
                .padding(start = 20.dp)
                .size(24.dp),
            imageVector = Icons.Filled.Search,
            tint = Gray3,
            contentDescription = "Navigate to Search"
        )
        Text(
            modifier = Modifier.padding(start = 52.dp),
            text = buildAnnotatedString {
                withStyle(
                    SpanStyle(
                        color = Gray1,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium
                    )
                ) {
                    append(stringResource(id = R.string.search_bar_title_1))
                }
                withStyle(
                    SpanStyle(
                        color = Gray3,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Medium
                    )
                ) {
                    append(stringResource(id = R.string.search_bar_title_2))
                }
            },
            lineHeight = 15.sp
        )
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
    val list = List(4) {
        AnnouncementHome("", "이동봉사 위치", "YY.mm.dd(요일)", -1, "", "")
    }
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
            iconId = R.drawable.ic_clock
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
    ReviewHome(
        onClick = onClick,
        review = review
    )
}

@Preview
@Composable
private fun HomeScreenPreview() {
    ConnectDogTheme {
        Column(modifier = Modifier.background(Color.White)) {
            HomeScreen(
                announcementUiState = AnnouncementUiState.Empty,
                reviewUiState = ReviewUiState.Empty,
                {},
                {},
                {},
                {},
                {},
                {}
            )
        }
    }
}
