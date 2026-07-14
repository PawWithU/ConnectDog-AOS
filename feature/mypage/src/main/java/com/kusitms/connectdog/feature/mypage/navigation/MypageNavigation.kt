package com.kusitms.connectdog.feature.mypage.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
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

fun NavController.navigateMypage(navOptions: NavOptions) {
    navigate(MypageRoute.route, navOptions)
}

fun NavController.navigateEditProfile(
    profileImageId: Int,
    nickName: String,
) {
    navigate("${MypageRoute.editProfile}/$profileImageId/$nickName")
}

fun NavController.navigateManageAccount(userType: UserType) {
    navigate("${MypageRoute.manageAccount}/$userType")
}

fun NavController.navigateNotification() {
    navigate(MypageRoute.notification)
}

fun NavController.navigateSetting(userType: UserType) {
    navigate("${MypageRoute.setting}/$userType")
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

fun NavController.navigatePasswordChange(userType: UserType) {
    navigate("${MypageRoute.password_change}/$userType")
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
    onNavigateToHome: (String) -> Unit,
    onNavigateToPasswordChange: (UserType) -> Unit,
) {
    composable(route = MypageRoute.route) {
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

    composable(
        route = "${MypageRoute.editProfile}/{profileImageId}/{nickName}",
        arguments =
            listOf(
                navArgument("profileImageId") { type = NavType.IntType },
                navArgument("nickName") { type = NavType.StringType },
            ),
    ) {
        EditProfileScreen(
            onBackClick = onBackClick,
            onEditProfileImageClick = onEditProfileImageClick,
            profileImageId = it.arguments!!.getInt("profileImageId"),
            nickName = it.arguments!!.getString("nickName")!!,
            viewModel = editProfileViewModel,
        )
    }

    composable(
        route = "${MypageRoute.manageAccount}/{userType}",
        arguments = listOf(navArgument("userType") { type = NavType.EnumType(UserType::class.java) }),
    ) {
        val userType = it.arguments!!.getSerializable("userType") as UserType
        ManageAccountScreen(
            onBackClick = onBackClick,
            userType = userType,
            onNavigateToPasswordChange = onNavigateToPasswordChange,
        )
    }

    composable(route = MypageRoute.notification) {
        NotificationScreen(
            onBackClick = onBackClick,
        )
    }

    composable(
        route = "${MypageRoute.setting}/{userType}",
        arguments = listOf(navArgument("userType") { type = NavType.EnumType(UserType::class.java) }),
    ) {
        val userType = it.arguments!!.getSerializable("userType") as UserType
        SettingScreen(
            onBackClick = onBackClick,
            onLogoutClick = onLogoutClick,
            onManageAccountClick = onManageAccountClick,
            userType = userType,
        )
    }

    composable(route = MypageRoute.badge) {
        BadgeScreen(
            onBackClick = onBackClick,
        )
    }

    composable(route = MypageRoute.bookmark) {
        BookmarkScreen(
            onBackClick = onBackClick,
            onDetailClick = onNavigateToDetail,
        )
    }

    composable(route = MypageRoute.editProfileImage) {
        SelectProfileImageScreen(
            onBackClick = onBackClick,
            viewModel = editProfileViewModel,
        )
    }

    composable(
        route = "${HomeRoute.detail}/{postId}",
        arguments =
            listOf(
                navArgument("postId") {
                    type = NavType.LongType
                },
            ),
    ) {
        DetailScreen(
            onBackClick = onBackClick,
            onApplyClick = { onNavigateToApply(it) },
            onIntermediatorProfileClick = onNavigateToIntermediatorProfile,
            postId = it.arguments!!.getLong("postId"),
        )
    }

    composable(
        route = "${MypageRoute.password_change}/{userType}",
        arguments = listOf(navArgument("userType") { type = NavType.EnumType(UserType::class.java) }),
    ) {
        PasswordChangeScreen(
            onBackClick = onBackClick,
            userType = it.arguments!!.getSerializable("userType") as UserType,
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
    const val password_change = "password_change"
}
