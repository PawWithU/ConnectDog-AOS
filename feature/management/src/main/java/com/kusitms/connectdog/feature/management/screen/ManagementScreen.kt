package com.kusitms.connectdog.feature.management.screen

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kusitms.connectdog.core.designsystem.component.ConnectDogSecondaryButton
import com.kusitms.connectdog.core.designsystem.component.ConnectDogTopAppBar
import com.kusitms.connectdog.core.designsystem.component.TopAppBarNavigationType
import com.kusitms.connectdog.core.designsystem.theme.Gray2
import com.kusitms.connectdog.core.model.Application
import com.kusitms.connectdog.core.util.UserType
import com.kusitms.connectdog.feature.management.R
import com.kusitms.connectdog.feature.management.component.Completed
import com.kusitms.connectdog.feature.management.component.InProgress
import com.kusitms.connectdog.feature.management.component.MyApplicationBottomSheet
import com.kusitms.connectdog.feature.management.component.PendingApproval
import com.kusitms.connectdog.feature.management.viewmodel.ManagementViewModel
import kotlinx.coroutines.launch

private const val TAG = "ManagementScreen"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun ManagementRoute(
    onBackClick: () -> Unit,
    onNavigateToCreateReview: (Application) -> Unit,
    onNavigateToCheckReview: (Long, UserType) -> Unit,
    onNavigateToHome: (String) -> Unit,
    onNavigateToDetail: (Long) -> Unit,
    onShowErrorSnackBar: (throwable: Throwable?) -> Unit,
    viewModel: ManagementViewModel = hiltViewModel(),
) {
    val pendingUiState by viewModel.waitingUiState.collectAsStateWithLifecycle()
    val inProgressUiState by viewModel.progressUiState.collectAsStateWithLifecycle()
    val completedUiState by viewModel.completedUiState.collectAsStateWithLifecycle()

    val volunteer by viewModel.volunteer.collectAsStateWithLifecycle()
    val selectedApplication by viewModel.selectedApplication.collectAsStateWithLifecycle()

    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var isSheetOpen by rememberSaveable { mutableStateOf(false) }

    val deleteDataState by viewModel.deleteDataUiState.collectAsStateWithLifecycle()

    BackHandler { onNavigateToHome(com.kusitms.connectdog.feature.management.navigation.ManagementRoute.route) }

    LaunchedEffect(deleteDataState) {
        if (deleteDataState is com.kusitms.connectdog.core.model.DataUiState.Success) {
            viewModel.refreshWaitingApplications()
        }
    }

    Column {
        ConnectDogTopAppBar(
            titleRes = null,
            navigationType = TopAppBarNavigationType.MANAGEMENT,
            navigationIconContentDescription = "Navigation icon",
            onNavigationClick = onBackClick,
        )
        ManagementScreen(
            onRefresh = viewModel::refreshTabSuspend,
            onTabSelected = viewModel::refreshByTabIndex,
            firstContent = {
                PendingApproval(
                    uiState = pendingUiState,
                    onItemClick = { application ->
                        viewModel.getVolunteerInfo(application.applicationId!!)
                        viewModel.updateSelectedApplication(application)
                        isSheetOpen = true
                    },
                    onNavigateToDetail = onNavigateToDetail,
                )
            },
            secondContent = {
                InProgress(inProgressUiState) { application ->
                    viewModel.getVolunteerInfo(application.applicationId!!)
                    viewModel.updateSelectedApplication(application)
                    isSheetOpen = true
                }
            },
            thirdContent = {
                Completed(
                    uiState = completedUiState,
                    onCreateReviewClick = onNavigateToCreateReview,
                    onCheckReviewClick = onNavigateToCheckReview,
                )
            },
        )
    }

    if (isSheetOpen && volunteer != null && selectedApplication != null) {
        MyApplicationBottomSheet(
            sheetState = sheetState,
            application = selectedApplication!!,
            volunteer = volunteer!!,
            onDismissRequest = { isSheetOpen = false },
            onDeleteClick = viewModel::deleteMyApplication,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ManagementScreen(
    onRefresh: suspend (Int) -> Unit,
    onTabSelected: (Int) -> Unit,
    firstContent: @Composable () -> Unit,
    secondContent: @Composable () -> Unit,
    thirdContent: @Composable () -> Unit,
) {
    val tabItems =
        listOf(
            stringResource(id = R.string.pending_approval),
            stringResource(id = R.string.inProgress),
            stringResource(id = R.string.completed),
        )

    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Top,
        ) {
            val pagerState = rememberPagerState(pageCount = { tabItems.size })
            val coroutineScope = rememberCoroutineScope()

            // 탭 변경 시 데이터 새로 고침
            LaunchedEffect(pagerState.currentPage) {
                onTabSelected(pagerState.currentPage)
            }

            TabRow(selectedTabIndex = pagerState.currentPage) {
                tabItems.forEachIndexed { index, title ->
                    Tab(
                        selected = pagerState.currentPage == index,
                        onClick = {
                            coroutineScope.launch {
                                pagerState.scrollToPage(index)
                            }
                        },
                        text = {
                            Text(
                                text = title,
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.SemiBold,
                                color = if (pagerState.currentPage == index) MaterialTheme.colorScheme.primary else Gray2,
                            )
                        },
                    )
                }
            }

            var isRefreshing by remember { mutableStateOf(false) }

            // pull-to-refresh: suspend 함수가 완료되면 isRefreshing을 false로 되돌림
            LaunchedEffect(isRefreshing) {
                if (isRefreshing) {
                    onRefresh(pagerState.currentPage)
                    isRefreshing = false
                }
            }

            PullToRefreshBox(
                isRefreshing = isRefreshing,
                onRefresh = { isRefreshing = true },
                modifier = Modifier.fillMaxSize(),
            ) {
                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier.fillMaxSize(),
                    verticalAlignment = Alignment.Top,
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
}

@Composable
internal fun OutlinedButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    ConnectDogSecondaryButton(
        modifier = modifier,
        contentRes = R.string.check_my_appliance_button,
    ) { onClick() }
}

@Composable
fun Loading() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}
