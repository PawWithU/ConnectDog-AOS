package com.kusitms.connectdog.feature.intermediator.navigation

import androidx.navigation.NavController
import androidx.navigation.NavOptions

fun NavController.navigateIntermediatorHome(navOptions: NavOptions) {
    navigate(IntermediatorRoute.route, navOptions)
}

object IntermediatorRoute {
    const val route = "intermediator"
}
