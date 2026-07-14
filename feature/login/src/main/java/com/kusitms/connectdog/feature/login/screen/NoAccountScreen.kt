package com.kusitms.connectdog.feature.login.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.kusitms.connectdog.core.designsystem.component.ConnectDogBottomButton
import com.kusitms.connectdog.core.designsystem.theme.Gray100
import com.kusitms.connectdog.core.designsystem.theme.Gray60
import com.kusitms.connectdog.core.util.AccountType
import com.kusitms.connectdog.feature.login.R

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun NoAccountScreen(
    accountType: AccountType,
    onNavigateToLoginRoute: () -> Unit,
) {
    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp)
                .padding(vertical = 32.dp),
    ) {
        Spacer(modifier = Modifier.height(48.dp))
        Text(
            text = stringResource(id = R.string.no_account_title),
            style = MaterialTheme.typography.titleLarge,
            color = Gray100,
        )
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = stringResource(id = R.string.no_account_subtitle),
            style = MaterialTheme.typography.bodySmall,
            color = Gray60,
        )
        Spacer(modifier = Modifier.weight(1f))
        ConnectDogBottomButton(
            content = "처음으로 돌아가기",
            onClick = onNavigateToLoginRoute,
        )
    }
}
