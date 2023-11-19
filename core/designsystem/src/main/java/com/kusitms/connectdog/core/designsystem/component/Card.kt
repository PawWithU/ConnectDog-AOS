package com.kusitms.connectdog.core.designsystem.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kusitms.connectdog.core.designsystem.theme.Gray3
import com.kusitms.connectdog.core.designsystem.theme.Gray7
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.wrapContentHeight

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
fun ConnectDogInformationCard(
    title: String,
    content: String
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(4.dp))
            .background(Gray7)
            .padding(all = 10.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = title,
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.height(5.dp))
            Text(
                text = content,
                fontSize = 14.sp,
                color = Gray3
            )
        }
    }
}

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
>>>>>>> develop
