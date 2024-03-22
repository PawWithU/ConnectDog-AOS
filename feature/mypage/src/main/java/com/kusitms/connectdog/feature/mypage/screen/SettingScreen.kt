package com.kusitms.connectdog.feature.mypage.screen

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
import androidx.hilt.navigation.compose.hiltViewModel
import com.kusitms.connectdog.core.designsystem.component.ConnectDogTopAppBar
import com.kusitms.connectdog.core.designsystem.component.TopAppBarNavigationType
import com.kusitms.connectdog.core.designsystem.theme.ConnectDogTheme
import com.kusitms.connectdog.core.designsystem.theme.Gray2
import com.kusitms.connectdog.core.designsystem.theme.Gray4
import com.kusitms.connectdog.feature.mypage.R
import com.kusitms.connectdog.feature.mypage.viewmodel.SettingViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SettingScreen(
    onBackClick: () -> Unit,
    onManageAccountClick: () -> Unit,
    onLogoutClick: () -> Unit,
    viewModel: SettingViewModel = hiltViewModel()
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
        Content(onManageAccountClick, onLogoutClick, viewModel)
    }
}

@Composable
private fun Content(
    onClick: () -> Unit,
    onLogoutClick: () -> Unit,
    viewModel: SettingViewModel
) {
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
            color = Gray2,
            modifier = Modifier.clickable {
                viewModel.initLogout()
                onLogoutClick()
            }
        )
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = "회원탈퇴",
            fontSize = 16.sp,
            color = Gray2,
            modifier = Modifier.clickable {
                viewModel.deleteAccount()
            }
        )
    }
}

@Preview
@Composable
private fun test() {
    ConnectDogTheme {
        SettingScreen(onBackClick = {}, onManageAccountClick = {}, onLogoutClick = {}, viewModel = hiltViewModel())
    }
}
