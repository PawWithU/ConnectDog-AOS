package com.kusitms.connectdog.signup.volunteer

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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import com.kusitms.connectdog.core.designsystem.R
import com.kusitms.connectdog.core.designsystem.component.ConnectDogNormalButton
import com.kusitms.connectdog.core.designsystem.component.ConnectDogTopAppBar
import com.kusitms.connectdog.core.designsystem.component.TopAppBarNavigationType
import com.kusitms.connectdog.core.designsystem.theme.Orange_40
import com.kusitms.connectdog.core.designsystem.theme.PetOrange
import com.kusitms.connectdog.core.util.getProfileImageId

@Composable
fun SelectProfileImageScreen(
    onBackClick: () -> Unit,
    viewModel: VolunteerProfileViewModel
) {
    val focusManager = LocalFocusManager.current
    val interactionSource = remember { MutableInteractionSource() }
    val selectedImageIndex by viewModel.selectedImageIndex.collectAsState()

    Box(
        modifier = Modifier
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
                onNavigationClick = onBackClick
            )
            Spacer(modifier = Modifier.height(32.dp))
            Text(
                text = "프로필 이미지를\n선택해주세요",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 20.dp)
            )
            Spacer(modifier = Modifier.height(40.dp))
            ProfileImageGrid(selectedImageIndex, viewModel)
        }

        ConnectDogNormalButton(
            content = "선택",
            color = if (selectedImageIndex != -1) {
                PetOrange
            } else {
                Orange_40
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .align(Alignment.BottomCenter)
                .padding(horizontal = 20.dp),
            onClick = {
                viewModel.updateProfileImageIndex(selectedImageIndex)
                onBackClick()
            }
        )
    }
}

@Composable
fun ProfileImageGrid(
    selectedImageIndex: Int,
    viewModel: VolunteerProfileViewModel
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
                                interactionSource = remember { MutableInteractionSource() }
                            ) {
                                viewModel.updateProfileImageIndex(index)
                            }
                            .then(
                                if (selectedImageIndex == index) {
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
