package com.kusitms.connectdog.feature.mypage.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.kusitms.connectdog.core.designsystem.component.ConnectDogNormalButton
import com.kusitms.connectdog.core.designsystem.component.ConnectDogOutlinedButton
import com.kusitms.connectdog.core.designsystem.component.ConnectDogTextFieldWithButton
import com.kusitms.connectdog.core.designsystem.component.ConnectDogTopAppBar
import com.kusitms.connectdog.core.designsystem.component.TopAppBarNavigationType
import com.kusitms.connectdog.core.designsystem.theme.ConnectDogTheme
import com.kusitms.connectdog.core.designsystem.theme.Gray5
import com.kusitms.connectdog.core.designsystem.theme.PetOrange
import com.kusitms.connectdog.core.designsystem.theme.Red1
import com.kusitms.connectdog.core.util.getProfileImageId
import com.kusitms.connectdog.feature.mypage.R
import com.kusitms.connectdog.feature.mypage.viewmodel.EditProfileViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun EditProfileScreen(
    onBackClick: () -> Unit,
    onEditProfileImageClick: () -> Unit,
    viewModel: EditProfileViewModel
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
        Content(onEditProfileImageClick, onBackClick, viewModel)
    }
}

@Composable
private fun Content(
    onEditProfileImageClick: () -> Unit,
    onBackClick: () -> Unit,
    viewModel: EditProfileViewModel
) {
    val focusManager = LocalFocusManager.current
    val interactionSource = remember { MutableInteractionSource() }
    val nickname by viewModel.nickname.collectAsState()
    val profileImageIndex by viewModel.selectedImageIndex.collectAsState()
    val isDuplicate by viewModel.isDuplicatedNickname.collectAsState()

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
        Image(
            painter = painterResource(id = getProfileImageId(profileImageIndex)),
            contentDescription = null,
            modifier = Modifier.size(100.dp)
        )
        Spacer(modifier = Modifier.height(12.dp))
        ConnectDogOutlinedButton(
            width = 110,
            height = 26,
            text = "프로필 이미지 변경",
            padding = 3,
            onClick = onEditProfileImageClick
        )
        Spacer(modifier = Modifier.height(40.dp))
        ConnectDogTextFieldWithButton(
            text = nickname,
            width = 62,
            height = 27,
            textFieldLabel = "닉네임",
            placeholder = "닉네임 입력",
            buttonLabel = "중복 확인",
            onTextChanged = { viewModel.updateNickname(it) },
            onClick = { viewModel.updateNicknameAvailability() },
            borderColor = when (isDuplicate) {
                true -> MaterialTheme.colorScheme.error
                false -> PetOrange
                else -> Gray5
            },
            isError = isDuplicate ?: false,
            padding = 5
        )
        Spacer(modifier = Modifier.height(4.dp))
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = isDuplicate?.let {
                    if (it) { "이미 사용중인 닉네임 입니다." } else { "사용할 수 있는 닉네임 입니다." }
                } ?: run { "" },
                color = when (isDuplicate) {
                    false -> PetOrange
                    else -> Red1
                },
                fontSize = 11.sp,
                modifier = Modifier.padding(top = 4.dp, start = 8.dp)
            )
            Spacer(modifier = Modifier.weight(1f))
        }
        Spacer(modifier = Modifier.weight(1f))
        ConnectDogNormalButton(
            content = "완료",
            onClick = {
                viewModel.updateUserInfo()
                onBackClick()
            }
        )
    }
}

@Preview
@Composable
private fun EditProfileScreenPreview() {
    ConnectDogTheme {
        EditProfileScreen(onBackClick = {}, onEditProfileImageClick = {}, hiltViewModel())
    }
}
