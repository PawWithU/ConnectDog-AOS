package com.kusitms.connectdog.feature.home.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kusitms.connectdog.core.designsystem.R
import com.kusitms.connectdog.core.designsystem.component.ConnectDogTextFieldWithButton
import com.kusitms.connectdog.core.designsystem.component.ConnectDogTopAppBar
import com.kusitms.connectdog.core.designsystem.component.NormalButton
import com.kusitms.connectdog.core.designsystem.component.NormalTextField
import com.kusitms.connectdog.core.designsystem.component.TopAppBarNavigationType

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun CertificationScreen(onBackClick: () -> Unit = {}, onApplyClick: () -> Unit = {}) {
    Scaffold(
        topBar = {
            ConnectDogTopAppBar(
                titleRes = R.string.apply_volunter,
                navigationType = TopAppBarNavigationType.BACK,
                navigationIconContentDescription = "Navigation icon",
                onNavigationClick = onBackClick
            )
        },
        bottomBar = {
            Box(
                modifier = Modifier.padding(bottom = 32.dp)
            ) {
                NormalButton(content = "다음", modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp).height(56.dp), onClick = onApplyClick)
            }
        }
    ) {
        Content()
    }
}

@Composable
private fun Content() {
    val focusManager = LocalFocusManager.current
    val interactionSource = remember { MutableInteractionSource() }

    Column(
        modifier = Modifier
            .padding(horizontal = 20.dp, vertical = 32.dp)
            .clickable(
                onClick = { focusManager.clearFocus() },
                indication = null,
                interactionSource = interactionSource
            )
    ) {
        Spacer(modifier = Modifier.height(48.dp))
        Text(
            text = "이동봉사를 신청하려면,\n이름과 휴대폰 번호 인증이 필요해요!",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(40.dp))
        NormalTextField(label = "이름", placeholder = "이름 입력", keyboardType = KeyboardType.Text)
        Spacer(modifier = Modifier.height(12.dp))
        ConnectDogTextFieldWithButton(
            width = 62,
            height = 27,
            textFieldLabel = "휴대폰 번호",
            placeholder = "'-'빼고 입력",
            buttonLabel = "인증 요청",
            keyboardType = KeyboardType.Number,
            padding = 5
        )
        Spacer(modifier = Modifier.height(12.dp))
        ConnectDogTextFieldWithButton(
            width = 62,
            height = 27,
            textFieldLabel = "인증번호",
            placeholder = "숫자 6자리",
            buttonLabel = "인증 확인",
            keyboardType = KeyboardType.Number,
            padding = 5
        )
    }
}

// @Preview
// @Composable
// fun test3() {
//    ConnectDogTheme {
//        ApplyScreen()
//    }
// }
