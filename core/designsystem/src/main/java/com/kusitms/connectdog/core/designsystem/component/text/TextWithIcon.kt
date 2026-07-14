package com.kusitms.connectdog.core.designsystem.component.text

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kusitms.connectdog.core.designsystem.theme.Gray2

@Composable
fun TextWithIcon(
    text: String,
    iconId: Int,
    size: Int = 12,
    spacer: Int = 6,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            painter = painterResource(id = iconId),
            contentDescription = null,
            tint = Gray2,
        )
        Spacer(modifier = Modifier.width(spacer.dp))
        Text(
            text = text,
            fontSize = size.sp,
            fontWeight = FontWeight.Medium,
            color = Gray2,
        )
    }
}
