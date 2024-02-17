package com.kusitms.connectdog.feature.signup.volunteer

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.kusitms.connectdog.core.designsystem.R
import com.kusitms.connectdog.core.designsystem.component.ConnectDogNormalButton
import com.kusitms.connectdog.core.designsystem.component.ConnectDogTopAppBar
import com.kusitms.connectdog.core.designsystem.component.TopAppBarNavigationType
import com.kusitms.connectdog.core.designsystem.theme.Orange_40
import com.kusitms.connectdog.core.designsystem.theme.PetOrange
import com.kusitms.connectdog.core.util.getProfileImage

@Composable
fun SelectProfileImageScreen(navigator: NavController, viewModel: SelectProfileImageViewModel) {
    val focusManager = LocalFocusManager.current
    val interactionSource = remember { MutableInteractionSource() }
    val selectedImageIndex = remember { mutableIntStateOf(-1) }

    val buttonColor = if (selectedImageIndex.intValue != -1) {
        PetOrange
    } else {
        Orange_40
    }

    Box(
        modifier =
        Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(bottom = 32.dp)
            .clickable(
                onClick = { focusManager.clearFocus() },
                indication = null,
                interactionSource = interactionSource
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
        ) {
            ConnectDogTopAppBar(
                titleRes = R.string.volunteer_signup,
                navigationType = TopAppBarNavigationType.BACK,
                navigationIconContentDescription = "Navigation icon",
                onNavigationClick = { navigator.popBackStack() }
            )
            Spacer(modifier = Modifier.height(32.dp))
            Text(
                text = "프로필 이미지를\n선택해주세요",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 20.dp)
            )
            Spacer(modifier = Modifier.height(40.dp))
            ProfileImageGrid(navigator, selectedImageIndex)
        }

        ConnectDogNormalButton(
            content = "선택",
            color = buttonColor,
            modifier =
            Modifier
                .fillMaxWidth()
                .height(56.dp)
                .align(Alignment.BottomCenter)
                .padding(horizontal = 20.dp),
            onClick = {
                if (selectedImageIndex.intValue != -1) {
                    viewModel.updateSelectedImageIndex(selectedImageIndex.intValue)
                    navigator.popBackStack()
                }
            }
        )
    }
}

@Composable
fun ProfileImageGrid(navigator: NavController, selectedImageIndex: MutableState<Int>) {
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
                        painter = painterResource(id = getProfileImage(index)),
                        contentDescription = "description for accessibility",
                        modifier = Modifier
                            .weight(1f)
                            .clickable(
                                indication = null,
                                interactionSource = remember { MutableInteractionSource() }
                            ) {
                                selectedImageIndex.value = index
                            }
                            .then(
                                if (selectedImageIndex.value == index) {
                                    modifier.border(4.dp, PetOrange, CircleShape)
                                } else {
                                    modifier
                                }
                            )
                    )
                }
            }
        }
    }
}
