package com.kusitms.connectdog.feature.login.screen

import android.annotation.SuppressLint
import android.widget.Toast
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.kusitms.connectdog.core.designsystem.component.ConnectDogBottomButton
import com.kusitms.connectdog.core.designsystem.component.ConnectDogTextField
import com.kusitms.connectdog.core.designsystem.component.ConnectDogTopAppBar
import com.kusitms.connectdog.core.designsystem.component.TopAppBarNavigationType
import com.kusitms.connectdog.core.designsystem.theme.Gray3
import com.kusitms.connectdog.core.util.UserType
import com.kusitms.connectdog.feature.login.R
import com.kusitms.connectdog.feature.login.viewmodel.PasswordResetViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun PasswordResetScreen(
    onBackClick: () -> Unit,
    imeHeight: Int,
    navigateToLoginRoute: () -> Unit,
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
            userType = userType,
            navigateToLoginRoute = navigateToLoginRoute,
        )
    }
}

@Composable
private fun Content(
    imeHeight: Int,
    viewModel: PasswordResetViewModel = hiltViewModel(),
    navigateToLoginRoute: () -> Unit,
    userType: UserType,
) {
    val focusManager = LocalFocusManager.current
    val interactionSource = remember { MutableInteractionSource() }
    val context = LocalContext.current
    val isValidPassword by viewModel.isValidPassword.collectAsState()
    val isValidConfirmPassword by viewModel.isValidConfirmPassword.collectAsState()

    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp)
                .clickable(
                    onClick = { focusManager.clearFocus() },
                    indication = null,
                    interactionSource = interactionSource,
                ),
    ) {
        Spacer(modifier = Modifier.height(80.dp))
        Text(
            text = "새로운 비밀번호를 입력해주세요",
            style = MaterialTheme.typography.titleLarge,
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
            },
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
            },
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "영문+숫자 10자 이상",
            modifier = Modifier.padding(start = 8.dp),
            style = MaterialTheme.typography.labelMedium,
            color = Gray3,
        )
        Spacer(modifier = Modifier.weight(1f))
        ConnectDogBottomButton(
            onClick = {
                navigateToLoginRoute()
                viewModel.changePassword(userType)
                Toast.makeText(context, "비밀번호가 변경되었습니다.", Toast.LENGTH_SHORT).show()
            },
            content = "완료",
            enabled = isValidPassword == false && isValidConfirmPassword == false,
        )
        Spacer(modifier = Modifier.height((imeHeight + 32).dp))
    }
}
