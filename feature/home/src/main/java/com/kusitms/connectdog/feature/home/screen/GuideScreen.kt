package com.kusitms.connectdog.feature.home.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.kusitms.connectdog.core.designsystem.component.ConnectDogDetailTopAppBar
import com.kusitms.connectdog.feature.home.R

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun GuideScreen(onBackClick: () -> Unit) {
    Scaffold(
        topBar = {
            ConnectDogDetailTopAppBar(
                onBackClick = onBackClick,
                onShareClick = {
//                    Toast.makeText(context, "아직 준비중인 기능입니다.", Toast.LENGTH_SHORT).show()
                },
            )
        },
    ) {
        Column(
            modifier =
                Modifier
                    .verticalScroll(rememberScrollState()),
        ) {
            Image(
                painter = painterResource(R.drawable.img_guide),
                contentDescription = "",
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}
