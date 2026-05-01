package com.kusitms.connectdog.feature.login.screen

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.kusitms.connectdog.core.designsystem.component.ActionRow
import com.kusitms.connectdog.core.designsystem.component.ConnectDogIconBottomButton
import com.kusitms.connectdog.core.designsystem.component.ConnectDogNormalButton
import com.kusitms.connectdog.core.designsystem.component.ConnectDogTextField
import com.kusitms.connectdog.core.designsystem.component.ConnectDogToast
import com.kusitms.connectdog.core.designsystem.component.SpeechBubble
import com.kusitms.connectdog.core.designsystem.theme.Gray2
import com.kusitms.connectdog.core.designsystem.theme.KAKAO
import com.kusitms.connectdog.core.designsystem.theme.NAVER
import com.kusitms.connectdog.core.designsystem.theme.PetOrange
import com.kusitms.connectdog.core.util.UserType
import com.kusitms.connectdog.feature.login.R
import com.kusitms.connectdog.feature.login.state.LoginSideEffect
import com.kusitms.connectdog.feature.login.viewmodel.LoginViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect
import com.kusitms.connectdog.core.designsystem.R.drawable as DR

@Composable
internal fun LoginRoute(
    finish: () -> Unit,
    onNavigateToNormalLogin: (UserType) -> Unit,
    onNavigateToSignup: (UserType) -> Unit,
    onNavigateToVolunteerHome: () -> Unit,
    onNavigateToIntermediatorHome: () -> Unit,
    onNavigateToEmailSearch: (UserType) -> Unit,
    onNavigateToPasswordSearch: (UserType) -> Unit,
    imeHeight: Int = 0
) {
    BackHandler { finish() }
    LoginScreen(
        onNavigateToNormalLogin = onNavigateToNormalLogin,
        onNavigateToSignup = onNavigateToSignup,
        onNavigateToVolunteerHome = onNavigateToVolunteerHome,
        onNavigateToIntermediatorHome = onNavigateToIntermediatorHome,
        onNavigateToEmailSearch = onNavigateToEmailSearch,
        onNavigateToPasswordSearch = onNavigateToPasswordSearch,
        imeHeight = imeHeight
    )
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun LoginScreen(
    onNavigateToNormalLogin: (UserType) -> Unit,
    onNavigateToSignup: (UserType) -> Unit,
    onNavigateToVolunteerHome: () -> Unit,
    onNavigateToIntermediatorHome: () -> Unit,
    onNavigateToEmailSearch: (UserType) -> Unit,
    onNavigateToPasswordSearch: (UserType) -> Unit,
    imeHeight: Int = 0
) {
    val focusManager = LocalFocusManager.current
    val interactionSource = remember { MutableInteractionSource() }
    var showToast by remember { mutableStateOf(false) }
    var toastMessage by remember { mutableStateOf("") }
    val coroutineScope = rememberCoroutineScope()

    val onShowToast: (String) -> Unit = {
        coroutineScope.launch {
            toastMessage = it
            showToast = true
            delay(2000)
            showToast = false
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .clickable(
                    onClick = focusManager::clearFocus,
                    indication = null,
                    interactionSource = interactionSource
                )
        ) {
            Text(
                modifier = Modifier.padding(start = 20.dp, top = 32.dp, bottom = 32.dp),
                text = stringResource(id = R.string.introduce),
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
            LoginContent(
                onNavigateToNormalLogin,
                onNavigateToSignup,
                onNavigateToVolunteerHome,
                onNavigateToIntermediatorHome,
                onNavigateToEmailSearch,
                onNavigateToPasswordSearch,
                onShowToast
            )
            Spacer(modifier = Modifier.weight(1f))
            Image(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f),
                painter = painterResource(id = DR.ic_main),
                contentDescription = null,
            )
        }

        ConnectDogToast(
            visible = showToast,
            message = toastMessage,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = (if (imeHeight > 0) imeHeight + 16 else 50).dp)
        )
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
private fun LoginContent(
    onNavigateToNormalLogin: (UserType) -> Unit,
    onNavigateToSignup: (UserType) -> Unit,
    onNavigateToVolunteerHome: () -> Unit,
    onNavigateToIntermediatorHome: () -> Unit,
    onNavigateToEmailSearch: (UserType) -> Unit,
    onNavigateToPasswordSearch: (UserType) -> Unit,
    onShowToast: (String) -> Unit
) {
    val pages = listOf("이동봉사자 회원", "이동봉사 모집자 회원")
    Column {
        val pagerState = rememberPagerState()
        val coroutineScope = rememberCoroutineScope()

        Row(
            modifier = Modifier
                .height(37.dp)
                .fillMaxWidth(0.5f)
                .padding(
                    start = if (pagerState.currentPage == 0) 0.dp else 33.dp,
                    end = if (pagerState.currentPage == 0) 33.dp else 0.dp
                )
                .align(if (pagerState.currentPage == 0) Alignment.End else Alignment.Start)
        ) {
            SpeechBubble(
                text = "이동봉사 공고 올리고 싶다면?",
                fontSize = 10,
                fontColor = PetOrange,
                fontWeight = FontWeight.SemiBold
            )
        }

        TabRow(
            modifier = Modifier.padding(horizontal = 10.dp),
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
                    onClick = { coroutineScope.launch { pagerState.scrollToPage(index) } }
                )
            }
        }

        HorizontalPager(
            modifier = Modifier.height(400.dp),
            count = pages.size,
            state = pagerState,
        ) {
            when (it) {
                0 -> Volunteer(
                    onNavigateToNormalLogin = onNavigateToNormalLogin,
                    onNavigateToSignup = onNavigateToSignup,
                    onNavigateToVolunteerHome = onNavigateToVolunteerHome
                )

                1 -> Intermediator(
                    onNavigateToIntermediatorHome = onNavigateToIntermediatorHome,
                    onNavigateToSignup = onNavigateToSignup,
                    onNavigateToEmailSearch = onNavigateToEmailSearch,
                    onNavigateToPasswordSearch = onNavigateToPasswordSearch,
                    onShowToast = onShowToast
                )
            }
        }
    }
}

@Composable
private fun Volunteer(
    onNavigateToNormalLogin: (UserType) -> Unit,
    onNavigateToSignup: (UserType) -> Unit,
    onNavigateToVolunteerHome: () -> Unit,
    viewModel: LoginViewModel = hiltViewModel()
)  {
    val context = LocalContext.current

    viewModel.collectSideEffect { sideEffect ->
        when (sideEffect) {
            is LoginSideEffect.NavigateToHome -> onNavigateToVolunteerHome()
            is LoginSideEffect.NavigateToSignUp -> onNavigateToSignup(UserType.SOCIAL_VOLUNTEER)
            else -> {}
        }
    }

    Column(
        modifier = Modifier
            .fillMaxHeight()
            .padding(top = 25.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.height(20.dp))
        ConnectDogIconBottomButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            color = KAKAO,
            iconId = R.drawable.ic_kakao,
            contentDescription = "kakao login",
            onClick = { viewModel.initKakaoLogin(context) },
            content = stringResource(id = R.string.kakao_login),
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
            onClick = { viewModel.initNaverLogin(context) },
            content = stringResource(id = R.string.naver_login)
        )
        Spacer(modifier = Modifier.height(30.dp))
        ActionRow(
            stringResource(id = R.string.email_signup) to { onNavigateToSignup(UserType.NORMAL_VOLUNTEER) },
            stringResource(id = R.string.email_login) to { onNavigateToNormalLogin(UserType.NORMAL_VOLUNTEER) }
        )
    }
}

@Composable
private fun Intermediator(
    onNavigateToIntermediatorHome: () -> Unit,
    onNavigateToSignup: (UserType) -> Unit,
    onNavigateToEmailSearch: (UserType) -> Unit,
    onNavigateToPasswordSearch: (UserType) -> Unit,
    onShowToast: (String) -> Unit,
    viewModel: LoginViewModel = hiltViewModel()
) {
    val uiState by viewModel.collectAsState()

    viewModel.collectSideEffect {
        when(it) {
            is LoginSideEffect.NavigateToHome -> onNavigateToIntermediatorHome()
            is LoginSideEffect.NavigateToSignUp -> null
            is LoginSideEffect.ShowErrorToast -> onShowToast(it.message)
        }
    }

    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxHeight()
            .padding(top = 25.dp)
            .padding(horizontal = 20.dp)
    ) {
        ConnectDogTextField(
            text = uiState.email,
            label = stringResource(id = R.string.email),
            placeholder = stringResource(id = R.string.input_email),
            keyboardType = KeyboardType.Text,
            onTextChanged = viewModel::onEmailChanged,
            isError = (uiState.isLoginSuccessful == false)
        )
        Spacer(modifier = Modifier.height(12.dp))
        ConnectDogTextField(
            text = uiState.password,
            label = stringResource(id = R.string.password),
            placeholder = stringResource(id = R.string.input_password),
            keyboardType = KeyboardType.Password,
            onTextChanged = viewModel::onPasswordChanged,
            isError = (uiState.isLoginSuccessful == false)
        )
        Spacer(modifier = Modifier.height(12.dp))
        ConnectDogNormalButton(
            modifier = Modifier.fillMaxWidth(),
            content = stringResource(id = R.string.login),
            onClick = viewModel::initIntermediatorLogin
        )
        Spacer(modifier = Modifier.height(30.dp))
        ActionRow(
            stringResource(id = R.string.email_signup) to { onNavigateToSignup(UserType.INTERMEDIATOR) },
            stringResource(id = R.string.email_search) to { onNavigateToEmailSearch(UserType.INTERMEDIATOR) },
            stringResource(id = R.string.password_search) to { onNavigateToPasswordSearch(UserType.INTERMEDIATOR) }
        )
    }
}
