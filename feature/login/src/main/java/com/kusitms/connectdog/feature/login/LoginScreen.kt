package com.kusitms.connectdog.feature.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.kusitms.connectdog.core.designsystem.component.ConnectDogIconBottomButton
import com.kusitms.connectdog.core.designsystem.component.ConnectDogNormalButton
import com.kusitms.connectdog.core.designsystem.theme.Gray2
import com.kusitms.connectdog.core.designsystem.theme.KAKAO
import com.kusitms.connectdog.core.designsystem.theme.NAVER
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
internal fun LoginRoute(
    onNavigateToNormalLogin: () -> Unit,
    onNavigateToSignup: () -> Unit
) {
    LoginScreen(
        onNavigateToNormalLogin = onNavigateToNormalLogin,
        onNavigateToSignup = onNavigateToSignup
    )
}

@Composable
fun LoginScreen(
    onNavigateToNormalLogin: () -> Unit,
    onNavigateToSignup: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = com.kusitms.connectdog.core.designsystem.R.drawable.ic_main),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .height(200.dp)
        )
        SelectLoginType(
            onNavigateToNormalLogin = onNavigateToNormalLogin,
            onNavigateToSignup = onNavigateToSignup
        )
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun SelectLoginType(
    onNavigateToNormalLogin: () -> Unit,
    onNavigateToSignup: () -> Unit
) {
    Surface {
        Column() {
            val pagerState = rememberPagerState()
            val coroutineScope = rememberCoroutineScope()
            TabRow(
                modifier = Modifier
                    .padding(start = 10.dp, end = 10.dp),
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
                    0 -> Volunteer(onNavigateToNormalLogin, onNavigateToSignup)
                    1 -> Intermediator(onNavigateToNormalLogin, onNavigateToSignup)
                }
            }
        }
    }
}

@Composable
fun Volunteer(
    onNavigateToNormalLogin: () -> Unit,
    onNavigateToSignup: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(top = 32.dp)
            .fillMaxHeight(),
        verticalArrangement = Arrangement.Top
    ) {
        ConnectDogIconBottomButton(
            iconId = R.drawable.ic_kakao,
            contentDescription = "kakao login",
            onClick = { },
            content = stringResource(id = com.kusitms.connectdog.feature.login.R.string.kakao_login),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            color = KAKAO,
            textColor = Color(0xFF373737)
        )
        Spacer(modifier = Modifier.height(10.dp))
        ConnectDogIconBottomButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            color = NAVER,
            iconId = R.drawable.ic_naver,
            contentDescription = "naver login",
            onClick = { },
            content = stringResource(id = R.string.naver_login)
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = stringResource(id = R.string.or),
            fontSize = 13.sp,
            color = Color(0xFF7B7B7B)
        )
        Spacer(modifier = Modifier.height(10.dp))
        ConnectDogNormalButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            content = stringResource(id = R.string.signup_with_connectdog),
            onClick = onNavigateToSignup
        )
        Spacer(modifier = Modifier.height(16.dp))
        NormalLogin(onNavigateToNormalLogin)
    }
}

@Composable
private fun Intermediator(
    onNavigateToNormalLogin: () -> Unit,
    onNavigateToSignup: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(top = 32.dp)
            .fillMaxHeight(),
        verticalArrangement = Arrangement.Top
    ) {
        ConnectDogNormalButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            content = "코넥독 계정으로 회원가입하기",
            onClick = onNavigateToSignup
        )
        Spacer(modifier = Modifier.height(16.dp))
        NormalLogin(onNavigateToNormalLogin)
    }
}

@Composable
private fun NormalLogin(onNavigateToNormalLogin: () -> Unit) {
    ClickableText(
        text = text,
        onClick = { onNavigateToNormalLogin() }
    )
}
