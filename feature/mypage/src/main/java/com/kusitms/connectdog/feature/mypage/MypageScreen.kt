package com.kusitms.connectdog.feature.mypage

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kusitms.connectdog.core.designsystem.component.ConnectDogOutlinedButton
import com.kusitms.connectdog.core.designsystem.component.ConnectDogTopAppBar
import com.kusitms.connectdog.core.designsystem.component.TopAppBarNavigationType
import com.kusitms.connectdog.core.designsystem.theme.ConnectDogTheme

@Composable
internal fun MypageRoute(
    padding: PaddingValues,
    onClick: () -> Unit,
    onEditProfileClick: () -> Unit,
    onManageAccountClick: () -> Unit,
    onNotificationClick: () -> Unit,
    onSettingClick: () -> Unit,
    onBadgeClick: () -> Unit,
    onShowErrorSnackBar: (throwable: Throwable?) -> Unit
) {
    MypageScreen(
        padding = padding,
        onClick = onClick,
        onEditProfileClick = onEditProfileClick,
        onSettingClick = onSettingClick,
        onManageAccountClick = onManageAccountClick,
        onNotificationClick = onNotificationClick,
        onBadgeClick = onBadgeClick
    )
}

@Composable
private fun TopBar(
    onManageAccountClick: () -> Unit,
    onNotificationClick: () -> Unit
) {
    ConnectDogTopAppBar(
        titleRes = R.string.my_page,
        navigationType = TopAppBarNavigationType.MYPAGE,
        navigationIconContentDescription = "Navigation icon mypage",
        actionButtons = {
            IconButton(onClick = {}) {
                Icon(
                    imageVector = Icons.Outlined.Notifications,
                    contentDescription = "Navigate to Search",
                    modifier = Modifier.clickable { onNotificationClick() }
                )
            }
            IconButton(onClick = {}) {
                Icon(
                    imageVector = Icons.Outlined.Settings,
                    contentDescription = "Navigate to Search",
                    modifier = Modifier.clickable { onManageAccountClick() }
                )
            }
        }
    )
}

@Composable
private fun MypageScreen(
    padding: PaddingValues,
    onClick: () -> Unit,
    onEditProfileClick: () -> Unit,
    onManageAccountClick: () -> Unit,
    onNotificationClick: () -> Unit,
    onSettingClick: () -> Unit,
    onBadgeClick: () -> Unit
) {
    Column {
        TopBar(onSettingClick, onNotificationClick)
        Spacer(modifier = Modifier.height(20.dp))
        MyInformation(onEditProfileClick)
        Spacer(modifier = Modifier.height(20.dp))
        InformationBox()
        Spacer(modifier = Modifier.height(40.dp))
        Text(text = "나의 이동봉사", style = MaterialTheme.typography.titleMedium, modifier = Modifier.padding(horizontal = 20.dp))
        Spacer(modifier = Modifier.height(20.dp))
        MypageTab(painter = R.drawable.ic_bookmark, title = "저장한 이동봉사 공고", onClick = {})
        Spacer(modifier = Modifier.height(20.dp))
        MypageTab(painter = R.drawable.ic_badge, title = "내 활동 배지", onClick = onBadgeClick)
    }
}

@Composable
private fun MyInformation(
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(
                id = com.kusitms.connectdog.core.designsystem.R.drawable.ic_profile_1
            ),
            contentDescription = null,
            modifier = Modifier.size(50.dp)
        )
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = "이름",
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold
        )
        Spacer(modifier = Modifier.weight(1f))
        ConnectDogOutlinedButton(
            width = 80,
            height = 26,
            text = "프로필 수정",
            padding = 5,
            onClick = onClick
        )
    }
}

@Composable
private fun MypageTab(
    @DrawableRes painter: Int,
    title: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(32.dp)
            .padding(horizontal = 20.dp)
            .clickable { onClick() }
    ) {
        Icon(
            painter = painterResource(id = painter),
            contentDescription = null,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(10.dp))
        Text(
            text = title,
            style = MaterialTheme.typography.bodyLarge
        )
        Spacer(modifier = Modifier.weight(1f))
        Icon(painter = painterResource(id = R.drawable.ic_right_arrow), contentDescription = null, modifier = Modifier.size(24.dp))
    }
}

@Composable
private fun InformationBox() {
    val shape = RoundedCornerShape(12.dp)
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .height(80.dp)
            .clip(shape)
            .background(MaterialTheme.colorScheme.primary)
    ) {
        Row {
            Information(3, "진행한 이동봉사", Modifier.weight(1f))
            Information(12, "봉사 후기", Modifier.weight(1f))
            Information(5, "입양 근황", Modifier.weight(1f))
        }
    }
}

@Composable
private fun Information(
    count: Int,
    title: String,
    modifier: Modifier
) {
    Column(
        modifier = modifier.fillMaxHeight(),
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "${count}회", color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.SemiBold, modifier = Modifier.align(Alignment.CenterHorizontally))
        Text(text = title, color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.SemiBold, modifier = Modifier.align(Alignment.CenterHorizontally))
    }
}

@Preview
@Composable
private fun test() {
    ConnectDogTheme {
//        MypageScreen()
    }
}
