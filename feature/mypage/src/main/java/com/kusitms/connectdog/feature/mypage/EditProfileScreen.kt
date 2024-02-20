package com.kusitms.connectdog.feature.mypage

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kusitms.connectdog.core.designsystem.component.ConnectDogNormalButton
import com.kusitms.connectdog.core.designsystem.component.ConnectDogOutlinedButton
import com.kusitms.connectdog.core.designsystem.component.ConnectDogTextFieldWithButton
import com.kusitms.connectdog.core.designsystem.component.ConnectDogTopAppBar
import com.kusitms.connectdog.core.designsystem.component.TopAppBarNavigationType
import com.kusitms.connectdog.core.designsystem.theme.ConnectDogTheme
import com.kusitms.connectdog.core.util.getProfileImageId

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun EditProfileScreen(
    onBackClick: () -> Unit
) {
    Scaffold(
        topBar = {
            ConnectDogTopAppBar(
                titleRes = R.string.edit_profile,
                navigationType = TopAppBarNavigationType.BACK,
                navigationIconContentDescription = null,
                onNavigationClick = onBackClick
            )
        }
    ) {
        Content()
    }
}

@Composable
private fun Content() {
    val focusManager = LocalFocusManager.current
    val interactionSource = remember { MutableInteractionSource() }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 20.dp, end = 20.dp, top = 80.dp, bottom = 32.dp)
            .clickable(
                onClick = { focusManager.clearFocus() },
                indication = null,
                interactionSource = interactionSource
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(painter = painterResource(id = getProfileImageId(0)), contentDescription = null, modifier = Modifier.size(100.dp))
        Spacer(modifier = Modifier.height(12.dp))
        ConnectDogOutlinedButton(
            width = 110,
            height = 26,
            text = "프로필 이미지 변경",
            padding = 3,
            onClick = { /*TODO*/ }
        )
        Spacer(modifier = Modifier.height(40.dp))
        ConnectDogTextFieldWithButton(
            width = 62,
            height = 27,
            textFieldLabel = "닉네임",
            placeholder = "닉네임 입력",
            buttonLabel = "중복 확인",
            padding = 5
        )
        Spacer(modifier = Modifier.weight(1f))
        ConnectDogNormalButton(content = "완료")
    }
}

@Preview
@Composable
private fun test() {
    ConnectDogTheme {
        EditProfileScreen(onBackClick = {})
    }
}
