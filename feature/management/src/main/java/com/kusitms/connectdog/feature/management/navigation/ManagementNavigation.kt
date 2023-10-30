package com.kusitms.connectdog.feature.management.navigation

import androidx.navigation.NavController
import androidx.navigation.NavOptions

fun NavController.navigateManagement(navOptions: NavOptions) {
    navigate(ManagementRoute.route, navOptions)
}


object ManagementRoute {
    const val route = "management"
}