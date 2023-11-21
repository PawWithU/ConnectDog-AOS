package com.kusitms.connectdog.feature.intermediator.screen

import androidx.annotation.StringRes
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
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
import androidx.compose.ui.unit.sp
import com.kusitms.connectdog.core.designsystem.component.ConnectDogTopAppBar
import com.kusitms.connectdog.core.designsystem.component.TopAppBarNavigationType
import com.kusitms.connectdog.core.designsystem.theme.Gray2
import com.kusitms.connectdog.core.model.InterApplication
import com.kusitms.connectdog.feature.intermediator.IntermediatorApplicationUiState
import com.kusitms.connectdog.feature.intermediator.R
import com.kusitms.connectdog.feature.intermediator.component.CompletedContent
import com.kusitms.connectdog.feature.intermediator.component.InProgressContent
import com.kusitms.connectdog.feature.intermediator.component.Loading
import com.kusitms.connectdog.feature.intermediator.component.PendingContent
import com.kusitms.connectdog.feature.intermediator.component.RecruitingContent

@Composable
private fun ManagementRoute(
    onBackClick: () -> Unit,
) {
    Column {
        TopAppBar(titleRes = R.string.manage_application) { onBackClick() }
        ManagementScreen(
            firstContent = { Recruiting(uiState =) },
            secondContent = { PendingApproval(uiState =, onClick =) },
            thirdContent = { InProgress(uiState =) },
            fourthContent = {
                Completed(
                    uiState =,
                    onClickReview = { /*TODO*/ },
                    onClickRecent = {})
            }
        )
    }
}

@Composable
private fun TopAppBar(
    @StringRes titleRes: Int,
    onBackClick: () -> Unit
) {
    ConnectDogTopAppBar(
        titleRes = titleRes,
        navigationType = TopAppBarNavigationType.BACK,
        navigationIconContentDescription = "back",
        onNavigationClick = onBackClick
    )
}

@Composable
private fun Recruiting(
    uiState: IntermediatorApplicationUiState
) {
    when (uiState) {
        is IntermediatorApplicationUiState.IntermediatorApplications -> {
            LazyColumn(verticalArrangement = Arrangement.Top) {
                items(uiState.applications) {
                    RecruitingContent(application = it)
                }
            }
        }

        else -> Loading()
    }
}

@Composable
private fun PendingApproval(
    uiState: IntermediatorApplicationUiState,
    onClick: (InterApplication) -> Unit
) {
    when (uiState) {
        is IntermediatorApplicationUiState.IntermediatorApplications -> {
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
    uiState: IntermediatorApplicationUiState
) {
    when (uiState) {
        is IntermediatorApplicationUiState.IntermediatorApplications -> {
            LazyColumn(verticalArrangement = Arrangement.Top) {
                items(uiState.applications) {
                    InProgressContent(application = it) {
                        //todo
                    }
                }
            }
        }

        else -> Loading()
    }
}

@Composable
private fun Completed(
    uiState: IntermediatorApplicationUiState,
    onClickReview: () -> Unit,
    onClickRecent: () -> Unit
) {
    when (uiState) {
        is IntermediatorApplicationUiState.IntermediatorApplications -> {
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


@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun ManagementScreen(
    firstContent: @Composable () -> Unit,
    secondContent: @Composable () -> Unit,
    thirdContent: @Composable () -> Unit,
    fourthContent: @Composable () -> Unit
) {
    val tabItems = listOf(
        stringResource(id = R.string.recruit),
        stringResource(id = R.string.waiting),
        stringResource(id = R.string.progress),
        stringResource(id = R.string.complete),
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
                    3 -> fourthContent()
                }
            }
        }

    }
}
