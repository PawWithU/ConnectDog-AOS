package com.kusitms.connectdog.feature.mypage.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.kusitms.connectdog.core.designsystem.component.ConnectDogTopAppBar
import com.kusitms.connectdog.core.designsystem.component.Notification
import com.kusitms.connectdog.core.designsystem.component.TopAppBarNavigationType
import com.kusitms.connectdog.core.designsystem.theme.Gray50
import com.kusitms.connectdog.core.designsystem.theme.Gray70
import com.kusitms.connectdog.core.model.notification.Notification
import com.kusitms.connectdog.feature.mypage.R
import com.kusitms.connectdog.feature.mypage.viewmodel.NotificationViewModel
import org.orbitmvi.orbit.compose.collectAsState

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun NotificationScreen(
    onBackClick: () -> Unit,
    viewModel: NotificationViewModel = hiltViewModel()
) {
    val uiState by viewModel.collectAsState()

    Scaffold(
        topBar = {
            ConnectDogTopAppBar(
                titleRes = R.string.notification,
                navigationType = TopAppBarNavigationType.BACK,
                navigationIconContentDescription = null,
                onNavigationClick = onBackClick
            )
        }
    ) {
        Content(notificationList = uiState.notificationList)
    }
}

@Composable
private fun Content(
    notificationList: List<Notification>?
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 50.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if(notificationList.isNullOrEmpty()) {
            Image(
                painter = painterResource(id = com.kusitms.connectdog.core.designsystem.R.drawable.img_dog_sad),
                contentDescription = "empty",
                modifier = Modifier.height(200.dp).padding(top = 120.dp)
            )
            Text(
                text = "알림이 없어요",
                fontSize = 14.sp,
                color = Gray70,
                fontWeight = FontWeight.W500
            )
            Text(
                text = "홈에서 원하는 공고를 신청해보세요!",
                fontSize = 12.sp,
                color = Gray50
            )
        } else {
            LazyColumn {
                items(notificationList.size){
                    Notification(
                        title = notificationList[it].title,
                        type = notificationList[it].notificationType,
                        content = notificationList[it].body,
                        date = notificationList[it].createdDate,
                    )
                }
            }
        }
    }
}
