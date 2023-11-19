package com.kusitms.connectdog.feature.management

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kusitms.connectdog.core.designsystem.component.ConnectDogSecondaryButton
import com.kusitms.connectdog.core.designsystem.component.ConnectDogTopAppBar
import com.kusitms.connectdog.core.designsystem.component.ListForUserItem
import com.kusitms.connectdog.core.designsystem.component.TopAppBarNavigationType
import com.kusitms.connectdog.core.designsystem.theme.ConnectDogTheme
import com.kusitms.connectdog.core.designsystem.theme.Gray2
import com.kusitms.connectdog.core.designsystem.theme.Gray7
import com.kusitms.connectdog.core.model.Application

@Composable
internal fun ManagementRoute(
    onBackClick: () -> Unit,
    onShowErrorSnackBar: (throwable: Throwable?) -> Unit
) {
    Column {
        ConnectDogTopAppBar(
            titleRes = null,
            navigationType = TopAppBarNavigationType.MANAGEMENT,
            navigationIconContentDescription = "Navigation icon",
            onNavigationClick = onBackClick
        )
        ManagementScreen(
            firstContent = { PendingApproval(list = listOf()){ /*todo*/ } },
            secondContent = { InProgress(list = listOf()) },
            thirdContent = { Completed(list = listOf(), onClickReview = {}, onClickRecent = {}) }
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
                    .weight(1f)
            ) { index ->
                when (index) {
                    0 -> firstContent()
                    1 -> secondContent()
                    3 -> thirdContent()
                }
            }
        }
    }
}

@Composable
private fun PendingApproval(
    list: List<Application>,
    onClick: () -> Unit
) {
    LazyColumn {
        items(list) {
            PendingContent(application = it) { onClick() }
        }
    }
}

@Composable
private fun InProgress(
    list: List<Application>,
) {
    LazyColumn {
        items(list) {
            InProgressContent(application = it)
        }
    }
}

@Composable
private fun Completed(
    list: List<Application>,
    onClickReview: () -> Unit,
    onClickRecent: () -> Unit
) {
    LazyColumn {
        items(list) {
            CompletedContent(
                application = it,
                onClickReview = onClickReview,
                onClickRecent = onClickRecent
            )
        }
    }
}

@Composable
private fun PendingContent(application: Application, onClick: () -> Unit) {
    Column {
        Column(modifier = Modifier.padding(20.dp)) {
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
            .padding(20.dp)
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        ListForUserItem(
            imageUrl = application.imageUrl,
            location = application.location,
            date = application.date,
            organization = application.organization,
            hasKennel = application.hasKennel,
        )
        ReviewRecentButton(
            modifier = Modifier
                .padding(vertical = 8.dp)
                .height(40.dp),
            onClickReview = onClickReview,
            onClickRecent = onClickRecent,
        )
    }
    Divider(thickness = 8.dp, color = Gray7)
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
    onClickReview: () -> Unit,
    onClickRecent: () -> Unit
) {
    Row(
        modifier = modifier
            .height(40.dp)
            .fillMaxWidth()
    ) {
        Text(
            modifier = modifier
                .weight(0.5f)
                .fillMaxHeight()
                .clickable { onClickReview() },
            text = stringResource(id = R.string.create_review),
            style = MaterialTheme.typography.titleSmall,
            fontSize = 12.sp,
            textAlign = TextAlign.Center
        )
        Divider(thickness = 1.dp, color = MaterialTheme.colorScheme.outline)
        Text(
            modifier = modifier
                .weight(0.5f)
                .fillMaxHeight()
                .clickable { onClickRecent() },
            text = stringResource(id = R.string.check_recent),
            style = MaterialTheme.typography.titleSmall,
            fontSize = 12.sp,
            textAlign = TextAlign.Center
        )
    }
}

@Preview
@Composable
private fun PendingContentPreview() {
    ConnectDogTheme {
        val list = List(2) {
            Application(0, "", "이동봉사 위치", "YY.mm.dd(요일)", "단체이름", false)
        }
        PendingApproval(list = list) {}
    }
}

@Preview
@Composable
private fun InProgressContentPreview() {
    ConnectDogTheme {
        val list = List(2) {
            Application(0, "", "이동봉사 위치", "YY.mm.dd(요일)", "단체이름", false)
        }
        InProgress(list = list)
    }
}

@Preview
@Composable
private fun CompletedContentPreview() {
    ConnectDogTheme {
        val list = List(2) {
            Application(0, "", "이동봉사 위치", "YY.mm.dd(요일)", "단체이름", false)
        }
        Completed(list = list, onClickRecent = {}, onClickReview = {})
    }
}

