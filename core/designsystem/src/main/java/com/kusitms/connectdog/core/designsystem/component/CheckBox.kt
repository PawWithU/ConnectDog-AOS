package com.kusitms.connectdog.core.designsystem.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kusitms.connectdog.core.designsystem.R
import com.kusitms.connectdog.core.designsystem.theme.Gray1
import com.kusitms.connectdog.core.designsystem.theme.Gray2
import com.kusitms.connectdog.core.designsystem.theme.Gray4
import com.kusitms.connectdog.core.designsystem.theme.PetOrange

@Composable
fun CheckBox(
    text: String,
    checked: Boolean,
    onClick: () -> Unit,
    hasDetail: Boolean,
    onDetailClick: () -> Unit = {}
) {
    var isChecked by remember { mutableStateOf(checked) }
    if (checked != isChecked) isChecked = checked

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .clickable {
                onClick()
                isChecked = !isChecked
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_checked),
            contentDescription = null,
            tint = if (isChecked) PetOrange else Gray4,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = text,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            color = if (isChecked) Color.Black else Gray2
        )
        Spacer(modifier = Modifier.weight(1f))
        if (hasDetail) {
            Text(
                modifier = Modifier.clickable { onDetailClick() },
                text = "보기",
                style = TextStyle(textDecoration = TextDecoration.Underline),
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = if (isChecked) Gray1 else Gray2,
            )
        }
    }
}
