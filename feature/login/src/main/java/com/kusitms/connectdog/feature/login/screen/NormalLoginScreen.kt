package com.kusitms.connectdog.feature.login.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.kusitms.connectdog.core.designsystem.R
import com.kusitms.connectdog.core.designsystem.component.ConnectDogErrorCard
import com.kusitms.connectdog.core.designsystem.component.ConnectDogNormalButton
import com.kusitms.connectdog.core.designsystem.component.ConnectDogTextField
import com.kusitms.connectdog.core.designsystem.component.ConnectDogTopAppBar
import com.kusitms.connectdog.core.designsystem.component.TopAppBarNavigationType
import com.kusitms.connectdog.core.util.UserType
import com.kusitms.connectdog.feature.login.viewmodel.LoginViewModel

private const val TAG = "EmailLoginScreen"

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun NormalLoginScreen(
    userType: UserType,
    onBackClick: () -> Unit,
    onNavigateToVolunteerHome: () -> Unit,
    onNavigateToIntermediatorHome: () -> Unit,
    viewModel: LoginViewModel = hiltViewModel()
) {
    val focusManager = LocalFocusManager.current
    val interactionSource = remember { MutableInteractionSource() }
    val isLoginSuccessful by viewModel.isLoginSuccessful.collectAsState()

    isLoginSuccessful?.let {
        if (it) {
            when (userType) {
                UserType.INTERMEDIATOR -> onNavigateToIntermediatorHome()
                else -> onNavigateToVolunteerHome()
            }
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
                titleRes = when (userType) {
                    UserType.SOCIAL_VOLUNTEER -> R.string.volunteer_login
                    UserType.NORMAL_VOLUNTEER -> R.string.volunteer_login
                    UserType.INTERMEDIATOR -> R.string.intermediator_login
                },
                navigationType = TopAppBarNavigationType.BACK,
                navigationIconContentDescription = "Navigation icon",
                onNavigationClick = onBackClick
            )
        }
    ) {
        Content(viewModel, userType, isLoginSuccessful)
    }
}

@Composable
private fun Content(
    viewModel: LoginViewModel,
    userType: UserType,
    isLoginSuccessful: Boolean?
) {
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
                text = viewModel.email,
                label = "이메일",
                placeholder = "이메일 입력",
                keyboardType = KeyboardType.Text,
                onTextChanged = { viewModel.updateEmail(it) },
                isError = isLoginSuccessful?.let { !it } ?: run { false }
            )
            Spacer(modifier = Modifier.height(12.dp))
            ConnectDogTextField(
                text = viewModel.password,
                label = "비밀번호",
                placeholder = "비밀번호 입력",
                keyboardType = KeyboardType.Password,
                onTextChanged = { viewModel.updatePassword(it) },
                isError = isLoginSuccessful?.let { !it } ?: run { false }
            )
            Spacer(modifier = Modifier.height(12.dp))
            ConnectDogNormalButton(
                content = "로그인",
                color = MaterialTheme.colorScheme.primary,
                onClick = {
                    when (userType) {
                        UserType.INTERMEDIATOR -> { viewModel.initIntermediatorLogin() }
                        UserType.NORMAL_VOLUNTEER -> { viewModel.initVolunteerLogin() }
                        UserType.SOCIAL_VOLUNTEER -> {}
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
            )
            Spacer(modifier = Modifier.height(12.dp))
            if (isLoginSuccessful?.let { !it } ?: run { false }) {
                ConnectDogErrorCard(R.string.login_error)
            }
        }
        Spacer(modifier = Modifier.weight(1f))
        Image(
            painter = painterResource(id = R.drawable.ic_main_large),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
        )
    }
}
