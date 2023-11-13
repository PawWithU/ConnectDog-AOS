package com.kusitms.connectdog.feature.signup

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.kusitms.connectdog.core.designsystem.component.ConnectDogTextFieldWithButton
import com.kusitms.connectdog.core.designsystem.theme.PetOrange
import com.kusitms.connectdog.feature.login.NormalButton
import com.kusitms.connectdog.feature.login.TopBar

@Composable
fun RegisterEmailScreen(
    title: String,
    navController: NavController,
    onclick: () -> Unit
) {
    val focusManager = LocalFocusManager.current
    val interactionSource = remember { MutableInteractionSource() }
    Box(
        modifier =
        Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(top = 32.dp, bottom = 32.dp)
            .clickable(
                onClick = { focusManager.clearFocus() },
                indication = null,
                interactionSource = interactionSource
            )
    ) {
        Column(
            modifier =
            Modifier
                .align(Alignment.TopCenter)
                .background(Color.White),
        ) {
            TopBar(title = title, navController)
            Spacer(modifier = Modifier.height(37.dp))
            Text(
                text = "로그인에 사용할\n이메일을 입력해주세요",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 20.dp)
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
            ConnectDogTextFieldWithButton(
                width = 62,
                height = 27,
                textFieldLabel = "인증 번호",
                placeholder = "숫자 6자리",
                buttonLabel = "인증 확인",
                padding = 5
            )
        }
        NormalButton(
            content = "다음",
            color = PetOrange,
            onClick = { navController.navigate("registerPassword") },
            modifier =
            Modifier
                .fillMaxWidth()
                .height(56.dp)
                .padding(horizontal = 20.dp)
                .align(Alignment.BottomCenter)
        )
    }
}