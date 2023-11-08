package com.kusitms.connectdog.feature.login

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SocialLoginScreen() {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Spacer(modifier = Modifier.height(75.dp))
        Title()
        NormalButton(content = "시작하기")
    }
}

@Composable
fun Title() {
    Text(
        text = "사용자 정보를 입력해주세요",
        fontSize = 20.sp,
        modifier = Modifier.padding(start = 44.dp, end = 44.dp)
    )
}

@Preview
@Composable
fun test() {
    SocialLoginScreen()
}
