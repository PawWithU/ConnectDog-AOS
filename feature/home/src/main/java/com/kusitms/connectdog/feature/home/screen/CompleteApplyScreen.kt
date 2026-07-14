package com.kusitms.connectdog.feature.home.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layout
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kusitms.connectdog.core.designsystem.R
import com.kusitms.connectdog.core.designsystem.component.ConnectDogNormalButton
import com.kusitms.connectdog.core.designsystem.theme.ConnectDogTheme
import com.kusitms.connectdog.core.designsystem.theme.Gray100
import com.kusitms.connectdog.core.designsystem.theme.Gray60

@Composable
fun CompleteApplyScreen(onClick: () -> Unit = {}) {
    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .background(Color.White),
        horizontalAlignment = Alignment.Start,
    ) {
        Spacer(modifier = Modifier.height(80.dp))
        Text(
            modifier = Modifier.padding(start = 20.dp),
            text = "이동봉사 신청이 완료되었어요!",
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.SemiBold,
            color = Gray100,
        )
        Text(
            modifier = Modifier.padding(start = 20.dp, top = 12.dp),
            text = "빠른 시간 내 모집자의 승인 여부를 알려드릴게요!",
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodySmall,
            color = Gray60,
        )
        Spacer(modifier = Modifier.weight(1f))
        Box(
            contentAlignment = Alignment.BottomCenter,
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_main_large),
                contentDescription = null,
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .aspectRatio(1f),
            )
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
                onClick = onClick,
            )
        }
    }
}

@Preview
@Composable
fun test4() {
    ConnectDogTheme {
        CompleteApplyScreen()
    }
}
