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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.kusitms.connectdog.core.designsystem.component.ConnectDogOutlinedButton
import com.kusitms.connectdog.core.designsystem.component.ConnectDogTextFieldWithButton
import com.kusitms.connectdog.core.designsystem.component.ConnectDogTopAppBar
import com.kusitms.connectdog.core.designsystem.component.TopAppBarNavigationType
import com.kusitms.connectdog.feature.signup.R
import com.kusitms.connectdog.signup.viewmodel.SignUpViewModel
import org.orbitmvi.orbit.compose.collectAsState

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun IntermediatorProfileScreen(
    onBackClick: () -> Unit,
    navigateToIntermediatorInfo: () -> Unit,
    imeHeight: Int,
    viewModel: SignUpViewModel,
) {
    Scaffold(
        topBar = {
            ConnectDogTopAppBar(
                titleRes = R.string.intermediator_signup,
                navigationType = TopAppBarNavigationType.BACK,
                onNavigationClick = onBackClick,
            )
        },
    ) {
        Content(
            viewModel = viewModel,
            imeHeight = imeHeight,
        )
    }
}

@Composable
private fun Content(
    viewModel: SignUpViewModel,
    imeHeight: Int,
) {
    val context = LocalContext.current
    val focusManager = LocalFocusManager.current
    val interactionSource = remember { MutableInteractionSource() }
    val scrollState = rememberScrollState()
    val uiState by viewModel.collectAsState()

    fun convertToBitmap(uri: Uri): Bitmap =
        ImageDecoder
            .decodeBitmap(
                ImageDecoder.createSource(context.contentResolver, uri),
            )
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    val photoPicker =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.PickVisualMedia(),
            onResult = { imageUri = it },
        )

    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(horizontal = 20.dp)
                .clickable(
                    onClick = { focusManager.clearFocus() },
                    indication = null,
                    interactionSource = interactionSource,
                )
                .verticalScroll(scrollState),
    ) {
        LaunchedEffect(imeHeight) {
            scrollState.animateScrollTo(scrollState.maxValue)
        }
        Spacer(modifier = Modifier.height(80.dp))
        Text(
            text = "모집자 프로필에 사용할\n정보를 입력해주세요",
            style = MaterialTheme.typography.titleLarge,
        )
        Spacer(modifier = Modifier.height(40.dp))
        Row(
            modifier =
                Modifier
                    .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
        ) {
            imageUri?.let {
                Image(
                    bitmap = convertToBitmap(it).asImageBitmap(),
                    modifier =
                        Modifier
                            .size(100.dp)
                            .clip(CircleShape),
                    contentDescription = "",
                )
//                viewModel.updateUri(it)
            } ?: run {
                Image(
                    painter = painterResource(id = com.kusitms.connectdog.core.util.R.drawable.ic_profile_1),
                    contentDescription = "",
                )
            }
        }
        Spacer(modifier = Modifier.height(12.dp))
        Row(
            modifier =
                Modifier
                    .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
        ) {
            ConnectDogOutlinedButton(
                width = 105,
                height = 27,
                text = "프로필 사진 선택",
                padding = 5,
                onClick = {
                    photoPicker.launch(
                        PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly),
                    )
                },
            )
        }
        Spacer(modifier = Modifier.height(40.dp))
        ConnectDogTextFieldWithButton(
            text = uiState.name,
            width = 62,
            height = 27,
            textFieldLabel = stringResource(id = R.string.intermediator_name),
            placeholder = stringResource(id = R.string.intermediator_name_input),
            padding = 5,
            onTextChanged = {},
            isError = false,
            onClick = { },
        )
        Spacer(modifier = Modifier.height(4.dp))
//        Text(
//            text = if (isDuplicatedName == true) "이미 사용중인 모집자명입니다." else if (isDuplicatedName == false) "사용 가능한 모집자명 입니다." else "",
//            fontSize = 10.sp,
//            color = if (isDuplicatedName == true) Red1 else if (isDuplicatedName == false) PetOrange else Gray1,
//            fontWeight = FontWeight.Normal
//        )
//        Spacer(modifier = Modifier.height(6.dp))
//        ConnectDogTextField(
//            text = viewModel.introduce,
//            onTextChanged = viewModel::updateIntroduce,
//            placeholder = stringResource(id = R.string.introduce_placeholder),
//            height = 130
//        )
//        Spacer(modifier = Modifier.height(20.dp))
//        Spacer(modifier = Modifier.weight(1f))
//        ConnectDogBottomButton(
//            content = "다음",
//            modifier = Modifier
//                .fillMaxWidth()
//                .height(56.dp),
//            enabled = viewModel.name != "" && viewModel.introduce != "" && viewModel.uri != null,
//            onClick = {
//                navigateToIntermediatorInfo()
//                signUpViewModel.updateIntro(viewModel.introduce)
//                signUpViewModel.updateNickname(viewModel.name)
//                signUpViewModel.updateInterProfileImage(viewModel.uri!!)
//            }
//        )
        Spacer(modifier = Modifier.height((imeHeight + 32).dp))
    }
}
