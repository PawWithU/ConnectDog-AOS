package com.kusitms.connectdog.feature.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.kusitms.connectdog.core.designsystem.component.ConnectDogBottomButton
import com.kusitms.connectdog.core.designsystem.theme.Gray2
import com.kusitms.connectdog.core.designsystem.theme.Gray5
import com.kusitms.connectdog.core.designsystem.theme.PetOrange

@Composable
fun LoginScreen(navigator: NavController) {
    Column(
        modifier = Modifier.fillMaxSize().background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(46.dp))
        Logo(124, 204)
        Spacer(modifier = Modifier.height(7.dp))
        MainLogo()
        Spacer(modifier = Modifier.height(37.dp))
        OutlinedButton { navigator.navigate("selectLogin") }
        Spacer(modifier = Modifier.height(16.dp))
        NormalButton(content = "회원가입", color = PetOrange)
    }
}

@Composable
fun Logo(
    width: Int,
    height: Int
) {
    Image(
        modifier = Modifier.width(width.dp).height(height.dp),
        painter = painterResource(com.kusitms.connectdog.core.designsystem.R.drawable.logo),
        contentDescription = "Local Image"
    )
}

@Composable
fun MainLogo() {
    Image(
        painter = painterResource(com.kusitms.connectdog.core.designsystem.R.drawable.ic_main),
        contentDescription = "Local Image"
    )
}

@Composable
fun OutlinedButton(onClick: () -> Unit) {
    val shape = RoundedCornerShape(12.dp)
    ConnectDogBottomButton(
        onClick = onClick,
        content = "로그인",
        color = Color.White,
        modifier =
        Modifier
            .fillMaxWidth()
            .height(56.dp)
            .padding(horizontal = 20.dp)
            .border(
                width = 1.dp,
                color = Gray5,
                shape = shape
            ),
        textColor = Gray2
    )
}

@Composable
fun NormalButton(
    content: String,
    color: Color = PetOrange,
    onClick: () -> Unit = {},
    textColor: Color = MaterialTheme.colorScheme.onPrimary,
    modifier: Modifier = Modifier.fillMaxWidth().height(56.dp).padding(horizontal = 20.dp)
) {
    ConnectDogBottomButton(
        onClick = onClick,
        content = content,
        color = color,
        modifier = modifier,
        textColor = textColor
    )
}
