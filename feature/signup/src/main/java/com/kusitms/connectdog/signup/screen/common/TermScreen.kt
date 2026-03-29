package com.kusitms.connectdog.signup.screen.common

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.kusitms.connectdog.core.designsystem.component.CheckBox
import com.kusitms.connectdog.core.designsystem.component.ConnectDogBottomButton
import com.kusitms.connectdog.core.designsystem.component.ConnectDogTopAppBar
import com.kusitms.connectdog.core.designsystem.component.HorizontalLine
import com.kusitms.connectdog.core.designsystem.component.TopAppBarNavigationType
import com.kusitms.connectdog.core.designsystem.theme.Gray3
import com.kusitms.connectdog.core.util.UserType
import com.kusitms.connectdog.feature.signup.R
import com.kusitms.connectdog.signup.viewmodel.SignUpViewModel
import com.kusitms.connectdog.signup.viewmodel.TermsViewModel
import org.orbitmvi.orbit.compose.collectAsState

@Composable
internal fun SignUpRoute(
    onBackClick: () -> Unit,
    navigateToCertification: () -> Unit,
    openWebBrowser: (String) -> Unit,
    userType: UserType,
    signUpViewModel: SignUpViewModel
) {
    LaunchedEffect(key1 = Unit) {
        signUpViewModel.updateUserType(userType)
    }

    TermScreen(
        onBackClick = onBackClick,
        userType = userType,
        navigateToCertification = navigateToCertification,
        openWebBrowser = openWebBrowser
    )
}

@SuppressLint("ResourceType", "UnusedMaterial3ScaffoldPaddingParameter")
@Composable
private fun TermScreen(
    userType: UserType,
    onBackClick: () -> Unit,
    navigateToCertification: () -> Unit,
    openWebBrowser: (String) -> Unit,
    viewModel: TermsViewModel = hiltViewModel(),
) {
    BackHandler { onBackClick() }
    Scaffold(
        topBar = {
            ConnectDogTopAppBar(
                titleRes = userType.topBarTitleRes,
                navigationType = TopAppBarNavigationType.BACK,
                navigationIconContentDescription = "Navigation icon",
                onNavigationClick = onBackClick
            )
        }
    ) {
        Content(
            viewModel = viewModel,
            navigateToCertification = navigateToCertification,
            openWebBrowser = openWebBrowser
        )
    }
}

@Composable
private fun Content(
    viewModel: TermsViewModel,
    navigateToCertification: () -> Unit,
    openWebBrowser: (String) -> Unit,
) {
    val uiState by viewModel.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(top = 48.dp, bottom = 32.dp)
    ) {
        Spacer(modifier = Modifier.height(32.dp))
        Text(
            text = "코넥독 서비스 이용약관에\n동의해주세요",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(start = 20.dp)
        )
        Spacer(modifier = Modifier.height(40.dp))
        CheckBox(
            text = uiState.agreeAll.title,
            checked = uiState.agreeAll.isChecked,
            hasDetail = uiState.agreeAll.hasDetail,
            onClick = viewModel::onAgreeAllClick
        )
        Spacer(modifier = Modifier.height(16.dp))
        HorizontalLine(
            height = 1,
            color = Gray3
        )
        Spacer(modifier = Modifier.height(16.dp))
        CheckBox(
            text = uiState.termsOfService.title,
            checked = uiState.termsOfService.isChecked,
            hasDetail = uiState.termsOfService.hasDetail,
            onDetailClick = { openWebBrowser(uiState.termsOfService.url!!) },
            onClick = viewModel::onTermsOfServiceClick
        )
        Spacer(modifier = Modifier.height(16.dp))
        CheckBox(
            text = uiState.privacy.title,
            checked = uiState.privacy.isChecked,
            onClick = viewModel::onPrivacyClick,
            hasDetail = uiState.privacy.hasDetail,
            onDetailClick = { openWebBrowser(uiState.privacy.url!!) }
        )
        Spacer(modifier = Modifier.height(16.dp))
        CheckBox(
            text = uiState.advertisement.title,
            checked = uiState.advertisement.isChecked,
            onClick = viewModel::onAdvertisementClick,
            hasDetail = uiState.advertisement.hasDetail,
        )
        Spacer(modifier = Modifier.weight(1f))
        ConnectDogBottomButton(
            modifier = Modifier.padding(horizontal = 20.dp),
            content = stringResource(id = R.string.next),
            enabled = uiState.enableNext,
            onClick = navigateToCertification
        )
    }
}
