package com.kusitms.connectdog.feature.home.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kusitms.connectdog.core.designsystem.component.ConnectDogBottomButton
import com.kusitms.connectdog.core.designsystem.component.ConnectDogTag
import com.kusitms.connectdog.core.designsystem.component.ConnectDogTopAppBar
import com.kusitms.connectdog.core.designsystem.component.NetworkImage
import com.kusitms.connectdog.core.designsystem.component.TopAppBarNavigationType
import com.kusitms.connectdog.core.designsystem.theme.Gray1
import com.kusitms.connectdog.core.designsystem.theme.Gray2
import com.kusitms.connectdog.core.designsystem.theme.Gray3
import com.kusitms.connectdog.core.designsystem.theme.Gray5
import com.kusitms.connectdog.core.designsystem.theme.Gray7
import com.kusitms.connectdog.core.designsystem.theme.PetOrange
import com.kusitms.connectdog.core.designsystem.theme.Typography
import com.kusitms.connectdog.feature.home.R

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
internal fun DetailScreen(
    onBackClick: () -> Unit,
    imageUrl: String = "",
    title: String = "인천 댕댕구 -> 서울 댕댕구",
    content: String = "내용",
    onCertificationClick: () -> Unit
) {
    Scaffold(
        topBar = {
            ConnectDogTopAppBar(
                titleRes = null,
                navigationType = TopAppBarNavigationType.BACK,
                navigationIconContentDescription = "Navigation icon",
                onNavigationClick = onBackClick
            )
        },
        bottomBar = {
            BottomButton(onCertificationClick)
        }
    ) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(modifier = Modifier.height(48.dp))
            NetworkImage(
                imageUrl = "https://dimg.donga.com/wps/NEWS/IMAGE/2022/01/28/111500268.2.jpg",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
            )
            Content(title, content)
            DogInfo()
            IntermediatorInfo()
        }
    }
}


@Composable
fun NormalButton(
    content: String,
    color: Color = PetOrange,
    onClick: () -> Unit = {},
    textColor: Color = MaterialTheme.colorScheme.onPrimary,
    modifier: Modifier = Modifier
        .fillMaxWidth()
        .height(56.dp)
) {
    ConnectDogBottomButton(
        onClick = onClick,
        content = content,
        color = color,
        modifier = modifier,
        textColor = textColor
    )
}

@Composable
fun BookmarkButton(onClick: () -> Unit = {}) {
    val shape = RoundedCornerShape(12.dp)
    val (imageResource, setImageResource) = remember { mutableIntStateOf(R.drawable.ic_bookmark) }
    val (borderColor, setBorderColor) = remember { mutableStateOf(Gray5) }
    val imagePainter: Painter = painterResource(id = imageResource)

    Button(
        onClick = {
            setImageResource(
                if (imageResource == R.drawable.ic_bookmark) R.drawable.ic_bookmark_filled else R.drawable.ic_bookmark
            )
            setBorderColor(
                if (borderColor == Gray5) PetOrange else Gray5
            )
        },
        contentPadding = PaddingValues(vertical = 16.dp),
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .width(56.dp)
            .height(56.dp)
            .border(
                width = 1.dp,
                color = borderColor,
                shape = shape
            ),
        colors = ButtonDefaults.buttonColors(containerColor = Color.White, contentColor = Gray2)
    ) {
        Image(
            painter = imagePainter,
            contentDescription = null,
        )
    }
}

@Composable
fun Content(title: String, content: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(all = 24.dp)
    ) {
        ConnectDogTag("모집중")
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = title,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp
        )
        Spacer(modifier = Modifier.height(20.dp))
        DetailInfo("일정", "23.08.01(수)")
        Spacer(modifier = Modifier.height(8.dp))
        DetailInfo("픽업시간", "23.08.01(수)")
        Spacer(modifier = Modifier.height(8.dp))
        DetailInfo("켄넬 여부", "23.08.01(수)")
        Spacer(modifier = Modifier.height(40.dp))
        Text(
            text = content,
            fontSize = 14.sp,
            fontWeight = FontWeight.Normal
        )
    }
    Divider(
        Modifier
            .height(8.dp)
            .fillMaxWidth(), color = Gray7)
}

@Composable
fun DetailInfo(
    title: String,
    content: String
) {
    Row {
        Text(
            text = title,
            modifier = Modifier.width(80.dp),
            color = Gray3,
            fontSize = 14.sp
        )
        Text(
            text = content,
            fontWeight = FontWeight.Medium,
            color = Gray1,
            fontSize = 14.sp
        )
    }
}

@Composable
fun DogInfo() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(all = 24.dp)
    ) {
        Text(
            text = "강아지 정보",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(20.dp))
        DetailInfo("이름", "밍밍이")
        Spacer(modifier = Modifier.height(8.dp))
        DetailInfo("사이즈", "밍밍이")
        Spacer(modifier = Modifier.height(8.dp))
        DetailInfo("성별", "밍밍이")
        Spacer(modifier = Modifier.height(8.dp))
        DetailInfo("몸무게", "밍밍이")
        Spacer(modifier = Modifier.height(20.dp))
        Significant()
    }
    Divider(
        Modifier
            .height(8.dp)
            .fillMaxWidth(), color = Gray7)
}

@Composable
fun Significant() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(4.dp))
            .background(Gray7)
            .padding(all = 10.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "특이사항",
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
            )
            Spacer(modifier = Modifier.height(5.dp))
            Text(
                text = "특이사항",
                fontSize = 14.sp,
                color = Gray3
            )
        }
    }
}

@Composable
fun IntermediatorInfo() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 24.dp, end = 24.dp, top = 24.dp, bottom = 112.dp)
    ) {
        Text(
            text = "이동봉사 중개",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(20.dp))
        Row(
            modifier = Modifier
                .height(40.dp)
                .fillMaxWidth()
        ) {
            NetworkImage(
                imageUrl = "https://png.pngtree.com/png-clipart/20201029/ourlarge/pngtree-circle-clipart-orange-circle-png-image_2381942.jpg",
                modifier = Modifier
                    .width(40.dp)
                    .height(40.dp)
                    .align(Alignment.CenterVertically)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                modifier = Modifier
                    .width(158.dp)
                    .align(Alignment.CenterVertically)
                    .weight(1f),
                text = "이동봉사 단체 이름",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
            )
            ProfileButton(
                modifier = Modifier
                    .width(100.dp)
                    .height(34.dp)
                    .align(Alignment.CenterVertically))
        }
    }
}

@Composable
private fun BottomButton(onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(112.dp)
            .padding(horizontal = 20.dp)
            .background(Color.White)
    ) {
        Row(
            modifier = Modifier.align(Alignment.Center)
        ) {
            BookmarkButton()
            Spacer(modifier = Modifier.width(10.dp))
            NormalButton(content = "신청하기", onClick = onClick)
        }
    }
}

@Composable
fun ProfileButton(onClick: () -> Unit = {}, modifier: Modifier) {
    val shape = RoundedCornerShape(8.dp)
    Button(
        onClick = onClick,
        shape = shape,
        modifier = modifier,
        colors = ButtonDefaults.outlinedButtonColors(
            contentColor = Gray2
        ),
        border = BorderStroke(1.dp, Gray5),
        contentPadding = PaddingValues(all = 8.dp)
    ) {
        Text(
            text = "프로필 바로가기",
            fontSize = 12.sp,
            fontWeight = FontWeight.SemiBold,
            color = Gray2 // Set the text color here
        )
    }
}