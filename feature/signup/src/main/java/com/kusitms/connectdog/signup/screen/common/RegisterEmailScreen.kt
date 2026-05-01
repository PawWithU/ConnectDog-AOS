package com.kusitms.connectdog.signup.screen.common

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import com.kusitms.connectdog.core.designsystem.component.ConnectDogBottomButton
import com.kusitms.connectdog.core.designsystem.component.ConnectDogTextField
import com.kusitms.connectdog.core.designsystem.component.ConnectDogTopAppBar
import com.kusitms.connectdog.core.designsystem.component.TopAppBarNavigationType
import com.kusitms.connectdog.core.designsystem.theme.Gray60
import com.kusitms.connectdog.core.designsystem.theme.Gray80
import com.kusitms.connectdog.core.designsystem.theme.Red1
import com.kusitms.connectdog.core.util.UserType
import com.kusitms.connectdog.feature.signup.R
import com.kusitms.connectdog.signup.state.SignUpSideEffect
import com.kusitms.connectdog.signup.state.SignUpUiState
import com.kusitms.connectdog.signup.viewmodel.SignUpViewModel
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "ResourceType")
@Composable
fun RegisterEmailScreen(
    onBackClick: () -> Unit,
    onNavigateToRegisterPassword: () -> Unit,
    viewModel: SignUpViewModel,
    imeHeight: Int,
) {
    val uiState by viewModel.collectAsState()

    LaunchedEffect(key1 = Unit) {
        Log.d("aswwwwaa", uiState.userType.toString())
    }

    viewModel.collectSideEffect {
        when(it) {
            SignUpSideEffect.NavigateToPasswordRegister -> onNavigateToRegisterPassword()
            else -> Unit
        }
    }

    Scaffold(
        topBar = {
            ConnectDogTopAppBar(
                titleRes = uiState.userType.topBarTitleRes,
                navigationType = TopAppBarNavigationType.BACK,
                onNavigationClick = onBackClick
            )
        }
    ) {
        Content(
            viewModel = viewModel,
            imeHeight = imeHeight,
            uiState = uiState
        )
    }
}

@Composable
private fun Content(
    viewModel: SignUpViewModel,
    uiState: SignUpUiState,
    imeHeight: Int
) {
    val focusManager = LocalFocusManager.current
    val interactionSource = remember { MutableInteractionSource() }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .clickable(
                    onClick = { focusManager.clearFocus() },
                    indication = null,
                    interactionSource = interactionSource
                )
        ) {
            Spacer(modifier = Modifier.height(80.dp))
            Text(
                text = stringResource(id = R.string.email_auth_title),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                lineHeight = 30.sp
            )
            Spacer(modifier = Modifier.height(40.dp))
            ConnectDogTextField(
                text = uiState.email,
                label = stringResource(id = R.string.email),
                placeholder = stringResource(id = R.string.input_email),
                isError = uiState.isValidEmail == false,
                onTextChanged = viewModel::onEmailChanged,
            )
            if (uiState.isValidEmail == false) {
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "올바른 이메일 형식이 아닙니다.",
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Normal,
                    color = Red1
                )
            }
            Spacer(modifier = Modifier.height(12.dp))
            if (uiState.isSendEmailAuthCode) {
                ConnectDogTextField(
                    text = uiState.inputEmailAuthCode,
                    label = stringResource(id = R.string.auth_code),
                    placeholder = stringResource(id = R.string.input_auth_code),
                    keyboardType = KeyboardType.Text,
                    onTextChanged = viewModel::onEmailAuthCodeChanged,
                    isError = uiState.isEmailAuthCodeError == true,
                    maxLength = 8
                )
                if(uiState.isEmailAuthCodeError == true) {
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
                        .padding(top = 28.dp),
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
                content = uiState.emailCertificationButtonText,
                enabled = uiState.enableEmailCertification && !uiState.isEmailLoading,
                onClick = viewModel::onEmailCertificationButtonClick,
            )
            Spacer(modifier = Modifier.height((imeHeight + 32).dp))
        }

        if (uiState.isEmailLoading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center),
                color = Color(0xFFFF7B51)
            )
        }
    }
}