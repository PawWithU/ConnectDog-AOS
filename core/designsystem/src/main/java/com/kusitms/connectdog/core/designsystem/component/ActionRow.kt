package com.kusitms.connectdog.core.designsystem.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kusitms.connectdog.core.designsystem.theme.Gray2

@Composable
fun ActionRow(vararg items: Pair<String, () -> Unit>) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
    ) {
        items.forEachIndexed { index, item ->
            Text(
                modifier = Modifier.clickable { item.second() },
                text = item.first,
                fontSize = 12.sp,
                color = Gray2,
            )
            if (index != items.lastIndex) {
                Text(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    text = "|",
                    fontSize = 12.sp,
                    color = Gray2,
                )
            }
        }
    }
}
