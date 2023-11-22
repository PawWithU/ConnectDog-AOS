package com.kusitms.connectdog.core.designsystem.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kusitms.connectdog.core.designsystem.theme.ConnectDogTheme
import com.kusitms.connectdog.core.designsystem.theme.Gray2
import com.kusitms.connectdog.core.designsystem.theme.Gray3
import com.kusitms.connectdog.core.designsystem.theme.Gray6

@Composable
fun Content(
    modifier: Modifier = Modifier,
    textStyle: TextStyle,
    titleRes1: Int,
    titleRes2: Int,
    titleRes3: Int,
    t1Value: String,
    t2Value: String,
    t3Value: String,
    isValid: Boolean = true
) {
    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(6.dp)) {
        TitleValue(
            title = stringResource(id = titleRes1),
            value = t1Value,
            style = textStyle,
            isValid = isValid
        )
        TitleValue(
            title = stringResource(id = titleRes2),
            value = t2Value,
            style = textStyle,
            isValid = isValid
        )
        TitleValue(
            title = stringResource(id = titleRes3),
            value = t3Value,
            style = textStyle,
            isValid = isValid
        )
    }
}

@Composable
fun TitleValue(
    title: String,
    value: String,
    style: TextStyle,
    isValid: Boolean = true
) {
    Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
        Text(
            text = title,
            style = style,
            color = if (isValid) Gray3 else Gray6,
        )
        Text(
            text = value,
            style = style,
            color = if (isValid) Gray2 else Gray6
        )
    }
}

@Preview
@Composable
private fun ContentPreview() {
    ConnectDogTheme {
        TitleValue(title = "일정", value = "23.10.19(목)", style = MaterialTheme.typography.labelLarge)
    }
}
