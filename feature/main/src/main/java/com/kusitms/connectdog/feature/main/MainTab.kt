package com.kusitms.connectdog.feature.main

import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import com.kusitms.connectdog.feature.home.navigation.HomeRoute
import com.kusitms.connectdog.feature.management.navigation.ManagementRoute
import com.kusitms.connectdog.feature.mypage.navigation.MypageRoute
import kotlin.reflect.KClass

internal enum class MainTab(
    val iconResId: Int,
    internal val contentDescription: String,
    val route: KClass<out Any>,
) {
    HOME(
        iconResId = R.drawable.ic_home,
        contentDescription = "홈",
        route = HomeRoute.Home::class,
    ),
    MANAGEMENT(
        iconResId = R.drawable.ic_list,
        contentDescription = "봉사 관리",
        route = ManagementRoute.Management::class,
    ),
    MYPAGE(
        iconResId = R.drawable.ic_profile,
        contentDescription = "마이페이지",
        route = MypageRoute.Mypage::class,
    ),
    ;

    companion object {
        fun find(destination: NavDestination?): MainTab? {
            return values().find { tab -> destination?.hasRoute(tab.route) == true }
        }
    }
}
