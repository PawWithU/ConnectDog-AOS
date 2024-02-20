package com.kusitms.connectdog.feature.mypage.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kusitms.connectdog.core.designsystem.component.ConnectDogTopAppBar
import com.kusitms.connectdog.core.designsystem.component.TopAppBarNavigationType
import com.kusitms.connectdog.core.designsystem.theme.ConnectDogTheme
import com.kusitms.connectdog.core.designsystem.theme.Gray2

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ManageAccountScreen(
    onBackClick: () -> Unit
) {
    Scaffold(
        topBar = {
            ConnectDogTopAppBar(
                titleRes = R.string.manage_account,
                navigationType = TopAppBarNavigationType.BACK,
                navigationIconContentDescription = null,
                onNavigationClick = onBackClick
            )
        }
    ) {
        Content()
    }
}

@Composable
private fun Content() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 20.dp, end = 20.dp, top = 68.dp)
    ) {
        Text(
            text = "회원 정보",
            style = MaterialTheme.typography.titleLarge
        )
        Spacer(modifier = Modifier.height(30.dp))
        Information(title = "이름", content = "김코넥")
        Spacer(modifier = Modifier.height(20.dp))
        Information(title = "휴대폰 번호", content = "김코넥")
        Spacer(modifier = Modifier.height(20.dp))
        Information(title = "이메일", content = "김코넥")
    }
}

@Composable
private fun Information(
    title: String,
    content: String
) {
    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.width(80.dp)
        )
        Spacer(modifier = Modifier.width(20.dp))
        Text(
            text = content,
            style = MaterialTheme.typography.bodyLarge,
            color = Gray2
        )
    }
}

@Preview
@Composable
private fun test() {
    ConnectDogTheme {
//        ManageAccountScreen()
    }
}
