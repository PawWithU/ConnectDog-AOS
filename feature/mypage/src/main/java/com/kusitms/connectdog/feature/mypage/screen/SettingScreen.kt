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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.kusitms.connectdog.core.designsystem.component.ConnectDogAlertDialog
import com.kusitms.connectdog.core.designsystem.component.ConnectDogLogOutDialog
import com.kusitms.connectdog.core.designsystem.component.ConnectDogTopAppBar
import com.kusitms.connectdog.core.designsystem.component.ConnectDogWithDrawDialog
import com.kusitms.connectdog.core.designsystem.component.TopAppBarNavigationType
import com.kusitms.connectdog.core.designsystem.theme.Gray2
import com.kusitms.connectdog.core.designsystem.theme.Gray4
import com.kusitms.connectdog.core.util.UserType
import com.kusitms.connectdog.feature.mypage.R
import com.kusitms.connectdog.feature.mypage.viewmodel.SettingViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SettingScreen(
    userType: UserType,
    onBackClick: () -> Unit,
    onManageAccountClick: (UserType) -> Unit,
    onLogoutClick: () -> Unit,
    viewModel: SettingViewModel = hiltViewModel(),
) {
    Scaffold(
        topBar = {
            ConnectDogTopAppBar(
                titleRes = R.string.setting,
                navigationType = TopAppBarNavigationType.BACK,
                navigationIconContentDescription = null,
                onNavigationClick = onBackClick,
            )
        },
    ) {
        Content(
            userType = userType,
            onClick = onManageAccountClick,
            onLogoutClick = onLogoutClick,
            viewModel = viewModel,
        )
    }
}

@Composable
private fun Content(
    userType: UserType,
    onClick: (UserType) -> Unit,
    onLogoutClick: () -> Unit,
    viewModel: SettingViewModel,
) {
    var checked by remember { mutableStateOf(true) }
    var isWithDrawFailDialogVisible by rememberSaveable { mutableStateOf(false) }
    var isWithDrawDialogVisible by rememberSaveable { mutableStateOf(false) }
    var isLogoutDialogVisible by rememberSaveable { mutableStateOf(false) }
    var isInquireDialogVisible by rememberSaveable { mutableStateOf(false) }
    var isWithDrawCompleteDialogVisible by rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(key1 = viewModel) {
        viewModel.isAbleWithdraw.collect {
            if (it == true) {
                isWithDrawDialogVisible = true
            } else if (it == false) {
                isWithDrawFailDialogVisible = true
            }
        }
    }

    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .padding(top = 68.dp, start = 20.dp, end = 20.dp),
    ) {
        Text(
            text = "알림 설정",
            style = MaterialTheme.typography.titleMedium,
        )
        Spacer(modifier = Modifier.height(20.dp))
        Box(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .height(40.dp),
        ) {
            Text(
                text = "알림 ON/OFF",
                style = MaterialTheme.typography.bodyLarge,
                color = Gray2,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.align(Alignment.TopStart),
            )
            Text(
                text = "이동봉사 신청 승인, 입양 후 근황 업로드 알림",
                style = MaterialTheme.typography.labelLarge,
                color = Gray4,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.align(Alignment.BottomStart),
            )
            Switch(
                modifier = Modifier.align(Alignment.CenterEnd),
                checked = checked,
                onCheckedChange = {
                    checked = it
                    viewModel.updateNotification()
                },
            )
        }
        Spacer(modifier = Modifier.height(30.dp))
        Text(
            text = "사용자 설정",
            style = MaterialTheme.typography.titleMedium,
        )
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = "계정 정보 관리",
            style = MaterialTheme.typography.bodyLarge,
            color = Gray2,
            modifier = Modifier.clickable { onClick(userType) },
        )
        Spacer(modifier = Modifier.height(30.dp))
        Text(
            text = "기타",
            style = MaterialTheme.typography.titleMedium,
        )
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = "문의하기",
            style = MaterialTheme.typography.bodyLarge,
            color = Gray2,
            modifier =
                Modifier.clickable {
                    isInquireDialogVisible = true
                },
        )
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = "로그아웃",
            style = MaterialTheme.typography.bodyLarge,
            color = Gray2,
            modifier = Modifier.clickable { isLogoutDialogVisible = true },
        )
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = "회원탈퇴",
            style = MaterialTheme.typography.bodyLarge,
            color = Gray2,
            modifier =
                Modifier.clickable {
                    viewModel.updateIsAbleWithdraw(userType)
                },
        )
    }

    if (isWithDrawDialogVisible) {
        ConnectDogWithDrawDialog(
            onDismissRequest = {
                isWithDrawDialogVisible = false
            },
            titleRes = R.string.withdraw_title,
            descriptionRes = R.string.withdraw_description,
            okText = R.string.withdraw_ok,
            cancelText = R.string.cancel,
            onClickOk = {
                isWithDrawDialogVisible = false
                viewModel.deleteAccount(userType)
                isWithDrawCompleteDialogVisible = true
            },
        )
    }

    if (isWithDrawCompleteDialogVisible) {
        ConnectDogAlertDialog(
            onDismissRequest = { isWithDrawCompleteDialogVisible = false },
            descriptionRes = R.string.withdraw_complete,
            okText = R.string.ok,
            onClickOk = {
                isWithDrawCompleteDialogVisible = false
                onLogoutClick()
            },
        )
    }

    if (isLogoutDialogVisible) {
        ConnectDogLogOutDialog(
            onDismissRequest = {
                isLogoutDialogVisible = false
                viewModel.initLogout()
                onLogoutClick()
            },
            titleRes = R.string.logout_title,
            descriptionRes = R.string.logout_description,
            okText = R.string.logout_cancel,
            cancelText = R.string.logout_ok,
            onClickOk = { isLogoutDialogVisible = false },
        )
    }

    if (isWithDrawFailDialogVisible) {
        ConnectDogAlertDialog(
            onDismissRequest = { isWithDrawFailDialogVisible = false },
            descriptionRes = R.string.description,
            okText = R.string.ok,
            onClickOk = { isWithDrawFailDialogVisible = false },
        )
    }

    if (isInquireDialogVisible) {
        ConnectDogAlertDialog(
            onDismissRequest = { isInquireDialogVisible = false },
            titleRes = R.string.inquire_title,
            descriptionRes = R.string.inquire_description,
            okText = R.string.inquire_ok,
            onClickOk = { isInquireDialogVisible = false },
        )
    }
}
