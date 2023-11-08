package com.kusitms.connectdog.feature.mypage

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
internal fun MypageRoute(
    padding: PaddingValues,
    onClick: () -> Unit,
    onShowErrorSnackBar: (throwable: Throwable?) -> Unit,
) {
    MypageScreen(
        padding = padding,
        onClick = onClick,
    )
}

@Composable
private fun MypageScreen(
    padding: PaddingValues,
    onClick: () -> Unit,
) {
    Text(text = "MYPAGE")
}
