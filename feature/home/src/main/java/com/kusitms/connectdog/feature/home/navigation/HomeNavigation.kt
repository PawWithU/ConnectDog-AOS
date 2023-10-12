package com.kusitms.connectdog.feature.home.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.kusitms.connectdog.feature.home.HomeRoute

fun NavController.navigateHome(navOptions: NavOptions) {
    navigate(HomeRoute.route, navOptions)
}

fun NavGraphBuilder.homeNavGraph(
    padding: PaddingValues,
    onClick: () -> Unit,
    onShowErrorSnackBar: (throwable: Throwable?) -> Unit
) {
    composable(route = HomeRoute.route) {
        HomeRoute(
            padding,
            onClick,
            onShowErrorSnackBar
        )
    }
}

object HomeRoute {
    const val route = "home"
}
