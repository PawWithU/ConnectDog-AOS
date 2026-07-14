package com.kusitms.connectdog.feature.mypage.screen

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.kusitms.connectdog.core.designsystem.component.ConnectDogBottomButton
import com.kusitms.connectdog.core.designsystem.component.ConnectDogTextField
import com.kusitms.connectdog.core.designsystem.component.ConnectDogTopAppBar
import com.kusitms.connectdog.core.designsystem.component.TopAppBarNavigationType
import com.kusitms.connectdog.core.designsystem.theme.Gray3
import com.kusitms.connectdog.core.util.UserType
import com.kusitms.connectdog.feature.mypage.R
import com.kusitms.connectdog.feature.mypage.viewmodel.PasswordChangeViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
internal fun PasswordChangeScreen(
    onBackClick: () -> Unit,
    userType: UserType,
) {
    Scaffold(
        topBar = {
            ConnectDogTopAppBar(
                titleRes = R.string.password_change,
                navigationType = TopAppBarNavigationType.BACK,
                onNavigationClick = onBackClick,
            )
        },
    ) {
        Content(
            onBackClick = onBackClick,
            userType = userType,
        )
    }
}

@Composable
private fun Content(
    viewModel: PasswordChangeViewModel = hiltViewModel(),
    onBackClick: () -> Unit,
    userType: UserType,
) {
    val focusManager = LocalFocusManager.current
    val interactionSource = remember { MutableInteractionSource() }
    val context = LocalContext.current

    val isRightPassword by viewModel.isRightPassword.collectAsState()
    val isValidPassword by viewModel.isValidPassword.collectAsState()
    val isValidConfirmPassword by viewModel.isValidConfirmPassword.collectAsState()

    LaunchedEffect(key1 = isRightPassword) {
        if (isRightPassword == true) {
            viewModel.changePassword(userType)
            onBackClick()
            Toast.makeText(context, "비밀번호를 변경하였습니다", Toast.LENGTH_SHORT).show()
        } else if (isRightPassword == false) {
            Toast.makeText(context, "기존 비밀번호를 올바르게 입력해주세요", Toast.LENGTH_SHORT).show()
        }
    }

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
            text = stringResource(id = R.string.password_change_title),
            style = MaterialTheme.typography.titleLarge,
        )
        Spacer(modifier = Modifier.height(40.dp))
        ConnectDogTextField(
            text = viewModel.previousPassword,
            label = "기존 비밀번호",
            placeholder = "기존 비밀번호 입력",
            keyboardType = KeyboardType.Password,
            onTextChanged = viewModel::updatePreviousPassword,
            isError = isRightPassword == false,
        )
        Spacer(modifier = Modifier.height(12.dp))
        ConnectDogTextField(
            text = viewModel.newPassword,
            label = "새 비밀번호",
            placeholder = "새 비밀번호 입력",
            keyboardType = KeyboardType.Password,
            isError = isValidPassword ?: false,
            onTextChanged = {
                viewModel.updateNewPassword(it)
                viewModel.checkPasswordValidity(it)
            },
        )
        Spacer(modifier = Modifier.height(12.dp))
        ConnectDogTextField(
            text = viewModel.checkPassword,
            label = "새 비밀번호 확인",
            placeholder = "새 비밀번호 확인",
            keyboardType = KeyboardType.Password,
            isError = isValidConfirmPassword ?: false,
            onTextChanged = {
                viewModel.updateCheckPassword(it)
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
            modifier = Modifier.padding(vertical = 24.dp),
            content = "완료",
            enabled = isValidPassword == false && isValidConfirmPassword == false && viewModel.previousPassword.isNotEmpty(),
            onClick = { viewModel.checkPreviousPassword(userType) },
        )
    }
}
