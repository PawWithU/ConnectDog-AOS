package com.kusitms.connectdog.signup

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kusitms.connectdog.core.designsystem.R
import com.kusitms.connectdog.core.designsystem.component.ConnectDogNormalButton
import com.kusitms.connectdog.core.designsystem.component.ConnectDogTextFieldWithButton
import com.kusitms.connectdog.core.designsystem.component.ConnectDogTopAppBar
import com.kusitms.connectdog.core.designsystem.component.TopAppBarNavigationType
import com.kusitms.connectdog.core.designsystem.theme.ConnectDogTheme
import com.kusitms.connectdog.core.util.Type

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun RegisterEmailScreen(
    onBackClick: () -> Unit,
    type: Type,
    onNavigateToRegisterPassword: (Type) -> Unit
) {
    val focusManager = LocalFocusManager.current
    val interactionSource = remember { MutableInteractionSource() }

    Scaffold(
        topBar = {
            ConnectDogTopAppBar(
                titleRes = when (type) {
                    Type.SOCIAL_VOLUNTEER -> R.string.volunteer_signup
                    Type.NORMAL_VOLUNTEER -> R.string.volunteer_signup
                    Type.INTERMEDIATOR -> R.string.intermediator_signup
                },
                navigationType = TopAppBarNavigationType.BACK,
                navigationIconContentDescription = "Navigation icon",
                onNavigationClick = onBackClick
            )
        }
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .clickable(
                    onClick = { focusManager.clearFocus() },
                    indication = null,
                    interactionSource = interactionSource
                )
        ) {
            Spacer(modifier = Modifier.height(80.dp))
            Text(
                text = "로그인에 사용할\n이메일을 입력해주세요",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(40.dp))
            ConnectDogTextFieldWithButton(
                width = 62,
                height = 27,
                textFieldLabel = "이메일",
                placeholder = "이메일 입력",
                buttonLabel = "인증 요청",
                padding = 5
            )
            Spacer(modifier = Modifier.height(12.dp))
            ConnectDogTextFieldWithButton(
                width = 62,
                height = 27,
                textFieldLabel = "인증 번호",
                placeholder = "숫자 6자리",
                buttonLabel = "인증 확인",
                padding = 5
            )
            Spacer(modifier = Modifier.weight(1f))
            ConnectDogNormalButton(
                content = "다음",
                onClick = { onNavigateToRegisterPassword(Type.NORMAL_VOLUNTEER) },
                modifier =
                Modifier
                    .fillMaxWidth()
                    .height(56.dp)
            )
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Preview
@Composable
private fun test() {
    ConnectDogTheme {
        RegisterEmailScreen(onBackClick = {}, type = Type.NORMAL_VOLUNTEER, onNavigateToRegisterPassword = {})
    }
}