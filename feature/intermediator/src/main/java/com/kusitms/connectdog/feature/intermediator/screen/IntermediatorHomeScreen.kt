package com.kusitms.connectdog.feature.intermediator.screen

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.kusitms.connectdog.core.designsystem.component.ConnectDogIntermediatorTopAppBar
import com.kusitms.connectdog.core.designsystem.component.NetworkImage
import com.kusitms.connectdog.core.designsystem.theme.Brown5
import com.kusitms.connectdog.core.designsystem.theme.ConnectDogTheme
import com.kusitms.connectdog.core.designsystem.theme.Gray2
import com.kusitms.connectdog.core.designsystem.theme.PetOrange
import com.kusitms.connectdog.core.designsystem.theme.Typography
import com.kusitms.connectdog.feature.intermediator.InterHomeViewModel
import com.kusitms.connectdog.feature.intermediator.R

val imageList = listOf(
    R.drawable.ic_recruit,
    R.drawable.ic_waiting,
    R.drawable.ic_progress,
    R.drawable.ic_complete
)

internal val titleList = listOf(
    R.string.recruit,
    R.string.waiting,
    R.string.progress,
    R.string.complete
)

internal data class CardItem(
    @DrawableRes val image: Int,
    @StringRes val title: Int,
    val value: Int
)

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun IntermediatorHomeScreen(
    onNotificationClick: () -> Unit,
    onSettingClick: () -> Unit,
    onDataClick: (Int) -> Unit,
    viewModel: InterHomeViewModel = hiltViewModel()
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
            onClick = onDataClick
        )
    }
}

@Composable
private fun Content(
    viewModel: InterHomeViewModel,
    onClick: (Int) -> Unit
) {
    val recruitingCount = viewModel.recruitingCount.collectAsState()
    val waitingCount = viewModel.waitingCount.collectAsState()
    val progressingCount = viewModel.progressingCount.collectAsState()
    val completedCount = viewModel.completedCount.collectAsState()
    val cnt = listOf(recruitingCount.value, waitingCount.value, progressingCount.value, completedCount.value)
    val list = cnt.mapIndexedNotNull { index, value ->
        value?.let {
            CardItem(
                image = imageList[index],
                title = titleList[index],
                value = value
            )
        }
    }
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Spacer(modifier = Modifier.height(48.dp))
        ProfileCard(viewModel)
        ManageBoard(list) { onClick(it) }
    }
}

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
private fun ProfileCard(
    viewModel: InterHomeViewModel
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(180.dp)
            .background(MaterialTheme.colorScheme.primary)
    ) {
        Row(
            modifier = Modifier
                .padding(start = 20.dp, end = 20.dp, bottom = 20.dp)
                .align(Alignment.Center)
        ) {
            Column {
                NetworkImage(
                    imageUrl = viewModel.profileImage.value,
                    modifier = Modifier.size(80.dp),
                    placeholder = painterResource(id = R.drawable.ic_default_intermediator)
                )
                Spacer(modifier = Modifier.height(18.dp))
                Text(
                    text = viewModel.intermediaryName.value,
                    style = MaterialTheme.typography.titleSmall,
                    color = Color.White,
                    fontSize = 20.sp
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = viewModel.intro.value,
                    fontSize = 11.sp,
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
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }
        }
    }
}

@Composable
private fun ManageBoard(
    list: List<CardItem>,
    onClick: (Int) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Brown5)
    ) {
        Spacer(modifier = Modifier.height(20.dp))
        Row {
            Text(
                "전체",
                modifier = Modifier.padding(start = 20.dp),
                style = MaterialTheme.typography.titleLarge,
                fontSize = 18.sp
            )
            Text(
                text = "${list.sumOf { it.value }}건",
                modifier = Modifier.padding(start = 5.dp),
                style = MaterialTheme.typography.titleLarge,
                color = PetOrange,
                fontSize = 18.sp
            )
        }
        Spacer(modifier = Modifier.height(20.dp))
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            horizontalArrangement = Arrangement.spacedBy(20.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            itemsIndexed(list) { index, item ->
                ManageCard(
                    title = item.title,
                    painter = item.image,
                    value = item.value
                ) { onClick(index) }
            }
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
    val context = LocalContext.current
    Button(
        onClick = { Toast.makeText(context, "아직 준비중인 기능입니다.", Toast.LENGTH_SHORT).show() },
        contentPadding = PaddingValues(vertical = 15.dp),
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .width(117.dp)
            .height(44.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = Color.White
        )
    ) {
        Icon(
            imageVector = Icons.Outlined.Add,
            contentDescription = null,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(6.dp))
        Text(text = "공고 등록", color = Color.White, style = Typography.titleSmall, fontSize = 12.sp)
    }
}

@Composable
private fun ManageCard(
    @StringRes title: Int,
    @DrawableRes painter: Int,
    value: Int,
    onClick: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        modifier = Modifier
            .width(170.dp)
            .height(200.dp)
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

@Preview
@Composable
private fun test() {
    ConnectDogTheme {
        IntermediatorHomeScreen(onNotificationClick = {}, onSettingClick = {}, {})
    }
}
