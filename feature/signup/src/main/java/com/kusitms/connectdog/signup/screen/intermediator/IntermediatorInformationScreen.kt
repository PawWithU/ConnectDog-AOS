package com.kusitms.connectdog.signup.screen.intermediator

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.kusitms.connectdog.core.designsystem.component.ConnectDogNormalButton
import com.kusitms.connectdog.core.designsystem.component.ConnectDogTextField
import com.kusitms.connectdog.core.designsystem.component.ConnectDogTopAppBar
import com.kusitms.connectdog.core.designsystem.component.TopAppBarNavigationType
import com.kusitms.connectdog.core.util.Type
import com.kusitms.connectdog.feature.signup.R
import com.kusitms.connectdog.signup.viewmodel.IntermediatorInformationViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun IntermediatorInformationScreen(
    onBackClick: () -> Unit,
    onNavigateToRegisterEmail: (Type) -> Unit,
    viewModel: IntermediatorInformationViewModel = hiltViewModel()
) {
    val focusManager = LocalFocusManager.current
    val interactionSource = remember { MutableInteractionSource() }

    Scaffold(
        topBar = {
            ConnectDogTopAppBar(
                titleRes = R.string.intermediator_signup,
                navigationType = TopAppBarNavigationType.BACK,
                onNavigationClick = onBackClick
            )
        }
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .clickable(
                    onClick = { focusManager.clearFocus() },
                    indication = null,
                    interactionSource = interactionSource
                )
        ) {
            Spacer(modifier = Modifier.height(80.dp))
            Text(
                text = "중개 회원 정보를\n입력해 주세요",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(40.dp))
            ConnectDogTextField(
                text = viewModel.name,
                onTextChanged = { viewModel.updateName(it) },
                label = "중개자명",
                placeholder = "중개자 이름, 중개 단체명 입력"
            )
            Spacer(modifier = Modifier.height(12.dp))
            ConnectDogTextField(
                text = viewModel.name,
                onTextChanged = { viewModel.updateName(it) },
                label = "중개자명",
                placeholder = "중개자 이름, 중개 단체명 입력"
            )
            Spacer(modifier = Modifier.weight(1f))
            ConnectDogNormalButton(
                content = "다음",
                onClick = { onNavigateToRegisterEmail(Type.INTERMEDIATOR) },
                modifier =
                Modifier
                    .fillMaxWidth()
                    .height(56.dp)
            )
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}
