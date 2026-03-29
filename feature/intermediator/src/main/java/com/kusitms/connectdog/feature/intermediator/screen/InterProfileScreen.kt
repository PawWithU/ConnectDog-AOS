package com.kusitms.connectdog.feature.intermediator.screen

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.kusitms.connectdog.core.designsystem.component.AnnouncementContent
import com.kusitms.connectdog.core.designsystem.component.ConnectDogBottomButton
import com.kusitms.connectdog.core.designsystem.component.ConnectDogInformationCard
import com.kusitms.connectdog.core.designsystem.component.ConnectDogTopAppBar
import com.kusitms.connectdog.core.designsystem.component.Loading
import com.kusitms.connectdog.core.designsystem.component.NetworkImage
import com.kusitms.connectdog.core.designsystem.component.ReviewItemContent
import com.kusitms.connectdog.core.designsystem.component.TopAppBarNavigationType
import com.kusitms.connectdog.core.designsystem.component.text.DetailInfo
import com.kusitms.connectdog.core.designsystem.theme.Gray1
import com.kusitms.connectdog.core.designsystem.theme.Gray2
import com.kusitms.connectdog.core.designsystem.theme.Gray4
import com.kusitms.connectdog.core.designsystem.theme.Gray7
import com.kusitms.connectdog.core.util.UserType
import com.kusitms.connectdog.feature.intermediator.R
import com.kusitms.connectdog.feature.intermediator.state.InterProfileFindingUiState
import com.kusitms.connectdog.feature.intermediator.state.InterProfileInfoUiState
import com.kusitms.connectdog.feature.intermediator.state.InterProfileReviewUiState
import com.kusitms.connectdog.feature.intermediator.viewmodel.InterProfileViewModel
import kotlinx.coroutines.launch

private val pages = listOf("기본 정보", "모집중", "완료 및 후기")

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
internal fun InterProfileScreen(
    onBackClick: () -> Unit,
    onNavigateToInterProfileEdit: (String) -> Unit,
    onNavigateToAnnouncementManagement: (Long) -> Unit,
    viewModel: InterProfileViewModel = hiltViewModel()
) {
    Scaffold(
        topBar = {
            ConnectDogTopAppBar(
                titleRes = R.string.inter_profile,
                navigationType = TopAppBarNavigationType.BACK,
                navigationIconContentDescription = "Navigation icon",
                onNavigationClick = onBackClick
            )
        }
    ) {
        Content(
            onNavigateToInterProfileEdit = onNavigateToInterProfileEdit,
            viewModel = viewModel,
            onNavigateToAnnouncementManagement = onNavigateToAnnouncementManagement
        )
    }
}

@Composable
private fun Content(
    onNavigateToInterProfileEdit: (String) -> Unit,
    onNavigateToAnnouncementManagement: (Long) -> Unit,
    viewModel: InterProfileViewModel
) {
    val reviewUiState by viewModel.interProfileReviewUiState.collectAsStateWithLifecycle()
    val findingUiState by viewModel.interProfileFindingUiState.collectAsStateWithLifecycle()
    val infoUiState by viewModel.interProfileInfoUiState.collectAsStateWithLifecycle()

    val context = LocalContext.current

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(80.dp))
        when (infoUiState) {
            is InterProfileInfoUiState.Loading -> {
                InterInfo(imageUrl = "", name = "", intro = "")
            }

            is InterProfileInfoUiState.InterProfile -> {
                val data = (infoUiState as InterProfileInfoUiState.InterProfile).data
                InterInfo(imageUrl = data.profileImage, name = data.name, intro = data.intro)
                Spacer(modifier = Modifier.height(8.dp))
                ConnectDogBottomButton(
                    onClick = {
                        Toast.makeText(context, "아직 준비중인 기능입니다.", Toast.LENGTH_SHORT).show()
//                        onNavigateToInterProfileEdit(data.profileImage)
                    },
                    content = "프로필 수정",
                    textColor = Gray1,
                    enabledColor = Gray7,
                    modifier = Modifier
                        .height(40.dp)
                        .padding(horizontal = 20.dp),
                    fontSize = 12,
                    paddingValues = PaddingValues(vertical = 11.dp),
                    border = BorderStroke(0.dp, color = Gray7)
                )
                Spacer(modifier = Modifier.height(32.dp))
                TabLayout(
                    info = infoUiState,
                    finding = findingUiState,
                    review = reviewUiState,
                    onNavigateToAnnouncementManagement = onNavigateToAnnouncementManagement
                )
            }
        }
    }
}

@Composable
private fun InterInfo(
    imageUrl: String,
    name: String,
    intro: String
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        NetworkImage(imageUrl = imageUrl, modifier = Modifier.size(80.dp))
        Spacer(modifier = Modifier.height(12.dp))
        Spacer(modifier = Modifier.height(12.dp))
        Text(name, fontSize = 18.sp, color = Gray1, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            modifier = Modifier.padding(horizontal = 60.dp),
            text = intro,
            fontSize = 12.sp,
            color = Gray4,
            lineHeight = 15.sp
        )
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
private fun TabLayout(
    info: InterProfileInfoUiState,
    finding: InterProfileFindingUiState,
    review: InterProfileReviewUiState,
    onNavigateToAnnouncementManagement: (Long) -> Unit
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
                    0 -> IntermediatorInformation(info)
                    1 -> Finding(finding, onNavigateToAnnouncementManagement)
                    2 -> CompleteAndReview(review)
                }
            }
        }
    }
}

@Composable
private fun IntermediatorInformation(
    info: InterProfileInfoUiState
) {
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .padding(all = 24.dp)
            .fillMaxSize()
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            text = "중개자 정보",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(20.dp))
        when (info) {
            is InterProfileInfoUiState.Loading -> Loading()
            is InterProfileInfoUiState.InterProfile -> {
                DetailInfo("링크", info.data.url)
                Spacer(modifier = Modifier.height(8.dp))
                DetailInfo("문의", info.data.contact)
                Spacer(modifier = Modifier.height(20.dp))
                ConnectDogInformationCard(title = "안내사항", content = info.data.guide)
            }
        }
    }
}

@Composable
private fun Finding(
    data: InterProfileFindingUiState,
    onClick: (Long) -> Unit
) {
    Column(
        verticalArrangement = Arrangement.Top
    ) {
        when (data) {
            is InterProfileFindingUiState.Loading -> Loading()
            is InterProfileFindingUiState.Empty -> {}
            is InterProfileFindingUiState.InterProfileFinding -> {
                LazyColumn {
                    items(data.data) {
                        AnnouncementContent(
                            postId = it.postId,
                            imageUrl = it.imageUrl,
                            dogName = it.dogName,
                            location = it.location,
                            isKennel = it.isKennel,
                            dogSize = it.dogSize,
                            date = it.date,
                            pickUpTime = "",
                            onClick = onClick
                        )
                    }
                }
            }
        }

//        ReviewLoading(modifier = modifier)
    }
}

@Composable
fun CompleteAndReview(
    data: InterProfileReviewUiState
) {
    Column(
        verticalArrangement = Arrangement.Top
    ) {
        when (data) {
            is InterProfileReviewUiState.Loading -> Loading()
            is InterProfileReviewUiState.Empty -> {
            }

            is InterProfileReviewUiState.InterProfileReview -> {
                LazyColumn(modifier = Modifier.padding(horizontal = 0.dp)) {
                    items(data.review.take(30)) {
                        ReviewItemContent(
                            review = it,
                            userType = UserType.INTERMEDIATOR
                        )
                    }
                }
            }
        }
    }
}
