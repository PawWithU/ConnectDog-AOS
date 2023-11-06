package com.kusitms.connectdog.feature.home.screen

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kusitms.connectdog.core.designsystem.component.ConnectDogTopAppBar
import com.kusitms.connectdog.core.designsystem.component.TopAppBarNavigationType
import com.kusitms.connectdog.core.designsystem.theme.ConnectDogTheme
import com.kusitms.connectdog.feature.home.ExampleCard
import com.kusitms.connectdog.feature.home.ExampleUiState
import com.kusitms.connectdog.feature.home.HomeViewModel
import kotlinx.coroutines.flow.collectLatest

@Composable
internal fun HomeRoute(
    onSearchIconClick: () -> Unit,
    onShowErrorSnackBar: (throwable: Throwable?) -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val exampleUiState by viewModel.exampleUiState.collectAsStateWithLifecycle()

    // 에러 발생할 때마다 에러 스낵바 표시
    LaunchedEffect(true) {
        viewModel.errorFlow.collectLatest { throwable -> onShowErrorSnackBar(throwable) }
    }

    Column {
        TopAppBar(onClickSearch = onSearchIconClick)
        HomeScreen(
            exampleUiState = exampleUiState
        )
    }
}

@Composable
private fun HomeScreen(
    exampleUiState: ExampleUiState
) {
    val scrollState = rememberScrollState()
    Column(
        modifier =
        Modifier
            .padding(horizontal = 8.dp)
            .verticalScroll(scrollState)
            .padding(bottom = 4.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = "Hello HOME!"
        )
        ExampleCard(uiState = exampleUiState)
    }
}

@Composable
private fun TopAppBar(
    onClickSearch: () -> Unit
) {
    ConnectDogTopAppBar(
        titleRes = null,
        navigationType = TopAppBarNavigationType.HOME,
        navigationIconContentDescription = "Navigation icon home",
        actionButtons = {
            IconButton(onClick = {
                Log.d("SearchScreen", "clickSearchIcon")
                onClickSearch()
            }) {
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = "Navigate to Search"
                )
            }
            IconButton(onClick = {}) {
                Icon(
                    imageVector = Icons.Outlined.Notifications,
                    contentDescription = "Navigate to Search"
                )
            }
        }
    )
}

@Preview
@Composable
private fun HomeScreenPreview() {
    ConnectDogTheme {
        HomeScreen(exampleUiState = ExampleUiState.Empty)
    }
}
