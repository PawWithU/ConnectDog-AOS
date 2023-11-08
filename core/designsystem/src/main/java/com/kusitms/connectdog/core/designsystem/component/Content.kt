package com.kusitms.connectdog.core.designsystem.component

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
) {
    Column(modifier = modifier) {
        TitleValue(title = stringResource(id = titleRes1), value = t1Value, style = textStyle)
        TitleValue(title = stringResource(id = titleRes2), value = t2Value, style = textStyle)
        TitleValue(title = stringResource(id = titleRes3), value = t3Value, style = textStyle)
    }
}

@Composable
fun TitleValue(
    title: String,
    value: String,
    style: TextStyle
) {
    Row {
        Text(
            text = title,
            style = style,
            color = Gray3,
            modifier = Modifier.defaultMinSize(minWidth = 35.dp),
        )
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = value,
            style = style,
            color = Gray2,
        )
    }
}

@Preview
@Composable
fun ContentPreview() {
    ConnectDogTheme {
        TitleValue(title = "일정", value = "23.10.19(목)", style = MaterialTheme.typography.labelLarge)
    }
}