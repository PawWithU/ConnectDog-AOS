package com.kusitms.connectdog.feature.login

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
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.kusitms.connectdog.core.designsystem.R
import com.kusitms.connectdog.core.designsystem.component.ConnectDogTopAppBar
import com.kusitms.connectdog.core.designsystem.component.NormalTextField
import com.kusitms.connectdog.core.designsystem.component.TopAppBarNavigationType
import com.kusitms.connectdog.core.designsystem.theme.ConnectDogTheme

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun EmailLoginScreen(
    title: String,
    navigator: NavController,
    onClick: () -> Unit,
    viewModel: LoginViewModel = hiltViewModel()
) {
    val loginSuccess by viewModel.loginSuccess.observeAsState()

    if (loginSuccess != null) {
        onClick()
    }

    val focusManager = LocalFocusManager.current
    val interactionSource = remember { MutableInteractionSource() }

    Scaffold(
        modifier = Modifier.clickable(
            onClick = { focusManager.clearFocus() },
            indication = null,
            interactionSource = interactionSource
        ),
        topBar = {
            ConnectDogTopAppBar(
                titleRes = R.string.volunteer_signup,
                navigationType = TopAppBarNavigationType.BACK,
                navigationIconContentDescription = "Navigation icon",
                onNavigationClick = {
                    navigator.popBackStack()
                }
            )
        }
    ) {
        Content(onClick, viewModel)
    }
}

@Composable
private fun Content(onClick: () -> Unit, viewModel: LoginViewModel) {
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
            val (phoneNumber, onPhoneNumberChanged) =
                remember {
                    mutableStateOf("")
                }

            val (password, onPasswordChanged) =
                remember {
                    mutableStateOf("")
                }

            NormalTextField(
                text = phoneNumber,
                label = "휴대폰 번호",
                placeholder = "예)01012341234",
                keyboardType = KeyboardType.Text,
                onTextChanged = onPhoneNumberChanged
            )
            Spacer(modifier = Modifier.height(12.dp))
            NormalTextField(
                text = password,
                label = "비밀번호",
                placeholder = "비밀번호 입력",
                keyboardType = KeyboardType.Password,
                onTextChanged = onPasswordChanged
            )
            Spacer(modifier = Modifier.height(12.dp))
            NormalButton(
                content = "로그인",
                color = MaterialTheme.colorScheme.primary,
                onClick = {
                    viewModel.normalLogin(phoneNumber, password)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
            )
        }

        Spacer(modifier = Modifier.weight(1f))
        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_main_large),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
            )
        }
    }
}

@Preview
@Composable
private fun tes() {
    ConnectDogTheme {
        EmailLoginScreen(
            title = "이동봉사자 로그인",
            navigator = rememberNavController(),
            onClick = {}
        )
    }
}
