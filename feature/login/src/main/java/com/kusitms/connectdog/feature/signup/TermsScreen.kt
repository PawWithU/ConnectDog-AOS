package com.kusitms.connectdog.feature.signup

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.kusitms.connectdog.core.designsystem.R
import com.kusitms.connectdog.core.designsystem.component.ConnectDogTopAppBar
import com.kusitms.connectdog.core.designsystem.component.TopAppBarNavigationType
import com.kusitms.connectdog.core.designsystem.theme.Gray2
import com.kusitms.connectdog.core.designsystem.theme.Gray3
import com.kusitms.connectdog.core.designsystem.theme.Gray4
import com.kusitms.connectdog.core.designsystem.theme.Orange_40
import com.kusitms.connectdog.core.designsystem.theme.PetOrange
import com.kusitms.connectdog.feature.login.NormalButton

@Composable
fun VolunteerSignUpScreen(navigator: NavController, viewModel: TermsViewModel) {
    val allChecked by viewModel.allChecked.observeAsState(initial = false)
    val privacyChecked by viewModel.privacyChecked.observeAsState(initial = false)
    val termsChecked by viewModel.termsChecked.observeAsState(initial = false)

    val focusManager = LocalFocusManager.current
    val interactionSource = remember { MutableInteractionSource() }

    val buttonColor = if (allChecked) {
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
                .background(Color.White),
        ) {
            ConnectDogTopAppBar(
                titleRes = R.string.volunteer_signup,
                navigationType = TopAppBarNavigationType.BACK,
                navigationIconContentDescription = "Navigation icon",
                onNavigationClick = {
                    navigator.popBackStack()
                    viewModel.resetState()
                }
            )
            Spacer(modifier = Modifier.height(32.dp))
            Text(
                text="코넥독 서비스 이용약관에\n동의해주세요",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 20.dp)
            )
            Spacer(modifier = Modifier.height(40.dp))

            CustomCheckbox("모두 동의", allChecked) {
                viewModel.toggleAllChecked()
                viewModel.toggleTermsChecked()
                viewModel.togglePrivacyChecked()
            }

            Spacer(modifier = Modifier.height(16.dp))
            HorizontalLine()
            Spacer(modifier = Modifier.height(16.dp))
            CustomCheckbox("[필수] 이용약관 동의", termsChecked) {
                viewModel.toggleTermsChecked()
            }
            Spacer(modifier = Modifier.height(16.dp))
            CustomCheckbox("[필수] 개인정보 수집 및 이용 동의", privacyChecked) {
                viewModel.togglePrivacyChecked()
            }
        }
        NormalButton(
            content = "다음",
            color = buttonColor,
            modifier =
            Modifier
                .fillMaxWidth()
                .height(56.dp)
                .align(Alignment.BottomCenter)
                .padding(horizontal = 20.dp),
            onClick = {
                if(allChecked) {
                    navigator.navigate("profile")
                    viewModel.resetState()
                }
            }
        )
    }
}

@Composable
fun HorizontalLine() {
    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .height(1.dp)
            .padding(horizontal = 20.dp)
    ) {
        drawLine(
            color = Gray3,
            start = Offset(0f, 0f),
            end = Offset(size.width, 0f),
            strokeWidth = 1f
        )
    }
}

@Composable
fun CustomCheckbox(text: String, checked: Boolean, onCheckedChange: (Boolean) -> Unit) {
    var isChecked by remember { mutableStateOf(checked) }

    if (checked != isChecked) {
        isChecked = checked
    }

    Row(
        modifier = Modifier
            .padding(horizontal = 20.dp)
            .clickable {
            isChecked = !isChecked
            onCheckedChange(isChecked)
        }
    ) {
        Icon(
            painter = painterResource(
                id = R.drawable.ic_checked
            ),
            contentDescription = "Custom Checkbox",
            tint = if (isChecked) PetOrange else Gray4,
            modifier = Modifier
                .size(24.dp)
        )

        Spacer(modifier = Modifier.width(12.dp))

        Text(
            text = text,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = if (isChecked) Color.Black else Gray2
        )
    }
}

@Composable
@Preview
fun VolunteerSignUpScreenPreview() {
    val navController = rememberNavController()
//    VolunteerSignUpScreen(navController)
}
