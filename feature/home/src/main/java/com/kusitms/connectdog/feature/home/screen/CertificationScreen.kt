package com.kusitms.connectdog.feature.home.screen

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kusitms.connectdog.core.designsystem.R
import com.kusitms.connectdog.core.designsystem.component.ConnectDogTextFieldWithButton
import com.kusitms.connectdog.core.designsystem.component.ConnectDogTopAppBar
import com.kusitms.connectdog.core.designsystem.component.NormalButton
import com.kusitms.connectdog.core.designsystem.component.NormalTextField
import com.kusitms.connectdog.core.designsystem.component.TopAppBarNavigationType
import com.kusitms.connectdog.core.designsystem.theme.Orange40
import com.kusitms.connectdog.feature.home.ApplyViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun CertificationScreen(
    onBackClick: () -> Unit,
    onApplyClick: (Long) -> Unit,
    onSendMessageClick: (String) -> Unit,
    onVerifyCodeClick: (String) -> Boolean,
    postId: Long,
    applyViewModel: ApplyViewModel = hiltViewModel()
) {
    Scaffold(
        topBar = {
            ConnectDogTopAppBar(
                titleRes = R.string.apply_volunter,
                navigationType = TopAppBarNavigationType.BACK,
                navigationIconContentDescription = "Navigation icon",
                onNavigationClick = onBackClick
            )
        }
    ) {
        Content(onApplyClick = { onApplyClick(it) }, postId, onSendMessageClick, onVerifyCodeClick, applyViewModel)
    }
}

@Composable
private fun Content(
    onApplyClick: (Long) -> Unit,
    postId: Long,
    onSendMessageClick: (String) -> Unit,
    onVerifyCodeClick: (String) -> Boolean,
    viewModel: ApplyViewModel
) {
    val focusManager = LocalFocusManager.current
    val interactionSource = remember { MutableInteractionSource() }
    val (name, onNameChanged) = remember { mutableStateOf("") }
    val isCertified by viewModel.isCertified.observeAsState(false)

    Log.d("asdfsd", "${name.isNotEmpty()} + name")

    val buttonColor = if (name.isNotEmpty() && isCertified) {
        MaterialTheme.colorScheme.primary
    } else {
        Orange40
    }

    Column(
        modifier = Modifier
            .padding(horizontal = 20.dp, vertical = 32.dp)
            .clickable(
                onClick = { focusManager.clearFocus() },
                indication = null,
                interactionSource = interactionSource
            )
    ) {
        Spacer(modifier = Modifier.height(48.dp))
        Text(
            text = "이동봉사를 신청하려면,\n이름과 휴대폰 번호 인증이 필요해요!",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(40.dp))
        NormalTextField(
            text = name,
            label = "이름",
            placeholder = "이름 입력",
            keyboardType = KeyboardType.Text,
            onTextChanged = onNameChanged
        )
        Spacer(modifier = Modifier.height(12.dp))
        ConnectDogTextFieldWithButton(
            width = 62,
            height = 27,
            textFieldLabel = "휴대폰 번호",
            placeholder = "'-'빼고 입력",
            buttonLabel = "인증 요청",
            keyboardType = KeyboardType.Number,
            padding = 5,
            onClick = {
                onSendMessageClick(it)
            }
        )
        Spacer(modifier = Modifier.height(12.dp))
        ConnectDogTextFieldWithButton(
            width = 62,
            height = 27,
            textFieldLabel = "인증번호",
            placeholder = "숫자 6자리",
            buttonLabel = "인증 확인",
            keyboardType = KeyboardType.Number,
            padding = 5,
            onClick = {
                onVerifyCodeClick(it)
                viewModel.updateIsCertified(true)
            }
        )
        Spacer(modifier = Modifier.weight(1f))
        NormalButton(
            content = "다음",
            color = buttonColor,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            onClick = { if (name.isNotEmpty() && isCertified) onApplyClick(postId) }
        )
    }
}

// @Preview
// @Composable
// fun test3() {
//    ConnectDogTheme {
//        ApplyScreen()
//    }
// }
