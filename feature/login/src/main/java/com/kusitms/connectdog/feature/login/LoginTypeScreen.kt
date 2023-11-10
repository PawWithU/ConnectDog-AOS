package com.kusitms.connectdog.feature.login

import android.content.Context
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.kusitms.connectdog.core.designsystem.theme.ConnectDogTheme
import com.kusitms.connectdog.core.designsystem.theme.PetOrange
import kotlinx.coroutines.launch

val pages = listOf("이동봉사자 회원", "이동봉사자 중개 회원")

val text =
    buildAnnotatedString {
        withStyle(style = SpanStyle(color = Color(0xFF7B7B7B), fontSize = 13.sp)) {
            append("이미 계정이 있나요? ")
        }
        withStyle(style = SpanStyle(color = Color(0xFF7B7B7B), fontSize = 13.sp, textDecoration = TextDecoration.Underline)) {
            append("로그인")
        }
    }

@Composable
fun LoginTypeScreen(
    navigator: NavController,
    viewModel: LoginViewModel,
    context: Context
) {
    Column(
        modifier =
        Modifier
            .fillMaxSize()
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(73.dp))
        MainLogo(
            modifier =
            Modifier
                .height(257.dp)
                .width(257.dp)
        )
        Spacer(modifier = Modifier.height(6.dp))
        TabLayout(navigator, viewModel, context)
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun TabLayout(
    navigator: NavController,
    viewModel: LoginViewModel,
    context: Context
) {
    ConnectDogTheme {
        Surface {
            Column(
                verticalArrangement = Arrangement.Center
            ) {
                val pagerState = rememberPagerState()
                val coroutineScope = rememberCoroutineScope()

                TabRow(
                    modifier = Modifier.padding(start = 10.dp, end = 10.dp),
                    selectedTabIndex = pagerState.currentPage
                ) {
                    pages.forEachIndexed { index, title ->
                        Tab(
                            text = {
                                Text(
                                    text = title,
                                    color =
                                    if (pagerState.currentPage == index) {
                                        MaterialTheme.colorScheme.primary
                                    } else {
                                        Color(0xFF7B7B7B)
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
                    state = pagerState,
                    modifier = Modifier.padding(top = 32.dp)
                ) {
                    when (it) {
                        0 -> Individual(navigator, viewModel, context)
                        1 -> Organization(navigator)
                    }
                }
            }
        }
    }
}

@Composable
fun Individual(
    navigator: NavController,
    viewModel: LoginViewModel,
    context: Context
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        NormalButton(
            content = "카카오톡으로 시작하기",
            color = Color(0xFFFEE500),
            textColor = Color(0xFF373737),
            onClick = {
                viewModel.initKakaoLogin(context)
            }
        )
        Spacer(modifier = Modifier.height(10.dp))
        NormalButton(
            content = "네이버로 시작하기",
            color = Color(0xFF1EC800),
            onClick = {
                Log.d("testt", "tests")
                viewModel.initNaverLogin(context)
            }
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = "또는",
            fontSize = 13.sp,
            color = Color(0xFF7B7B7B)
        )
        Spacer(modifier = Modifier.height(10.dp))
        NormalButton(content = "코넥독 계정으로 회원가입하기", color = PetOrange)
        Spacer(modifier = Modifier.height(16.dp))
        LoginText(navigator)
    }
}

@Composable
fun Organization(navigator: NavController) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        NormalButton(content = "코넥독 계정으로 회원가입하기")
        Spacer(modifier = Modifier.height(16.dp))
        LoginText(navigator)
    }
}

@Composable
fun LoginText(navigator: NavController) {
    ClickableText(
        text = text,
        onClick = {
            navigator.navigate(route = "emailLogin")
        }
    )
}
