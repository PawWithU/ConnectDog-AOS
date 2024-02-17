package com.kusitms.connectdog.feature.home.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.kusitms.connectdog.core.data.api.model.volunteer.ApplyBody
import com.kusitms.connectdog.core.designsystem.R
import com.kusitms.connectdog.core.designsystem.component.ConnectDogTopAppBar
import com.kusitms.connectdog.core.designsystem.component.ConnectDogNormalButton
import com.kusitms.connectdog.core.designsystem.component.NormalTextField
import com.kusitms.connectdog.core.designsystem.component.TopAppBarNavigationType
import com.kusitms.connectdog.core.designsystem.theme.Gray2
import com.kusitms.connectdog.core.designsystem.theme.Orange20
import com.kusitms.connectdog.feature.home.ApplyViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ApplyScreen(
    onBackClick: () -> Unit = {},
    onClick: () -> Unit = {},
    postId: Long,
    viewModel: ApplyViewModel = hiltViewModel()
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
        Content(viewModel, postId, onClick)
    }
}

@Composable
private fun Content(
    viewModel: ApplyViewModel,
    postId: Long,
    onClick: () -> Unit
) {
    val focusManager = LocalFocusManager.current
    val interactionSource = remember { MutableInteractionSource() }

    val (name, onNameChanged) = remember { mutableStateOf("") }
    val (phoneNumber, onPhoneNumberChanged) = remember { mutableStateOf("") }
    val (transportation, onTransportationChanged) = remember { mutableStateOf("") }
    val (content, onContentChanged) = remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .padding(start = 20.dp, end = 20.dp, top = 48.dp, bottom = 32.dp)
            .verticalScroll(rememberScrollState())
            .clickable(
                onClick = { focusManager.clearFocus() },
                indication = null,
                interactionSource = interactionSource
            )
    ) {
        Text(
            text = "이동봉사 중개 측에 전달할\n정보를 입력해주세요",
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp
        )
        Spacer(modifier = Modifier.height(20.dp))
//        Row(verticalAlignment = Alignment.CenterVertically) {
//            Checkbox(checked = false, onCheckedChange = null)
//            Spacer(modifier = Modifier.width(4.dp))
//            Text(
//                text = "기본정보 불러오기",
//                color = Gray2,
//                fontWeight = FontWeight.Normal,
//                fontSize = 14.sp
//            )
//        }
        Text(
            text = "성함을 입력해주세요",
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp
        )
        Spacer(modifier = Modifier.height(12.dp))
        NormalTextField(
            text = name,
            label = "이름",
            placeholder = "이름 입력",
            keyboardType = KeyboardType.Text,
            onTextChanged = { onNameChanged(it) }
        )
        Spacer(modifier = Modifier.height(30.dp))
        Text(
            text = "휴대폰 번호를 입력해주세요",
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp
        )
        Spacer(modifier = Modifier.height(12.dp))
        NormalTextField(
            text = phoneNumber,
            label = "휴대폰 번호",
            placeholder = "휴대폰 번호 입력",
            keyboardType = KeyboardType.Number,
            onTextChanged = { onPhoneNumberChanged(it) }
        )
        Spacer(modifier = Modifier.height(6.dp))
        NoticeCard()
        Spacer(modifier = Modifier.height(30.dp))
        Text(
            text = "어떤 교통 수단으로 이동봉사를 진행하나요?",
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp
        )
        Spacer(modifier = Modifier.height(12.dp))
        NormalTextField(
            text = transportation,
            label = "교통수단",
            placeholder = "예) 자차, 택시, KTX, 비행기 등",
            keyboardType = KeyboardType.Text,
            onTextChanged = { onTransportationChanged(it) }
        )
        Spacer(modifier = Modifier.height(30.dp))
        Text(
            text = "봉사자의 한마디",
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp
        )
        Spacer(modifier = Modifier.height(12.dp))
        NormalTextField(
            text = content,
            label = "한마디 입력",
            placeholder = "신청한 이유, 봉사 이력 등을 간단하게 작성해주세요!\n(10자 이상, 100자 이내로 입력해주세요)",
            keyboardType = KeyboardType.Text,
            height = 129,
            onTextChanged = onContentChanged
        )

        ConnectDogNormalButton(
            content = "완료",
            onClick = {
                viewModel.postApplyVolunteer(
                    postId,
                    ApplyBody(
                        content = content,
                        name = name,
                        phone = phoneNumber,
                        transportation = transportation
                    )
                )
                onClick()
            }
        )
    }
}

// @Composable
// private fun BottomButton(
//    onClick: () -> Unit,
// ) {
//    Box(
//        modifier = Modifier
//            .fillMaxWidth()
//            .height(112.dp)
//            .background(Color.White)
//    ) {
//        Row(
//            modifier = Modifier.align(Alignment.Center)
//        ) {
//
//        }
//    }
// }

@Composable
fun NoticeCard() {
    Card(
        colors = CardDefaults.cardColors(containerColor = Orange20),
        modifier = Modifier
            .fillMaxWidth()
            .height(40.dp)
    ) {
        Text(
            text = "이동봉사 중개 측에서 해당 휴대폰 번호로 연락드릴 예정입니다.",
            color = Gray2,
            fontSize = 13.sp,
            modifier = Modifier
                .fillMaxSize()
                .wrapContentSize(Alignment.Center)
        )
    }
}
