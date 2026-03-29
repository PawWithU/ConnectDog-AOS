package com.kusitms.connectdog.feature.intermediator.screen

import android.annotation.SuppressLint
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.kusitms.connectdog.core.designsystem.component.ConnectDogBottomButton
import com.kusitms.connectdog.core.designsystem.component.ConnectDogIntermediatorTopAppBar
import com.kusitms.connectdog.core.designsystem.theme.Gray100
import com.kusitms.connectdog.core.designsystem.theme.Gray2
import com.kusitms.connectdog.core.designsystem.theme.Gray60
import com.kusitms.connectdog.core.designsystem.theme.Gray7
import com.kusitms.connectdog.core.designsystem.theme.Gray90
import com.kusitms.connectdog.core.designsystem.theme.Typography
import com.kusitms.connectdog.core.util.UserType
import com.kusitms.connectdog.feature.intermediator.R
import com.kusitms.connectdog.feature.intermediator.viewmodel.InterHomeViewModel

private val imageList = listOf(
    R.drawable.ic_recruit,
    R.drawable.ic_waiting,
    R.drawable.ic_progress,
    R.drawable.ic_complete
)

private val titleList = listOf(
    R.string.recruit,
    R.string.waiting,
    R.string.progress,
    R.string.complete
)

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun InterHomeScreen(
    onNotificationClick: () -> Unit,
    onSettingClick: (UserType) -> Unit,
    onManageClick: (Int) -> Unit,
    onProfileClick: () -> Unit,
    onNavigateToCreateAnnouncementScreen: () -> Unit,
    viewModel: InterHomeViewModel = hiltViewModel(),
) {
    LaunchedEffect(Unit) {
        viewModel.fetchIntermediatorInfo()
    }
    Scaffold(
        topBar = {
            ConnectDogIntermediatorTopAppBar(
                onNotificationClick = onNotificationClick,
                onSettingClick = onSettingClick
            )
        }
    ) {
        Content(
            viewModel = viewModel,
            onManageClick = onManageClick,
            navigateToProfile = onProfileClick,
            navigateToCreateAnnouncementScreen = onNavigateToCreateAnnouncementScreen
        )
    }
}

@Composable
private fun Content(
    viewModel: InterHomeViewModel,
    navigateToCreateAnnouncementScreen: () -> Unit,
    navigateToProfile: () -> Unit,
    onManageClick: (Int) -> Unit
) {
    val recruitingCount = viewModel.recruitingCount.collectAsState()
    val waitingCount = viewModel.waitingCount.collectAsState()
    val progressingCount = viewModel.progressingCount.collectAsState()
    val completedCount = viewModel.completedCount.collectAsState()
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Spacer(modifier = Modifier.height(48.dp))
        ProfileCard(viewModel, navigateToProfile)
        Spacer(modifier = Modifier
            .fillMaxWidth()
            .height(8.dp)
            .background(Gray7))
        ManageBoard(
            onClick = onManageClick,
            onNavigateToCreateAnnouncementScreen = navigateToCreateAnnouncementScreen,
            recruitingCount = recruitingCount.value ?: 0,
            waitingCount = waitingCount.value ?: 0,
            progressingCount = progressingCount.value ?: 0,
            completedCount = completedCount.value ?: 0,
        )
    }
}

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
private fun ProfileCard(
    viewModel: InterHomeViewModel,
    onProfileClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp)
    ) {
        Box(
            modifier = Modifier
                .size(80.dp)
                .clip(CircleShape)
                .border(
                    width = 1.dp,
                    color = Color.LightGray,
                    shape = CircleShape
                )
        ) {
            AsyncImage(
                model = viewModel.profileImage.value,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }
        Spacer(modifier = Modifier.width(20.dp))
        Column(

        ) {
            Text(
                text = viewModel.intermediaryName.value,
                style = MaterialTheme.typography.titleSmall,
                color = Color.Black,
                fontSize = 16.sp
            )
            Spacer(modifier = Modifier.height(12.dp))
            ConnectDogBottomButton(
                modifier = Modifier.width(120.dp),
                paddingValues = PaddingValues(vertical = 0.dp),
                height = 40,
                radius = 8,
                fontSize = 12,
                enabledColor = Gray7,
                textColor = Gray100,
                content = "프로필 확인",
                onClick = onProfileClick
            )
        }
    }
}

@Composable
private fun ManageBoard(
    onClick: (Int) -> Unit,
    onNavigateToCreateAnnouncementScreen: () -> Unit,
    recruitingCount: Int,
    waitingCount: Int,
    progressingCount: Int,
    completedCount: Int,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(20.dp))
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "전체 ${recruitingCount+waitingCount+progressingCount+completedCount}건",
                fontWeight = FontWeight.W700,
                fontSize = 18.sp
            )
            IconButton(onClick = { onClick(0) }) {
                Icon(
                    painter = painterResource(id = com.kusitms.connectdog.core.designsystem.R.drawable.ic_right_arrow),
                    contentDescription = "move to another screen",
                    modifier = Modifier.size(24.dp),
                    tint = Gray100
                )
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
        RecruitingCard(
            cnt = recruitingCount,
            onClick = onClick
        )
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            ApprovalWaitingCard(
                modifier = Modifier.weight(1f),
                cnt = waitingCount,
                onClick = onClick
            )
            Spacer(modifier = Modifier.width(12.dp))
            InProgressCard(
                modifier = Modifier.weight(1f),
                cnt = progressingCount,
                onClick = onClick
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        CompleteCard(cnt = completedCount, onClick = onClick)
        Spacer(modifier = Modifier.height(24.dp))
        ApplyButton(onClick = onNavigateToCreateAnnouncementScreen)
    }
}

@Composable
private fun ApplyButton(onClick: () -> Unit) {
    val context = LocalContext.current
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .height(48.dp)
            .width(149.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Gray90,
            contentColor = Color.White
        ),
        contentPadding = PaddingValues(horizontal = 15.dp)
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_add),
            contentDescription = null
        )
        Spacer(modifier = Modifier.width(11.dp))
        Text(
            text = "공고 등록하기",
            color = Color.White,
            style = Typography.titleSmall,
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(vertical = 10.dp)
        )
    }
}

@Composable
private fun ManageCard(
    @StringRes title: Int,
    @DrawableRes painter: Int,
    value: String,
    onClick: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        modifier = Modifier
            .size(width = 150.dp, height = 190.dp)
            .clickable { onClick() }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 20.dp, top = 20.dp),
            verticalArrangement = Arrangement.Top
        ) {
            Text(
                text = stringResource(id = title),
                style = MaterialTheme.typography.titleSmall,
                color = Gray2
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "${value}건",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
            Spacer(modifier = Modifier.weight(1f))
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Spacer(modifier = Modifier.weight(1f))
                Image(
                    painter = painterResource(id = painter),
                    contentDescription = null
                )
            }
        }
    }
}

@Composable
fun RecruitingCard(
    cnt: Int,
    onClick: (Int) -> Unit
) {
    Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = Gray7
        ),
        modifier = Modifier
            .fillMaxWidth()
            .height(110.dp)
            .clickable { onClick(0) }
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp)
        ) {
            Column(
                modifier = Modifier.align(Alignment.CenterVertically)
            ) {
                Text(
                    text = "모집중",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.W700,
                )
                Text(
                    text = "${cnt}건",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.W500,
                    color = Gray60
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            Image(
                modifier = Modifier.align(Alignment.Bottom),
                painter = painterResource(id = R.drawable.ic_recruit),
                contentDescription = null
            )
        }
    }
}

@Composable
fun ApprovalWaitingCard(
    modifier: Modifier,
    cnt: Int,
    onClick: (Int) -> Unit
) {
    Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = Gray7
        ),
        modifier = modifier
            .fillMaxWidth()
            .height(130.dp)
            .clickable { onClick(1) }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 20.dp, start = 20.dp)
        ) {
            Text(
                text = "승인 대기중",
                fontSize = 14.sp,
                fontWeight = FontWeight.W700,
            )
            Text(
                text = "${cnt}건",
                fontSize = 12.sp,
                fontWeight = FontWeight.W500,
                color = Gray60
            )
            Spacer(modifier = Modifier.weight(1f))
            Image(
                modifier = Modifier.align(Alignment.End),
                painter = painterResource(id = R.drawable.ic_waiting),
                contentDescription = null
            )
        }
    }
}

@Composable
fun InProgressCard(
    modifier: Modifier,
    cnt: Int,
    onClick: (Int) -> Unit
) {
    Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = Gray7
        ),
        modifier = modifier
            .fillMaxWidth()
            .height(130.dp)
            .clickable { onClick(2) }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 20.dp, start = 20.dp)
        ) {
            Text(
                text = "진행중",
                fontSize = 14.sp,
                fontWeight = FontWeight.W700,
            )
            Text(
                text = "${cnt}건",
                fontSize = 12.sp,
                fontWeight = FontWeight.W500,
                color = Gray60
            )
            Spacer(modifier = Modifier.weight(1f))
            Image(
                modifier = Modifier.align(Alignment.End),
                painter = painterResource(id = R.drawable.ic_progress),
                contentDescription = null
            )
        }
    }
}

@Composable
fun CompleteCard(
    cnt: Int,
    onClick: (Int) -> Unit
) {
    Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = Gray7
        ),
        modifier = Modifier
            .fillMaxWidth()
            .height(110.dp)
            .clickable { onClick(3) }
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp)
        ) {
            Column(
                modifier = Modifier.align(Alignment.CenterVertically)
            ) {
                Text(
                    text = "봉사 완료",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.W700,
                )
                Text(
                    text = "${cnt}건",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.W500,
                    color = Gray60
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            Image(
                modifier = Modifier.align(Alignment.Bottom),
                painter = painterResource(id = R.drawable.ic_complete),
                contentDescription = null
            )
            Image(
                modifier = Modifier.align(Alignment.Bottom),
                painter = painterResource(id = R.drawable.ic_complete2),
                contentDescription = null
            )
        }
    }
}
