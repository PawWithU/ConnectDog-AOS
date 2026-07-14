package com.kusitms.connectdog.core.designsystem.component

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kusitms.connectdog.core.designsystem.R
import com.kusitms.connectdog.core.designsystem.theme.Gray1
import com.kusitms.connectdog.core.designsystem.theme.Gray3
import com.kusitms.connectdog.core.designsystem.theme.Gray5

@Composable
fun SearchBar(onClick: () -> Unit) {
    Row(
        modifier =
            Modifier
                .padding(horizontal = 20.dp)
                .padding(vertical = 12.dp)
                .fillMaxWidth()
                .border(
                    width = 1.dp,
                    color = Gray5,
                    shape = RoundedCornerShape(90.dp),
                )
                .clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            modifier =
                Modifier
                    .padding(start = 20.dp)
                    .size(24.dp),
            imageVector = Icons.Filled.Search,
            tint = Gray3,
            contentDescription = "Navigate to Search",
        )
        Spacer(modifier = Modifier.width(5.dp))
        Text(
            modifier = Modifier.padding(vertical = 10.dp),
            text =
                buildAnnotatedString {
                    withStyle(
                        SpanStyle(
                            color = Gray1,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium,
                        ),
                    ) {
                        append(stringResource(id = R.string.search_bar_title))
                    }
                    withStyle(
                        SpanStyle(
                            color = Gray3,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Medium,
                        ),
                    ) {
                        append(stringResource(id = R.string.search_bar_subtitle))
                    }
                },
            lineHeight = 15.sp,
        )
    }
}
