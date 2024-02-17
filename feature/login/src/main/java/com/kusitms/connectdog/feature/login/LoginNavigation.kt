package com.kusitms.connectdog.feature.login

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.kusitms.connectdog.feature.signup.VolunteerSignupScreen

fun NavController.navigateLogin() {
    navigate(LoginRoute.route)
}

fun NavController.navigateSignup() {
    navigate(LoginRoute.signup)
}

fun NavController.navigateNormalLogin() {
    navigate(LoginRoute.normal_login)
}

fun NavGraphBuilder.loginNavGraph(
    onBackClick: () -> Unit,
    onNavigateToNormalLogin: () -> Unit,
    onNavigateToVolunteer: () -> Unit,
    onNavigateToSignup: () -> Unit
) {
    composable(route = LoginRoute.route) {
        LoginRoute(
            onNavigateToNormalLogin,
            onNavigateToSignup
        )
    }

    composable(route = LoginRoute.normal_login) {
        NormalLoginScreen(
            onBackClick = onBackClick,
            initVolunteer = {},
            initIntermediator = {},
            test = onNavigateToVolunteer
        )
    }

    composable(route = LoginRoute.signup) {
        VolunteerSignupScreen(
//            onBackClick = onBackClick
        )
    }
}

object LoginRoute {
    const val route = "login"
    const val normal_login = "normal_login"
    const val signup = "signup"
}
