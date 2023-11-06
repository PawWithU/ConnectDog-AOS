package com.kusitms.connectdog.feature.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.kusitms.connectdog.core.designsystem.R
import com.kusitms.connectdog.core.designsystem.component.ConnectDogTextField
import com.kusitms.connectdog.core.designsystem.theme.ConnectDogTheme
import com.kusitms.connectdog.core.designsystem.theme.PetOrange
import com.kusitms.connectdog.core.designsystem.theme.Red1
import com.kusitms.connectdog.core.designsystem.theme.Red2

@Composable
fun EmailLoginScreen(
    title: String,
    navController: NavController,
) {
    val focusManager = LocalFocusManager.current
    val interactionSource = remember { MutableInteractionSource() }
    Box(
        modifier =
            Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(top = 32.dp, bottom = 32.dp)
                .clickable(
                    onClick = { focusManager.clearFocus() },
                    indication = null,
                    interactionSource = interactionSource,
                ),
    ) {
        Column(
            modifier =
                Modifier
                    .align(Alignment.TopCenter)
                    .background(Color.White),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            TopBar(title = title, navController)
            Spacer(modifier = Modifier.height(37.dp))
            Logo(175, 119)
            LoginTextField(
                label = "휴대폰 번호",
                placeholder = "예)01012341234",
                keyboardType = KeyboardType.Text,
            )
            Spacer(modifier = Modifier.height(12.dp))
            LoginTextField(
                label = "비밀번호",
                placeholder = "비밀번호 입력",
                keyboardType = KeyboardType.Password,
            )
            Spacer(modifier = Modifier.height(12.dp))
            ErrorCard()
        }
        NormalButton(
            content = "로그인",
            color = PetOrange,
            onClick = {
            },
            modifier =
                Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .padding(horizontal = 20.dp)
                    .align(Alignment.BottomCenter),
        )
    }
}

@Composable
fun TopBar(
    title: String,
    navController: NavController,
) {
    Row(
        modifier =
            Modifier
                .fillMaxWidth(),
        verticalAlignment = CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        IconButton(
            onClick = { navController.popBackStack() },
            modifier =
                Modifier
                    .weight(1f)
                    .padding(end = 84.dp)
                    .background(Color.Transparent),
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_left),
                contentDescription = "Back Button",
            )
        }
        Text(
            text = title,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.weight(1f),
        )
        Spacer(
            modifier =
                Modifier
                    .size(24.dp)
                    .weight(1f),
        )
    }
}

@Composable
fun ErrorCard() {
    Card(
        colors =
            CardDefaults.cardColors(
                containerColor = Red2,
            ),
        modifier =
            Modifier
                .fillMaxWidth()
                .height(40.dp)
                .padding(horizontal = 20.dp),
    ) {
        Row(
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(start = 13.dp),
            verticalAlignment = CenterVertically,
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_error),
                contentDescription = "ErrorIcon",
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = "이메일 혹은 비밀번호가 일치하지 않습니다.",
                color = Red1,
                fontSize = 13.sp,
            )
        }
    }
}

@Composable
fun LoginTextField(
    label: String,
    placeholder: String,
    keyboardType: KeyboardType,
) {
    val (text, onTextChanged) =
        remember {
            mutableStateOf("")
        }
    ConnectDogTheme {
        ConnectDogTextField(
            text = text,
            onTextChanged = onTextChanged,
            label = label,
            placeholder = placeholder,
            keyboardType = keyboardType,
        )
    }
}
