package com.kusitms.connectdog.core.designsystem.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kusitms.connectdog.core.designsystem.theme.Gray1
import com.kusitms.connectdog.core.designsystem.theme.Gray3

@Composable
fun DetailInfo(
    title: String,
    content: String
) {
    Row {
        Text(
            text = title,
            modifier = Modifier.width(80.dp),
            color = Gray3,
            fontSize = 14.sp
        )
        Text(
            text = content,
            fontWeight = FontWeight.Medium,
            color = Gray1,
            fontSize = 14.sp
        )
    }
}