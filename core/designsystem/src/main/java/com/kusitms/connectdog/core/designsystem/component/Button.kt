package com.kusitms.connectdog.core.designsystem.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kusitms.connectdog.core.designsystem.R
import com.kusitms.connectdog.core.designsystem.theme.ConnectDogTheme
import com.kusitms.connectdog.core.designsystem.theme.Typography

@Composable
fun ConnectDogBottomButton(
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.primary,
    textColor: Color = MaterialTheme.colorScheme.onPrimary,
    onClick: () -> Unit,
    content: String
) {
    Button(
        onClick = onClick,
        contentPadding = PaddingValues(vertical = 16.dp),
        shape = RoundedCornerShape(12.dp),
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(containerColor = color, contentColor = textColor)
    ) {
        Text(text = content, style = Typography.titleSmall, color = textColor)
    }
}

@Composable
fun ConnectDogIconBottomButton(
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
        shape = RoundedCornerShape(12.dp),
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(containerColor = color, contentColor = textColor)
    ) {
        Icon(
            painter = painterResource(id = iconId),
            contentDescription = contentDescription,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(10.dp))
        Text(text = content, color = textColor, style = Typography.titleSmall)
    }
}

@Composable
fun ConnectDogOutlinedButton(
    modifier: Modifier = Modifier,
    isSelected: Boolean = false,
    onClick: () -> Unit,
    content: @Composable () -> Unit,
) {
    Button(
        onClick = onClick, modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(width = 1.dp, color = MaterialTheme.colorScheme.outline),
        colors = ButtonDefaults.buttonColors(containerColor = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface)
    ) {
        content()
    }
}

@Preview
@Composable
private fun BottomButtonPreview() {
    ConnectDogTheme {
        ConnectDogBottomButton(
            onClick = {},
            content = "간편 회원가입하기",
            modifier = Modifier.size(230.dp, 56.dp)
        )
    }
}

@Preview
@Composable
private fun ConnectDogIconButton() {
    ConnectDogTheme {
        ConnectDogIconBottomButton(
            iconId = R.drawable.ic_left,
            contentDescription = "네이버 로그인",
            onClick = {},
            content = "네이버로 계속하기",
            modifier = Modifier.size(230.dp, 56.dp)
        )
    }
}
