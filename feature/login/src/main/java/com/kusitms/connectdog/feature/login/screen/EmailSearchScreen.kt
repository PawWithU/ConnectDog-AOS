package com.kusitms.connectdog.feature.login.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.kusitms.connectdog.core.designsystem.component.ConnectDogBottomButton
import com.kusitms.connectdog.core.designsystem.component.ConnectDogTextField
import com.kusitms.connectdog.core.designsystem.component.ConnectDogTopAppBar
import com.kusitms.connectdog.core.designsystem.component.TopAppBarNavigationType
import com.kusitms.connectdog.core.designsystem.theme.Gray60
import com.kusitms.connectdog.core.designsystem.theme.Gray80
import com.kusitms.connectdog.core.designsystem.theme.Red1
import com.kusitms.connectdog.core.util.UserType
import com.kusitms.connectdog.feature.login.R
import com.kusitms.connectdog.feature.login.state.EmailSearchSideEffect
import com.kusitms.connectdog.feature.login.viewmodel.EmailSearchViewModel
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun EmailSearchScreen(
    imeHeight: Int,
    onBackClick: () -> Unit,
    navigateToCompleteScreen: (String) -> Unit,
    onSendMessageClick: (String) -> Unit,
    onVerifyCodeClick: (String, (Boolean) -> Unit) -> Unit,
    userType: UserType,
    viewModel: EmailSearchViewModel = hiltViewModel()
) {
    Scaffold(
        topBar = {
            ConnectDogTopAppBar(
                titleRes = R.string.email_search,
                navigationType = TopAppBarNavigationType.BACK,
                onNavigationClick = onBackClick
            )
        }
    ) {
        Content(
            imeHeight = imeHeight,
            navigateToCompleteScreen = navigateToCompleteScreen,
            onSendMessageClick = onSendMessageClick,
            onVerifyCodeClick = onVerifyCodeClick,
            viewModel = viewModel,
            userType = userType
        )
    }
}

@Composable
private fun Content(
    imeHeight: Int,
    navigateToCompleteScreen: (String) -> Unit,
    onSendMessageClick: (String) -> Unit,
    onVerifyCodeClick: (String, (Boolean) -> Unit) -> Unit,
    userType: UserType,
    viewModel: EmailSearchViewModel
) {
    val uiState by viewModel.collectAsState()

    viewModel.collectSideEffect { sideEffect ->
        when(sideEffect) {
            is EmailSearchSideEffect.NavigateToEmailSearchResult -> {
                uiState.email?.let { navigateToCompleteScreen(it) }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp)
    ) {
        Spacer(modifier = Modifier.height(80.dp))
        Text(
            text = stringResource(id = R.string.email_auth_title),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            lineHeight = 25.sp
        )
        Spacer(modifier = Modifier.height(40.dp))
        ConnectDogTextField(
            text = uiState.phoneNumber,
            label = stringResource(id = R.string.phone_number),
            keyboardType = KeyboardType.Number,
            placeholder = "- 빼고 입력",
            onTextChanged = viewModel::onPhoneNumberChanged,
        )
        Spacer(modifier = Modifier.height(12.dp))
        if(uiState.isSendAuthCode) {
            ConnectDogTextField(
                text = uiState.authCode,
                label = "인증번호",
                keyboardType = KeyboardType.Number,
                placeholder = "인증번호 6자리",
                onTextChanged = viewModel::onAuthCodeChanged,
                isError = (uiState.isAuthCodeError == true)
            )
            if(uiState.isAuthCodeError == true) {
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "올바른 인증번호를 입력해주세요",
                    fontSize = 10.sp,
                    color = Red1
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "인증번호가 오지 않는다면?",
                    color = Gray60,
                    fontSize = 12.sp
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    modifier = Modifier.clickable { },
                    text = "재발송",
                    fontSize = 12.sp,
                    color = Gray80,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
        Spacer(modifier = Modifier.weight(1f))
        ConnectDogBottomButton(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            content = uiState.bottomButtonText,
            enabled = uiState.enableNext,
            onClick = { viewModel.onNextClick(userType, onSendMessageClick, onVerifyCodeClick) },
        )
        Spacer(modifier = Modifier.height((imeHeight + 32).dp))
    }
}
