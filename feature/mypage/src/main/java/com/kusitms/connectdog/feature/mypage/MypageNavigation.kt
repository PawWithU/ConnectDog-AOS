package com.kusitms.connectdog.feature.mypage

import androidx.navigation.NavController
import androidx.navigation.NavOptions

fun NavController.navigateMypage(navOptions: NavOptions) {
    navigate(MypageRoute.route, navOptions)
}


object MypageRoute {
    const val route = "mypage"
}