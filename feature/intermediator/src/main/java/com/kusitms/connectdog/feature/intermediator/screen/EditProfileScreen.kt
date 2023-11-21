package com.kusitms.connectdog.feature.intermediator.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kusitms.connectdog.core.designsystem.component.ConnectDogOutlinedButton
import com.kusitms.connectdog.core.designsystem.component.ConnectDogTopAppBar
import com.kusitms.connectdog.core.designsystem.component.NetworkImage
import com.kusitms.connectdog.core.designsystem.component.TopAppBarNavigationType
import com.kusitms.connectdog.core.designsystem.theme.ConnectDogTheme
import com.kusitms.connectdog.feature.intermediator.R

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun EditProfileScreen() {
    Scaffold(
        topBar = {
            ConnectDogTopAppBar(
                titleRes = R.string.edit_profile,
                navigationType = TopAppBarNavigationType.BACK,
                navigationIconContentDescription = null
            )
        }
    ) {
        Content()
    }
}

@Composable
private fun Content() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 80.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        NetworkImage(imageUrl = "", placeholder = painterResource(id = R.drawable.ic_default_intermediator))
        Spacer(modifier = Modifier.height(12.dp))
        ConnectDogOutlinedButton(
            width = 51,
            height = 14,
            text = "봉사 3회 진행",
            padding = 5,
            onClick = { /*TODO*/ }
        )
    }
}

@Preview
@Composable
private fun test() {
    ConnectDogTheme {
        EditProfileScreen()
    }
}
