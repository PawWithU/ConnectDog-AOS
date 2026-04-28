package com.kusitms.connectdog.feature.management.screen

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.kusitms.connectdog.core.designsystem.component.ConnectDogSecondaryButton
import com.kusitms.connectdog.core.designsystem.component.ConnectDogTopAppBar
import com.kusitms.connectdog.core.designsystem.component.TopAppBarNavigationType
import com.kusitms.connectdog.core.designsystem.component.UiState
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
    viewModel: ManagementViewModel = hiltViewModel()
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
            onNavigationClick = onBackClick
        )
        ManagementScreen(
            firstContent = {
                PendingApproval(
                    uiState = pendingUiState,
                    onItemClick = { application ->
                        viewModel.getVolunteerInfo(application.applicationId!!)
                        viewModel.updateSelectedApplication(application)
                        isSheetOpen = true
                    },
                    onNavigateToDetail = onNavigateToDetail
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
                    onCheckReviewClick = onNavigateToCheckReview
                )
            }
        )
    }

    if (isSheetOpen && volunteer != null && selectedApplication != null) {
        MyApplicationBottomSheet(
            sheetState = sheetState,
            application = selectedApplication!!,
            volunteer = volunteer!!,
            onDismissRequest = { isSheetOpen = false },
            onDeleteClick = viewModel::deleteMyApplication
        )
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
private fun ManagementScreen(
    firstContent: @Composable () -> Unit,
    secondContent: @Composable () -> Unit,
    thirdContent: @Composable () -> Unit
) {
    val tabItems = listOf(
        stringResource(id = R.string.pending_approval),
        stringResource(id = R.string.inProgress),
        stringResource(id = R.string.completed)
    )

    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Top
        ) {
            val pagerState = rememberPagerState()
            val coroutineScope = rememberCoroutineScope()
            TabRow(
                selectedTabIndex = pagerState.currentPage
            ) {
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
                                style = MaterialTheme.typography.titleSmall,
                                fontSize = 14.sp,
                                color = if (pagerState.currentPage == index) MaterialTheme.colorScheme.primary else Gray2
                            )
                        }
                    )
                }
            }
            HorizontalPager(
                state = pagerState,
                count = tabItems.size,
                modifier = Modifier.fillMaxSize(),
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
internal fun OutlinedButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    ConnectDogSecondaryButton(
        modifier = modifier,
        contentRes = R.string.check_my_appliance
    ) { onClick() }
}

@Composable
fun Loading() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}
