package com.kusitms.connectdog.feature.home.navigation

import android.util.Log
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.kusitms.connectdog.feature.home.screen.HomeRoute
import com.kusitms.connectdog.feature.home.screen.SearchScreen

fun NavController.navigateHome(navOptions: NavOptions) {
    navigate(HomeRoute.route, navOptions)
}

fun NavController.navigateSearch() {
    Log.d("SearchScreen", "navigateSearch")
    navigate(HomeRoute.search)
}

fun NavGraphBuilder.homeNavGraph(
    onBackClick: () -> Unit,
    onSearchIconClick: () -> Unit,
    onShowErrorSnackBar: (throwable: Throwable?) -> Unit
) {
    composable(route = HomeRoute.route) {
        HomeRoute(
            onSearchIconClick,
            onShowErrorSnackBar
        )
    }

    composable(route = HomeRoute.search) {
        SearchScreen(
            onBackClick = onBackClick
        )
    }
}

object HomeRoute {
    const val route = "home"
    const val main = "main"
    const val search = "search"
}
