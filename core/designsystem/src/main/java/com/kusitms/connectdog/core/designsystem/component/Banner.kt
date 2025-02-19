package com.kusitms.connectdog.core.designsystem.component

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kusitms.connectdog.core.designsystem.R
import com.kusitms.connectdog.core.designsystem.theme.Gray5
import com.kusitms.connectdog.core.designsystem.theme.PetOrange

@Composable
fun BannerGuideline(
    onClick: () -> Unit = {}
) {
    val context = LocalContext.current

    Row(
        modifier = Modifier
            .padding(vertical = 12.dp)
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .defaultMinSize(minHeight = 84.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(MaterialTheme.colorScheme.primary)
            .padding(horizontal = 20.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = buildAnnotatedString {
                withStyle(
                    SpanStyle(
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium
                    )
                ) {
                    append(stringResource(id = R.string.home_banner_1))
                }
                withStyle(
                    SpanStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                ) {
                    append(stringResource(id = R.string.home_banner_2))
                }
            },
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.background,
            lineHeight = 20.sp
        )
        Text(
            text = stringResource(id = R.string.home_banner_button_guideline),
            style = MaterialTheme.typography.labelMedium,
            fontWeight = FontWeight.Bold,
            color = PetOrange,
            modifier = Modifier
                .background(
                    shape = RoundedCornerShape(90.dp),
                    color = Color.White
                )
                .padding(horizontal = 9.dp, vertical = 5.dp)
                .clickable {
                    Toast
                        .makeText(context, "아직 준비중인 기능입니다.", Toast.LENGTH_SHORT)
                        .show()
                },
            fontSize = 11.sp
        )
    }
}
