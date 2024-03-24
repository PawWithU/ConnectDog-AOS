package com.kusitms.connectdog.feature.main

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.kusitms.connectdog.core.util.AppMode
import com.kusitms.connectdog.core.util.UserType
import com.kusitms.connectdog.feature.home.model.Filter
import com.kusitms.connectdog.feature.home.navigation.HomeRoute
import com.kusitms.connectdog.feature.home.navigation.navigateApply
import com.kusitms.connectdog.feature.home.navigation.navigateCertification
import com.kusitms.connectdog.feature.home.navigation.navigateComplete
import com.kusitms.connectdog.feature.home.navigation.navigateDetail
import com.kusitms.connectdog.feature.home.navigation.navigateFilter
import com.kusitms.connectdog.feature.home.navigation.navigateFilterSearch
import com.kusitms.connectdog.feature.home.navigation.navigateHome
import com.kusitms.connectdog.feature.home.navigation.navigateIntermediatorProfile
import com.kusitms.connectdog.feature.home.navigation.navigateReview
import com.kusitms.connectdog.feature.home.navigation.navigateSearch
import com.kusitms.connectdog.feature.home.navigation.navigateSearchWithFilter
import com.kusitms.connectdog.feature.intermediator.navigation.IntermediatorRoute
import com.kusitms.connectdog.feature.intermediator.navigation.navigateIntermediatorHome
import com.kusitms.connectdog.feature.login.LoginRoute
import com.kusitms.connectdog.feature.login.navigateNormalLogin
import com.kusitms.connectdog.feature.login.onLogoutClick
import com.kusitms.connectdog.feature.management.navigation.navigateManagement
import com.kusitms.connectdog.feature.mypage.navigation.navigateBadge
import com.kusitms.connectdog.feature.mypage.navigation.navigateBookmark
import com.kusitms.connectdog.feature.mypage.navigation.navigateEditProfile
import com.kusitms.connectdog.feature.mypage.navigation.navigateEditProfileImage
import com.kusitms.connectdog.feature.mypage.navigation.navigateManageAccount
import com.kusitms.connectdog.feature.mypage.navigation.navigateMypage
import com.kusitms.connectdog.feature.mypage.navigation.navigateNotification
import com.kusitms.connectdog.feature.mypage.navigation.navigateSetting
import com.kusitms.connectdog.signup.navigateCompleteSignUp
import com.kusitms.connectdog.signup.navigateIntermediatorInformation
import com.kusitms.connectdog.signup.navigateRegisterEmail
import com.kusitms.connectdog.signup.navigateRegisterPassword
import com.kusitms.connectdog.signup.navigateSelectProfileImage
import com.kusitms.connectdog.signup.navigateSignup
import com.kusitms.connectdog.signup.navigateToIntermediatorProfile
import com.kusitms.connectdog.signup.navigateToVolunteerProfile

internal class MainNavigator(
    val navController: NavHostController,
    mode: AppMode
) {
    private val currentDestination: NavDestination?
        @Composable get() = navController.currentBackStackEntryAsState().value?.destination

    val startDestination = when (mode) {
        AppMode.VOLUNTEER -> MainTab.HOME.route
        AppMode.INTERMEDIATOR -> IntermediatorRoute.route
        AppMode.LOGIN -> LoginRoute.route
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
    fun onLogoutClick() = navController.onLogoutClick()

    // signup navigator
    fun navigateVolunteerProfile(userType: UserType) = navController.navigateToVolunteerProfile(userType)
    fun navigateIntermediatorProfile() = navController.navigateToIntermediatorProfile()
    fun navigateRegisterEmail(userType: UserType) = navController.navigateRegisterEmail(userType)
    fun navigateRegisterPassword(userType: UserType) = navController.navigateRegisterPassword(userType)
    fun navigateSelectProfileImage() = navController.navigateSelectProfileImage()
    fun navigateCompleteSignUp(userType: UserType) = navController.navigateCompleteSignUp(userType)
    fun navigateIntermediatorInformation() = navController.navigateIntermediatorInformation()

    // volunteer navigator
    fun navigateHome() = navigate(MainTab.HOME)
    fun navigateHomeSearch() = navController.navigateSearch()
    fun navigateHomeSearchWithFilter(filter: Filter) = navController.navigateSearchWithFilter(filter)
    fun navigateHomeFilterSearch() = navController.navigateFilterSearch()
    fun navigateHomeFilter(filter: Filter) = navController.navigateFilter(filter)
    fun navigateHomeReview() = navController.navigateReview()
    fun navigateHomeDetail(postId: Long) = navController.navigateDetail(postId)
    fun navigateCertification(postId: Long) = navController.navigateCertification(postId)
    fun navigateApply(postId: Long) = navController.navigateApply(postId)
    fun navigateComplete() = navController.navigateComplete()
    fun navigateIntermediatorProfile(intermediaryId: Long) = navController.navigateIntermediatorProfile(intermediaryId)
    fun navigateEditProfile() = navController.navigateEditProfile()
    fun navigateManageAccount() = navController.navigateManageAccount()
    fun navigateNotification() = navController.navigateNotification()
    fun navigateSetting() = navController.navigateSetting()
    fun navigateBadge() = navController.navigateBadge()
    fun navigateBookmark() = navController.navigateBookmark()
    fun navigateEditProfileImage() = navController.navigateEditProfileImage()

    // intermediator
    fun navigateIntermediatorHome() = navController.navigateIntermediatorHome()

    fun popBackStackIfNotHome() {
        if (!isSameCurrentDestination(HomeRoute.route)) {
            navController.popBackStack()
        }
    }

    private fun isSameCurrentDestination(route: String) =
        navController.currentDestination?.route == route

    @Composable
    fun shouldShowBottomBar(): Boolean {
        val currentRoute = currentDestination?.route ?: return false
        return currentRoute in MainTab
    }
}

@Composable
internal fun rememberMainNavigator(
    navController: NavHostController = rememberNavController(),
    mode: AppMode
): MainNavigator =
    remember(navController) {
        MainNavigator(navController, mode)
    }
