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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import com.kusitms.connectdog.core.designsystem.component.ConnectDogBottomButton
import com.kusitms.connectdog.core.designsystem.component.ConnectDogNormalButton
import com.kusitms.connectdog.core.designsystem.component.ConnectDogOutlinedButton
import com.kusitms.connectdog.core.designsystem.component.ConnectDogTextField
import com.kusitms.connectdog.core.designsystem.component.ConnectDogTopAppBar
import com.kusitms.connectdog.core.designsystem.component.TopAppBarNavigationType
import com.kusitms.connectdog.core.designsystem.theme.Red1
import com.kusitms.connectdog.core.util.UserType
import com.kusitms.connectdog.core.util.getProfileImageId
import com.kusitms.connectdog.feature.signup.R
import com.kusitms.connectdog.signup.state.SignUpSideEffect
import com.kusitms.connectdog.signup.viewmodel.SignUpViewModel
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun VolunteerProfileScreen(
    onBackClick: () -> Unit,
    onNavigateToSelectProfileImage: () -> Unit,
    onNavigateToCompleteSignUp: () -> Unit,
    imeHeight: Int,
    viewModel: SignUpViewModel
) {
    viewModel.collectSideEffect {
        when(it) {
            is SignUpSideEffect.NavigateToSignUpComplete -> onNavigateToCompleteSignUp()
            else -> Unit
        }
    }

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
        Content(
            viewModel = viewModel,
            onNavigateToSelectProfileImage = onNavigateToSelectProfileImage,
            imeHeight = imeHeight
        )
    }
}

@Composable
private fun Content(
    viewModel: SignUpViewModel,
    onNavigateToSelectProfileImage: () -> Unit,
    imeHeight: Int,
) {
    val focusManager = LocalFocusManager.current
    val interactionSource = remember { MutableInteractionSource() }
    val uiState by viewModel.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(horizontal = 20.dp)
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
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = getProfileImageId(uiState.profileImageId)),
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
        ConnectDogTextField(
            text = uiState.nickname,
            label = "닉네임",
            placeholder = "닉네임 입력",
            onTextChanged = viewModel::onNickNameChanged,
            isError = uiState.isDuplicatedNickname == true
        )
        if(uiState.isDuplicatedNickname == true) {
            Text(
                modifier = Modifier.padding(top = 4.dp, start = 8.dp),
                text = uiState.nickNameErrorMessage,
                color = Red1,
                fontSize = 11.sp,
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        ConnectDogBottomButton(
            content = "가입완료",
            onClick = viewModel::onCheckNicknameDuplicationButtonClick,
            enabled = uiState.enableNicknameDuplication
        )
        Spacer(modifier = Modifier.height((imeHeight + 32).dp))
    }
}
