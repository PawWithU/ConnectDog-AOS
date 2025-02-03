package com.kusitms.connectdog.signup.screen.volunteer

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import com.kusitms.connectdog.core.designsystem.component.ConnectDogBottomButton
import com.kusitms.connectdog.core.designsystem.component.ConnectDogNormalButton
import com.kusitms.connectdog.core.designsystem.component.ConnectDogTopAppBar
import com.kusitms.connectdog.core.designsystem.component.TopAppBarNavigationType
import com.kusitms.connectdog.core.designsystem.theme.Orange_40
import com.kusitms.connectdog.core.designsystem.theme.PetOrange
import com.kusitms.connectdog.core.util.getProfileImageId
import com.kusitms.connectdog.feature.signup.R
import com.kusitms.connectdog.signup.viewmodel.SignUpViewModel
import com.kusitms.connectdog.signup.viewmodel.SelectProfileImageViewModel
import org.orbitmvi.orbit.compose.collectAsState

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "ResourceType")
@Composable
fun SelectProfileImageScreen(
    onBackClick: () -> Unit,
    viewModel: SelectProfileImageViewModel = hiltViewModel(),
    signUpViewModel: SignUpViewModel
) {
    val uiState by signUpViewModel.collectAsState()

    LaunchedEffect(key1 = Unit) {
        viewModel.updateProfileImageIndex(uiState.profileImageId)
    }

    Scaffold(
        topBar = {
            ConnectDogTopAppBar(
                titleRes = uiState.userType.topBarTitleRes,
                navigationType = TopAppBarNavigationType.BACK,
                onNavigationClick = onBackClick
            )
        }
    ) {
        Content(
            onBackClick = onBackClick,
            viewModel = viewModel,
            updateProfileImageIndex = signUpViewModel::updateProfileImageIndex
        )
    }
}

@Composable
private fun Content(
    onBackClick: () -> Unit,
    viewModel: SelectProfileImageViewModel,
    updateProfileImageIndex: (Int) -> Unit
) {
    val uiState by viewModel.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(top = 48.dp, bottom = 32.dp)
            .padding(horizontal = 20.dp)
    ) {
        Spacer(modifier = Modifier.height(32.dp))
        Text(
            text = stringResource(id = R.string.select_profile_image_title),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
        )
        Spacer(modifier = Modifier.height(40.dp))
        ProfileImageGrid(
            selectedImageIndex = uiState.selectedImageId,
            updateProfileImageIndex = viewModel::updateProfileImageIndex
        )
        Spacer(modifier = Modifier.weight(1f))
        ConnectDogBottomButton(
            content = stringResource(id = R.string.select),
            onClick = {
                updateProfileImageIndex(uiState.selectedImageId)
                onBackClick()
            }
        )
    }
}

@Composable
fun ProfileImageGrid(
    selectedImageIndex: Int,
    updateProfileImageIndex: (Int) -> Unit
) {
    val modifier = Modifier
        .padding(15.dp)
        .aspectRatio(1f)
        .clip(CircleShape)

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        for (i in 0 until 3) {
            Row {
                for (j in 0 until 3) {
                    val index = i * 3 + j
                    Image(
                        painter = painterResource(id = getProfileImageId(index)),
                        contentDescription = "description for accessibility",
                        modifier = Modifier
                            .weight(1f)
                            .clickable(
                                indication = null,
                                interactionSource = remember { MutableInteractionSource() },
                                onClick = { updateProfileImageIndex(index) }
                            ).then(
                                if (selectedImageIndex != index) modifier
                                else modifier.border(4.dp, PetOrange, CircleShape)
                            )
                    )
                }
            }
        }
    }
}
