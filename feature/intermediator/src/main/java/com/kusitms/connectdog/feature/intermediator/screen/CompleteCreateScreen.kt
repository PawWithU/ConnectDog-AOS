package com.kusitms.connectdog.feature.intermediator.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kusitms.connectdog.core.designsystem.component.ConnectDogNormalButton
import com.kusitms.connectdog.core.designsystem.theme.Gray60
import com.kusitms.connectdog.feature.intermediator.R

@Composable
fun CompleteCreateScreen(
    navigateToHome: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
    ) {
        Text(
            modifier = Modifier.padding(top = 80.dp, bottom = 12.dp, start = 20.dp),
            text = stringResource(R.string.complete_register),
            textAlign = TextAlign.Center,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            modifier = Modifier.padding(start = 20.dp),
            text = stringResource(R.string.complete_register_sub),
            textAlign = TextAlign.Center,
            fontSize = 15.sp,
            color = Gray60
        )
        Spacer(modifier = Modifier.weight(1f))
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f),
            painter = painterResource(id = R.drawable.img_complete_register),
            contentDescription = null,
        )
        ConnectDogNormalButton(
            content = "확인",
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .padding(top = 24.dp, bottom = 32.dp)
                .height(56.dp), // height는 padding 후에
            onClick = navigateToHome
        )
    }
}
