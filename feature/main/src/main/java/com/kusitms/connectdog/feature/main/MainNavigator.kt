package com.kusitms.connectdog.feature.main

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.kusitms.connectdog.core.util.AccountType
import com.kusitms.connectdog.core.util.UserType
import com.kusitms.connectdog.domain.usecase.login.AppMode
import com.kusitms.connectdog.feature.home.model.Filter
import com.kusitms.connectdog.feature.home.navigation.HomeRoute
import com.kusitms.connectdog.feature.home.navigation.navigateApply
import com.kusitms.connectdog.feature.home.navigation.navigateComplete
import com.kusitms.connectdog.feature.home.navigation.navigateDetail
import com.kusitms.connectdog.feature.home.navigation.navigateFilter
import com.kusitms.connectdog.feature.home.navigation.navigateFilterSearch
import com.kusitms.connectdog.feature.home.navigation.navigateGuide
import com.kusitms.connectdog.feature.home.navigation.navigateHome
import com.kusitms.connectdog.feature.home.navigation.navigateIntermediatorProfile
import com.kusitms.connectdog.feature.home.navigation.navigateReview
import com.kusitms.connectdog.feature.home.navigation.navigateSearch
import com.kusitms.connectdog.feature.home.navigation.navigateSearchWithFilter
import com.kusitms.connectdog.feature.intermediator.navigation.IntermediatorRoute
import com.kusitms.connectdog.feature.intermediator.navigation.navigateInterHome
import com.kusitms.connectdog.feature.intermediator.navigation.navigateInterManagement
import com.kusitms.connectdog.feature.intermediator.navigation.navigateInterProfile
import com.kusitms.connectdog.feature.intermediator.navigation.navigateToAnnouncementManagement
import com.kusitms.connectdog.feature.intermediator.navigation.navigateToCreateAnnouncementScreen
import com.kusitms.connectdog.feature.intermediator.navigation.navigateToCreateComplete
import com.kusitms.connectdog.feature.intermediator.navigation.navigateToCreateDog
import com.kusitms.connectdog.feature.intermediator.navigation.navigateToInterProfileEdit
import com.kusitms.connectdog.feature.login.navigation.LoginRoute
import com.kusitms.connectdog.feature.login.navigation.navigateEmailSearch
import com.kusitms.connectdog.feature.login.navigation.navigateEmailSearchComplete
import com.kusitms.connectdog.feature.login.navigation.navigateNormalLogin
import com.kusitms.connectdog.feature.login.navigation.navigatePasswordSearch
import com.kusitms.connectdog.feature.login.navigation.navigatePasswordSearchAuth
import com.kusitms.connectdog.feature.login.navigation.navigateToLoginRoute
import com.kusitms.connectdog.feature.login.navigation.navigateToNoAccount
import com.kusitms.connectdog.feature.management.navigation.navigateCheckReview
import com.kusitms.connectdog.feature.management.navigation.navigateCreateReview
import com.kusitms.connectdog.feature.management.navigation.navigateManagement
import com.kusitms.connectdog.feature.mypage.navigation.navigateBadge
import com.kusitms.connectdog.feature.mypage.navigation.navigateBookmark
import com.kusitms.connectdog.feature.mypage.navigation.navigateEditProfile
import com.kusitms.connectdog.feature.mypage.navigation.navigateEditProfileImage
import com.kusitms.connectdog.feature.mypage.navigation.navigateManageAccount
import com.kusitms.connectdog.feature.mypage.navigation.navigateMypage
import com.kusitms.connectdog.feature.mypage.navigation.navigateNotification
import com.kusitms.connectdog.feature.mypage.navigation.navigatePasswordChange
import com.kusitms.connectdog.feature.mypage.navigation.navigateSetting
import com.kusitms.connectdog.signup.navigation.navigateCompleteSignUp
import com.kusitms.connectdog.signup.navigation.navigateIntermediatorInformation
import com.kusitms.connectdog.signup.navigation.navigateRegisterEmail
import com.kusitms.connectdog.signup.navigation.navigateRegisterPassword
import com.kusitms.connectdog.signup.navigation.navigateSelectProfileImage
import com.kusitms.connectdog.signup.navigation.navigateSignup
import com.kusitms.connectdog.signup.navigation.navigateToCertification
import com.kusitms.connectdog.signup.navigation.navigateToIntermediatorProfile
import com.kusitms.connectdog.signup.navigation.navigateToVolunteerProfile

internal class MainNavigator(
    val navController: NavHostController,
    mode: AppMode,
) {
    private val currentDestination: NavDestination?
        @Composable get() = navController.currentBackStackEntryAsState().value?.destination

    val startDestination =
        when (mode) {
            AppMode.VOLUNTEER -> MainTab.HOME.route
            AppMode.INTERMEDIATOR -> IntermediatorRoute.route
            AppMode.LOGIN -> LoginRoute.ROUTE
        }

    val currentTab: MainTab?
        @Composable get() =
            currentDestination
                ?.route
                ?.let(MainTab::find)

    fun navigate(tab: MainTab) {
        val navOptions =
            navOptions {
                popUpTo(navController.graph.findStartDestination().id) {
                    saveState = true
                    inclusive = true
                }
                launchSingleTop = true
                restoreState = true
            }

        when (tab) {
            MainTab.HOME -> navController.navigateHome(navOptions)
            MainTab.MANAGEMENT -> navController.navigateManagement(navOptions)
            MainTab.MYPAGE -> navController.navigateMypage(navOptions)
        }
    }

    // login navigator
    fun navigateNormalLogin(userType: UserType) = navController.navigateNormalLogin(userType)

    fun navigateSignup(userType: UserType) = navController.navigateSignup(userType)

    fun navigateEmailSearch(userType: UserType) = navController.navigateEmailSearch(userType)

    fun navigateEmailSearchComplete(email: String) = navController.navigateEmailSearchComplete(email)

    fun navigatePasswordSearchAuth(userType: UserType) = navController.navigatePasswordSearchAuth(userType)

    fun onLogoutClick() = navController.navigateToLoginRoute()

    fun navigatePasswordSearch(userType: UserType) = navController.navigatePasswordSearch(userType)

    fun navigateNoAccount(accountType: AccountType) = navController.navigateToNoAccount(accountType)

    // signup navigator
    fun navigateVolunteerProfile() = navController.navigateToVolunteerProfile()

    fun navigateIntermediatorProfile() = navController.navigateToIntermediatorProfile()

    fun navigateRegisterEmail() = navController.navigateRegisterEmail()

    fun navigateRegisterPassword() = navController.navigateRegisterPassword()

    fun navigateSelectProfileImage() = navController.navigateSelectProfileImage()

    fun navigateCompleteSignUp() = navController.navigateCompleteSignUp()

    fun navigateIntermediatorInformation() = navController.navigateIntermediatorInformation()

    fun navigateCertification() = navController.navigateToCertification()

    // volunteer navigator
    fun navigateHome() = navigate(MainTab.HOME)

    fun navigateHomeSearch() = navController.navigateSearch()

    fun navigateHomeSearchWithFilter(filter: Filter) = navController.navigateSearchWithFilter(filter)

    fun navigateHomeFilterSearch() = navController.navigateFilterSearch()

    fun navigateHomeFilter(filter: Filter) = navController.navigateFilter(filter)

    fun navigateHomeReview() = navController.navigateReview()

    fun navigateHomeDetail(postId: Long) = navController.navigateDetail(postId)

    fun navigateApply(postId: Long) = navController.navigateApply(postId)

    fun navigateComplete() = navController.navigateComplete()

    fun navigateIntermediatorProfile(intermediaryId: Long) = navController.navigateIntermediatorProfile(intermediaryId)

    fun navigateEditProfile(
        profileImageId: Int,
        nickName: String,
    ) = navController.navigateEditProfile(profileImageId, nickName)

    fun navigateManageAccount(userType: UserType) = navController.navigateManageAccount(userType)

    fun navigateNotification() = navController.navigateNotification()

    fun navigateSetting(userType: UserType) = navController.navigateSetting(userType)

    fun navigateBadge() = navController.navigateBadge()

    fun navigateBookmark() = navController.navigateBookmark()

    fun navigateEditProfileImage() = navController.navigateEditProfileImage()

    fun navigateCreateReview(application: String) = navController.navigateCreateReview(application)

    fun navigateCheckReview(
        reviewId: Long,
        userType: UserType,
    ) = navController.navigateCheckReview(reviewId, userType)

    fun navigateToGuide() = navController.navigateGuide()

    fun navigatePasswordChange(userType: UserType) = navController.navigatePasswordChange(userType)

    // intermediator
    fun navigateIntermediatorHome() = navController.navigateInterHome()

    fun navigateInterManagement(index: Int) = navController.navigateInterManagement(index)

    fun navigateInterProfile() = navController.navigateInterProfile()

    fun navigateCreateAnnouncement() = navController.navigateToCreateAnnouncementScreen()

    fun navigateToInterProfileEdit(profileImage: String) = navController.navigateToInterProfileEdit(profileImage)

    fun navigateToCreateDog() = navController.navigateToCreateDog()

    fun navigateToAnnouncementManagement(postId: Long) = navController.navigateToAnnouncementManagement(postId)

    fun navigateToCompleteCreate() = navController.navigateToCreateComplete()

    fun popBackStackIfNotHome() {
        if (!isSameCurrentDestination(HomeRoute.route)) {
            navController.popBackStack()
        }
    }

    fun navigateToHomeClearBackStack(current: String) {
        navController.navigate(HomeRoute.route) {
            popUpTo(current) { inclusive = true }
        }
    }

    private fun isSameCurrentDestination(route: String) = navController.currentDestination?.route == route

    @Composable
    fun shouldShowBottomBar(): Boolean {
        val currentRoute = currentDestination?.route ?: return false
        return currentRoute in MainTab
    }
}

@Composable
internal fun rememberMainNavigator(
    navController: NavHostController = rememberNavController(),
    mode: AppMode,
): MainNavigator = remember(navController) { MainNavigator(navController, mode) }
