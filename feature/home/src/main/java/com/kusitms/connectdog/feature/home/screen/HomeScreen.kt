package com.kusitms.connectdog.feature.home.screen

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kusitms.connectdog.core.designsystem.component.ConnectDogTopAppBar
import com.kusitms.connectdog.core.designsystem.component.TopAppBarNavigationType
import com.kusitms.connectdog.core.designsystem.theme.ConnectDogTheme
import com.kusitms.connectdog.core.designsystem.theme.Gray3
import com.kusitms.connectdog.feature.home.ExampleUiState
import com.kusitms.connectdog.feature.home.HomeViewModel
import com.kusitms.connectdog.feature.home.R
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
        modifier = Modifier.verticalScroll(scrollState)
    ) {
        TopTitle(modifier = Modifier.padding(20.dp))
        StatisticBanner(modifier = Modifier.padding(start = 20.dp, end = 20.dp, bottom = 20.dp))
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

@Composable
private fun TopTitle(modifier: Modifier) {
    Text(
        text = stringResource(id = R.string.home_description_title),
        style = MaterialTheme.typography.titleMedium,
        fontSize = 15.sp,
        modifier = modifier
    )
}

@Composable
private fun StatisticBanner(modifier: Modifier) {
    Column(horizontalAlignment = Alignment.End, modifier = modifier) {
        Row(
            modifier = Modifier
                .clickable { }
                .padding(end = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_info),
                contentDescription = "info icon",
                modifier = Modifier.padding(2.dp)
            )
            Text(
                text = stringResource(id = R.string.home_counting_guide),
                style = MaterialTheme.typography.labelMedium,
                color = Gray3
            )
        }
        Surface(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            color = MaterialTheme.colorScheme.primaryContainer
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.Bottom,
                modifier = Modifier.height(80.dp)
            ) {
                StatisticInfoItem(
                    number = "105",
                    descriptionRes = R.string.home_need_move_description,
                    painter = painterResource(id = R.drawable.img_man_dog)
                )
                Divider(
                    color = MaterialTheme.colorScheme.background,
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(1.dp)
                )
                StatisticInfoItem(
                    number = "22",
                    descriptionRes = R.string.home_moved_description,
                    painter = painterResource(id = R.drawable.img_woman)
                )
            }
        }
    }
}

@Composable
private fun StatisticInfoItem(
    number: String = "0",
    descriptionRes: Int,
    painter: Painter
) {
    Row() {
        Column(horizontalAlignment = Alignment.Start) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = number,
                    style = MaterialTheme.typography.titleSmall,
                    fontSize = 20.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = stringResource(id = R.string.home_dog_unit),
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.padding(start = 2.dp)
                )
            }
            Text(
                text = stringResource(id = descriptionRes),
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
        Image(painter = painter, contentDescription = "mandog")
    }
}

@Preview
@Composable
private fun HomeScreenPreview() {
    ConnectDogTheme {
        Column(modifier = Modifier.background(Color.White)) {
            TopAppBar(onClickSearch = {})
            HomeScreen(exampleUiState = ExampleUiState.Empty)
        }
    }
}
