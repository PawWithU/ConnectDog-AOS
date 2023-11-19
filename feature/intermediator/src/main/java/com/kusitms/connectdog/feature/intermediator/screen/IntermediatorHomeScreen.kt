package com.kusitms.connectdog.feature.intermediator.screen

import android.annotation.SuppressLint
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kusitms.connectdog.core.designsystem.component.ConnectDogIntermediatorTopAppBar
import com.kusitms.connectdog.core.designsystem.component.NetworkImage
import com.kusitms.connectdog.core.designsystem.theme.Brown5
import com.kusitms.connectdog.core.designsystem.theme.ConnectDogTheme
import com.kusitms.connectdog.core.designsystem.theme.Typography
import com.kusitms.connectdog.feature.intermediator.R

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun IntermediatorHomeScreen(
    onNotificationClick: () -> Unit,
    onSettingClick: () -> Unit
) {
    Scaffold(
        topBar = {
            ConnectDogIntermediatorTopAppBar(
                onNotificationClick = onNotificationClick,
                onSettingClick = onSettingClick
            )
        }
    ) {
        Content()
    }
}

@Composable
private fun Content() {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Spacer(modifier = Modifier.height(48.dp))
        Information()
        ManageBoard()
    }
}

@Composable
private fun Information() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primary)
            .heightIn(min = 0.dp, max = 185.dp)
    ) {
        ProfileCard()
    }
}

@Composable
private fun ProfileCard() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primary)
    ) {
        Row(
            modifier = Modifier
                .padding(start = 20.dp, end = 20.dp, bottom = 20.dp)
                .align(Alignment.Center)
        ) {
            Column {
                NetworkImage(imageUrl = "", placeholder = painterResource(id = R.drawable.ic_default_intermediator))
                Spacer(modifier = Modifier.height(18.dp))
                Text(
                    text = "중개자 이름",
                    style = MaterialTheme.typography.titleSmall,
                    color = Color.White,
                    fontSize = 20.sp
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = "프로필 설명을 여기에 넣으세요...프로필 설명을 여기에 넣으세요...프로필 설명을 여기에 넣으세요...",
                    fontSize = 10.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.White,
                    modifier = Modifier.widthIn(min = 0.dp, max = 220.dp),
                    lineHeight = 12.sp
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            Column(
                modifier = Modifier
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_dog),
                    contentDescription = null,
                    modifier = Modifier.align(Alignment.CenterHorizontally) // Image를 수평 중앙에 정렬
                )
            }
        }
    }
}

@Composable
private fun ManageBoard() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Brown5)
    ) {
        Spacer(modifier = Modifier.height(20.dp))
        Text("전체 12건", modifier = Modifier.padding(start = 20.dp), style = MaterialTheme.typography.titleLarge, fontSize = 18.sp)
        Spacer(modifier = Modifier.height(20.dp))
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            ManageCard(
                title = R.string.recruit,
                painter = R.drawable.ic_recruit,
                value = 3
            )
            Spacer(modifier = Modifier.width(20.dp))
            ManageCard(
                title = R.string.recruit,
                painter = R.drawable.ic_recruit,
                value = 3
            )
        }
        Spacer(modifier = Modifier.height(20.dp))
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            ManageCard(
                title = R.string.recruit,
                painter = R.drawable.ic_recruit,
                value = 3
            )
            ManageCard(
                title = R.string.recruit,
                painter = R.drawable.ic_recruit,
                value = 3
            )
        }
        Spacer(modifier = Modifier.height(20.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            ApplyButton(onClick = {})
        }
    }
}

@Composable
private fun ApplyButton(onClick: () -> Unit) {
    Button(
        onClick = onClick,
        contentPadding = PaddingValues(vertical = 15.dp),
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .width(117.dp)
            .height(44.dp),
        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary, contentColor = Color.White)
    ) {
        Icon(
            imageVector = Icons.Outlined.Add,
            contentDescription = null,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(10.dp))
        Text(text = "공고 등록", color = Color.White, style = Typography.titleSmall, fontSize = 12.sp)
    }
}

@Composable
private fun ManageCard(
    @StringRes title: Int,
    @DrawableRes painter: Int,
    value: Int,
) {
    Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
        ),
        modifier = Modifier
            .width(150.dp)
            .height(180.dp),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(id = title),
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "${value}건",
                fontWeight = FontWeight.Bold,
                fontSize =  18.sp
            )
            Spacer(modifier = Modifier.height(16.dp)) // 텍스트와 이미지 사이의 간격을 조정합니다.
            Image(
                painter = painterResource(id = painter),
                contentDescription = null,
            )
        }
    }
}


@Preview
@Composable
private fun test() {
    ConnectDogTheme {
        IntermediatorHomeScreen(onNotificationClick = {}, onSettingClick = {})
    }
}
