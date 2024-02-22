package com.kusitms.connectdog.signup.screen.volunteer

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kusitms.connectdog.core.designsystem.component.ConnectDogNormalButton
import com.kusitms.connectdog.core.designsystem.component.ConnectDogOutlinedButton
import com.kusitms.connectdog.core.designsystem.component.ConnectDogTextFieldWithButton
import com.kusitms.connectdog.core.designsystem.component.ConnectDogTopAppBar
import com.kusitms.connectdog.core.designsystem.component.TopAppBarNavigationType
import com.kusitms.connectdog.core.designsystem.theme.Orange_40
import com.kusitms.connectdog.core.designsystem.theme.PetOrange
import com.kusitms.connectdog.core.designsystem.theme.Red1
import com.kusitms.connectdog.core.util.Type
import com.kusitms.connectdog.feature.signup.R
import com.kusitms.connectdog.signup.viewmodel.VolunteerProfileViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun VolunteerProfileScreen(
    onBackClick: () -> Unit,
    onNavigateToSelectProfileImage: () -> Unit,
    onNavigateToCompleteSignUp: (Type) -> Unit,
    viewModel: VolunteerProfileViewModel
) {
    val focusManager = LocalFocusManager.current
    val interactionSource = remember { MutableInteractionSource() }
    val isAvailableNickname by viewModel.isAvailableNickname.collectAsState()
    val selectedImageIndex by viewModel.selectedImageIndex.collectAsState()

    Scaffold(
        topBar = {
            ConnectDogTopAppBar(
                titleRes = R.string.volunteer_signup,
                navigationType = TopAppBarNavigationType.BACK,
                navigationIconContentDescription = "Navigation icon",
                onNavigationClick = onBackClick
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(start = 20.dp, end = 20.dp, bottom = 32.dp)
                .clickable(
                    onClick = { focusManager.clearFocus() },
                    indication = null,
                    interactionSource = interactionSource
                )
        ) {
            Spacer(modifier = Modifier.height(80.dp))
            Text(
                text = "프로필 정보를\n입력해주세요",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(40.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = painterResource(id = viewModel.getProfileImageId()),
                    contentDescription = "volunteer profile image"
                )
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
                    onClick = onNavigateToSelectProfileImage
                )
            }
            Spacer(modifier = Modifier.height(40.dp))
            ConnectDogTextFieldWithButton(
                text = viewModel.nickname,
                width = 62,
                height = 27,
                textFieldLabel = "닉네임",
                placeholder = "닉네임 입력",
                buttonLabel = "중복 확인",
                onClick = { viewModel.updateNicknameAvailability(it) },
                onTextChanged = { viewModel.updateNickname(it) },
                padding = 5,
                isError = when (isAvailableNickname) {
                    false -> true
                    else -> false
                }
            )
            Text(
                text = isAvailableNickname?.let {
                    if (it) { "사용할 수 있는 닉네임 입니다." } else { "이미 사용중인 닉네임 입니다." }
                } ?: run { "" },
                color = when (isAvailableNickname) {
                    false -> Red1
                    else -> PetOrange
                },
                fontSize = 11.sp,
                modifier = Modifier.padding(top = 4.dp, start = 8.dp)
            )
            Spacer(modifier = Modifier.weight(1f))
            ConnectDogNormalButton(
                content = "다음",
                modifier =
                Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                color = if (selectedImageIndex != -1 && isAvailableNickname == true) { PetOrange } else { Orange_40 },
                onClick = { if (selectedImageIndex != -1 && isAvailableNickname == true) onNavigateToCompleteSignUp(Type.NORMAL_VOLUNTEER) }
            )
        }
    }
}
