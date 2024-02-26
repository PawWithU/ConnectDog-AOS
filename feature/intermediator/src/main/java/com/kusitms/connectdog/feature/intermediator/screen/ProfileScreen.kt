package com.kusitms.connectdog.feature.intermediator.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.kusitms.connectdog.core.designsystem.component.ConnectDogInformationCard
import com.kusitms.connectdog.core.designsystem.component.ConnectDogNormalButton
import com.kusitms.connectdog.core.designsystem.component.ConnectDogOutlinedButton
import com.kusitms.connectdog.core.designsystem.component.ConnectDogTopAppBar
import com.kusitms.connectdog.core.designsystem.component.DetailInfo
import com.kusitms.connectdog.core.designsystem.component.NetworkImage
import com.kusitms.connectdog.core.designsystem.component.TopAppBarNavigationType
import com.kusitms.connectdog.core.designsystem.theme.ConnectDogTheme
import com.kusitms.connectdog.core.designsystem.theme.Gray1
import com.kusitms.connectdog.core.designsystem.theme.Gray2
import com.kusitms.connectdog.core.designsystem.theme.Gray4
import kotlinx.coroutines.launch

val pages = listOf("기본 정보", "후기", "근황")

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ProfileScreen(
    onBackClick: () -> Unit = {}
) {
    Scaffold(
        topBar = {
            ConnectDogTopAppBar(
                titleRes = null,
                navigationType = TopAppBarNavigationType.BACK,
                navigationIconContentDescription = "Navigation icon",
                onNavigationClick = onBackClick
            )
        }
    ) {
        Content()
    }
}

@Composable
private fun Content() {
    Column {
        Spacer(modifier = Modifier.height(80.dp))
        IntermediatorProfile()
    }
}

@Composable
fun IntermediatorProfile() {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        NetworkImage(imageUrl = "", modifier = Modifier.size(80.dp))
        Spacer(modifier = Modifier.height(12.dp))
        ConnectDogOutlinedButton(
            width = 51,
            height = 14,
            text = "봉사 3회 진행",
            padding = 4,
            onClick = { /*TODO*/ }
        )
        Spacer(modifier = Modifier.height(12.dp))
        Text("중개단체 이름", fontSize = 18.sp, color = Gray1, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "한줄소개 한줄소개 한줄소개 한줄소개 한줄소개 한줄소개 한줄소개 한줄소개",
            fontSize = 12.sp,
            color = Gray4,
            modifier = Modifier.widthIn(min = 0.dp, max = 240.dp),
            lineHeight = 15.sp
        )
        Spacer(modifier = Modifier.height(32.dp))
        TabLayout()
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun TabLayout() {
    Surface {
        Column {
            val pagerState = rememberPagerState()
            val coroutineScope = rememberCoroutineScope()
            TabRow(
                selectedTabIndex = pagerState.currentPage
            ) {
                pages.forEachIndexed { index, title ->
                    Tab(
                        text = {
                            Text(
                                text = title,
                                color = if (pagerState.currentPage == index) {
                                    MaterialTheme.colorScheme.primary
                                } else {
                                    Gray2
                                }
                            )
                        },
                        selected = pagerState.currentPage == index,
                        onClick = {
                            coroutineScope.launch {
                                pagerState.scrollToPage(index)
                            }
                        }
                    )
                }
            }

            HorizontalPager(
                count = pages.size,
                state = pagerState
            ) {
                when (it) {
                    0 -> Information()
                    1 -> Review()
                    2 -> News()
                }
            }
        }
    }
}

@Composable
private fun Information() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        IntermediatorInformation()
        Spacer(modifier = Modifier.weight(1f))
        ConnectDogNormalButton(content = "수정하기", modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp))
        Spacer(modifier = Modifier.height(32.dp))
    }
}

@Composable
fun IntermediatorInformation() {
    Column(
        modifier = Modifier.padding(all = 24.dp),
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            text = "중개자 정보",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(20.dp))
        DetailInfo("이름", "밍밍이")
        Spacer(modifier = Modifier.height(8.dp))
        DetailInfo("이름", "밍밍이")
        Spacer(modifier = Modifier.height(20.dp))
        ConnectDogInformationCard(title = "안내사항", content = "안내사항")
    }
}

@Composable
fun Review() {
    val modifier = Modifier.padding(horizontal = 0.dp)
    Column(
        verticalArrangement = Arrangement.Top
    ) {
//        ReviewLoading(modifier = modifier)
    }
}

@Composable
fun News() {
    Column(
        modifier = Modifier.padding(all = 24.dp),
        verticalArrangement = Arrangement.Top
    ) {
    }
}

@Preview
@Composable
private fun test() {
    ConnectDogTheme {
        ProfileScreen()
    }
}
