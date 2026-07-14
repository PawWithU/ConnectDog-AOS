package com.kusitms.connectdog.feature.intermediator.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.kusitms.connectdog.core.designsystem.component.ConnectDogBottomButton
import com.kusitms.connectdog.core.designsystem.component.ConnectDogOutlinedButton
import com.kusitms.connectdog.core.designsystem.component.ConnectDogTextField
import com.kusitms.connectdog.core.designsystem.component.ConnectDogTopAppBar
import com.kusitms.connectdog.core.designsystem.component.NetworkImage
import com.kusitms.connectdog.core.designsystem.component.TopAppBarNavigationType
import com.kusitms.connectdog.core.designsystem.theme.Gray7
import com.kusitms.connectdog.feature.intermediator.R

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun InterProfileEditScreen(
    profileImage: String,
    onBackClick: () -> Unit,
    imeHeight: Int,
) {
    Scaffold(
        topBar = {
            ConnectDogTopAppBar(
                titleRes = R.string.inter_profile_edit,
                navigationType = TopAppBarNavigationType.BACK,
                navigationIconContentDescription = "Navigation icon",
                onNavigationClick = onBackClick,
            )
        },
    ) {
        Content(
            profileImage = profileImage,
            imeHeight = imeHeight,
        )
    }
}

@Composable
private fun Content(
    profileImage: String,
    imeHeight: Int,
) {
    val scrollState = rememberScrollState()

    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.height(80.dp))
        NetworkImage(
            imageUrl = profileImage,
            modifier = Modifier.size(80.dp),
            placeholder = painterResource(id = R.drawable.ic_default_intermediator),
        )
        Spacer(modifier = Modifier.height(12.dp))
        ConnectDogOutlinedButton(
            width = 115,
            height = 26,
            text = "프로필 이미지 변경",
            padding = 10,
            onClick = { },
        )
        First()
        Divider(thickness = 8.dp, color = Gray7)
        Second()
        Spacer(
            modifier =
                Modifier
                    .height((imeHeight + 32).dp)
                    .padding(horizontal = 20.dp),
        )
    }
}

@Composable
private fun First() {
    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp),
    ) {
        Spacer(modifier = Modifier.height(40.dp))
        ConnectDogTextField(text = "", onTextChanged = {}, label = "", placeholder = "")
        Spacer(modifier = Modifier.height(12.dp))
        ConnectDogTextField(text = "", onTextChanged = {}, label = "", placeholder = "")
        Spacer(modifier = Modifier.height(12.dp))
        ConnectDogTextField(text = "", onTextChanged = {}, label = "", placeholder = "")
        Spacer(modifier = Modifier.height(32.dp))
    }
}

@Composable
private fun Second() {
    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp),
    ) {
        Spacer(modifier = Modifier.height(32.dp))
        Text(text = "문의 받을 연락처를 입력해주세요")
        Spacer(modifier = Modifier.height(12.dp))
        ConnectDogTextField(text = "", onTextChanged = {}, label = "", placeholder = "")
        Spacer(modifier = Modifier.height(40.dp))
        Text(text = "문의 받을 연락처를 입력해주세요")
        Spacer(modifier = Modifier.height(12.dp))
        ConnectDogTextField(text = "", onTextChanged = {}, label = "", placeholder = "")
        Spacer(modifier = Modifier.height(24.dp))
        ConnectDogBottomButton(onClick = { /*TODO*/ }, content = "완료")
    }
}
