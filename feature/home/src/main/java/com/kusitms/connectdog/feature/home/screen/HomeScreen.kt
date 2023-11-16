package com.kusitms.connectdog.feature.home.screen

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kusitms.connectdog.core.designsystem.component.AnnouncementContent
import com.kusitms.connectdog.core.designsystem.component.ConnectDogReview
import com.kusitms.connectdog.core.designsystem.component.ConnectDogTopAppBar
import com.kusitms.connectdog.core.designsystem.component.NetworkImage
import com.kusitms.connectdog.core.designsystem.component.TopAppBarNavigationType
import com.kusitms.connectdog.core.designsystem.theme.ConnectDogTheme
import com.kusitms.connectdog.core.designsystem.theme.Gray2
import com.kusitms.connectdog.core.designsystem.theme.Gray3
import com.kusitms.connectdog.core.model.Announcement
import com.kusitms.connectdog.core.model.Review
import com.kusitms.connectdog.feature.home.HomeViewModel
import com.kusitms.connectdog.feature.home.R
import com.kusitms.connectdog.feature.home.state.AnnouncementUiState
import com.kusitms.connectdog.feature.home.state.ExampleUiState
import com.kusitms.connectdog.feature.home.state.ReviewUiState
import kotlinx.coroutines.flow.collectLatest

@Composable
internal fun HomeRoute(
    onBackClick: () -> Unit,
    onNavigateToSearch: () -> Unit,
    onNavigateToReview: () -> Unit,
    onNavigateToDetail: () -> Unit,
    onShowErrorSnackBar: (throwable: Throwable?) -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val exampleUiState by viewModel.exampleUiState.collectAsStateWithLifecycle()
    val announcementUiState by viewModel.announcementUiState.collectAsStateWithLifecycle()
    val reviewUiState by viewModel.reviewUiState.collectAsStateWithLifecycle()

    // 에러 발생할 때마다 에러 스낵바 표시
    LaunchedEffect(true) {
        viewModel.errorFlow.collectLatest { throwable -> onShowErrorSnackBar(throwable) }
    }

    Column {
        TopAppBar(onClickSearch = onNavigateToSearch)
        HomeScreen(
            exampleUiState = exampleUiState,
            announcementUiState = announcementUiState,
            reviewUiState = reviewUiState,
            onBackClick = onBackClick,
            onNavigateToSearch = onNavigateToSearch,
            onNavigateToReview = onNavigateToReview,
            onNavigateToDetail = onNavigateToDetail
        )
    }
}

@Composable
private fun HomeScreen(
    exampleUiState: ExampleUiState,
    announcementUiState: AnnouncementUiState,
    reviewUiState: ReviewUiState,
    onBackClick: () -> Unit,
    onNavigateToSearch: () -> Unit,
    onNavigateToReview: () -> Unit,
    onNavigateToDetail: () -> Unit
) {
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier.verticalScroll(scrollState)
    ) {
        TopTitle(modifier = Modifier.padding(20.dp))
        StatisticBanner(modifier = Modifier.padding(start = 20.dp, end = 20.dp, bottom = 20.dp))
        BannerGuideline()
        MoveContent(onClick = { onNavigateToSearch() }, titleRes = R.string.home_navigate_search)
        AnnouncementContent(announcementUiState, onClick = onNavigateToDetail)
        MoveContent(onClick = { onNavigateToReview() }, titleRes = R.string.home_navigate_review)
        ReviewContent(uiState = reviewUiState)
        Spacer(modifier = Modifier.height(90.dp))
    }
}

@Composable
private fun TopAppBar(
    onClickSearch: () -> Unit
) {
    ConnectDogTopAppBar(
        titleRes = null,
        navigationType = TopAppBarNavigationType.HOME,
        navigationIconContentDescription = "Navigation icon home",
        actionButtons = {
            IconButton(onClick = {
                Log.d("SearchScreen", "clickSearchIcon")
                onClickSearch()
            }) {
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = "Navigate to Search"
                )
            }
            IconButton(onClick = {}) {
                Icon(
                    imageVector = Icons.Outlined.Notifications,
                    contentDescription = "Navigate to Search"
                )
            }
        }
    )
}

@Composable
private fun TopTitle(modifier: Modifier) {
    Text(
        text = stringResource(id = R.string.home_description_title),
        style = MaterialTheme.typography.titleMedium,
        fontSize = 15.sp,
        modifier = modifier
    )
}

@Composable
private fun StatisticBanner(modifier: Modifier) {
    Column(horizontalAlignment = Alignment.End, modifier = modifier) {
        Row(
            modifier = Modifier
                .clickable { }
                .padding(end = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_info),
                contentDescription = "info icon",
                modifier = Modifier.padding(2.dp),
                tint = Gray3
            )
            Text(
                text = stringResource(id = R.string.home_counting_guide),
                style = MaterialTheme.typography.labelMedium,
                color = Gray3
            )
        }
        Surface(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            color = MaterialTheme.colorScheme.primaryContainer
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.Bottom,
                modifier = Modifier.height(80.dp)
            ) {
                StatisticInfoItem(
                    number = "105",
                    descriptionRes = R.string.home_need_move_description,
                    painter = painterResource(id = R.drawable.img_man_dog)
                )
                Divider(
                    color = MaterialTheme.colorScheme.background,
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(1.dp)
                )
                StatisticInfoItem(
                    number = "22",
                    descriptionRes = R.string.home_moved_description,
                    painter = painterResource(id = R.drawable.img_woman)
                )
            }
        }
    }
}

@Composable
private fun StatisticInfoItem(
    number: String = "0",
    descriptionRes: Int,
    painter: Painter
) {
    Row() {
        Column(horizontalAlignment = Alignment.Start) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = number,
                    style = MaterialTheme.typography.titleSmall,
                    fontSize = 20.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = stringResource(id = R.string.home_dog_unit),
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.padding(start = 2.dp)
                )
            }
            Text(
                text = stringResource(id = descriptionRes),
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
        Image(painter = painter, contentDescription = "mandog")
    }
}

@Composable
private fun BannerGuideline() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .defaultMinSize(minHeight = 52.dp)
            .background(MaterialTheme.colorScheme.primary)
            .padding(horizontal = 20.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = stringResource(id = R.string.home_banner),
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.background
        )
        Button(
            onClick = {},
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.background,
                contentColor = MaterialTheme.colorScheme.primary
            ),
            modifier = Modifier
                .wrapContentSize()
                .defaultMinSize(minHeight = 22.dp, minWidth = 96.dp)
        ) {
            Text(
                text = stringResource(id = R.string.home_banner_button_guideline),
                style = MaterialTheme.typography.titleMedium,
                fontSize = 10.sp
            )
        }
    }
}

@Composable
private fun MoveContent(
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
private fun AnnouncementContent(uiState: AnnouncementUiState, onClick: () -> Unit) {
    val modifier = Modifier.padding(horizontal = 20.dp)
    when (uiState) {
        is AnnouncementUiState.Announcements -> {
            AnnouncementListContent(
                list = uiState.announcements,
                modifier = modifier,
                arrangement = Arrangement.spacedBy(12.dp)
            )
        }

        else -> AnnouncementLoading(modifier = modifier, arrangement = Arrangement.spacedBy(12.dp), onClick = onClick)
    }
}

@Composable
private fun ReviewContent(uiState: ReviewUiState) {
    val modifier = Modifier.padding(horizontal = 20.dp)
    when (uiState) {
        is ReviewUiState.Reviews -> {
            ReviewListContent(
                list = uiState.reviews,
                modifier = modifier,
                arrangement = Arrangement.spacedBy(12.dp)
            )
        }

        else -> ReviewLoading(modifier = modifier, arrangement = Arrangement.spacedBy(12.dp))
    }
}

@Composable
private fun AnnouncementListContent(
    list: List<Announcement>,
    modifier: Modifier,
    arrangement: Arrangement.Horizontal
) {
    LazyRow(horizontalArrangement = arrangement, modifier = modifier) {
        items(list.take(10)) {
            AnnouncementCardContent(announcement = it)
        }
    }
}

@Composable
private fun AnnouncementLoading(modifier: Modifier, arrangement: Arrangement.Horizontal, onClick: () -> Unit) {
    val list = List(4) {
        Announcement("", "이동봉사 위치", "YY.mm.dd(요일)", "단체이름", false)
    }
    LazyRow(horizontalArrangement = arrangement, modifier = modifier) {
        items(list) {
            AnnouncementCardContent(announcement = it, onClick = onClick)
        }
    }
}

@Composable
private fun ReviewListContent(
    list: List<Review>,
    modifier: Modifier,
    arrangement: Arrangement.Horizontal
) {
    LazyRow(horizontalArrangement = arrangement, modifier = modifier) {
        items(list.take(10)) {
            ReviewCardContent(review = it)
        }
    }
}

@Composable
private fun ReviewLoading(modifier: Modifier, arrangement: Arrangement.Horizontal) {
    val list = List(4) {
        Review(
            profileUrl = "",
            dogName = "멍멍이",
            userName = "츄",
            contentUrl = "",
            date = "23.10.19(목)",
            location = "서울 강남구 -> 서울 도봉구",
            organization = "단체이름",
            content = "진짜 천사같은 아기와 하루를 함께해서 행복했습니다 너무 감사드려요 봉사 또 해야징 ><"
        )
    }
    LazyRow(horizontalArrangement = arrangement, modifier = modifier) {
        items(list) {
            ReviewCardContent(review = it)
        }
    }
}

@Composable
private fun AnnouncementCardContent(
    announcement: Announcement,
    onClick: () -> Unit = {}
) {
    Column(
        horizontalAlignment = Alignment.Start,
        modifier = Modifier.width(150.dp).clickable {
            onClick()
        }
    ) {
        NetworkImage(
            imageUrl = announcement.imageUrl,
            placeholder = ColorPainter(MaterialTheme.colorScheme.primaryContainer),
            modifier = Modifier
                .size(150.dp)
                .shadow(shape = RoundedCornerShape(12.dp), elevation = 2.dp)
        )
        Text(
            text = announcement.location,
            maxLines = 2,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(start = 1.dp, top = 10.dp, bottom = 8.dp)
        )
        AnnouncementContent(
            date = announcement.date,
            organization = announcement.organization,
            hasKennel = announcement.hasKennel,
            style = MaterialTheme.typography.labelMedium
        )
    }
}

@Composable
private fun ReviewCardContent(
    review: Review
) {
    Surface(
        color = MaterialTheme.colorScheme.surface,
        shape = RoundedCornerShape(12.dp),
        shadowElevation = 1.dp,
        border = BorderStroke(width = 1.dp, color = MaterialTheme.colorScheme.outline)
    ) {
        ConnectDogReview(review = review, modifier = Modifier.width(272.dp))
    }
}

@Preview
@Composable
private fun HomeScreenPreview() {
    ConnectDogTheme {
        Column(modifier = Modifier.background(Color.White)) {
            TopAppBar(onClickSearch = {})
            HomeScreen(
                exampleUiState = ExampleUiState.Empty,
                announcementUiState = AnnouncementUiState.Empty,
                reviewUiState = ReviewUiState.Empty,
                {},
                {},
                {},
                {}
            )
        }
    }
}

@Preview
@Composable
private fun AnnouncementPreview() {
    ConnectDogTheme {
        AnnouncementCardContent(
            announcement = Announcement(
                "",
                "서울시 강남구 -> 서울시 도봉구",
                "23.10.19(수)",
                "단체이름이름",
                true
            )
        )
    }
}
