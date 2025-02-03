package com.kusitms.connectdog.feature.login.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import com.kusitms.connectdog.core.designsystem.R as DR
import com.kusitms.connectdog.core.designsystem.component.ActionRow
import com.kusitms.connectdog.core.designsystem.component.ConnectDogErrorCard
import com.kusitms.connectdog.core.designsystem.component.ConnectDogNormalButton
import com.kusitms.connectdog.core.designsystem.component.ConnectDogTextField
import com.kusitms.connectdog.core.designsystem.component.ConnectDogTopAppBar
import com.kusitms.connectdog.core.designsystem.component.TopAppBarNavigationType
import com.kusitms.connectdog.core.util.UserType
import com.kusitms.connectdog.feature.login.R
import com.kusitms.connectdog.feature.login.state.LoginSideEffect
import com.kusitms.connectdog.feature.login.viewmodel.LoginViewModel
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
internal fun NormalLoginScreen(
    onBackClick: () -> Unit,
    onNavigateToSignUp: (UserType) -> Unit,
    onNavigateToVolunteerHome: () -> Unit,
    onNavigateToEmailSearch: (UserType) -> Unit,
    onNavigateToPasswordSearch: (UserType) -> Unit,
    viewModel: LoginViewModel = hiltViewModel()
) {
    val focusManager = LocalFocusManager.current
    val interactionSource = remember { MutableInteractionSource() }

    viewModel.collectSideEffect {
        when (it) {
            is LoginSideEffect.NavigateToHome -> onNavigateToVolunteerHome()
            is LoginSideEffect.NavigateToSignUp -> null
        }
    }

    Scaffold(
        modifier = Modifier
            .clickable(
                onClick = { focusManager.clearFocus() },
                indication = null,
                interactionSource = interactionSource
            ),
        topBar = {
            ConnectDogTopAppBar(
                titleRes = DR.string.volunteer_login,
                navigationType = TopAppBarNavigationType.BACK,
                navigationIconContentDescription = "Navigation icon",
                onNavigationClick = onBackClick
            )
        }
    ) {
        Content(
            viewModel = viewModel,
            onNavigateToSignUp = onNavigateToSignUp,
            onNavigateToEmailSearch = onNavigateToEmailSearch,
            onNavigateToPasswordSearch = onNavigateToPasswordSearch,
        )
    }
}

@Composable
private fun Content(
    viewModel: LoginViewModel,
    onNavigateToSignUp: (UserType) -> Unit,
    onNavigateToEmailSearch: (UserType) -> Unit,
    onNavigateToPasswordSearch: (UserType) -> Unit,
) {
    val uiState by viewModel.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 98.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
        ) {
            ConnectDogTextField(
                text = uiState.email,
                label = stringResource(id = R.string.email),
                placeholder = stringResource(id = R.string.email),
                keyboardType = KeyboardType.Text,
                onTextChanged = viewModel::onEmailChanged,
                isError = uiState.isLoginSuccessful?.let { !it } ?: run { false }
            )
            Spacer(modifier = Modifier.height(12.dp))
            ConnectDogTextField(
                text = uiState.password,
                label = stringResource(id = R.string.password),
                placeholder = stringResource(id = R.string.input_password),
                keyboardType = KeyboardType.Password,
                onTextChanged = viewModel::onPasswordChanged,
                isError = uiState.isLoginSuccessful?.let { !it } ?: run { false }
            )
            Spacer(modifier = Modifier.height(12.dp))
            ConnectDogNormalButton(
                content = stringResource(id = R.string.login),
                color = MaterialTheme.colorScheme.primary,
                onClick = viewModel::initVolunteerLogin,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
            )
            Spacer(modifier = Modifier.height(30.dp))
            ActionRow(
                stringResource(id = R.string.email_signup) to { onNavigateToSignUp(UserType.NORMAL_VOLUNTEER) },
                stringResource(id = R.string.email_search) to { onNavigateToEmailSearch(UserType.NORMAL_VOLUNTEER) },
                stringResource(id = R.string.password_search) to {
                    onNavigateToPasswordSearch(
                        UserType.NORMAL_VOLUNTEER
                    )
                }
            )
        }
        Spacer(modifier = Modifier.height(30.dp))
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            if (uiState.isLoginSuccessful?.let { !it } ?: run { false }) {
                ConnectDogErrorCard(
                    modifier = Modifier
                        .zIndex(1f)
                        .align(Alignment.TopCenter)
                        .padding(horizontal = 20.dp),
                    errorMessage = DR.string.login_error
                )
            }
            Image(
                painter = painterResource(id = DR.drawable.ic_main_large),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .aspectRatio(1f)
            )
        }
    }
}
