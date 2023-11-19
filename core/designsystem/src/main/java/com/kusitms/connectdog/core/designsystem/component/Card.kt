package com.kusitms.connectdog.core.designsystem.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ConnectDogCard(
    modifier: Modifier = Modifier,
    borderColor: Color = MaterialTheme.colorScheme.outline,
    content: @Composable () -> Unit
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp),
        border = BorderStroke(width = 1.dp, color = borderColor),
        color = MaterialTheme.colorScheme.surface,
        shape = RoundedCornerShape(12.dp),
        shadowElevation = 2.dp,
        content = content
    )
}

@Composable
fun ConnectDogExpandableCard(
    modifier: Modifier = Modifier,
    isExpended: Boolean = false,
    onClick: () -> Unit,
    defaultContent: @Composable () -> Unit,
    expandedContent: @Composable () -> Unit
) {
    ConnectDogCard(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .clickable { onClick() }
    ) {
        if (isExpended) {
            expandedContent()
        } else {
            defaultContent()
        }
    }
}

@Composable
fun ConnectDogCardButton(
    modifier: Modifier = Modifier,
    isSelected: Boolean = false,
    onSelected: () -> Unit,
    content: @Composable () -> Unit
) {
    Surface(
        modifier = modifier.clickable { onSelected() },
        border = BorderStroke(width = 1.dp, color = MaterialTheme.colorScheme.outline),
        color = if (!isSelected) MaterialTheme.colorScheme.surface else MaterialTheme.colorScheme.primary,
        shape = RoundedCornerShape(12.dp),
        content = content
    )
}
