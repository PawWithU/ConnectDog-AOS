package com.kusitms.connectdog.feature.home.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.kusitms.connectdog.core.designsystem.component.ConnectDogTopAppBar
import com.kusitms.connectdog.core.designsystem.component.TopAppBarNavigationType
import com.kusitms.connectdog.feature.home.R

@Composable
internal fun SearchScreen(
    onBackClick: () -> Unit
) {
    Column {
        TopAppBar {
            onBackClick()
        }
        Text(text = "SEARCH")
    }
}

@Composable
private fun TopAppBar(
    onBackClick: () -> Unit
) {
    ConnectDogTopAppBar(
        titleRes = R.string.search_app_bar_title,
        navigationType = TopAppBarNavigationType.BACK,
        navigationIconContentDescription = "Back",
        onNavigationClick = { onBackClick() }
    )
}
