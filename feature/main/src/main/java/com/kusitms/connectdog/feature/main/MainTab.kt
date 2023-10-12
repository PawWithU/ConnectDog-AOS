package com.kusitms.connectdog.feature.main

import com.kusitms.connectdog.feature.home.navigation.HomeRoute

internal enum class MainTab(
    val iconResId: Int,
    internal val contentDescription: String,
    val route: String
) {
    HOME(
        iconResId = R.drawable.ic_home,
        contentDescription = "홈",
        route = HomeRoute.route
    )
    ;

    companion object {
        operator fun contains(route: String): Boolean {
            return values().map { it.route }.contains(route)
        }

        fun find(route: String): MainTab? {
            return values().find { it.route == route }
        }
    }
}
