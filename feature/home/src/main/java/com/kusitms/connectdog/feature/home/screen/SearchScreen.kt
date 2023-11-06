package com.kusitms.connectdog.feature.home.screen

import android.util.Log
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
internal fun SearchScreen(
    onBackClick: () -> Unit
) {
    Log.d("SearchScreen", "SearchScreen")
    Text(text = "SEARCH")
}
