package com.kusitms.connectdog.feature.intermediator.navigation

import android.util.Log
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.kusitms.connectdog.feature.intermediator.screen.InterManagementRoute
import com.kusitms.connectdog.feature.intermediator.screen.IntermediatorHomeScreen

fun NavController.navigateIntermediatorHome() {
    navigate(IntermediatorRoute.route)
}

fun NavController.navigateInterManagement(tabIndex: Int) {
    Log.d("InterNavigation", "navigateInterManagement: tabIndex = $tabIndex")
    val route = "${IntermediatorRoute.management}?tabIndex=$tabIndex"
    navigate(route)
}

fun NavGraphBuilder.intermediatorNavGraph(
    onBackClick: () -> Unit,
    onSettingClick: () -> Unit
) {
    composable(route = IntermediatorRoute.route) {
        IntermediatorHomeScreen(
            onNotificationClick = { },
            onSettingClick = onSettingClick,
            onDataClick = { }
//                    index -> navigator.navigateInterManagement(index)
        )
    }

    composable(
        "${IntermediatorRoute.management}?tabIndex={tabIndex}",
        arguments = listOf(navArgument("tabIndex") { defaultValue = 0 })
    ) {
        InterManagementRoute(
            onBackClick = onBackClick,
            tabIndex = it.arguments?.getInt("tabIndex") ?: 0
        )
    }
}

object IntermediatorRoute {
    const val route = "inter_home"
    const val management = "management"
}
