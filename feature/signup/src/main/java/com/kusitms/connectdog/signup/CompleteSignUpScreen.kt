package com.kusitms.connectdog.signup

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layout
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kusitms.connectdog.core.designsystem.R
import com.kusitms.connectdog.core.designsystem.component.ConnectDogNormalButton
import com.kusitms.connectdog.core.designsystem.theme.ConnectDogTheme

@Composable
fun CompleteSignUpScreen(
    onClick: () -> Unit = {}
) {
    val focusManager = LocalFocusManager.current
    val interactionSource = remember { MutableInteractionSource() }

    Box(
        modifier =
        Modifier
            .fillMaxSize()
            .background(Color.White)
            .clickable(
                onClick = { focusManager.clearFocus() },
                indication = null,
                interactionSource = interactionSource
            )
    ) {
        Image(
            painter = painterResource(id = R.drawable.background),
            contentDescription = "",
            modifier = Modifier.fillMaxSize()
        )
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(134.dp))
            Image(
                modifier = Modifier
                    .width(200.dp)
                    .height(200.dp),
                painter = painterResource(R.drawable.ic_logo_complete),
                contentDescription = "Local Image"
            )
            Spacer(modifier = Modifier.height(30.dp))
            Text(
                text = "회원가입이 완료되었어요!\n코넥독의 회원이 된 것을 환영합니다!",
                textAlign = TextAlign.Center,
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
        ConnectDogNormalButton(
            content = "확인",
            modifier =
            Modifier
                .fillMaxWidth()
                .height(56.dp)
                .align(Alignment.BottomCenter)
                .padding(start = 20.dp, end = 20.dp)
                .layout { measurable, constraints ->
                    val placeable = measurable.measure(constraints)
                    layout(placeable.width, placeable.height + 64.dp.roundToPx()) {
                        placeable.place(0, 0)
                    }
                },
            onClick = onClick
        )
    }
}

@Preview
@Composable
private fun test() {
    ConnectDogTheme {
        CompleteSignUpScreen(onClick = {})
    }
}
