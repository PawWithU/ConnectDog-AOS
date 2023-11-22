package com.kusitms.connectdog.feature.signup.volunteer

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.kusitms.connectdog.core.designsystem.component.ConnectDogOutlinedButton
import com.kusitms.connectdog.core.designsystem.component.ConnectDogTextFieldWithButton
import com.kusitms.connectdog.core.designsystem.component.ConnectDogTopAppBar
import com.kusitms.connectdog.core.designsystem.component.TopAppBarNavigationType
import com.kusitms.connectdog.core.designsystem.theme.PetOrange
import com.kusitms.connectdog.core.util.getProfileImage
import com.kusitms.connectdog.feature.login.NormalButton

@Composable
fun ProfileScreen(navigator: NavController, viewModel: SelectProfileImageViewModel) {
    val focusManager = LocalFocusManager.current
    val interactionSource = remember { MutableInteractionSource() }

    val profileImageId = if (viewModel.selectedImageIndex.value == null) {
        com.kusitms.connectdog.core.designsystem.R.drawable.ic_circle
    } else {
        getProfileImage(viewModel.selectedImageIndex.value!!)
    }

    Box(
        modifier =
        Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(bottom = 32.dp)
            .clickable(
                onClick = { focusManager.clearFocus() },
                indication = null,
                interactionSource = interactionSource
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
        ) {
            ConnectDogTopAppBar(
                titleRes = com.kusitms.connectdog.core.designsystem.R.string.volunteer_signup,
                navigationType = TopAppBarNavigationType.BACK,
                navigationIconContentDescription = "Navigation icon",
                onNavigationClick = { navigator.popBackStack() }
            )
            Spacer(modifier = Modifier.height(32.dp))
            Text(
                text = "프로필 정보를\n입력해주세요",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 20.dp)
            )
            Spacer(modifier = Modifier.height(40.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Image(painter = painterResource(id = profileImageId), contentDescription = "")
            }
            Spacer(modifier = Modifier.height(12.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                ConnectDogOutlinedButton(
                    width = 105,
                    height = 27,
                    text = "프로필 사진 선택",
                    padding = 5,
                    onClick = { navigator.navigate("profileImageSelect") }
                )
            }
            Spacer(modifier = Modifier.height(40.dp))
            ConnectDogTextFieldWithButton(
                width = 62,
                height = 27,
                textFieldLabel = "닉네임",
                placeholder = "닉네임 입력",
                buttonLabel = "중복 확인",
                padding = 5
            ) {
            }
            Text(
                text = "사용할 수 있는 닉네임입니다.",
                color = PetOrange,
                fontSize = 10.sp,
                modifier = Modifier.padding(start = 28.dp, top = 4.dp)
            )
        }
        NormalButton(
            content = "다음",
            modifier =
            Modifier
                .fillMaxWidth()
                .height(56.dp)
                .align(Alignment.BottomCenter)
                .padding(horizontal = 20.dp),
            onClick = {
                navigator.navigate("registerEmail")
            }
        )
    }
}
