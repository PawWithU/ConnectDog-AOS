package com.kusitms.connectdog.feature.login

import android.annotation.SuppressLint
import android.util.Log
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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.kusitms.connectdog.core.designsystem.R
import com.kusitms.connectdog.core.designsystem.component.ConnectDogErrorCard
import com.kusitms.connectdog.core.designsystem.component.ConnectDogNormalButton
import com.kusitms.connectdog.core.designsystem.component.ConnectDogTopAppBar
import com.kusitms.connectdog.core.designsystem.component.NormalTextField
import com.kusitms.connectdog.core.designsystem.component.TopAppBarNavigationType

private const val TAG = "EmailLoginScreen"

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun NormalLoginScreen(
    test: () -> Unit,
    onBackClick: () -> Unit,
    initVolunteer: () -> Unit,
    initIntermediator: () -> Unit,
    viewModel: LoginViewModel = hiltViewModel()
) {
    val focusManager = LocalFocusManager.current
    val interactionSource = remember { MutableInteractionSource() }
    val volunteerLoginSuccess by viewModel.volunteerLoginSuccess.observeAsState()
    val intermediatorLoginSuccess by viewModel.intermediatorLoginSuccess.observeAsState()
    val loginFail by viewModel.loginError.observeAsState(null)

    var isError = false

    if (volunteerLoginSuccess != null) {
        Log.d(TAG, volunteerLoginSuccess.toString())
        initVolunteer()
    }

    if (intermediatorLoginSuccess != null) {
        initIntermediator()
    }

    if (loginFail != null) { isError = true }

    Scaffold(
        modifier = Modifier.clickable(
            onClick = { focusManager.clearFocus() },
            indication = null,
            interactionSource = interactionSource
        ),
        topBar = {
            ConnectDogTopAppBar(
                titleRes = R.string.login,
                navigationType = TopAppBarNavigationType.BACK,
                navigationIconContentDescription = "Navigation icon",
                onNavigationClick = onBackClick
            )
        }
    ) {
        Content(initVolunteer, viewModel, isError, test)
    }
}

@Composable
private fun Content(
    onClick: () -> Unit,
    viewModel: LoginViewModel,
    isError: Boolean,
    test: () -> Unit
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
                label = "이메일",
                placeholder = "이메일 입력",
                keyboardType = KeyboardType.Text,
                onTextChanged = onPhoneNumberChanged,
                isError = isError
            )
            Spacer(modifier = Modifier.height(12.dp))
            NormalTextField(
                text = password,
                label = "비밀번호",
                placeholder = "비밀번호 입력",
                keyboardType = KeyboardType.Password,
                onTextChanged = onPasswordChanged,
                isError = isError
            )
            Spacer(modifier = Modifier.height(12.dp))
            ConnectDogNormalButton(
                content = "로그인",
                color = MaterialTheme.colorScheme.primary,
                onClick = {
                    test()
//                    viewModel.normalVolunteerLogin(phoneNumber, password)
//                    viewModel.intermediatorLogin(phoneNumber, password)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
            )
            Spacer(modifier = Modifier.height(12.dp))
            if (isError) {
                ConnectDogErrorCard()
            }
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
