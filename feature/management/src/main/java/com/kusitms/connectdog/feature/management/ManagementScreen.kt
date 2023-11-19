package com.kusitms.connectdog.feature.management

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ScrollableTabRow
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

@Composable
internal fun ManagementRoute(
    padding: PaddingValues,
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
        ManagementScreen()
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun ManagementScreen() {
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
        LaunchedEffect(selectedTabIndex){
            pagerState.animateScrollToPage(selectedTabIndex)
        }
        LaunchedEffect(pagerState.currentPage){
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
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = tabItems[index])
                }
            }
        }
    }
}
