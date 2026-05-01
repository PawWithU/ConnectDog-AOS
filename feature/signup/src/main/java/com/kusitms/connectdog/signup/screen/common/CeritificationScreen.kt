package com.kusitms.connectdog.signup.screen.common

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import com.kusitms.connectdog.core.designsystem.component.ConnectDogNormalButton
import com.kusitms.connectdog.core.designsystem.component.ConnectDogTextField
import com.kusitms.connectdog.core.designsystem.component.ConnectDogTextFieldWithTimer
import com.kusitms.connectdog.core.designsystem.component.ConnectDogTopAppBar
import com.kusitms.connectdog.core.designsystem.component.TopAppBarNavigationType
import com.kusitms.connectdog.core.designsystem.theme.Gray100
import com.kusitms.connectdog.core.designsystem.theme.Gray60
import com.kusitms.connectdog.core.designsystem.theme.Red1
import com.kusitms.connectdog.core.util.UserType
import com.kusitms.connectdog.feature.signup.R
import com.kusitms.connectdog.signup.state.SignUpSideEffect
import com.kusitms.connectdog.signup.viewmodel.SignUpViewModel
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "ResourceType")
@Composable
fun CertificationScreen(
    onBackClick: () -> Unit,
    onNavigateToRegisterEmail: () -> Unit,
    onNavigateToVolunteerProfile: () -> Unit,
    onSendMessageClick: (String) -> Unit,
    onVerifyCodeClick: (String, (Boolean) -> Unit) -> Unit,
    imeHeight: Int,
    viewModel: SignUpViewModel,
) {
    val uiState by viewModel.collectAsState()

    viewModel.collectSideEffect { sideEffect ->
        when (sideEffect) {
            is SignUpSideEffect.NavigateToProfile -> onNavigateToVolunteerProfile()
            is SignUpSideEffect.NavigateToEmailRegister -> onNavigateToRegisterEmail()
            else -> Unit
        }
    }

    LaunchedEffect(Unit) {
        Log.d("aswwwwaa", uiState.userType.toString())
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
            onSendMessageClick = onSendMessageClick,
            onVerifyCodeClick = onVerifyCodeClick,
            imeHeight = imeHeight,
            viewModel = viewModel,
        )
    }
}

@Composable
private fun Content(
    imeHeight: Int,
    onSendMessageClick: (String) -> Unit,
    onVerifyCodeClick: (String, (Boolean) -> Unit) -> Unit,
    viewModel: SignUpViewModel,
) {
    val focusManager = LocalFocusManager.current
    val interactionSource = remember { MutableInteractionSource() }
    val scrollState = rememberScrollState()
    val uiState by viewModel.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 32.dp)
            .padding(horizontal = 20.dp)
            .clickable(
                onClick = { focusManager.clearFocus() },
                indication = null,
                interactionSource = interactionSource
            )
            .verticalScroll(scrollState)
    ) {
        Spacer(modifier = Modifier.height(48.dp))
        Text(
            text = stringResource(id = R.string.certification_title),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            lineHeight = 30.sp
        )
        Spacer(modifier = Modifier.height(40.dp))
        ConnectDogTextField(
            text = uiState.name,
            label = stringResource(id = R.string.name),
            placeholder = stringResource(id = R.string.input_name),
            keyboardType = KeyboardType.Text,
            onTextChanged = viewModel::onNameChanged
        )
        Spacer(modifier = Modifier.height(12.dp))
        ConnectDogTextField(
            text = uiState.phoneNumber,
            onTextChanged = viewModel::onPhoneNumberChanged,
            label = stringResource(id = R.string.phone_number),
            placeholder = stringResource(id = R.string.phone_number_requirement),
            keyboardType = KeyboardType.Number,
            isError = uiState.isValidPhoneNumber == false
        )
        if (uiState.isValidPhoneNumber == false) {
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "잘못된 휴대폰 번호 형식입니다.",
                fontSize = 10.sp,
                fontWeight = FontWeight.Normal,
                color = Red1
            )
        }
        if (uiState.isSendPhoneAuthCode) {
            Spacer(modifier = Modifier.height(12.dp))
            ConnectDogTextFieldWithTimer(
                text = uiState.phoneAuthCode,
                textFieldLabel = stringResource(id = R.string.auth_code),
                placeholder = stringResource(id = R.string.auth_code_requirement),
                keyboardType = KeyboardType.Number,
                onTextChanged = viewModel::onPhoneAuthCodeChanged,
                isError = (uiState.isPhoneNumberCertified == false),
                maxLength = 6
            )
            if(uiState.isPhoneNumberCertified == false) {
                Text(
                    modifier = Modifier.padding(top = 4.dp),
                    text = stringResource(id = R.string.auth_code_incorrect),
                    color = Red1,
                    fontSize = 10.sp
                )
            }
            Spacer(modifier = Modifier.height(28.dp))
            Row(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = stringResource(id = R.string.resend_title),
                    color = Gray60,
                    fontSize = 12.sp
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    modifier = Modifier.clickable { },
                    text = stringResource(id = R.string.resend),
                    fontSize = 12.sp,
                    color = Gray100,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
        Spacer(modifier = Modifier.weight(1f))
        ConnectDogNormalButton(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            content = uiState.phoneCertificationButtonText,
            enabled = uiState.enablePhoneCertification,
            onClick = {
                viewModel.onPhoneCertificationButtonClick(
                    onSendMessageClick = onSendMessageClick,
                    onVerifyCodeClick = onVerifyCodeClick,
                )
            }
        )
        Spacer(modifier = Modifier.height((imeHeight).dp))
    }
}
