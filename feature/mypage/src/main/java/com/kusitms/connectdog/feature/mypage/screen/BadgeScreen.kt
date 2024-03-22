package com.kusitms.connectdog.feature.mypage.screen

import android.annotation.SuppressLint
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.kusitms.connectdog.core.designsystem.component.ConnectDogTopAppBar
import com.kusitms.connectdog.core.designsystem.component.NetworkImage
import com.kusitms.connectdog.core.designsystem.component.TopAppBarNavigationType
import com.kusitms.connectdog.core.designsystem.theme.ConnectDogTheme
import com.kusitms.connectdog.core.designsystem.theme.Gray2
import com.kusitms.connectdog.core.designsystem.theme.Orange20
import com.kusitms.connectdog.feature.mypage.R
import com.kusitms.connectdog.feature.mypage.viewmodel.MyPageViewModel

private val volunteerDescriptionList = listOf(
    R.string.review_first,
    R.string.review_second,
    R.string.review_third,
    R.string.review_fourth,
    R.string.review_fifth,
    R.string.review_sixth
)

private val reviewDescriptionList = listOf(
    R.string.first,
    R.string.second,
    R.string.third,
    R.string.fourth,
    R.string.fifth,
    R.string.sixth
)

data class BadgeItem(
    val imageUrl: String?,
    @StringRes val description: Int
)

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun BadgeScreen(
    onBackClick: () -> Unit,
    viewModel: MyPageViewModel = hiltViewModel()
) {
    val showBottomSheet by viewModel.showBottomSheet.observeAsState(initial = false)
    val sheetState = rememberModalBottomSheetState()

    LaunchedEffect(Unit) {
        viewModel.fetchBadge()
    }

    Scaffold(
        topBar = {
            ConnectDogTopAppBar(
                titleRes = R.string.badge,
                navigationType = TopAppBarNavigationType.BACK,
                navigationIconContentDescription = null,
                onNavigationClick = onBackClick
            )
        }
    ) {
        Content()

        if (showBottomSheet) {
            ModalBottomSheet(
                onDismissRequest = {
                    viewModel.updateBottomSheet()
                },
                sheetState = sheetState
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.height(24.dp))
                    NetworkImage(
                        imageUrl = viewModel.badgeItem.value!!.imageUrl,
                        modifier = Modifier.size(80.dp),
                        placeholder = painterResource(id = R.drawable.ic_lock)
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = stringResource(
                            id = viewModel.badgeItem.value!!.description
                        ),
                        style = MaterialTheme.typography.titleMedium,
                        fontSize = 12.sp,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(30.dp))
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(6.dp))
                            .background(Orange20)
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp)
                            .height(40.dp)
                    ) {
                        Text(
                            text = "이동봉사를 1회 진행했어요",
                            textAlign = TextAlign.Center,
                            fontSize = 12.sp,
                            color = Gray2,
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                    Spacer(modifier = Modifier.height(24.dp))
                }
            }
        }
    }
}

@Composable
private fun Content(
    viewModel: MyPageViewModel = hiltViewModel()
) {
    val badgeData by viewModel.badge.observeAsState()

    val volunteerItems = List(6) { cnt ->
        badgeData?.let {
            BadgeItem(it[cnt].image, volunteerDescriptionList[cnt])
        }
    }

    val reviewItems = List(6) { cnt ->
        badgeData?.let {
            BadgeItem(it[cnt + 6].image, reviewDescriptionList[cnt])
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 48.dp)
    ) {
        BadgeGrid(titleRes = R.string.volunteer_title, volunteerItems)
        BadgeGrid(titleRes = R.string.review_title, reviewItems)
    }
}

@Composable
private fun BadgeGrid(
    @StringRes titleRes: Int,
    list: List<BadgeItem?>,
    viewModel: MyPageViewModel = hiltViewModel()
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 30.dp)
    ) {
        Text(
            text = stringResource(id = titleRes),
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(modifier = Modifier.height(20.dp))
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            items(list) {
                if (it != null) {
                    BadgeContent(item = it) {
                        viewModel.updateBottomSheet()
                        viewModel.updateBottomSheetData(it)
                    }
                }
            }
        }
    }
}

@Composable
private fun BadgeContent(
    item: BadgeItem,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier.clickable {
            onClick()
        },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        NetworkImage(
            imageUrl = item.imageUrl,
            modifier = Modifier.size(80.dp),
            placeholder = painterResource(id = R.drawable.ic_lock)
        )
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = stringResource(id = item.description),
            style = MaterialTheme.typography.labelLarge,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
    }
}

@Preview
@Composable
private fun test() {
    ConnectDogTheme {
//        Content()
    }
}
