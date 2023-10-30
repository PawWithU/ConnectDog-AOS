package com.kusitms.connectdog.feature.management

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
internal fun ManagementRoute(
    padding: PaddingValues,
    onClick: () -> Unit,
    onShowErrorSnackBar: (throwable: Throwable?) -> Unit
) {
    ManagementScreen(
        padding = padding,
        onClick = onClick
    )
}

@Composable
private fun ManagementScreen(
    padding: PaddingValues,
    onClick: () -> Unit
) {
    Text(text = "MANAGEMENT")
}
