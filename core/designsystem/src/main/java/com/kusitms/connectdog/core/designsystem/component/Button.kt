package com.kusitms.connectdog.core.designsystem.component

import android.graphics.drawable.Icon
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.kusitms.connectdog.core.designsystem.theme.Typography

@Composable
fun BottomButton(
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.primary,
    textColor: Color = MaterialTheme.colorScheme.onPrimary,
    onClick: () -> Unit,
    content: String
) {
    Button(
        onClick = onClick,
        contentPadding = PaddingValues(vertical = 16.dp),
        modifier = modifier
            .padding(horizontal = 20.dp)
            .background(color)
    ) {
        Text(text = content, color = textColor, style = Typography.titleSmall)
    }
}

@Composable
fun ConnectDogIconButton(
    modifier: Modifier = Modifier,
    iconId: Int,
    contentDescription: String,
    color: Color = MaterialTheme.colorScheme.primary,
    textColor: Color = MaterialTheme.colorScheme.onPrimary,
    onClick: () -> Unit,
    content: String
) {
    Button(
        onClick = onClick,
        contentPadding = PaddingValues(vertical = 16.dp),
        modifier = modifier
            .padding(horizontal = 20.dp)
            .background(color)
    ) {
        Icon(
            painter = painterResource(id = iconId),
            contentDescription = contentDescription,
            modifier = Modifier.then(modifier.size(24.dp))
        )
        Spacer(modifier = Modifier.width(10.dp))
        Text(text = content, color = textColor, style = Typography.titleSmall)
    }
}
