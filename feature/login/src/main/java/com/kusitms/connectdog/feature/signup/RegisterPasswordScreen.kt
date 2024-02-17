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
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.kusitms.connectdog.core.designsystem.R
import com.kusitms.connectdog.core.designsystem.component.ConnectDogNormalButton
import com.kusitms.connectdog.core.designsystem.component.ConnectDogTopAppBar
import com.kusitms.connectdog.core.designsystem.component.NormalTextField
import com.kusitms.connectdog.core.designsystem.component.TopAppBarNavigationType

@Composable
fun RegisterPasswordScreen(
    navigator: NavController,
    onclick: () -> Unit
) {
    val focusManager = LocalFocusManager.current
    val interactionSource = remember { MutableInteractionSource() }
    Box(
        modifier =
        Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(bottom = 32.dp)
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
                .background(Color.White)
        ) {
            ConnectDogTopAppBar(
                titleRes = R.string.volunteer_signup,
                navigationType = TopAppBarNavigationType.BACK,
                navigationIconContentDescription = "Navigation icon",
                onNavigationClick = { navigator.popBackStack() }
            )
            Spacer(modifier = Modifier.height(37.dp))
            Text(
                text = "로그인에 사용할\n비밀번호를 입력해주세요",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 20.dp)
            )
            Spacer(modifier = Modifier.height(40.dp))
            NormalTextField(
                label = "비밀번호",
                placeholder = "비밀번호 입력",
                keyboardType = KeyboardType.Password
            )
            Spacer(modifier = Modifier.height(12.dp))
            NormalTextField(
                label = "비밀번호 확인",
                placeholder = "비밀번호 확인",
                keyboardType = KeyboardType.Password
            )
        }
        ConnectDogNormalButton(
            content = "다음",
            color = MaterialTheme.colorScheme.primary,
            onClick = { navigator.navigate("completeSignUp") },
            modifier =
            Modifier
                .fillMaxWidth()
                .height(56.dp)
                .padding(horizontal = 20.dp)
                .align(Alignment.BottomCenter)
        )
    }
}
