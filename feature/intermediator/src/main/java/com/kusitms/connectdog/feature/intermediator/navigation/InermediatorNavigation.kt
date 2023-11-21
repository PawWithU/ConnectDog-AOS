package com.kusitms.connectdog.feature.intermediator.navigation

import android.util.Log
import androidx.navigation.NavController
import androidx.navigation.NavOptions

fun NavController.navigateIntermediatorHome(navOptions: NavOptions) {
    navigate(IntermediatorRoute.route, navOptions)
}

fun NavController.navigateInterManagement(tabIndex: Int){
    Log.d("InterNavigation", "navigateInterManagement: tabIndex = $tabIndex")
    val route = "${IntermediatorRoute.management}?tabIndex=$tabIndex"
    navigate(route)
}

object IntermediatorRoute {
    const val route = "intermediator"
    const val home = "home"
    const val management = "management"
}
