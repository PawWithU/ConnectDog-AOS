package com.kusitms.connectdog.feature.intermediator.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layout
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kusitms.connectdog.core.designsystem.component.ConnectDogNormalButton
import com.kusitms.connectdog.core.designsystem.theme.Gray100
import com.kusitms.connectdog.core.designsystem.theme.Gray60
import com.kusitms.connectdog.feature.intermediator.R
import com.kusitms.connectdog.core.designsystem.R as DR

@Composable
fun CompleteCreateScreen(
    navigateToHome: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        horizontalAlignment = Alignment.Start
    ) {
        Spacer(modifier = Modifier.height(80.dp))
        Text(
            modifier = Modifier.padding(start = 20.dp),
            text = stringResource(id = R.string.create_announcement_complete_title),
            textAlign = TextAlign.Center,
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold,
            color = Gray100
        )
        Text(
            modifier = Modifier.padding(start = 20.dp, top = 12.dp),
            text = stringResource(id = R.string.create_announcement_complete_sub),
            textAlign = TextAlign.Center,
            fontSize = 15.sp,
            fontWeight = FontWeight.W400,
            color = Gray60
        )
        Spacer(modifier = Modifier.weight(1f))
        Box(
            contentAlignment = Alignment.BottomCenter
        ) {
            androidx.compose.foundation.Image(
                painter = painterResource(id = DR.drawable.ic_main_large),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
            )
            ConnectDogNormalButton(
                content = "확인",
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .padding(start = 20.dp, end = 20.dp)
                        .layout { measurable, constraints ->
                            val placeable = measurable.measure(constraints)
                            layout(placeable.width, placeable.height + 64.dp.roundToPx()) {
                                placeable.place(0, 0)
                            }
                        },
                onClick = navigateToHome
            )
        }
    }
}
