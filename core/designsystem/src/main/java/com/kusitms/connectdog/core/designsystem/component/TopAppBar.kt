package com.kusitms.connectdog.core.designsystem.component

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kusitms.connectdog.core.designsystem.R
import com.kusitms.connectdog.core.designsystem.theme.ConnectDogTheme

@Composable
fun ConnectDogTopAppBar(
    @StringRes titleRes: Int,
    navigationType: TopAppBarNavigationType,
    modifier: Modifier = Modifier,
    navigationIconContentDescription: String?,
    contentColor: Color = MaterialTheme.colorScheme.onSurface,
    containerColor: Color = MaterialTheme.colorScheme.surface,
    actionButtons: @Composable () -> Unit = {},
    onNavigationClick: () -> Unit = {},
) {
    val icon: @Composable (Modifier, imageRes: Int) -> Unit =
        { modifier, imageRes ->
            IconButton(onClick = onNavigationClick, modifier = modifier.size(48.dp)) {
                Icon(
                    painter = painterResource(id = imageRes),
                    contentDescription = navigationIconContentDescription
                )
            }
        }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = containerColor)
            .then(modifier)
    ) {
        if (navigationType == TopAppBarNavigationType.BACK) {
            icon(
                Modifier.align(Alignment.CenterStart),
                R.drawable.ic_left
            )
        }
        Row(modifier = Modifier.align(Alignment.CenterEnd)) {
            actionButtons
        }
        Text(
            text = stringResource(id = titleRes),
            color = contentColor,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.align(Alignment.Center)
        )
    }

}

enum class TopAppBarNavigationType { BACK }

@Preview
@Composable
private fun ConnectDogTopAppBarPreviewBack(){
    ConnectDogTheme {
        ConnectDogTopAppBar(
            titleRes = R.string.untitled,
            navigationType = TopAppBarNavigationType.BACK,
            navigationIconContentDescription = "Navigation icon"
        )
    }
}