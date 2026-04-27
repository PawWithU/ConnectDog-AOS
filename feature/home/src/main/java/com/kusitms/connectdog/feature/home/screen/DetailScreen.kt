package com.kusitms.connectdog.feature.home.screen

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.kusitms.connectdog.core.data.api.model.volunteer.NoticeDetailResponseItem
import com.kusitms.connectdog.core.designsystem.component.ConnectDogBottomButton
import com.kusitms.connectdog.core.designsystem.component.ConnectDogDetailTopAppBar
import com.kusitms.connectdog.core.designsystem.component.ConnectDogInformationCard
import com.kusitms.connectdog.core.designsystem.component.ConnectDogTag
import com.kusitms.connectdog.core.designsystem.component.ConnectDogTagWithIcon
import com.kusitms.connectdog.core.designsystem.component.NetworkImage
import com.kusitms.connectdog.core.designsystem.component.button.BookmarkButton
import com.kusitms.connectdog.core.designsystem.component.button.ProfileButton
import com.kusitms.connectdog.core.designsystem.component.text.DetailInfo
import com.kusitms.connectdog.core.designsystem.component.text.TextWithIcon
import com.kusitms.connectdog.core.designsystem.theme.Gray2
import com.kusitms.connectdog.core.designsystem.theme.Gray3
import com.kusitms.connectdog.core.designsystem.theme.Gray7
import com.kusitms.connectdog.feature.home.DetailViewModel
import com.kusitms.connectdog.feature.home.R

private const val TAG = "DetailScreen"

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun DetailScreen(
    onBackClick: () -> Unit,
    onApplyClick: (Long) -> Unit,
    onIntermediatorProfileClick: (Long) -> Unit,
    postId: Long,
    viewModel: DetailViewModel = hiltViewModel()
) {
    val detail by viewModel.detail.observeAsState(null)
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.initNoticeDetail(postId)
    }

    LaunchedEffect(postId) {
        viewModel.initNoticeDetail(postId)
    }

    Scaffold(
        topBar = {
            ConnectDogDetailTopAppBar(
                onBackClick = onBackClick,
                onShareClick = {
                    Toast.makeText(context, "아직 준비중인 기능입니다.", Toast.LENGTH_SHORT).show()
                }
            )
        },
        bottomBar = {
            detail?.isBookmark
                ?.let {
                    BottomBar(
                        isBookmark = it,
                        onSaveClick = { viewModel.postBookmark(postId) },
                        onDeleteClick = { viewModel.deleteBookmark(postId) },
                        onClick = { onApplyClick(postId) },
                        postStatus = detail!!.postStatus
                    )
                }
        }
    ) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
        ) {
            if (detail != null) {
                val imageList = detail!!.images.ifEmpty { listOf(detail!!.mainImage) }
                val imagePagerState = rememberPagerState { imageList.size }

                Spacer(modifier = Modifier.height(48.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(250.dp)
                ) {
                    HorizontalPager(
                        state = imagePagerState,
                        modifier = Modifier.fillMaxSize()
                    ) { page ->
                        NetworkImage(
                            imageUrl = imageList[page],
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                    Box(
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(16.dp)
                            .background(
                                color = Color.Black.copy(alpha = 0.5f),
                                shape = RoundedCornerShape(12.dp)
                            )
                            .padding(horizontal = 10.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = "${imagePagerState.currentPage + 1}/${imageList.size}",
                            color = Color.White,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
                Content(
                    detail = detail!!,
                    onIntermediatorProfileClick = onIntermediatorProfileClick
                )
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Content(
    detail: NoticeDetailResponseItem,
    onIntermediatorProfileClick: (Long) -> Unit
) {
    val tabItems = listOf(
        "이동봉사 정보",
        "동물 정보",
        "모집자 정보"
    )

    var selectedTabIndex by remember {
        mutableIntStateOf(0)
    }
    val pagerState = rememberPagerState {
        tabItems.size
    }
    LaunchedEffect(selectedTabIndex) {
        pagerState.animateScrollToPage(selectedTabIndex)
    }
    LaunchedEffect(pagerState.currentPage) {
        selectedTabIndex = pagerState.currentPage
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        BasicInfo(detail = detail)
        Divider(
            Modifier
                .height(8.dp)
                .fillMaxWidth(),
            color = Gray7
        )
        TabRow(selectedTabIndex = selectedTabIndex) {
            tabItems.forEachIndexed { index, title ->
                Tab(
                    selected = index == selectedTabIndex,
                    onClick = {
                        selectedTabIndex = index
                    },
                    text = {
                        Text(
                            text = title,
                            style = MaterialTheme.typography.titleSmall,
                            fontSize = 14.sp,
                            color = if (index == selectedTabIndex) MaterialTheme.colorScheme.primary else Gray2
                        )
                    }
                )
            }
        }
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.Top
        ) { index ->
            when (index) {
                0 -> VolunteerInfo(detail = detail)
                1 -> DogInfo(detail = detail)
                2 -> IntermediatorInfo(detail = detail, onClick = onIntermediatorProfileClick)
            }
        }
    }
}

@Composable
private fun BasicInfo(
    detail: NoticeDetailResponseItem
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(all = 24.dp)
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = detail.dogName,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
            Spacer(modifier = Modifier.weight(1f))
            ConnectDogTag(detail.postStatus)
        }
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = "${detail.departureLoc} → ${detail.arrivalLoc}",
            fontSize = 12.sp,
            color = Gray3
        )
        Spacer(modifier = Modifier.height(6.dp))
        TextWithIcon(iconId = R.drawable.ic_clock, text = detail.startDate, size = 14)
        Spacer(modifier = Modifier.height(8.dp))
        TextWithIcon(iconId = R.drawable.ic_clock, text = detail.pickUpTime, size = 14)
        Spacer(modifier = Modifier.height(17.dp))
        Row {
            ConnectDogTagWithIcon(
                iconId = R.drawable.ic_dog_size,
                backgroundColor = Gray7,
                contentColor = Gray3,
                text = detail.dogSize
            )
            Spacer(modifier = Modifier.width(4.dp))
            ConnectDogTagWithIcon(
                iconId = R.drawable.ic_kennel,
                backgroundColor = Gray7,
                contentColor = Gray3,
                text = if (detail.isKennel) {
                    stringResource(id = com.kusitms.connectdog.core.designsystem.R.string.has_kennel)
                } else {
                    stringResource(id = com.kusitms.connectdog.core.designsystem.R.string.has_not_kennel)
                }
            )
        }
    }
}

@Composable
fun VolunteerInfo(detail: NoticeDetailResponseItem) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 24.dp, end = 24.dp, top = 24.dp, bottom = 137.dp)
    ) {
        Text(
            text = "이동봉사 정보",
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp
        )
        Spacer(modifier = Modifier.height(20.dp))
        DetailInfo("출발지", detail.departureLoc)
        Spacer(modifier = Modifier.height(8.dp))
        DetailInfo("도착지", detail.arrivalLoc)
        Spacer(modifier = Modifier.height(8.dp))
        DetailInfo("픽업 일시", detail.pickUpTime)
        Spacer(modifier = Modifier.height(8.dp))
        Spacer(modifier = Modifier.height(40.dp))
        Text(
            text = detail.content,
            fontSize = 15.sp,
            fontWeight = FontWeight.Normal,
            lineHeight = 22.sp
        )
    }
}

@Composable
fun DogInfo(detail: NoticeDetailResponseItem) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 24.dp, end = 24.dp, top = 24.dp, bottom = 137.dp)
    ) {
        Text(
            text = "강아지 정보",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(20.dp))
        DetailInfo("이름", detail.dogName)
        Spacer(modifier = Modifier.height(8.dp))
        DetailInfo("동물 크기", detail.dogSize)
        Spacer(modifier = Modifier.height(20.dp))
        ConnectDogInformationCard(title = "특이사항", content = detail.specifics ?: "없습니다.")
    }
    Divider(
        Modifier
            .height(8.dp)
            .fillMaxWidth(),
        color = Gray7
    )
}

@Composable
fun IntermediatorInfo(
    detail: NoticeDetailResponseItem,
    onClick: (Long) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 24.dp, end = 24.dp, top = 24.dp, bottom = 137.dp)
    ) {
        Text(
            text = "이동봉사 중개",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(20.dp))
        Row(
            modifier = Modifier
                .height(40.dp)
                .fillMaxWidth()
        ) {
            NetworkImage(
                imageUrl = detail.intermediaryProfileImage,
                modifier = Modifier
                    .width(40.dp)
                    .height(40.dp)
                    .align(Alignment.CenterVertically)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                modifier = Modifier
                    .width(158.dp)
                    .align(Alignment.CenterVertically)
                    .weight(1f),
                text = detail.intermediaryName,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )
            ProfileButton(
                modifier = Modifier
                    .wrapContentWidth()
                    .height(34.dp)
                    .align(Alignment.CenterVertically),
                onClick = { onClick(detail.intermediaryId.toLong()) }
            )
        }
    }
}

@Composable
private fun BottomBar(
    isBookmark: Boolean,
    onSaveClick: () -> Unit,
    onDeleteClick: () -> Unit,
    postStatus: String,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(112.dp)
            .padding(horizontal = 20.dp)
            .background(Color.White)
    ) {
        Row(
            modifier = Modifier.align(Alignment.Center)
        ) {
            BookmarkButton(isBookmark, onSaveClick, onDeleteClick)
            Spacer(modifier = Modifier.width(10.dp))
            ConnectDogBottomButton(
                content = when (postStatus) {
                    "모집중" -> "신청하기"
                    "승인 대기중" -> "승인 대기중인 공고입니다"
                    "봉사 완료" -> "봉사 완료된 공고입니다"
                    "마감" -> "마감된 공고입니다"
                    else -> ""
                },
                enabled = postStatus == "모집중",
                onClick = onClick
            )
        }
    }
}
