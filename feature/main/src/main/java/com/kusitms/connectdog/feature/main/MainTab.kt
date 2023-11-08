package com.kusitms.connectdog.feature.main

import com.kusitms.connectdog.feature.home.navigation.HomeRoute
import com.kusitms.connectdog.feature.management.navigation.ManagementRoute
import com.kusitms.connectdog.feature.mypage.navigation.MypageRoute

internal enum class MainTab(
    val iconResId: Int,
    internal val contentDescription: String,
    val route: String
) {
    HOME(
        iconResId = R.drawable.ic_home,
        contentDescription = "홈",
        route = HomeRoute.route
    ),
    MANAGEMENT(
        iconResId = R.drawable.ic_paw,
        contentDescription = "신청 관리",
        route = ManagementRoute.route
    ),
    MYPAGE(
        iconResId = R.drawable.ic_profile,
        contentDescription = "마이페이지",
        route = MypageRoute.route
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
