package com.kusitms.connectdog.core.designsystem.component.button

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kusitms.connectdog.core.designsystem.R
import com.kusitms.connectdog.core.designsystem.theme.Gray2
import com.kusitms.connectdog.core.designsystem.theme.Gray5

@Composable
fun ProfileButton(
    onClick: () -> Unit,
    modifier: Modifier,
) {
    val shape = RoundedCornerShape(8.dp)
    Button(
        onClick = onClick,
        shape = shape,
        modifier = modifier,
        colors =
            ButtonDefaults.outlinedButtonColors(
                contentColor = Gray2,
            ),
        border = BorderStroke(1.dp, Gray5),
        contentPadding = PaddingValues(all = 8.dp),
    ) {
        Text(
            text = stringResource(id = R.string.profile_button),
            fontSize = 12.sp,
            fontWeight = FontWeight.SemiBold,
            color = Gray2,
        )
    }
}
