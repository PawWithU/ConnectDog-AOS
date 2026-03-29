package com.kusitms.connectdog.signup.screen.common

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
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
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(40.dp))
        ConnectDogTextField(
            text = uiState.email,
            label = stringResource(id = R.string.email),
            placeholder = stringResource(id = R.string.input_email),
            isError = uiState.isValidEmail == false || uiState.isEmailError == true,
            onTextChanged = viewModel::onEmailChanged,
        )
        if(uiState.isEmailError == true) {
            Text(
                modifier = Modifier.padding(top = 4.dp),
                text = stringResource(id = R.string.duplicated_email),
                color = Red1,
                fontSize = 10.sp
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
                isError = uiState.isEmailAuthCodeError == true
            )
            if(uiState.isEmailAuthCodeError == true) {
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "올바른 인증번호를 입력해주세요",
                    fontSize = 10.sp,
                    color = Red1
                )
            }
        }
        Spacer(modifier = Modifier.weight(1f))
        ConnectDogBottomButton(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            content = uiState.emailCertificationButtonText,
            enabled = uiState.enableEmailCertification,
            onClick = viewModel::onEmailCertificationButtonClick,
        )
        Spacer(modifier = Modifier.height((imeHeight + 32).dp))
    }
}