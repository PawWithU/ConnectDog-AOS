package com.kusitms.connectdog.feature.login

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.kusitms.connectdog.core.util.UserType
import com.kusitms.connectdog.feature.login.screen.LoginRoute
import com.kusitms.connectdog.feature.login.screen.NormalLoginScreen

fun NavController.onLogoutClick() {
    navigate(LoginRoute.route) {
        popUpTo(graph.id) {
            inclusive = true
        }
    }
}

fun NavController.navigateNormalLogin(userType: UserType) {
    navigate("${LoginRoute.normal_login}/$userType")
}

fun NavGraphBuilder.loginNavGraph(
    onBackClick: () -> Unit,
    onNavigateToNormalLogin: (UserType) -> Unit,
    onNavigateToVolunteer: () -> Unit,
    onNavigateToIntermediatorHome: () -> Unit,
    onNavigateToSignup: (UserType) -> Unit
) {
    composable(route = LoginRoute.route) {
        LoginRoute(
            onNavigateToNormalLogin,
            onNavigateToSignup,
            onNavigateToVolunteer
        )
    }

    composable(
        route = "${LoginRoute.normal_login}/{type}",
        arguments = listOf(
            navArgument("type") {
                type = NavType.EnumType(UserType::class.java)
            }
        )
    ) {
        NormalLoginScreen(
            onBackClick = onBackClick,
            userType = it.arguments!!.getSerializable("type") as UserType,
            onNavigateToVolunteerHome = onNavigateToVolunteer,
            onNavigateToIntermediatorHome = onNavigateToIntermediatorHome
        )
    }
}

object LoginRoute {
    const val route = "login"
    const val normal_login = "normal_login"
}
