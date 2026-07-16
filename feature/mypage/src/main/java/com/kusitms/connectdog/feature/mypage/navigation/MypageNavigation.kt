package com.kusitms.connectdog.feature.mypage.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.kusitms.connectdog.core.util.UserType
import com.kusitms.connectdog.feature.home.navigation.HomeRoute
import com.kusitms.connectdog.feature.home.screen.DetailScreen
import com.kusitms.connectdog.feature.mypage.screen.BadgeScreen
import com.kusitms.connectdog.feature.mypage.screen.BookmarkScreen
import com.kusitms.connectdog.feature.mypage.screen.EditProfileScreen
import com.kusitms.connectdog.feature.mypage.screen.ManageAccountScreen
import com.kusitms.connectdog.feature.mypage.screen.MypageRoute
import com.kusitms.connectdog.feature.mypage.screen.NotificationScreen
import com.kusitms.connectdog.feature.mypage.screen.PasswordChangeScreen
import com.kusitms.connectdog.feature.mypage.screen.SelectProfileImageScreen
import com.kusitms.connectdog.feature.mypage.screen.SettingScreen
import com.kusitms.connectdog.feature.mypage.viewmodel.EditProfileViewModel
import kotlinx.serialization.Serializable

fun NavController.navigateMypage(navOptions: NavOptions) {
    navigate(MypageRoute.Mypage, navOptions)
}

fun NavController.navigateEditProfile(
    profileImageId: Int,
    nickName: String,
) {
    navigate(MypageRoute.EditProfile(profileImageId, nickName))
}

fun NavController.navigateManageAccount(userType: UserType) {
    navigate(MypageRoute.ManageAccount(userType))
}

fun NavController.navigateNotification() {
    navigate(MypageRoute.Notification)
}

fun NavController.navigateSetting(userType: UserType) {
    navigate(MypageRoute.Setting(userType))
}

fun NavController.navigateBadge() {
    navigate(MypageRoute.Badge)
}

fun NavController.navigateBookmark() {
    navigate(MypageRoute.Bookmark)
}

fun NavController.navigateEditProfileImage() {
    navigate(MypageRoute.EditProfileImage)
}

fun NavController.navigatePasswordChange(userType: UserType) {
    navigate(MypageRoute.PasswordChange(userType))
}

fun NavGraphBuilder.mypageNavGraph(
    padding: PaddingValues,
    onLogoutClick: () -> Unit,
    onBackClick: () -> Unit,
    onEditProfileClick: (Int, String) -> Unit,
    onManageAccountClick: (UserType) -> Unit,
    onNotificationClick: () -> Unit,
    onSettingClick: (UserType) -> Unit,
    onBadgeClick: () -> Unit,
    onBookmarkClick: () -> Unit,
    onShowErrorSnackbar: (throwable: Throwable?) -> Unit,
    onEditProfileImageClick: () -> Unit,
    editProfileViewModel: EditProfileViewModel,
    onNavigateToIntermediatorProfile: (Long) -> Unit,
    onNavigateToDetail: (Long) -> Unit,
    onNavigateToApply: (Long) -> Unit,
    onNavigateToHome: () -> Unit,
    onNavigateToPasswordChange: (UserType) -> Unit,
) {
    composable<MypageRoute.Mypage> {
        MypageRoute(
            onEditProfileClick,
            onNotificationClick,
            onSettingClick,
            onBadgeClick,
            onBookmarkClick,
            onNavigateToHome,
            onShowErrorSnackbar,
        )
    }

    composable<MypageRoute.EditProfile> { backStackEntry ->
        val route: MypageRoute.EditProfile = backStackEntry.toRoute()
        EditProfileScreen(
            onBackClick = onBackClick,
            onEditProfileImageClick = onEditProfileImageClick,
            profileImageId = route.profileImageId,
            nickName = route.nickName,
            viewModel = editProfileViewModel,
        )
    }

    composable<MypageRoute.ManageAccount> { backStackEntry ->
        val route: MypageRoute.ManageAccount = backStackEntry.toRoute()
        ManageAccountScreen(
            onBackClick = onBackClick,
            userType = route.userType,
            onNavigateToPasswordChange = onNavigateToPasswordChange,
        )
    }

    composable<MypageRoute.Notification> {
        NotificationScreen(
            onBackClick = onBackClick,
        )
    }

    composable<MypageRoute.Setting> { backStackEntry ->
        val route: MypageRoute.Setting = backStackEntry.toRoute()
        SettingScreen(
            onBackClick = onBackClick,
            onLogoutClick = onLogoutClick,
            onManageAccountClick = onManageAccountClick,
            userType = route.userType,
        )
    }

    composable<MypageRoute.Badge> {
        BadgeScreen(
            onBackClick = onBackClick,
        )
    }

    composable<MypageRoute.Bookmark> {
        BookmarkScreen(
            onBackClick = onBackClick,
            onDetailClick = onNavigateToDetail,
        )
    }

    composable<MypageRoute.EditProfileImage> {
        SelectProfileImageScreen(
            onBackClick = onBackClick,
            viewModel = editProfileViewModel,
        )
    }

    composable<HomeRoute.Detail> { backStackEntry ->
        val route: HomeRoute.Detail = backStackEntry.toRoute()
        DetailScreen(
            onBackClick = onBackClick,
            onApplyClick = { onNavigateToApply(it) },
            onIntermediatorProfileClick = onNavigateToIntermediatorProfile,
            postId = route.postId,
        )
    }

    composable<MypageRoute.PasswordChange> { backStackEntry ->
        val route: MypageRoute.PasswordChange = backStackEntry.toRoute()
        PasswordChangeScreen(
            onBackClick = onBackClick,
            userType = route.userType,
        )
    }
}

object MypageRoute {
    @Serializable
    data object Mypage

    @Serializable
    data class EditProfile(val profileImageId: Int, val nickName: String)

    @Serializable
    data class ManageAccount(val userType: UserType)

    @Serializable
    data object Notification

    @Serializable
    data class Setting(val userType: UserType)

    @Serializable
    data object Badge

    @Serializable
    data object Bookmark

    @Serializable
    data object EditProfileImage

    @Serializable
    data class PasswordChange(val userType: UserType)
}
