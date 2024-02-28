package com.kusitms.connectdog.feature.home.screen

import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kusitms.connectdog.core.designsystem.R
import com.kusitms.connectdog.core.designsystem.component.ConnectDogNormalButton
import com.kusitms.connectdog.core.designsystem.component.ConnectDogTextField
import com.kusitms.connectdog.core.designsystem.component.ConnectDogTextFieldWithButton
import com.kusitms.connectdog.core.designsystem.component.ConnectDogTopAppBar
import com.kusitms.connectdog.core.designsystem.component.TopAppBarNavigationType
import com.kusitms.connectdog.core.designsystem.theme.Orange40
import com.kusitms.connectdog.core.designsystem.theme.PetOrange
import com.kusitms.connectdog.feature.home.ApplyViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun CertificationScreen(
    onBackClick: () -> Unit,
    onApplyClick: (Long) -> Unit,
    onSendMessageClick: (String) -> Unit,
    onVerifyCodeClick: (String, (Boolean) -> Unit) -> Unit,
    postId: Long,
    imeHeight: Int,
    viewModel: ApplyViewModel
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
        Content(
            onApplyClick = { onApplyClick(it) },
            postId = postId,
            onSendMessageClick = onSendMessageClick,
            onVerifyCodeClick = onVerifyCodeClick,
            viewModel = viewModel,
            imeHeight = imeHeight
        )
    }
}

@Composable
private fun Content(
    imeHeight: Int,
    postId: Long,
    onApplyClick: (Long) -> Unit,
    onSendMessageClick: (String) -> Unit,
    onVerifyCodeClick: (String, (Boolean) -> Unit) -> Unit,
    viewModel: ApplyViewModel
) {
    val focusManager = LocalFocusManager.current
    val interactionSource = remember { MutableInteractionSource() }
    val context = LocalContext.current
    val isSendNumber by remember { viewModel.isSendNumber }.collectAsState()
    val isCertified by remember { viewModel.isCertified }.collectAsState()

    Column(
        modifier = Modifier
            .padding(start = 20.dp, end = 20.dp, top = 32.dp)
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
        ConnectDogTextField(
            text = viewModel.name,
            label = "이름",
            placeholder = "이름 입력",
            keyboardType = KeyboardType.Text,
            onTextChanged = { viewModel.updateName(it) }
        )
        Spacer(modifier = Modifier.height(12.dp))
        ConnectDogTextFieldWithButton(
            text = viewModel.phoneNumber,
            width = 62,
            height = 27,
            textFieldLabel = "휴대폰 번호",
            placeholder = "'-'빼고 입력",
            buttonLabel = "인증 요청",
            keyboardType = KeyboardType.Number,
            padding = 5,
            onTextChanged = { viewModel.updatePhoneNumber(it) },
            onClick = {
                if (viewModel.phoneNumber.isEmpty()) {
                    Toast.makeText(context, "휴대폰 번호를 입력해주세요", Toast.LENGTH_SHORT).show()
                } else {
                    onSendMessageClick(it)
                    viewModel.updateIsSendNumber(true)
                }
            }
        )
        Spacer(modifier = Modifier.height(12.dp))
        ConnectDogTextFieldWithButton(
            text = viewModel.certificationNumber,
            width = 62,
            height = 27,
            textFieldLabel = "인증번호",
            placeholder = "숫자 6자리",
            buttonLabel = "인증 확인",
            keyboardType = KeyboardType.Number,
            padding = 5,
            onTextChanged = { viewModel.updateCertificationNumber(it) },
            onClick = {
                Log.d("send", isSendNumber.toString())
                viewModel.updateIsCertified(true)
                if (!isSendNumber) {
                    Toast.makeText(context, "먼저 인증번호를 전송해주세요", Toast.LENGTH_SHORT).show()
                } else {
                    if (viewModel.certificationNumber.isEmpty()) {
                        Toast.makeText(context, "인증 번호를 입력해주세요", Toast.LENGTH_SHORT).show()
                    } else {
                        onVerifyCodeClick(it) {
                            viewModel.updateIsCertified(it)
                            Log.d("casz", isCertified.toString())
                        }
                    }
                }
            }
        )
        Spacer(modifier = Modifier.weight(1f))
        ConnectDogNormalButton(
            content = "다음",
            color = if (viewModel.name.isNotEmpty() && isCertified) { PetOrange } else { Orange40 },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            onClick = { if (viewModel.name.isNotEmpty() && isCertified) onApplyClick(postId) }
        )
        Spacer(modifier = Modifier.height((imeHeight + 32).dp))
    }
}
