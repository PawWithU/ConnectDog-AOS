package com.kusitms.connectdog.feature.mypage.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.kusitms.connectdog.feature.mypage.BadgeScreen
import com.kusitms.connectdog.feature.mypage.BookmarkScreen
import com.kusitms.connectdog.feature.mypage.EditProfileScreen
import com.kusitms.connectdog.feature.mypage.ManageAccountScreen
import com.kusitms.connectdog.feature.mypage.MypageRoute
import com.kusitms.connectdog.feature.mypage.NotificationScreen
import com.kusitms.connectdog.feature.mypage.SettingScreen

fun NavController.navigateMypage(navOptions: NavOptions) {
    navigate(MypageRoute.route, navOptions)
}

fun NavController.navigateEditProfile() {
    navigate(MypageRoute.editProfile)
}

fun NavController.navigateManageAccount() {
    navigate(MypageRoute.manageAccount)
}

fun NavController.navigateNotification() {
    navigate(MypageRoute.notification)
}

fun NavController.navigateSetting() {
    navigate(MypageRoute.setting)
}

fun NavController.navigateBadge() {
    navigate(MypageRoute.badge)
}

fun NavController.navigateBookmark() {
    navigate(MypageRoute.bookmark)
}

fun NavGraphBuilder.mypageNavGraph(
    padding: PaddingValues,
    onClick: () -> Unit,
    onLogoutClick: () -> Unit,
    onBackClick: () -> Unit,
    onEditProfileClick: () -> Unit,
    onManageAccountClick: () -> Unit,
    onNotificationClick: () -> Unit,
    onSettingClick: () -> Unit,
    onBadgeClick: () -> Unit,
    onBookmarkClick: () -> Unit,
    onShowErrorSnackbar: (throwable: Throwable?) -> Unit
) {
    composable(route = MypageRoute.route) {
        MypageRoute(
            padding,
            onClick,
            onEditProfileClick,
            onManageAccountClick,
            onNotificationClick,
            onSettingClick,
            onBadgeClick,
            onBookmarkClick,
            onShowErrorSnackbar
        )
    }

    composable(route = MypageRoute.editProfile) {
        EditProfileScreen(
            onBackClick = onBackClick
        )
    }

    composable(route = MypageRoute.manageAccount) {
        ManageAccountScreen(
            onBackClick = onBackClick
        )
    }

    composable(route = MypageRoute.notification) {
        NotificationScreen(
            onBackClick = onBackClick
        )
    }

    composable(route = MypageRoute.setting) {
        SettingScreen(
            onBackClick = onBackClick,
            onLogoutClick = onLogoutClick,
            onManageAccountClick = onManageAccountClick
        )
    }

    composable(route = MypageRoute.badge) {
        BadgeScreen(
            onBackClick = onBackClick
        )
    }

    composable(route = MypageRoute.bookmark) {
        BookmarkScreen(
            onBackClick = onBackClick
        )
    }
}

object MypageRoute {
    const val route = "mypage"
    const val editProfile = "editProfile"
    const val manageAccount = "manageAccount"
    const val notification = "notification"
    const val setting = "setting"
    const val badge = "badge"
    const val bookmark = "bookmark"
}
