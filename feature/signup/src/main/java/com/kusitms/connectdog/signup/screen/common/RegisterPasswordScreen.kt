package com.kusitms.connectdog.signup.screen.common

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kusitms.connectdog.core.designsystem.component.ConnectDogBottomButton
import com.kusitms.connectdog.core.designsystem.component.ConnectDogNormalButton
import com.kusitms.connectdog.core.designsystem.component.ConnectDogTextField
import com.kusitms.connectdog.core.designsystem.component.ConnectDogTopAppBar
import com.kusitms.connectdog.core.designsystem.component.TopAppBarNavigationType
import com.kusitms.connectdog.core.designsystem.theme.Gray3
import com.kusitms.connectdog.feature.signup.R
import com.kusitms.connectdog.signup.state.SignUpSideEffect
import com.kusitms.connectdog.signup.state.SignUpUiState
import com.kusitms.connectdog.signup.viewmodel.SignUpViewModel
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "ResourceType")
@Composable
fun RegisterPasswordScreen(
    onBackClick: () -> Unit,
    onNavigateToVolunteerProfile: () -> Unit,
    onNavigateToIntermediatorProfile: () -> Unit,
    imeHeight: Int,
    viewModel: SignUpViewModel
) {
    val uiState by viewModel.collectAsState()

    viewModel.collectSideEffect {
        when(it) {
            is SignUpSideEffect.NavigateToProfile -> onNavigateToVolunteerProfile()
            else -> Unit
        }
    }
    Scaffold(
        topBar = {
            ConnectDogTopAppBar(
                titleRes = uiState.userType.topBarTitleRes,
                navigationType = TopAppBarNavigationType.BACK,
                navigationIconContentDescription = "Navigation icon",
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
    imeHeight: Int,
    uiState: SignUpUiState,
) {
    val focusManager = LocalFocusManager.current
    val interactionSource = remember { MutableInteractionSource() }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(horizontal = 20.dp)
            .clickable(
                onClick = { focusManager.clearFocus() },
                indication = null,
                interactionSource = interactionSource
            )
    ) {
        Spacer(modifier = Modifier.height(80.dp))
        Text(
            text = stringResource(id = R.string.register_password_title),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(40.dp))
        ConnectDogTextField(
            text = uiState.password,
            label = stringResource(id = R.string.password),
            placeholder = stringResource(id = R.string.input_passwrod),
            keyboardType = KeyboardType.Password,
            isError = (uiState.isValidPassword == false),
            onTextChanged = viewModel::onPasswordChanged
        )
        Spacer(modifier = Modifier.height(12.dp))
        ConnectDogTextField(
            text = uiState.confirmPassword,
            label = stringResource(id = R.string.confirm_password),
            placeholder = stringResource(id = R.string.confirm_password),
            keyboardType = KeyboardType.Password,
            isError = uiState.isValidConfirmPassword == false,
            onTextChanged = viewModel::onConfirmPasswordChanged
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = stringResource(id = R.string.password_requirement),
            modifier = Modifier.padding(start = 8.dp),
            fontSize = 11.sp,
            color = Gray3
        )
        Spacer(modifier = Modifier.weight(1f))
        ConnectDogBottomButton(
            content = stringResource(id = R.string.next),
            enabled = uiState.enablePasswordRegister,
            onClick = viewModel::onPasswordRegisterNextButtonClick
        )
        Spacer(modifier = Modifier.height((imeHeight + 32).dp))
    }
}
