package com.kusitms.connectdog.feature.main

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.kusitms.connectdog.feature.home.navigation.HomeRoute
import com.kusitms.connectdog.feature.home.navigation.navigateApply
import com.kusitms.connectdog.feature.home.navigation.navigateCertification
import com.kusitms.connectdog.feature.home.navigation.navigateComplete
import com.kusitms.connectdog.feature.home.navigation.navigateDetail
import com.kusitms.connectdog.feature.home.navigation.navigateHome
import com.kusitms.connectdog.feature.home.navigation.navigateIntermediatorProfile
import com.kusitms.connectdog.feature.home.navigation.navigateReview
import com.kusitms.connectdog.feature.home.navigation.navigateSearch
import com.kusitms.connectdog.feature.management.navigation.navigateManagement
import com.kusitms.connectdog.feature.mypage.navigation.navigateMypage

internal class MainNavigator(
    val navController: NavHostController
) {
    private val currentDestination: NavDestination?
        @Composable get() =
            navController
                .currentBackStackEntryAsState().value?.destination

    val startDestination = MainTab.HOME.route

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

    fun navigateHomeSearch() {
        navController.navigateSearch()
    }

    fun navigateHomeReview() {
        navController.navigateReview()
    }

    fun navigateHomeDetail() {
        navController.navigateDetail()
    }

    fun navigateCertification() {
        navController.navigateCertification()
    }

    fun navigateApply() {
        navController.navigateApply()
    }

    fun popBackStackIfNotHome() {
        if (!isSameCurrentDestination(HomeRoute.route)) {
            navController.popBackStack()
        }
    }

    fun navigateComplete() {
        navController.navigateComplete()
    }

    fun navigateIntermediatorProfile() {
        navController.navigateIntermediatorProfile()
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
internal fun rememberMainNavigator(navController: NavHostController = rememberNavController()): MainNavigator =
    remember(navController) {
        MainNavigator(navController)
    }
