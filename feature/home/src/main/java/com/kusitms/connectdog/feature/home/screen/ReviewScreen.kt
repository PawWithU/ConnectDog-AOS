package com.kusitms.connectdog.feature.home.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import com.kusitms.connectdog.core.designsystem.component.ConnectDogTopAppBar
import com.kusitms.connectdog.core.designsystem.component.TopAppBarNavigationType
import com.kusitms.connectdog.feature.home.R

@Composable
fun ReviewScreen(
    onBackClick: () -> Unit
) {
    Column {
        TopAppBar {
            onBackClick()
        }
    }
}

@Composable
private fun TopAppBar(
    onBackClick: () -> Unit
) {
    ConnectDogTopAppBar(
        titleRes = R.string.review_top_app_bar_title,
        navigationType = TopAppBarNavigationType.BACK,
        navigationIconContentDescription = "Back",
        onNavigationClick = { onBackClick() }
    )
}
