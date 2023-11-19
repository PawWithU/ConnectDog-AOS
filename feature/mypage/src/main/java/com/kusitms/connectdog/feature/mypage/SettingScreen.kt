package com.kusitms.connectdog.feature.mypage

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kusitms.connectdog.core.designsystem.component.ConnectDogTopAppBar
import com.kusitms.connectdog.core.designsystem.component.TopAppBarNavigationType
import com.kusitms.connectdog.core.designsystem.theme.ConnectDogTheme
import com.kusitms.connectdog.core.designsystem.theme.Gray2
import com.kusitms.connectdog.core.designsystem.theme.Gray4

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SettingScreen(
    onBackClick: () -> Unit,
    onManageAccountClick: () -> Unit
) {
    Scaffold(
        topBar = {
            ConnectDogTopAppBar(
                titleRes = R.string.setting,
                navigationType = TopAppBarNavigationType.BACK,
                navigationIconContentDescription = null,
                onNavigationClick = onBackClick
            )
        }
    ) {
        Content(onManageAccountClick)
    }
}

@Composable
private fun Content(onClick: () -> Unit) {
    var checked by remember { mutableStateOf(true) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 68.dp, start = 20.dp, end = 20.dp)
    ) {
        Text(
            text = "알림설정",
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(modifier = Modifier.height(20.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp)
        ) {
            Text(
                text = "알림 ON/OFF",
                color = Gray2,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.align(Alignment.TopStart)
            )
            Text(
                text = "이동봉사 신청 승인, 입양 후 근황 업로드 알림",
                color = Gray4,
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.align(Alignment.BottomStart)
            )
            Switch(
                modifier = Modifier.align(Alignment.CenterEnd),
                checked = checked,
                onCheckedChange = {
                    checked = it
                }
            )
        }
        Spacer(modifier = Modifier.height(30.dp))
        Text(
            text = "사용자 설정",
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = "계정 정보 관리",
            fontSize = 16.sp,
            color = Gray2,
            modifier = Modifier.clickable { onClick() }
        )
        Spacer(modifier = Modifier.height(30.dp))
        Text(
            text = "기타",
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = "로그아웃",
            fontSize = 16.sp,
            color = Gray2
        )
    }
}

@Preview
@Composable
private fun test() {
    ConnectDogTheme {
        SettingScreen(onBackClick = {}, onManageAccountClick = {})
    }
}
