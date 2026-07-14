package com.kusitms.connectdog.feature.login.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.kusitms.connectdog.core.designsystem.component.ConnectDogBottomButton
import com.kusitms.connectdog.core.designsystem.component.ConnectDogTextField
import com.kusitms.connectdog.core.designsystem.component.ConnectDogTopAppBar
import com.kusitms.connectdog.core.designsystem.component.TopAppBarNavigationType
import com.kusitms.connectdog.core.util.AccountType
import com.kusitms.connectdog.core.util.UserType
import com.kusitms.connectdog.feature.login.R
import com.kusitms.connectdog.feature.login.state.EmailAuthForPasswordResetSideEffect
import com.kusitms.connectdog.feature.login.viewmodel.EmailAuthForPasswordResetViewModel
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun EmailAuthForPasswordResetScreen(
    onBackClick: () -> Unit,
    onNavigateToPasswordSearch: (UserType) -> Unit,
    onNavigateToNoAccount: (AccountType) -> Unit,
    imeHeight: Int,
    userType: UserType,
) {
    Scaffold(
        topBar = {
            ConnectDogTopAppBar(
                titleRes = R.string.password_search,
                navigationType = TopAppBarNavigationType.BACK,
                onNavigationClick = onBackClick,
            )
        },
    ) {
        Content(
            imeHeight = imeHeight,
            onNavigateToPasswordSearch = onNavigateToPasswordSearch,
            onNavigateToNoAccount = onNavigateToNoAccount,
            userType = userType,
        )
    }
}

@Composable
private fun Content(
    imeHeight: Int,
    onNavigateToPasswordSearch: (UserType) -> Unit,
    onNavigateToNoAccount: (AccountType) -> Unit,
    userType: UserType,
    viewModel: EmailAuthForPasswordResetViewModel = hiltViewModel(),
) {
    val focusManager = LocalFocusManager.current
    val interactionSource = remember { MutableInteractionSource() }

    val uiState by viewModel.collectAsState()

    viewModel.collectSideEffect {
        when (it) {
            EmailAuthForPasswordResetSideEffect.NavigateToFail -> onNavigateToNoAccount(AccountType.PASSWORD)
            EmailAuthForPasswordResetSideEffect.NavigateToPasswordReset -> onNavigateToPasswordSearch(userType)
        }
    }

    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .padding(top = 80.dp, bottom = 24.dp)
                .padding(horizontal = 20.dp)
                .clickable(
                    onClick = focusManager::clearFocus,
                    indication = null,
                    interactionSource = interactionSource,
                ),
    ) {
        Text(
            text = "이메일 인증을\n진행해주세요",
            style = MaterialTheme.typography.titleLarge,
        )
        Spacer(modifier = Modifier.height(40.dp))
        ConnectDogTextField(
            text = uiState.email,
            label = "이메일",
            placeholder = "이메일 입력",
            onTextChanged = viewModel::onEmailChanged,
        )
        Spacer(modifier = Modifier.height(12.dp))
        if (uiState.isSendAuthCode) {
            ConnectDogTextField(
                text = uiState.inputAuthCode,
                label = "인증 번호",
                placeholder = "숫자 6자리",
                keyboardType = KeyboardType.Text,
                onTextChanged = viewModel::onInputAuthCodeChanged,
                isError = uiState.isAuthCodeError,
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        ConnectDogBottomButton(
            onClick = { viewModel.onNextButtonClick(userType) },
            content = uiState.bottomButtonText,
            enabled = uiState.enableNext,
        )
        Spacer(modifier = Modifier.height((imeHeight + 32).dp))
    }
}
