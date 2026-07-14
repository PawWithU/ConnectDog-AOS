package com.kusitms.connectdog.feature.home.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.kusitms.connectdog.core.data.api.model.intermediator.IntermediatorInfoResponseItem
import com.kusitms.connectdog.core.designsystem.component.AnnouncementContent
import com.kusitms.connectdog.core.designsystem.component.ConnectDogInformationCard
import com.kusitms.connectdog.core.designsystem.component.ConnectDogTopAppBar
import com.kusitms.connectdog.core.designsystem.component.NetworkImage
import com.kusitms.connectdog.core.designsystem.component.ReviewItemContent
import com.kusitms.connectdog.core.designsystem.component.TopAppBarNavigationType
import com.kusitms.connectdog.core.designsystem.component.text.DetailInfo
import com.kusitms.connectdog.core.designsystem.theme.Gray2
import com.kusitms.connectdog.core.designsystem.theme.Gray4
import com.kusitms.connectdog.core.model.Announcement
import com.kusitms.connectdog.core.model.Review
import com.kusitms.connectdog.core.util.UserType
import com.kusitms.connectdog.feature.home.IntermediatorProfileViewModel
import kotlinx.coroutines.launch

private val pages = listOf("기본 정보", "모집중", "완료 및 후기")

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun IntermediatorProfileScreen(
    onBackClick: () -> Unit = {},
    onDetailClick: (Long) -> Unit,
    intermediaryId: Long,
    viewModel: IntermediatorProfileViewModel = hiltViewModel(),
) {
    val intermediator by viewModel.intermediator.observeAsState(null)
    val notice by viewModel.notice.observeAsState(null)
    val review by viewModel.review.observeAsState(null)

    LaunchedEffect(key1 = Unit) {
        viewModel.initIntermediatorProfile(intermediaryId)
        viewModel.initIntermediatorNotice(intermediaryId)
        viewModel.initIntermediatorReview(intermediaryId)
    }

    val noticeItem =
        notice?.let { item ->
            List(item.size) {
                Announcement(
                    imageUrl = item[it].mainImage,
                    location = "${item[it].departureLoc} → ${item[it].arrivalLoc}",
                    date = "${item[it].startDate} ~ ${item[it].endDate}",
                    postId = item[it].postId.toInt(),
                    dogName = item[it].dogName,
                    pickUpTime = item[it].pickUpTime,
                    dogSize = item[it].dogSize,
                    isKennel = item[it].isKennel,
                )
            }
        }

    val reviewItem =
        review?.let { item ->
            List(item.size) {
                item[it]
            }
        }

    Scaffold(
        topBar = {
            ConnectDogTopAppBar(
                titleRes = null,
                navigationType = TopAppBarNavigationType.BACK,
                navigationIconContentDescription = "Navigation icon",
                onNavigationClick = onBackClick,
            )
        },
    ) {
        intermediator?.let {
            if (noticeItem != null && reviewItem != null) {
                Content(it, noticeItem, reviewItem, onDetailClick)
            }
        }
    }
}

@Composable
private fun Content(
    intermediator: IntermediatorInfoResponseItem,
    noticeItem: List<Announcement>,
    reviewItem: List<Review>,
    onDetailClick: (Long) -> Unit,
) {
    Column {
        Spacer(modifier = Modifier.height(80.dp))
        IntermediatorProfile(intermediator, noticeItem, reviewItem, onDetailClick)
    }
}

@Composable
fun IntermediatorProfile(
    intermediator: IntermediatorInfoResponseItem,
    noticeItem: List<Announcement>,
    reviewItem: List<Review>,
    onDetailClick: (Long) -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        NetworkImage(imageUrl = intermediator.profileImage, modifier = Modifier.size(80.dp))
        Spacer(modifier = Modifier.height(12.dp))
        Text(intermediator.name, style = MaterialTheme.typography.headlineSmall)
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = intermediator.intro,
            style = MaterialTheme.typography.labelLarge,
            color = Gray4,
            modifier = Modifier.widthIn(min = 0.dp, max = 240.dp),
            lineHeight = 15.sp,
            textAlign = TextAlign.Center,
        )
        Spacer(modifier = Modifier.height(32.dp))
        TabLayout(intermediator, noticeItem, reviewItem, onDetailClick)
    }
}

@Composable
fun TabLayout(
    intermediator: IntermediatorInfoResponseItem,
    noticeItem: List<Announcement>,
    reviewItem: List<Review>,
    onDetailClick: (Long) -> Unit,
) {
    Surface {
        Column {
            val pagerState = rememberPagerState(pageCount = { pages.size })
            val coroutineScope = rememberCoroutineScope()
            TabRow(
                selectedTabIndex = pagerState.currentPage,
            ) {
                pages.forEachIndexed { index, title ->
                    Tab(
                        text = {
                            Text(
                                text = title,
                                color =
                                    if (pagerState.currentPage == index) {
                                        MaterialTheme.colorScheme.primary
                                    } else {
                                        Gray2
                                    },
                            )
                        },
                        selected = pagerState.currentPage == index,
                        onClick = {
                            coroutineScope.launch {
                                pagerState.scrollToPage(index)
                            }
                        },
                    )
                }
            }

            HorizontalPager(
                state = pagerState,
            ) {
                when (it) {
                    0 -> Information(intermediator)
                    1 -> InProgress(noticeItem, onDetailClick)
                    2 -> Review(reviewItem)
                }
            }
        }
    }
}

@Composable
private fun Information(intermediator: IntermediatorInfoResponseItem) {
    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
    ) {
        IntermediatorInformation(intermediator)
        Spacer(modifier = Modifier.height(30.dp))
    }
}

@Composable
private fun IntermediatorInformation(intermediator: IntermediatorInfoResponseItem) {
    Column(
        modifier = Modifier.padding(all = 24.dp),
        verticalArrangement = Arrangement.Top,
    ) {
        Text(
            text = "중개자 정보",
            style = MaterialTheme.typography.titleMedium,
        )
        Spacer(modifier = Modifier.height(20.dp))
        DetailInfo("링크", intermediator.url)
        Spacer(modifier = Modifier.height(8.dp))
        DetailInfo("문의", intermediator.contact)
        Spacer(modifier = Modifier.height(20.dp))
        ConnectDogInformationCard(title = "안내사항", content = intermediator.guide)
    }
}

@Composable
private fun Review(reviewItem: List<Review>) {
    LazyColumn(
        verticalArrangement = Arrangement.Top,
        modifier = Modifier.fillMaxSize(),
    ) {
        items(reviewItem.take(30)) {
            ReviewItemContent(
                review = it,
                onInterProfileClick = {},
                userType = UserType.INTERMEDIATOR,
            )
        }
    }
}

@Composable
private fun InProgress(
    list: List<Announcement>,
    onDetailClick: (Long) -> Unit,
) {
    Column(
        verticalArrangement = Arrangement.Top,
        modifier = Modifier.fillMaxSize(),
    ) {
        LazyColumn {
            items(list) {
                AnnouncementContent(
                    postId = it.postId,
                    imageUrl = it.imageUrl,
                    dogName = it.dogName,
                    location = it.location,
                    isKennel = it.isKennel,
                    dogSize = it.dogSize,
                    date = it.date,
                    pickUpTime = it.pickUpTime,
                    onClick = onDetailClick,
                )
            }
        }
    }
}
