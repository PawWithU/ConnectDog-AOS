package com.kusitms.connectdog.feature.mypage.screen

import androidx.activity.compose.BackHandler
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.kusitms.connectdog.core.designsystem.component.BannerGuideline
import com.kusitms.connectdog.core.designsystem.component.ConnectDogOutlinedButton
import com.kusitms.connectdog.core.designsystem.component.ConnectDogTopAppBar
import com.kusitms.connectdog.core.designsystem.component.TopAppBarNavigationType
import com.kusitms.connectdog.core.designsystem.theme.ConnectDogTheme
import com.kusitms.connectdog.core.designsystem.theme.Gray50
import com.kusitms.connectdog.core.designsystem.theme.Gray7
import com.kusitms.connectdog.core.designsystem.theme.PetOrange
import com.kusitms.connectdog.core.util.UserType
import com.kusitms.connectdog.core.util.getProfileImageId
import com.kusitms.connectdog.feature.mypage.R
import com.kusitms.connectdog.feature.mypage.viewmodel.MyPageViewModel

@Composable
internal fun MypageRoute(
    onEditProfileClick: (Int, String) -> Unit,
    onNotificationClick: () -> Unit,
    onSettingClick: (UserType) -> Unit,
    onBadgeClick: () -> Unit,
    onBookmarkClick: () -> Unit,
    onNavigateToHome: () -> Unit,
    onShowErrorSnackBar: (throwable: Throwable?) -> Unit,
) {
    BackHandler {
        onNavigateToHome()
    }

    MypageScreen(
        onEditProfileClick = onEditProfileClick,
        onSettingClick = onSettingClick,
        onNotificationClick = onNotificationClick,
        onBadgeClick = onBadgeClick,
        onBookmarkClick = onBookmarkClick,
    )
}

@Composable
private fun TopBar(
    onManageAccountClick: (UserType) -> Unit,
    onNotificationClick: () -> Unit,
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
                    modifier = Modifier.clickable { onNotificationClick() },
                )
            }
            IconButton(onClick = {}) {
                Icon(
                    imageVector = Icons.Outlined.Settings,
                    contentDescription = "Navigate to Search",
                    modifier = Modifier.clickable { onManageAccountClick(UserType.NORMAL_VOLUNTEER) },
                )
            }
        },
    )
}

@Composable
private fun MypageScreen(
    onEditProfileClick: (Int, String) -> Unit,
    onNotificationClick: () -> Unit,
    onSettingClick: (UserType) -> Unit,
    onBadgeClick: () -> Unit,
    onBookmarkClick: () -> Unit,
    viewModel: MyPageViewModel = hiltViewModel(),
) {
    LaunchedEffect(Unit) {
        viewModel.fetchUserInfo()
        viewModel.fetchBadge()
        viewModel.fetchBookmark()
    }

    val bookmarkCount by viewModel.bookmark.observeAsState()
    val badgeList by viewModel.badge.observeAsState()

    Column {
        TopBar(onSettingClick, onNotificationClick)
        Spacer(modifier = Modifier.height(20.dp))
        MyInformation(onEditProfileClick, viewModel)
        Spacer(modifier = Modifier.height(20.dp))
        InformationBox()
        HorizontalDivider(modifier = Modifier.height(8.dp).background(Gray7).padding(vertical = 20.dp))
        BannerGuideline()
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = "나의 이동봉사",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(horizontal = 20.dp),
        )
        Spacer(modifier = Modifier.height(20.dp))
        MypageTab(
            painter = R.drawable.ic_bookmark,
            title = stringResource(id = R.string.bookmark),
            onClick = onBookmarkClick,
            count = bookmarkCount?.size ?: 0,
        )
        Spacer(modifier = Modifier.height(20.dp))
        MypageTab(
            painter = R.drawable.ic_badge,
            title = stringResource(id = R.string.badge),
            onClick = onBadgeClick,
            count = (badgeList?.count { it.image != null }) ?: 0,
        )
    }
}

@Composable
private fun MyInformation(
    onEditProfileClick: (Int, String) -> Unit,
    viewModel: MyPageViewModel,
) {
    val userInfo by viewModel.myInfo.observeAsState(null)

    Row(
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        userInfo?.let {
            Image(
                painter =
                    painterResource(
                        id = getProfileImageId(it.profileImageNum),
                    ),
                contentDescription = null,
                modifier = Modifier.size(80.dp),
            )
            Spacer(modifier = Modifier.width(20.dp))
            Column {
                Text(
                    text = it.nickname,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.SemiBold,
                )
                Spacer(modifier = Modifier.height(12.dp))
                ConnectDogOutlinedButton(
                    width = 80,
                    height = 26,
                    text = "프로필 수정",
                    padding = 5,
                    onClick = { onEditProfileClick(userInfo!!.profileImageNum, userInfo!!.nickname) },
                )
            }
        }
    }
}

@Composable
private fun MypageTab(
    @DrawableRes painter: Int,
    title: String,
    onClick: () -> Unit,
    count: Int?,
) {
    Row(
        modifier =
            Modifier
                .fillMaxWidth()
                .height(32.dp)
                .padding(horizontal = 20.dp)
                .clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            painter = painterResource(id = painter),
            contentDescription = null,
            modifier = Modifier.size(24.dp),
        )
        Spacer(modifier = Modifier.width(10.dp))
        Text(
            text = title,
            style = MaterialTheme.typography.bodyLarge,
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = count?.toString() ?: "",
            style = MaterialTheme.typography.bodyLarge,
            color = PetOrange,
            fontWeight = FontWeight.Medium,
        )
        Spacer(modifier = Modifier.weight(1f))
        Icon(
            painter = painterResource(id = R.drawable.ic_right_arrow),
            contentDescription = null,
            modifier = Modifier.size(24.dp),
        )
    }
}

@Composable
private fun InformationBox(viewModel: MyPageViewModel = hiltViewModel()) {
    val myInformation by viewModel.myInfo.observeAsState(null)

    val shape = RoundedCornerShape(12.dp)
    Box(
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .height(80.dp)
                .clip(shape)
                .background(Gray7),
    ) {
        myInformation?.let {
            Row {
                Information(it.waitingCount, "승인대기중", Modifier.weight(1f))
                Information(it.progressingCount, "진행중", Modifier.weight(1f))
                Information(it.completedCount, "완료", Modifier.weight(1f))
                Information(it.reviewCount, "후기", Modifier.weight(1f))
            }
        }
    }
}

@Composable
private fun Information(
    count: Int,
    title: String,
    modifier: Modifier,
) {
    Column(
        modifier = modifier.fillMaxHeight(),
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            text = "${count}회",
            style = MaterialTheme.typography.titleLarge,
            color = Gray50,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.align(Alignment.CenterHorizontally),
        )
        Text(
            text = title,
            style = MaterialTheme.typography.labelLarge,
            color = Gray50,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.align(Alignment.CenterHorizontally),
        )
    }
}

@Preview
@Composable
private fun Test() {
    ConnectDogTheme {
//        MypageScreen()
    }
}
