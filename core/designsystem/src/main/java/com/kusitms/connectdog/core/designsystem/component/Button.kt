package com.kusitms.connectdog.core.designsystem.component

import android.graphics.drawable.Icon
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kusitms.connectdog.core.designsystem.R
import com.kusitms.connectdog.core.designsystem.theme.ConnectDogTheme
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
            modifier = Modifier.then(modifier.size(24.dp).weight(1f))
        )
        Spacer(modifier = Modifier.width(10.dp))
        Text(text = content, color = textColor, style = Typography.titleSmall)
    }
}

@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
@Preview
@Composable
private fun BottomButtonPreview(){
    ConnectDogTheme {
        BottomButton(onClick = {}, content = "간편 회원가입하기", modifier = Modifier.fillMaxWidth())
    }
}

@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
@Preview
@Composable
private fun ConnectDogIconButton(){
    ConnectDogTheme {
        ConnectDogIconButton(
            iconId = R.drawable.ic_right,
            contentDescription = "네이버 로그인",
            onClick = {},
            content = "네이버로 계속하기",
            modifier = Modifier.size(230.dp, 56.dp)
        )
    }
}
