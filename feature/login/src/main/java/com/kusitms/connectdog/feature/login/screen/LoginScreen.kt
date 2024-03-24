package com.kusitms.connectdog.feature.login.screen

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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.kusitms.connectdog.core.designsystem.component.ConnectDogIconBottomButton
import com.kusitms.connectdog.core.designsystem.component.ConnectDogNormalButton
import com.kusitms.connectdog.core.designsystem.theme.Gray2
import com.kusitms.connectdog.core.designsystem.theme.KAKAO
import com.kusitms.connectdog.core.designsystem.theme.NAVER
import com.kusitms.connectdog.core.util.SocialType
import com.kusitms.connectdog.core.util.UserType
import com.kusitms.connectdog.feature.login.R
import com.kusitms.connectdog.feature.login.viewmodel.LoginViewModel
import com.kusitms.connectdog.feature.login.viewmodel.Provider
import kotlinx.coroutines.launch

val pages = listOf("이동봉사자 회원", "이동봉사자 중개 회원")

@Composable
internal fun LoginRoute(
    onNavigateToNormalLogin: (UserType) -> Unit,
    onNavigateToSignup: (UserType) -> Unit,
    onNavigateToVolunteerHome: () -> Unit
) {
    LoginScreen(
        onNavigateToNormalLogin = onNavigateToNormalLogin,
        onNavigateToSignup = onNavigateToSignup,
        onNavigateToVolunteerHome = onNavigateToVolunteerHome
    )
}

@Composable
fun LoginScreen(
    onNavigateToNormalLogin: (UserType) -> Unit,
    onNavigateToSignup: (UserType) -> Unit,
    onNavigateToVolunteerHome: () -> Unit
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
            onNavigateToSignup = onNavigateToSignup,
            onNavigateToVolunteerHome = onNavigateToVolunteerHome
        )
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun SelectLoginType(
    onNavigateToNormalLogin: (UserType) -> Unit,
    onNavigateToSignup: (UserType) -> Unit,
    onNavigateToVolunteerHome: () -> Unit
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
                    0 -> Volunteer(
                        onNavigateToNormalLogin,
                        onNavigateToSignup,
                        onNavigateToVolunteerHome
                    )

                    1 -> Intermediator(
                        onNavigateToNormalLogin,
                        onNavigateToSignup
                    )
                }
            }
        }
    }
}

@Composable
fun Volunteer(
    onNavigateToNormalLogin: (UserType) -> Unit,
    onNavigateToSignup: (UserType) -> Unit,
    onNavigateToVolunteerHome: () -> Unit,
    viewModel: LoginViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val socialType by viewModel.socialType.collectAsState()

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
            onClick = { viewModel.initSocialLogin(Provider.KAKAO, context) },
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
            onClick = { viewModel.initSocialLogin(Provider.NAVER, context) },
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
            onClick = { onNavigateToSignup(UserType.NORMAL_VOLUNTEER) }
        )
        Spacer(modifier = Modifier.height(16.dp))
        NormalLogin(onNavigateToNormalLogin, UserType.NORMAL_VOLUNTEER)
    }

    socialType?.let {
        when (it) {
            SocialType.VOLUNTEER -> onNavigateToVolunteerHome()
            SocialType.GUEST -> onNavigateToSignup(UserType.SOCIAL_VOLUNTEER)
        }
    }
}

@Composable
private fun Intermediator(
    onNavigateToNormalLogin: (UserType) -> Unit,
    onNavigateToSignup: (UserType) -> Unit
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
            content = stringResource(id = R.string.signup_with_connectdog),
            onClick = { onNavigateToSignup(UserType.INTERMEDIATOR) }
        )
        Spacer(modifier = Modifier.height(16.dp))
        NormalLogin(onNavigateToNormalLogin, UserType.INTERMEDIATOR)
    }
}

@Composable
private fun NormalLogin(onNavigateToNormalLogin: (UserType) -> Unit, userType: UserType) {
    ClickableText(
        text = buildAnnotatedString {
            withStyle(
                style = SpanStyle(
                    color = Color(0xFF7B7B7B),
                    fontSize = 13.sp
                )
            ) {
                append("이미 계정이 있나요? ")
            }
            withStyle(
                style = SpanStyle(
                    color = Color(0xFF7B7B7B),
                    fontSize = 13.sp,
                    textDecoration = TextDecoration.Underline
                )
            ) {
                append("로그인")
            }
        },
        onClick = { onNavigateToNormalLogin(userType) }
    )
}
