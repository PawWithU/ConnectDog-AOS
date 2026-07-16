package com.kusitms.connectdog.feature.main

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideIn
import androidx.compose.animation.slideOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import com.kusitms.connectdog.core.designsystem.theme.ConnectDogTheme
import com.kusitms.connectdog.core.util.UserType
import com.kusitms.connectdog.domain.usecase.login.AppMode
import com.kusitms.connectdog.feature.home.navigation.homeNavGraph
import com.kusitms.connectdog.feature.intermediator.navigation.intermediatorNavGraph
import com.kusitms.connectdog.feature.intermediator.viewmodel.CreateApplicationViewModel
import com.kusitms.connectdog.feature.login.navigation.loginNavGraph
import com.kusitms.connectdog.feature.management.navigation.managementNavGraph
import com.kusitms.connectdog.feature.mypage.navigation.mypageNavGraph
import com.kusitms.connectdog.feature.mypage.viewmodel.EditProfileViewModel
import com.kusitms.connectdog.signup.navigation.signUpGraph
import kotlinx.collections.immutable.toPersistentList

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
internal fun MainScreen(
    mode: AppMode,
    navigator: MainNavigator = rememberMainNavigator(mode = mode),
    sendVerificationCode: (String) -> Unit,
    verifyCode: (String, (Boolean) -> Unit) -> Unit,
    finish: () -> Unit,
    openWebBrowser: (String) -> Unit,
    imeHeight: Int,
) {
    val editProfileViewModel: EditProfileViewModel = hiltViewModel()
    val createApplicationViewModel: CreateApplicationViewModel = hiltViewModel()

    Scaffold(
        content = {
            Box(
                modifier =
                    Modifier
                        .fillMaxSize()
                        .background(
                            color = MaterialTheme.colorScheme.onPrimary,
                            shape = RectangleShape,
                        ),
            ) {
                NavHost(
                    navController = navigator.navController,
                    startDestination = navigator.startDestination,
                ) {
                    loginNavGraph(
                        imeHeight = imeHeight,
                        finish = finish,
                        onBackClick = { navigator.popBackStackIfNotHome() },
                        onNavigateToNormalLogin = { navigator.navigateNormalLogin(it) },
                        onNavigateToVolunteer = { navigator.navigateHome() },
                        onNavigateToIntermediatorHome = { navigator.navigateIntermediatorHome() },
                        onNavigateToSignup = { navigator.navigateSignup(it) },
                        onNavigateToEmailSearch = navigator::navigateEmailSearch,
                        onNavigateToPasswordSearch = navigator::navigatePasswordSearch,
                        onNavigateToEmailSearchComplete = navigator::navigateEmailSearchComplete,
                        onNavigateToPasswordSearchAuth = navigator::navigatePasswordSearchAuth,
                        onSendMessage = { sendVerificationCode(it) },
                        onVerifyCode = { code, callback -> verifyCode(code) { callback(it) } },
                        onNavigateToLoginRoute = navigator::onLogoutClick,
                        onNavigateToNoAccount = navigator::navigateNoAccount,
                    )
                    signUpGraph(
                        navController = navigator.navController,
                        onBackClick = navigator::popBackStackIfNotHome,
                        navigateToVolunteerProfile = navigator::navigateVolunteerProfile,
                        navigateToIntermediatorInformation = navigator::navigateIntermediatorInformation,
                        navigateToIntermediatorProfile = navigator::navigateIntermediatorProfile,
                        navigateToRegisterEmail = navigator::navigateRegisterEmail,
                        navigateToRegisterPassword = navigator::navigateRegisterPassword,
                        navigateToSelectProfileImage = { navigator.navigateSelectProfileImage() },
                        navigateToCompleteSignUp = navigator::navigateCompleteSignUp,
                        navigateToVolunteerHome = navigator::navigateHome,
                        navigateToIntermediatorHome = navigator::navigateIntermediatorHome,
                        imeHeight = imeHeight,
                        navigateToCertification = navigator::navigateCertification,
                        onSendMessage = { sendVerificationCode(it) },
                        onVerifyCode = { code, callback -> verifyCode(code) { callback(it) } },
                        navigateToLogin = { navigator.onLogoutClick() },
                        openWebBrowser = openWebBrowser,
                    )
                    homeNavGraph(
                        onBackClick = navigator::popBackStackIfNotHome,
                        onNavigateToSearch = { navigator.navigateHomeSearch() },
                        onNavigateToSearchWithFilter = { navigator.navigateHomeSearchWithFilter(it) },
                        onNavigateToFilterSearch = { navigator.navigateHomeFilterSearch() },
                        onNavigateToFilter = { navigator.navigateHomeFilter(it) },
                        onNavigateToReview = { navigator.navigateHomeReview() },
                        onNavigateToDetail = { navigator.navigateHomeDetail(it) },
                        onNavigateToApply = { navigator.navigateApply(it) },
                        onNavigateToComplete = { navigator.navigateComplete() },
                        onNavigateToIntermediatorProfile = {
                            navigator.navigateIntermediatorProfile(it)
                        },
                        onNavigateToNotification = { navigator.navigateNotification() },
                        onShowErrorSnackBar = {},
                        onSendMessage = { sendVerificationCode(it) },
                        onVerifyCode = { code, callback -> verifyCode(code) { callback(it) } },
                        imeHeight = imeHeight,
                        finish = finish,
                        onNavigateToGuideScreen = navigator::navigateToGuide,
                        onNavigateToReviewDetail = { navigator.navigateCheckReview(it, UserType.NORMAL_VOLUNTEER) },
                    )
                    managementNavGraph(
                        onBackClick = navigator::popBackStackIfNotHome,
                        onShowErrorSnackbar = {},
                        onNavigateToCheckReview = navigator::navigateCheckReview,
                        onNavigateToInterProfile = navigator::navigateIntermediatorProfile,
                        onNavigateToHome = navigator::navigateHomeFromManagement,
                        onNavigateToDetail = navigator::navigateHomeDetail,
                        onNavigateToCreateReview = { navigator.navigateCreateReview(it) },
                    )
                    mypageNavGraph(
                        padding = it,
                        onLogoutClick = { navigator.onLogoutClick() },
                        onBackClick = navigator::popBackStackIfNotHome,
                        onEditProfileClick = navigator::navigateEditProfile,
                        onManageAccountClick = { navigator.navigateManageAccount(it) },
                        onNotificationClick = { navigator.navigateNotification() },
                        onSettingClick = { navigator.navigateSetting(it) },
                        onBadgeClick = { navigator.navigateBadge() },
                        onBookmarkClick = { navigator.navigateBookmark() },
                        onEditProfileImageClick = { navigator.navigateEditProfileImage() },
                        editProfileViewModel = editProfileViewModel,
                        onNavigateToApply = { navigator.navigateApply(it) },
                        onNavigateToDetail = { navigator.navigateHomeDetail(it) },
                        onNavigateToIntermediatorProfile = {
                            navigator.navigateIntermediatorProfile(it)
                        },
                        onShowErrorSnackbar = {},
                        onNavigateToHome = navigator::navigateHomeFromMypage,
                        onNavigateToPasswordChange = navigator::navigatePasswordChange,
                    )
                    intermediatorNavGraph(
                        imeHeight = imeHeight,
                        createApplicationViewModel = createApplicationViewModel,
                        onBackClick = navigator::popBackStackIfNotHome,
                        onSettingClick = { navigator.navigateSetting(it) },
                        onNotificationClick = { navigator.navigateNotification() },
                        onManagementClick = { navigator.navigateInterManagement(it) },
                        onProfileClick = { navigator.navigateInterProfile() },
                        onNavigateToCreateAnnouncement = { navigator.navigateCreateAnnouncement() },
                        onNavigateToInterProfileEdit = navigator::navigateToInterProfileEdit,
                        onNavigateToReview = navigator::navigateCheckReview,
                        onNavigateToCreateDog = { navigator.navigateToCreateDog() },
                        onNavigateToAnnouncementManagement = navigator::navigateToAnnouncementManagement,
                        onNavigateToInterHome = navigator::navigateIntermediatorHome,
                        onNavigateToCreateComplete = navigator::navigateToCompleteCreate,
                    )
                }
            }
        },
        bottomBar = {
            AnimatedVisibility(
                visible = navigator.shouldShowBottomBar(),
                enter = fadeIn() + slideIn { IntOffset(0, it.height) },
                exit = fadeOut() + slideOut { IntOffset(0, it.height) },
            ) {
                Column(
                    modifier = Modifier.height(68.dp),
                ) {
                    HorizontalDivider(thickness = 1.dp, color = MaterialTheme.colorScheme.outline)
                    NavigationBar(
                        containerColor = Color.Transparent,
                        modifier = Modifier.background(Color.White),
                    ) {
                        MainTab.values().toList().toPersistentList().forEach {
                            NavigationBarItem(
                                selected = navigator.currentTab == it,
                                onClick = { navigator.navigate(it) },
                                icon = {
                                    NavigationIcon(
                                        tab = it,
                                        selected = navigator.currentTab == it,
                                    )
                                },
                                label = {
                                    NavigationLabel(
                                        tab = it,
                                        selected = navigator.currentTab == it,
                                    )
                                },
                            )
                        }
                    }
                }
            }
        },
    )
}

@Composable
private fun NavigationIcon(
    tab: MainTab,
    selected: Boolean,
) {
    Icon(
        modifier = Modifier.size(24.dp),
        painter = painterResource(id = tab.iconResId),
        contentDescription = tab.contentDescription,
        tint = if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface,
    )
}

@Composable
private fun NavigationLabel(
    tab: MainTab,
    selected: Boolean,
) {
    Text(
        text = tab.contentDescription,
        style = MaterialTheme.typography.labelLarge,
        color =
            if (selected) {
                MaterialTheme.colorScheme.primary
            } else {
                MaterialTheme.colorScheme.onSurface
            },
    )
}

@Preview
@Composable
private fun MainScreenPreview() {
    ConnectDogTheme {
        NavigationBar(
            containerColor = Color.Transparent,
            modifier = Modifier.background(Color.White),
        ) {
            MainTab.values().toList().toPersistentList().forEach {
                NavigationBarItem(
                    selected = it == MainTab.HOME,
                    onClick = { },
                    icon = { NavigationIcon(tab = it, selected = it == MainTab.HOME) },
                    label = { NavigationLabel(tab = it, selected = it == MainTab.HOME) },
                )
            }
        }
    }
}
