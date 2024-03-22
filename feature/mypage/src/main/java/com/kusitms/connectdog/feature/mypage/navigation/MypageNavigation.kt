package com.kusitms.connectdog.feature.mypage.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.kusitms.connectdog.feature.home.navigation.HomeRoute
import com.kusitms.connectdog.feature.home.screen.DetailScreen
import com.kusitms.connectdog.feature.mypage.screen.BadgeScreen
import com.kusitms.connectdog.feature.mypage.screen.BookmarkScreen
import com.kusitms.connectdog.feature.mypage.screen.EditProfileScreen
import com.kusitms.connectdog.feature.mypage.screen.ManageAccountScreen
import com.kusitms.connectdog.feature.mypage.screen.MypageRoute
import com.kusitms.connectdog.feature.mypage.screen.NotificationScreen
import com.kusitms.connectdog.feature.mypage.screen.SelectProfileImageScreen
import com.kusitms.connectdog.feature.mypage.screen.SettingScreen
import com.kusitms.connectdog.feature.mypage.viewmodel.EditProfileViewModel

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

fun NavController.navigateEditProfileImage() {
    navigate(MypageRoute.editProfileImage)
}

fun NavGraphBuilder.mypageNavGraph(
    padding: PaddingValues,
    onLogoutClick: () -> Unit,
    onBackClick: () -> Unit,
    onEditProfileClick: () -> Unit,
    onManageAccountClick: () -> Unit,
    onNotificationClick: () -> Unit,
    onSettingClick: () -> Unit,
    onBadgeClick: () -> Unit,
    onBookmarkClick: () -> Unit,
    onShowErrorSnackbar: (throwable: Throwable?) -> Unit,
    onEditProfileImageClick: () -> Unit,
    editProfileViewModel: EditProfileViewModel,
    onNavigateToIntermediatorProfile: (Long) -> Unit,
    onNavigateToDetail: (Long) -> Unit,
    onNavigateToCertification: (Long) -> Unit
) {
    composable(route = MypageRoute.route) {
        MypageRoute(
            onEditProfileClick,
            onNotificationClick,
            onSettingClick,
            onBadgeClick,
            onBookmarkClick,
            onShowErrorSnackbar
        )
    }

    composable(route = MypageRoute.editProfile) {
        EditProfileScreen(
            onBackClick = onBackClick,
            onEditProfileImageClick = onEditProfileImageClick,
            viewModel = editProfileViewModel
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
            onBackClick = onBackClick,
            onDetailClick = onNavigateToDetail
        )
    }

    composable(route = MypageRoute.editProfileImage) {
        SelectProfileImageScreen(
            onBackClick = onBackClick,
            viewModel = editProfileViewModel
        )
    }

    composable(
        route = "${HomeRoute.detail}/{postId}",
        arguments = listOf(
            navArgument("postId") {
                type = NavType.LongType
            }
        )
    ) {
        DetailScreen(
            onBackClick = onBackClick,
            onCertificationClick = { onNavigateToCertification(it) },
            onIntermediatorProfileClick = onNavigateToIntermediatorProfile,
            postId = it.arguments!!.getLong("postId")
        )
    }
}

object MypageRoute {
    const val route = "mypage"
    const val editProfile = "editProfile"
    const val editProfileImage = "editProfileImage"
    const val manageAccount = "manageAccount"
    const val notification = "notification"
    const val setting = "setting"
    const val badge = "badge"
    const val bookmark = "bookmark"
}
