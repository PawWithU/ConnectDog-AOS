package com.kusitms.connectdog.feature.intermediator.screen

import android.util.Log
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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kusitms.connectdog.core.designsystem.component.ConnectDogTopAppBar
import com.kusitms.connectdog.core.designsystem.component.TopAppBarNavigationType
import com.kusitms.connectdog.core.designsystem.theme.Gray2
import com.kusitms.connectdog.core.model.InterApplication
import com.kusitms.connectdog.feature.intermediator.DataUiState
import com.kusitms.connectdog.feature.intermediator.InterApplicationUiState
import com.kusitms.connectdog.feature.intermediator.InterManagementViewModel
import com.kusitms.connectdog.feature.intermediator.R
import com.kusitms.connectdog.feature.intermediator.component.CompletedContent
import com.kusitms.connectdog.feature.intermediator.component.CompletedDialog
import com.kusitms.connectdog.feature.intermediator.component.InProgressContent
import com.kusitms.connectdog.feature.intermediator.component.Loading
import com.kusitms.connectdog.feature.intermediator.component.PendingContent
import com.kusitms.connectdog.feature.intermediator.component.RecruitingContent
import com.kusitms.connectdog.feature.intermediator.component.VolunteerBottomSheet

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun InterManagementRoute(
    onBackClick: () -> Unit,
    tabIndex: Int = 0,
    viewModel: InterManagementViewModel = hiltViewModel()
) {
    val recruitingUiState by viewModel.recruitingUiState.collectAsStateWithLifecycle()
    val pendingUiState by viewModel.waitingUiState.collectAsStateWithLifecycle()
    val inProgressUiState by viewModel.progressUiState.collectAsStateWithLifecycle()
    val completedUiState by viewModel.completedUiState.collectAsStateWithLifecycle()

    val sheetState = rememberModalBottomSheetState()
    var isSheetOpen by rememberSaveable { mutableStateOf(false) }

    val pendingDataState by viewModel.pendingDataState.collectAsState()
    //Log.d("InterManagementRoute", "dataUiState = $dataUiState")
    UiState(dataUiState = pendingDataState) {
        viewModel.refreshWaitingUiState()
    }

    val progressDataState by viewModel.progressDataState.collectAsState()
    UiState(dataUiState = progressDataState) {
        viewModel.refreshInProgressUiState()
        viewModel.refreshCompletedUiState()
    }

    var isCompletedDialogVisible by remember { mutableStateOf(false) }

    Column {
        TopAppBar(titleRes = R.string.manage_application) { onBackClick() }
        ManagementScreen(
            tabIndex = tabIndex,
            firstContent = { Recruiting(uiState = recruitingUiState) },
            secondContent = {
                PendingApproval(uiState = pendingUiState, onClick = {
                    viewModel.selectedApplication = it
                    isSheetOpen = true
                })
            },
            thirdContent = {
                InProgress(
                    uiState = inProgressUiState,
                    onClick = {
                        viewModel.completeApplication(it.applicationId!!)
                        isCompletedDialogVisible = true
                    }
                )
            },
            fourthContent = {
                Completed(
                    uiState = completedUiState,
                    onClickReview = { /*TODO*/ },
                    onClickRecent = {}
                )
            }
        )
    }


    if (isSheetOpen && viewModel.selectedApplication != null) {
        VolunteerBottomSheet(
            interApplication = viewModel.selectedApplication!!,
            sheetState = sheetState,
            onDismissRequest = { isSheetOpen = false },
            viewModel = viewModel
        )
    }

    if (isCompletedDialogVisible) {
        CompletedDialog {
            isCompletedDialogVisible = false
        }
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
    uiState: InterApplicationUiState
) {
    when (uiState) {
        is InterApplicationUiState.InterApplications -> {
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
    uiState: InterApplicationUiState,
    onClick: (InterApplication) -> Unit
) {
    when (uiState) {
        is InterApplicationUiState.InterApplications -> {
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
    uiState: InterApplicationUiState,
    onClick: (InterApplication) -> Unit
) {
    when (uiState) {
        is InterApplicationUiState.InterApplications -> {
            LazyColumn(verticalArrangement = Arrangement.Top) {
                items(uiState.applications) {
                    InProgressContent(application = it) { onClick(it) }
                }
            }
        }

        else -> Loading()
    }
}

@Composable
private fun Completed(
    uiState: InterApplicationUiState,
    onClickReview: () -> Unit,
    onClickRecent: () -> Unit
) {
    when (uiState) {
        is InterApplicationUiState.InterApplications -> {
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
    tabIndex: Int,
    firstContent: @Composable () -> Unit,
    secondContent: @Composable () -> Unit,
    thirdContent: @Composable () -> Unit,
    fourthContent: @Composable () -> Unit
) {
    Log.d("InterManagementScreen", "ManagementScreen: tabIndex = $tabIndex")
    val tabItems = listOf(
        stringResource(id = R.string.recruit),
        stringResource(id = R.string.waiting),
        stringResource(id = R.string.progress),
        stringResource(id = R.string.complete)
    )

    Surface(modifier = Modifier.fillMaxSize()) {
        var selectedTabIndex by remember { mutableIntStateOf(tabIndex) }
        val pagerState = rememberPagerState(initialPage = tabIndex) {
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

@Composable
private fun UiState(dataUiState: DataUiState, onSuccess: () -> Unit) {
    when (dataUiState) {
        is DataUiState.Loading -> Loading()
        is DataUiState.Success -> onSuccess()
        is DataUiState.Yet -> {}
    }
}
