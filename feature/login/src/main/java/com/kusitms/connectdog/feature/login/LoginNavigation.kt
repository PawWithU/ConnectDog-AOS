package com.kusitms.connectdog.feature.login

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.kusitms.connectdog.core.util.Type
import com.kusitms.connectdog.signup.SignUpRoute

fun NavController.navigateLogin() {
    navigate(LoginRoute.route)
}

fun NavController.navigateSignup(type: Type) {
    navigate("${SignUpRoute.route}/$type")
}

fun NavController.navigateNormalLogin(type: Type) {
    navigate("${LoginRoute.normal_login}/$type")
}

fun NavGraphBuilder.loginNavGraph(
    onBackClick: () -> Unit,
    onNavigateToNormalLogin: (Type) -> Unit,
    onNavigateToVolunteer: () -> Unit,
    onNavigateToSignup: (Type) -> Unit
) {
    composable(route = LoginRoute.route) {
        LoginRoute(
            onNavigateToNormalLogin,
            onNavigateToSignup
        )
    }

    composable(
        route = "${LoginRoute.normal_login}/{type}",
        arguments = listOf(
            navArgument("type") {
                type = NavType.EnumType(Type::class.java)
            }
        )
    ) {
        NormalLoginScreen(
            onBackClick = onBackClick,
            type = it.arguments!!.getSerializable("type") as Type,
            test = onNavigateToVolunteer
        )
    }

    composable(route = LoginRoute.signup) {
//        SignUpSc(
// //            onBackClick = onBackClick
//        )
    }
}

object LoginRoute {
    const val route = "login"
    const val normal_login = "normal_login"
    const val signup = "signup"
}
