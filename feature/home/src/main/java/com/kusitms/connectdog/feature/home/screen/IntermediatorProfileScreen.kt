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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.kusitms.connectdog.core.data.api.model.intermediator.IntermediatorInfoResponseItem
import com.kusitms.connectdog.core.designsystem.component.ConnectDogInformationCard
import com.kusitms.connectdog.core.designsystem.component.ConnectDogOutlinedButton
import com.kusitms.connectdog.core.designsystem.component.ConnectDogTopAppBar
import com.kusitms.connectdog.core.designsystem.component.DetailInfo
import com.kusitms.connectdog.core.designsystem.component.NetworkImage
import com.kusitms.connectdog.core.designsystem.component.TopAppBarNavigationType
import com.kusitms.connectdog.core.designsystem.theme.Gray1
import com.kusitms.connectdog.core.designsystem.theme.Gray2
import com.kusitms.connectdog.core.designsystem.theme.Gray4
import com.kusitms.connectdog.core.designsystem.theme.Gray7
import com.kusitms.connectdog.core.model.Announcement
import com.kusitms.connectdog.feature.home.IntermediatorProfileViewModel
import com.kusitms.connectdog.feature.home.R
import kotlinx.coroutines.launch

val pages = listOf("기본 정보", "후기", "근황")

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun IntermediatorProfileScreen(
    onBackClick: () -> Unit = {},
//    onDetailClick: (Long) -> Unit,
    intermediaryId: Long,
    viewModel: IntermediatorProfileViewModel = hiltViewModel()
) {
    viewModel.initIntermediatorProfile(intermediaryId)
    viewModel.initIntermediatorReview(intermediaryId)
    val intermediator by viewModel.intermediator.observeAsState(null)
    val notice by viewModel.notice.observeAsState(null)
//    val review by viewModel.review.observeAsState(null)

    val noticeItem = notice?.let { item ->
        List(item.size) {
            Announcement(
                imageUrl = item[it].mainImage,
                location = "${item[it].departureLoc} → ${item[it].arrivalLoc}",
                date = "${item[it].startDate} ~ ${item[it].endDate}",
                organization = item[it].intermediaryName,
                hasKennel = item[it].isKennel,
                postId = item[it].postId.toInt()
            )
        }
    }
    Scaffold(
        topBar = {
            ConnectDogTopAppBar(
                titleRes = null,
                navigationType = TopAppBarNavigationType.BACK,
                navigationIconContentDescription = "Navigation icon",
                onNavigationClick = onBackClick
            )
        }
    ) {
        intermediator?.let {
            if (noticeItem != null) {
                Content(it, noticeItem)
            }
        }
    }
}

@Composable
private fun Content(intermediator: IntermediatorInfoResponseItem, noticeItem: List<Announcement>) {
    Column {
        Spacer(modifier = Modifier.height(80.dp))
        IntermediatorProfile(intermediator, noticeItem)
    }
}

@Composable
fun IntermediatorProfile(
    intermediator: IntermediatorInfoResponseItem,
    noticeItem: List<Announcement>
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        NetworkImage(imageUrl = intermediator.profileImage, modifier = Modifier.size(80.dp))
        Spacer(modifier = Modifier.height(12.dp))
        ConnectDogOutlinedButton(
            width = 51,
            height = 14,
            text = "봉사 3회 진행",
            padding = 4,
            onClick = { /*TODO*/ }
        )
        Spacer(modifier = Modifier.height(12.dp))
        Text(intermediator.name, fontSize = 18.sp, color = Gray1, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = intermediator.intro,
            fontSize = 12.sp,
            color = Gray4,
            modifier = Modifier.widthIn(min = 0.dp, max = 240.dp),
            lineHeight = 15.sp,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(32.dp))
        TabLayout(intermediator, noticeItem)
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun TabLayout(
    intermediator: IntermediatorInfoResponseItem,
    noticeItem: List<Announcement>
) {
    Surface {
        Column {
            val pagerState = rememberPagerState()
            val coroutineScope = rememberCoroutineScope()
            TabRow(
                selectedTabIndex = pagerState.currentPage
            ) {
                pages.forEachIndexed { index, title ->
                    Tab(
                        text = {
                            Text(
                                text = title,
                                color = if (pagerState.currentPage == index) {
                                    MaterialTheme.colorScheme.primary
                                } else {
                                    Gray2
                                }
                            )
                        },
                        selected = pagerState.currentPage == index,
                        onClick = {
                            coroutineScope.launch {
                                pagerState.scrollToPage(index)
                            }
                        }
                    )
                }
            }

            HorizontalPager(
                count = pages.size,
                state = pagerState
            ) {
                when (it) {
                    0 -> Information(intermediator, noticeItem)
                    1 -> Review()
                    2 -> News()
                }
            }
        }
    }
}

@Composable
fun Information(
    intermediator: IntermediatorInfoResponseItem,
    noticeItem: List<Announcement>
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        IntermediatorInformation(intermediator)
        Divider(
            Modifier
                .height(8.dp)
                .fillMaxWidth(),
            color = Gray7
        )
        Announcement(noticeItem)
        Spacer(modifier = Modifier.height(30.dp))
    }
}

@Composable
fun IntermediatorInformation(intermediator: IntermediatorInfoResponseItem) {
    Column(
        modifier = Modifier.padding(all = 24.dp),
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            text = "중개자 정보",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
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
fun Announcement(noticeItem: List<Announcement>) {
    val modifier = Modifier.padding(horizontal = 20.dp)
    Column() {
        MoveContent(onClick = { }, titleRes = R.string.home_navigate_search)
        AnnouncementListContent(list = noticeItem, modifier = modifier, arrangement = Arrangement.spacedBy(12.dp), onClick = {})
    }
}

@Composable
fun Review() {
    val modifier = Modifier.padding(horizontal = 0.dp)
    Column(
        verticalArrangement = Arrangement.Top
    ) {
        ReviewLoading(modifier = modifier)
    }
}

@Composable
fun News() {
    Column(
        modifier = Modifier.padding(all = 24.dp),
        verticalArrangement = Arrangement.Top
    ) {
    }
}

// @Preview
// @Composable
// private fun test() {
//    ConnectDogTheme {
//        IntermediatorProfileScreen(intermediaryId = it.arguments!!.getLong("intermediaryId"))
//    }
// }
