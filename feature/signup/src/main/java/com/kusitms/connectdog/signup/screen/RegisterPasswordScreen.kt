package com.kusitms.connectdog.signup.screen

import android.annotation.SuppressLint
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.kusitms.connectdog.core.designsystem.R
import com.kusitms.connectdog.core.designsystem.component.ConnectDogNormalButton
import com.kusitms.connectdog.core.designsystem.component.ConnectDogTextField
import com.kusitms.connectdog.core.designsystem.component.ConnectDogTopAppBar
import com.kusitms.connectdog.core.designsystem.component.TopAppBarNavigationType
import com.kusitms.connectdog.core.designsystem.theme.ConnectDogTheme
import com.kusitms.connectdog.core.designsystem.theme.Gray3
import com.kusitms.connectdog.core.designsystem.theme.Orange_40
import com.kusitms.connectdog.core.designsystem.theme.PetOrange
import com.kusitms.connectdog.core.util.UserType
import com.kusitms.connectdog.signup.viewmodel.RegisterPasswordViewModel
import com.kusitms.connectdog.signup.viewmodel.SignUpViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun RegisterPasswordScreen(
    onBackClick: () -> Unit,
    onNavigateToVolunteerProfile: (UserType) -> Unit,
    onNavigateToIntermediatorProfile: () -> Unit,
    userType: UserType,
    imeHeight: Int,
    signUpViewModel: SignUpViewModel,
    viewModel: RegisterPasswordViewModel = hiltViewModel()
) {
    val focusManager = LocalFocusManager.current
    val interactionSource = remember { MutableInteractionSource() }
    val isValidPassword by viewModel.isValidPassword.collectAsState()
    val isValidConfirmPassword by viewModel.isValidConfirmPassword.collectAsState()

    Scaffold(
        topBar = {
            ConnectDogTopAppBar(
                titleRes = when (userType) {
                    UserType.INTERMEDIATOR -> R.string.intermediator_signup
                    else -> R.string.volunteer_signup
                },
                navigationType = TopAppBarNavigationType.BACK,
                navigationIconContentDescription = "Navigation icon",
                onNavigationClick = onBackClick
            )
        }
    ) {
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
                text = "로그인에 사용할\n비밀번호를 입력해주세요",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(40.dp))
            ConnectDogTextField(
                text = viewModel.password,
                label = "비밀번호",
                placeholder = "비밀번호 입력",
                keyboardType = KeyboardType.Password,
                isError = isValidPassword ?: false,
                onTextChanged = {
                    viewModel.updatePassword(it)
                    viewModel.checkPasswordValidity(it)
                }
            )
            Spacer(modifier = Modifier.height(12.dp))
            ConnectDogTextField(
                text = viewModel.confirmPassword,
                label = "비밀번호 확인",
                placeholder = "비밀번호 확인",
                keyboardType = KeyboardType.Password,
                isError = isValidConfirmPassword ?: false,
                onTextChanged = {
                    viewModel.updateConfirmPassword(it)
                    viewModel.checkConfirmPasswordValidity(it)
                }
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "영문+숫자 10자 이상 또는 영문+숫자+특수기호 8자 이상",
                modifier = Modifier.padding(start = 8.dp),
                fontSize = 11.sp,
                color = Gray3
            )
            Spacer(modifier = Modifier.weight(1f))
            ConnectDogNormalButton(
                content = "다음",
                modifier =
                Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                color = if (isValidPassword == false && isValidConfirmPassword == false) {
                    PetOrange
                } else {
                    Orange_40
                },
                onClick = {
                    if (isValidPassword == false && isValidConfirmPassword == false) {
                        signUpViewModel.updatePassword(viewModel.password)
                        when (userType) {
                            UserType.INTERMEDIATOR -> onNavigateToIntermediatorProfile()
                            else -> onNavigateToVolunteerProfile(userType)
                        }
                    }
                }
            )
            Spacer(modifier = Modifier.height((imeHeight + 32).dp))
        }
    }
}

@Preview
@Composable
private fun Preview() {
    ConnectDogTheme {
        RegisterPasswordScreen({}, {}, {}, UserType.INTERMEDIATOR, 10, hiltViewModel())
    }
}
