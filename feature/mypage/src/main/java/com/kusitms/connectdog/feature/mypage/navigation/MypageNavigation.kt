package com.kusitms.connectdog.feature.mypage.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.kusitms.connectdog.feature.mypage.MypageRoute

fun NavController.navigateMypage(navOptions: NavOptions) {
    navigate(MypageRoute.route, navOptions)
}

fun NavGraphBuilder.mypageNavGraph(
    padding: PaddingValues,
    onClick: () -> Unit,
    onShowErrorSnackbar: (throwable: Throwable?) -> Unit,
) {
    composable(route = MypageRoute.route) {
        MypageRoute(
            padding,
            onClick,
            onShowErrorSnackbar,
        )
    }
}

object MypageRoute {
    const val route = "mypage"
}
