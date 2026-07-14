package com.kusitms.connectdog.core.designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kusitms.connectdog.core.designsystem.theme.ConnectDogTheme

@Composable
fun ConnectDogTag(
    text: String,
    color: Color = MaterialTheme.colorScheme.primary,
) {
    Text(
        text = text,
        style = MaterialTheme.typography.labelMedium,
        fontWeight = FontWeight.SemiBold,
        color = color,
        modifier =
            Modifier
                .background(
                    shape = RoundedCornerShape(4.dp),
                    color = MaterialTheme.colorScheme.primaryContainer,
                )
                .padding(horizontal = 8.dp, vertical = 4.dp),
        fontSize = 10.sp,
    )
}

@Composable
fun ConnectDogTagWithIcon(
    iconId: Int,
    text: String,
    contentColor: Color,
    backgroundColor: Color,
) {
    Box(
        modifier =
            Modifier
                .background(
                    shape = RoundedCornerShape(4.dp),
                    color = backgroundColor,
                )
                .padding(horizontal = 6.dp, vertical = 4.dp),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(painter = painterResource(id = iconId), contentDescription = null, tint = contentColor)
            Spacer(modifier = Modifier.width(3.dp))
            Text(text = text, fontSize = 10.sp, color = contentColor)
        }
    }
}

@Composable
@Preview
private fun ConnectDogTagPreview() {
    ConnectDogTheme {
        ConnectDogTag(text = "모집중")
    }
}

@Composable
@Preview
private fun ConnectDogTagWithIcon() {
    ConnectDogTheme {
//        ConnectDogTagWithIcon(iconId = R.drawable.ic_dog, text = "소형", contentColor = Gray3, backgroundColor = Gray7)
    }
}
