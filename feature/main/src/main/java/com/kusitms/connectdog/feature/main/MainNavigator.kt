package com.kusitms.connectdog.feature.main

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
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
import com.kusitms.connectdog.feature.login.LoginRoute
import com.kusitms.connectdog.feature.login.navigateNormalLogin
import com.kusitms.connectdog.feature.login.navigateSignup
import com.kusitms.connectdog.feature.management.navigation.navigateManagement
import com.kusitms.connectdog.feature.mypage.navigation.navigateBadge
import com.kusitms.connectdog.feature.mypage.navigation.navigateBookmark
import com.kusitms.connectdog.feature.mypage.navigation.navigateEditProfile
import com.kusitms.connectdog.feature.mypage.navigation.navigateManageAccount
import com.kusitms.connectdog.feature.mypage.navigation.navigateMypage
import com.kusitms.connectdog.feature.mypage.navigation.navigateNotification
import com.kusitms.connectdog.feature.mypage.navigation.navigateSetting

enum class Mode {
    LOGIN,
    VOLUNTEER,
    INTERMEDIATOR
}

internal class MainNavigator(
    val navController: NavHostController,
    type: Mode
) {
    private val currentDestination: NavDestination?
        @Composable get() =
            navController
                .currentBackStackEntryAsState().value?.destination

    val startDestination = when (type) {
        Mode.VOLUNTEER -> MainTab.HOME.route
        Mode.INTERMEDIATOR -> MainTab.HOME.route
        Mode.LOGIN -> LoginRoute.route
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
    fun navigateNormalLogin() = navController.navigateNormalLogin()
    fun navigateSignup() = navController.navigateSignup()

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
internal fun rememberMainNavigator(navController: NavHostController = rememberNavController(), type: Mode): MainNavigator =
    remember(navController) {
        MainNavigator(navController, type)
    }
