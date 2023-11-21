package com.kusitms.connectdog.feature.management

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kusitms.connectdog.core.designsystem.component.ConnectDogSecondaryButton
import com.kusitms.connectdog.core.designsystem.component.ConnectDogTopAppBar
import com.kusitms.connectdog.core.designsystem.component.ListForUserItem
import com.kusitms.connectdog.core.designsystem.component.TopAppBarNavigationType
import com.kusitms.connectdog.core.designsystem.theme.ConnectDogTheme
import com.kusitms.connectdog.core.designsystem.theme.Gray1
import com.kusitms.connectdog.core.designsystem.theme.Gray2
import com.kusitms.connectdog.core.designsystem.theme.Gray4
import com.kusitms.connectdog.core.designsystem.theme.Gray7
import com.kusitms.connectdog.core.model.Application

val TAG = "ManagementScreen"

@Composable
internal fun ManagementRoute(
    onBackClick: () -> Unit,
    onShowErrorSnackBar: (throwable: Throwable?) -> Unit,
    viewModel: ManagementViewModel = hiltViewModel()
) {

    val pendingUiState by viewModel.waitingUiState.collectAsStateWithLifecycle()
    val inProgressUiState by viewModel.progressUiState.collectAsStateWithLifecycle()
    val completedUiState by viewModel.completedUiState.collectAsStateWithLifecycle()

    Column {
        ConnectDogTopAppBar(
            titleRes = null,
            navigationType = TopAppBarNavigationType.MANAGEMENT,
            navigationIconContentDescription = "Navigation icon",
            onNavigationClick = onBackClick
        )
        ManagementScreen(
            firstContent = { PendingApproval(pendingUiState) {

            } },
            secondContent = { InProgress(inProgressUiState) },
            thirdContent = { Completed(completedUiState, onClickReview = {}, onClickRecent = {}) }
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun ManagementScreen(
    firstContent: @Composable () -> Unit,
    secondContent: @Composable () -> Unit,
    thirdContent: @Composable () -> Unit,
) {

    val tabItems = listOf(
        stringResource(id = R.string.pending_approval),
        stringResource(id = R.string.inProgress),
        stringResource(id = R.string.completed)
    )

    Surface(modifier = Modifier.fillMaxSize()) {
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
        Column(modifier = Modifier.fillMaxSize()) {
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
                    .fillMaxWidth()
                    .weight(1f),
                verticalAlignment = Alignment.Top
            ) { index ->
                when (index) {
                    0 -> firstContent()
                    1 -> secondContent()
                    2 -> thirdContent()
                }
            }
        }
    }
}

@Composable
private fun PendingApproval(
    uiState: ApplicationUiState,
    onClick: (Application) -> Unit
) {
    when (uiState) {
        is ApplicationUiState.Applications -> {
            LazyColumn(verticalArrangement = Arrangement.Top) {
                items(uiState.applications) {
                    PendingContent(application = it) { onClick(it) }
                }
            }
        }

        else -> Loading()
    }
}

@Composable
private fun InProgress(
    uiState: ApplicationUiState,
) {
    when (uiState) {
        is ApplicationUiState.Applications -> {
            LazyColumn(verticalArrangement = Arrangement.Top) {
                items(uiState.applications) {
                    InProgressContent(application = it)
                }
            }
        }

        else -> Loading()
    }
}

@Composable
private fun Completed(
    uiState: ApplicationUiState,
    onClickReview: () -> Unit,
    onClickRecent: () -> Unit
) {
    when (uiState) {
        is ApplicationUiState.Applications -> {
            LazyColumn(verticalArrangement = Arrangement.Top) {
                items(uiState.applications) {
                    CompletedContent(
                        application = it,
                        onClickReview = onClickReview,
                        onClickRecent = onClickRecent
                    )
                }
            }
        }

        else -> Loading()
    }
}

@Composable
private fun PendingContent(application: Application, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .padding(20.dp)
            .fillMaxWidth()
            .wrapContentSize()
    ) {
        ListForUserItem(
            imageUrl = application.imageUrl,
            location = application.location,
            date = application.date,
            organization = application.organization,
            hasKennel = application.hasKennel,
        )
        OutlinedButton(modifier = Modifier.padding(top = 20.dp)) {
            onClick()
        }
    }
    Divider(thickness = 8.dp, color = Gray7)
}

@Composable
private fun InProgressContent(application: Application) {
    ListForUserItem(
        modifier = Modifier.padding(20.dp),
        imageUrl = application.imageUrl,
        location = application.location,
        date = application.date,
        organization = application.organization,
        hasKennel = application.hasKennel,
    )
    Divider(thickness = 8.dp, color = Gray7)
}

@Composable
private fun CompletedContent(
    application: Application,
    onClickReview: () -> Unit,
    onClickRecent: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            ListForUserItem(
                imageUrl = application.imageUrl,
                location = application.location,
                date = application.date,
                organization = application.organization,
                hasKennel = application.hasKennel,
            )
            Spacer(modifier = Modifier.size(20.dp))
            ReviewRecentButton(
                modifier = Modifier.height(40.dp),
                hasReview = application.reviewId != null,
                hasRecent = application.dogStatusId != null,
                onClickReview = onClickReview,
                onClickRecent = onClickRecent,
            )
        }
        Divider(thickness = 8.dp, color = Gray7)
    }
}

@Composable
private fun OutlinedButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    ConnectDogSecondaryButton(
        modifier = modifier,
        contentRes = R.string.check_my_appliance
    ) {
        onClick()
    }
}

@Composable
private fun ReviewRecentButton(
    modifier: Modifier = Modifier,
    hasReview: Boolean,
    hasRecent: Boolean,
    onClickReview: () -> Unit,
    onClickRecent: () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .border(
                shape = RoundedCornerShape(6.dp),
                color = MaterialTheme.colorScheme.outline,
                width = 1.dp
            )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Box(
                modifier = modifier
                    .fillMaxHeight()
                    .weight(0.5f)
                    .clickable(enabled = hasReview) { onClickReview() },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(id = R.string.create_review),
                    style = MaterialTheme.typography.titleSmall,
                    fontSize = 12.sp,
                    textAlign = TextAlign.Center,
                    color = if (!hasReview) Gray4 else Gray1
                )
            }
            Box(
                modifier = modifier
                    .fillMaxHeight()
                    .weight(0.5f)
                    .clickable(enabled = hasRecent) { onClickRecent() },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(id = R.string.check_recent),
                    style = MaterialTheme.typography.titleSmall,
                    fontSize = 12.sp,
                    textAlign = TextAlign.Center,
                    color = if (!hasRecent) Gray4 else Gray1
                )
            }
        }
        Divider(
            color = MaterialTheme.colorScheme.outline,
            modifier = modifier
                .align(Alignment.Center)
                .width(1.dp)
                .fillMaxHeight()
                .padding(vertical = 8.dp)
        )
    }
}

@Composable
private fun Loading() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}

@Preview
@Composable
private fun CompletedContentPreview() {
    val application = Application("", "이동봉사 위치", "YY.mm.dd(요일)", "단체이름", false, 0, 0)
    ConnectDogTheme {
        CompletedContent(application = application, onClickReview = { }, onClickRecent = {})
    }
}
