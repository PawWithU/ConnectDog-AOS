package com.kusitms.connectdog.feature.home.screen

import android.annotation.SuppressLint
import android.util.Log
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
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.kusitms.connectdog.core.data.api.model.volunteer.NoticeDetailResponseItem
import com.kusitms.connectdog.core.designsystem.component.ConnectDogInformationCard
import com.kusitms.connectdog.core.designsystem.component.ConnectDogNormalButton
import com.kusitms.connectdog.core.designsystem.component.ConnectDogTag
import com.kusitms.connectdog.core.designsystem.component.ConnectDogTopAppBar
import com.kusitms.connectdog.core.designsystem.component.DetailInfo
import com.kusitms.connectdog.core.designsystem.component.NetworkImage
import com.kusitms.connectdog.core.designsystem.component.TopAppBarNavigationType
import com.kusitms.connectdog.core.designsystem.theme.Gray2
import com.kusitms.connectdog.core.designsystem.theme.Gray5
import com.kusitms.connectdog.core.designsystem.theme.Gray7
import com.kusitms.connectdog.core.designsystem.theme.PetOrange
import com.kusitms.connectdog.feature.home.DetailViewModel
import com.kusitms.connectdog.feature.home.R

private const val TAG = "DetailScreen"

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun DetailScreen(
    onBackClick: () -> Unit,
    onCertificationClick: (Long) -> Unit,
    onIntermediatorProfileClick: (Long) -> Unit,
    postId: Long,
    viewModel: DetailViewModel = hiltViewModel()
) {
    val detail by viewModel.detail.observeAsState(null)

    LaunchedEffect(postId) {
        viewModel.initNoticeDetail(postId)
    }

    Scaffold(
        topBar = {
            DetailTopAppBar(
                onBackClick = onBackClick,
                onShareClick = {}
            )
        },
        bottomBar = {
            detail?.isBookmark
                ?.let {
                    BottomButton(
                        isBookmark = it,
                        onSaveClick = { viewModel.postBookmark(postId) },
                        onDeleteClick = { viewModel.deleteBookmark(postId) },
                        onClick = { onCertificationClick(postId) }
                    )
                }
        }
    ) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
        ) {
            if (detail != null) {
                Spacer(modifier = Modifier.height(48.dp))
                NetworkImage(
                    imageUrl = detail!!.mainImage,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(250.dp)
                )
                Content(detail!!)
                DogInfo(detail!!)
                IntermediatorInfo(detail!!, onIntermediatorProfileClick)
            }
        }
    }
}

@Composable
private fun DetailTopAppBar(
    onBackClick: () -> Unit,
    onShareClick: () -> Unit
) {
    ConnectDogTopAppBar(
        titleRes = null,
        navigationType = TopAppBarNavigationType.BACK,
        navigationIconContentDescription = "Navigation icon home",
        onNavigationClick = onBackClick,
        actionButtons = {
            IconButton(onClick = onShareClick) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_share),
                    contentDescription = "Navigate to Search"
                )
            }
        }
    )
}

@Composable
fun BookmarkButton(
    isBookmark: Boolean,
    onSaveClick: () -> Unit,
    onDeleteClick: () -> Unit
) {
    var isActive by remember { mutableStateOf(isBookmark) }
    val shape = RoundedCornerShape(12.dp)
    val (imageResource, setImageResource) = remember { mutableIntStateOf(if (isActive) R.drawable.ic_bookmark_filled else R.drawable.ic_bookmark) }
    val (borderColor, setBorderColor) = remember { mutableStateOf(if (isActive) PetOrange else Gray5) }
    val imagePainter: Painter = painterResource(id = imageResource)

    Button(
        onClick = {
            isActive = !isActive
            setImageResource(if (isActive) R.drawable.ic_bookmark_filled else R.drawable.ic_bookmark)
            setBorderColor(if (isActive) PetOrange else Gray5)
            Log.d("testsss", isActive.toString())
            if (isActive) {
                onSaveClick()
            } else {
                onDeleteClick()
            }
        },
        contentPadding = PaddingValues(vertical = 16.dp),
        shape = shape,
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
            contentDescription = null
        )
    }
}

@Composable
fun Content(detail: NoticeDetailResponseItem) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(all = 24.dp)
    ) {
        ConnectDogTag(detail.postStatus)
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "${detail.departureLoc} → ${detail.arrivalLoc}",
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp
        )
        Spacer(modifier = Modifier.height(20.dp))
        DetailInfo("일정", "${detail.startDate} ~ ${detail.endDate}")
        Spacer(modifier = Modifier.height(8.dp))
        DetailInfo("픽업시간", detail.pickUpTime)
        Spacer(modifier = Modifier.height(8.dp))
        DetailInfo(
            "켄넬 여부",
            if (detail.isKennel) {
                stringResource(id = com.kusitms.connectdog.core.designsystem.R.string.has_kennel)
            } else {
                stringResource(id = com.kusitms.connectdog.core.designsystem.R.string.has_not_kennel)
            }
        )
        Spacer(modifier = Modifier.height(40.dp))
        Text(
            text = detail.content,
            fontSize = 14.sp,
            fontWeight = FontWeight.Normal
        )
    }
    Divider(
        Modifier
            .height(8.dp)
            .fillMaxWidth(),
        color = Gray7
    )
}

@Composable
fun DogInfo(detail: NoticeDetailResponseItem) {
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
        DetailInfo("이름", detail.dogName)
        Spacer(modifier = Modifier.height(8.dp))
        DetailInfo("사이즈", detail.dogSize)
        Spacer(modifier = Modifier.height(8.dp))
        DetailInfo("성별", detail.dogGender)
        Spacer(modifier = Modifier.height(8.dp))
        DetailInfo("몸무게", "${detail.dogWeight}kg")
        Spacer(modifier = Modifier.height(20.dp))
        ConnectDogInformationCard(title = "특이사항", content = detail.specifics ?: "없습니다.")
    }
    Divider(
        Modifier
            .height(8.dp)
            .fillMaxWidth(),
        color = Gray7
    )
}

@Composable
fun IntermediatorInfo(
    detail: NoticeDetailResponseItem,
    onClick: (Long) -> Unit
) {
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
                imageUrl = detail.intermediaryProfileImage,
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
                text = detail.intermediaryName,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )
            ProfileButton(
                modifier = Modifier
                    .width(100.dp)
                    .height(34.dp)
                    .align(Alignment.CenterVertically),
                onClick = { onClick(detail.intermediaryId.toLong()) }
            )
        }
    }
}

@Composable
private fun BottomButton(
    isBookmark: Boolean,
    onSaveClick: () -> Unit,
    onDeleteClick: () -> Unit,
    onClick: () -> Unit
) {
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
            BookmarkButton(isBookmark, onSaveClick, onDeleteClick)
            Spacer(modifier = Modifier.width(10.dp))
            ConnectDogNormalButton(content = "신청하기", onClick = onClick)
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
