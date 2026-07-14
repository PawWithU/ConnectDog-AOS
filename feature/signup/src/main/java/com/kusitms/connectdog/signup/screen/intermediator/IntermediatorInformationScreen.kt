package com.kusitms.connectdog.signup.screen.intermediator

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.kusitms.connectdog.core.designsystem.component.ConnectDogNormalButton
import com.kusitms.connectdog.core.designsystem.component.ConnectDogTextField
import com.kusitms.connectdog.core.designsystem.component.ConnectDogTopAppBar
import com.kusitms.connectdog.core.designsystem.component.TopAppBarNavigationType
import com.kusitms.connectdog.feature.signup.R
import com.kusitms.connectdog.signup.viewmodel.IntermediatorInformationViewModel
import com.kusitms.connectdog.signup.viewmodel.SignUpViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun IntermediatorInformationScreen(
    onBackClick: () -> Unit,
    onNavigateToCompleteSignUp: () -> Unit,
    imeHeight: Int,
    viewModel: IntermediatorInformationViewModel = hiltViewModel(),
    signUpViewModel: SignUpViewModel,
) {
    val focusManager = LocalFocusManager.current
    val interactionSource = remember { MutableInteractionSource() }
    val scrollState = rememberScrollState()
    val context = LocalContext.current

    Scaffold(
        topBar = {
            ConnectDogTopAppBar(
                titleRes = R.string.intermediator_signup,
                navigationType = TopAppBarNavigationType.BACK,
                onNavigationClick = onBackClick,
            )
        },
    ) {
        Column(
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(horizontal = 20.dp)
                    .clickable(
                        onClick = { focusManager.clearFocus() },
                        indication = null,
                        interactionSource = interactionSource,
                    )
                    .verticalScroll(scrollState),
        ) {
            Spacer(modifier = Modifier.height(80.dp))
            Text(
                text = "모집자 프로필에 사용할\n정보를 입력해주세요 (선택)",
                style = MaterialTheme.typography.titleLarge,
            )
            Spacer(modifier = Modifier.height(40.dp))
            ConnectDogTextField(
                text = viewModel.url,
                onTextChanged = viewModel::updateUrl,
                label = "링크",
                placeholder = "모집자 정보를 보여줄 수 있는 URL 입력",
            )
            Spacer(modifier = Modifier.height(12.dp))
            ConnectDogTextField(
                text = viewModel.contact,
                onTextChanged = viewModel::updateContact,
                label = "문의 받을 연락처",
                placeholder = "문의 받을 채널과 연락처를 입력해주세요.",
                height = 144,
            )
            Spacer(modifier = Modifier.height(20.dp))
            Spacer(modifier = Modifier.weight(1f))
            ConnectDogNormalButton(
                content = "다음",
                onClick = {
                    onNavigateToCompleteSignUp()
//                    signUpViewModel.updateUrl(viewModel.url)
//                    signUpViewModel.updateContact(viewModel.contact)
//                    signUpViewModel.postIntermediatorSignUp(context)
                },
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .height(56.dp),
            )
            Spacer(modifier = Modifier.height((imeHeight + 32).dp))
        }
    }
}
