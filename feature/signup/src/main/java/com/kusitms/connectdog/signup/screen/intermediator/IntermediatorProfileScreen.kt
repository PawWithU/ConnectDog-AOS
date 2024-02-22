package com.kusitms.connectdog.signup.screen.intermediator

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.kusitms.connectdog.core.designsystem.component.ConnectDogNormalButton
import com.kusitms.connectdog.core.designsystem.component.ConnectDogOutlinedButton
import com.kusitms.connectdog.core.designsystem.component.ConnectDogTextField
import com.kusitms.connectdog.core.designsystem.component.ConnectDogTopAppBar
import com.kusitms.connectdog.core.designsystem.component.TopAppBarNavigationType
import com.kusitms.connectdog.core.designsystem.theme.PetOrange
import com.kusitms.connectdog.core.util.Type
import com.kusitms.connectdog.feature.signup.R
import com.kusitms.connectdog.signup.viewmodel.IntermediatorProfileViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun IntermediatorProfileScreen(
    onBackClick: () -> Unit,
    navigateToCompleteSignUp: (Type) -> Unit,
    viewModel: IntermediatorProfileViewModel = hiltViewModel()
) {
    val context = LocalContext.current

    fun convertToBitmap(uri: Uri): Bitmap = ImageDecoder
        .decodeBitmap(
            ImageDecoder.createSource(context.contentResolver, uri)
        )
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    val photoPicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { imageUri = it }
    )

    Scaffold(
        topBar = {
            ConnectDogTopAppBar(
                titleRes = R.string.intermediator_signup,
                navigationType = TopAppBarNavigationType.BACK,
                onNavigationClick = onBackClick
            )
        }
    ) {
        val focusManager = LocalFocusManager.current
        val interactionSource = remember { MutableInteractionSource() }
        val scrollState = rememberScrollState()

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
                .verticalScroll(scrollState)
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
                imageUri?.let {
                    Image(
                        bitmap = convertToBitmap(it).asImageBitmap(),
                        modifier = Modifier
                            .size(100.dp)
                            .clip(CircleShape),
                        contentDescription = ""
                    )
                } ?: run {
                    Image(
                        painter = painterResource(id = com.kusitms.connectdog.core.util.R.drawable.ic_profile_1),
                        contentDescription = ""
                    )
                }
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
                    onClick = {
                        photoPicker.launch(
                            PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                        )
                    }
                )
            }
            Spacer(modifier = Modifier.height(40.dp))
            ConnectDogTextField(
                text = viewModel.introduce,
                onTextChanged = { viewModel.updateIntroduce(it) },
                label = "한줄 소개",
                placeholder = "소개 입력",
                height = 130
            )
            ConnectDogTextField(
                text = viewModel.introduce,
                onTextChanged = { viewModel.updateIntroduce(it) },
                label = "문의 받을 연락처",
                placeholder = "문의받을 연락처를 입력해주세요.",
                height = 200
            )
            Spacer(modifier = Modifier.weight(1f))
            ConnectDogNormalButton(
                content = "다음",
                modifier =
                Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                color = PetOrange,
                onClick = { navigateToCompleteSignUp(Type.INTERMEDIATOR) }
            )
        }
    }
}
