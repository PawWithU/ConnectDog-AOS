package com.kusitms.connectdog.core.designsystem.component

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Notifications
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
    @StringRes titleRes: Int?,
    navigationType: TopAppBarNavigationType,
    modifier: Modifier = Modifier,
    navigationIconContentDescription: String?,
    contentColor: Color = MaterialTheme.colorScheme.onSurface,
    containerColor: Color = MaterialTheme.colorScheme.surface,
    actionButtons: @Composable () -> Unit = {},
    onNavigationClick: () -> Unit = {}
) {
    val icon: @Composable (Modifier, imageRes: Int) -> Unit =
        { modifier, imageRes ->
            IconButton(onClick = onNavigationClick, modifier = modifier.size(48.dp)) {
                Icon(
                    modifier = modifier
                        .fillMaxSize()
                        .padding(vertical = 16.dp, horizontal = 12.dp),
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
        when (navigationType) {
            TopAppBarNavigationType.BACK -> {
                icon(
                    Modifier.align(Alignment.CenterStart),
                    R.drawable.ic_left
                )
            }
            TopAppBarNavigationType.CLOSE -> {
                icon(
                    Modifier.align(Alignment.CenterStart),
                    R.drawable.ic_x
                )
            }
            TopAppBarNavigationType.HOME -> {
                HomeIcon(
                    modifier = Modifier.size(66.dp, 45.dp).align(Alignment.CenterStart),
                    imageRes = R.drawable.ic_logo_home,
                    iconContentDescription = "connect dog home"
                )
            }
        }

        Row(modifier = Modifier.align(Alignment.CenterEnd)) {
            actionButtons()
        }
        if (titleRes != null) {
            Text(
                text = stringResource(id = titleRes),
                color = contentColor,
                style = if (navigationType == TopAppBarNavigationType.MYPAGE) {
                    MaterialTheme.typography.titleLarge
                } else { MaterialTheme.typography.titleMedium },
                modifier = if (navigationType == TopAppBarNavigationType.MYPAGE) {
                    Modifier.align(Alignment.CenterStart).padding(start = 24.dp)
                } else {
                    Modifier.align(Alignment.Center)
                }
            )
        }
    }
}

@Composable
private fun HomeIcon(
    modifier: Modifier = Modifier,
    imageRes: Int,
    tintColor: Color = MaterialTheme.colorScheme.primary,
    iconContentDescription: String
) {
    Icon(
        modifier = modifier
            .padding(start = 16.dp),
        painter = painterResource(id = imageRes),
        tint = tintColor,
        contentDescription = iconContentDescription
    )
}

enum class TopAppBarNavigationType { BACK, HOME, CLOSE, MYPAGE }

@Preview
@Composable
private fun ConnectDogTopAppBarPreviewBack() {
    ConnectDogTheme {
        ConnectDogTopAppBar(
            titleRes = R.string.untitled,
            navigationType = TopAppBarNavigationType.BACK,
            navigationIconContentDescription = "Navigation icon"
        )
    }
}

@Preview
@Composable
private fun ConnectDogTopAppBarPreviewHome() {
    ConnectDogTheme {
        ConnectDogTopAppBar(
            titleRes = null,
            navigationType = TopAppBarNavigationType.HOME,
            navigationIconContentDescription = "Navigation icon home",
            actionButtons = {
                IconButton(onClick = {}) {
                    Icon(
                        imageVector = Icons.Filled.Search,
                        contentDescription = "Navigate to Search"
                    )
                }
                IconButton(onClick = {}) {
                    Icon(
                        imageVector = Icons.Outlined.Notifications,
                        contentDescription = "Navigate to Search"
                    )
                }
            }
        )
    }
}
