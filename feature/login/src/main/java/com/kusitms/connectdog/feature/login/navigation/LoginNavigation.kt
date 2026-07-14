package com.kusitms.connectdog.feature.login.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.kusitms.connectdog.core.util.AccountType
import com.kusitms.connectdog.core.util.UserType
import com.kusitms.connectdog.feature.login.screen.EmailAuthForPasswordResetScreen
import com.kusitms.connectdog.feature.login.screen.EmailSearchResultScreen
import com.kusitms.connectdog.feature.login.screen.EmailSearchScreen
import com.kusitms.connectdog.feature.login.screen.LoginRoute
import com.kusitms.connectdog.feature.login.screen.NoAccountScreen
import com.kusitms.connectdog.feature.login.screen.NormalLoginScreen
import com.kusitms.connectdog.feature.login.screen.PasswordResetScreen

fun NavController.navigateToLoginRoute() {
    navigate(LoginRoute.ROUTE) {
        popUpTo(graph.id) {
            inclusive = true
        }
    }
}

fun NavController.navigateNormalLogin(userType: UserType) {
    navigate("${LoginRoute.NORMAL_LOGIN}/$userType")
}

fun NavController.navigateEmailSearch(userType: UserType) {
    navigate("${LoginRoute.EMAIL_SEARCH}/$userType")
}

fun NavController.navigateEmailSearchComplete(email: String) {
    navigate("${LoginRoute.email_search_complete}/$email")
}

fun NavController.navigatePasswordSearchAuth(userType: UserType) {
    navigate("${LoginRoute.password_search_auth}/$userType")
}

fun NavController.navigatePasswordSearch(userType: UserType) {
    navigate("${LoginRoute.PASSWORD_SEARCH}/$userType")
}

fun NavController.navigateToNoAccount(accountType: AccountType) {
    navigate("${LoginRoute.NO_ACCOUNT}/$accountType")
}

fun NavGraphBuilder.loginNavGraph(
    imeHeight: Int,
    finish: () -> Unit,
    onBackClick: () -> Unit,
    onNavigateToNormalLogin: (UserType) -> Unit,
    onNavigateToVolunteer: () -> Unit,
    onNavigateToIntermediatorHome: () -> Unit,
    onNavigateToSignup: (UserType) -> Unit,
    onNavigateToEmailSearch: (UserType) -> Unit,
    onNavigateToPasswordSearch: (UserType) -> Unit,
    onNavigateToEmailSearchComplete: (String) -> Unit,
    onNavigateToPasswordSearchAuth: (UserType) -> Unit,
    onNavigateToLoginRoute: () -> Unit,
    onSendMessage: (String) -> Unit,
    onVerifyCode: (String, (Boolean) -> Unit) -> Unit,
    onNavigateToNoAccount: (AccountType) -> Unit,
) {
    composable(route = LoginRoute.ROUTE) {
        LoginRoute(
            finish,
            onNavigateToNormalLogin,
            onNavigateToSignup,
            onNavigateToVolunteer,
            onNavigateToIntermediatorHome,
            onNavigateToEmailSearch,
            onNavigateToPasswordSearchAuth,
            imeHeight = imeHeight,
        )
    }

    composable(
        route = "${LoginRoute.NORMAL_LOGIN}/{type}",
        arguments =
            listOf(
                navArgument("type") {
                    type = NavType.EnumType(UserType::class.java)
                },
            ),
    ) {
        NormalLoginScreen(
            onBackClick = onBackClick,
            onNavigateToSignUp = onNavigateToSignup,
            onNavigateToVolunteerHome = onNavigateToVolunteer,
            onNavigateToEmailSearch = onNavigateToEmailSearch,
            onNavigateToPasswordSearch = onNavigateToPasswordSearchAuth,
            imeHeight = imeHeight,
        )
    }

    composable(
        route = "${LoginRoute.EMAIL_SEARCH}/{type}",
        arguments =
            listOf(
                navArgument("type") {
                    type = NavType.EnumType(UserType::class.java)
                },
            ),
    ) {
        EmailSearchScreen(
            imeHeight = imeHeight,
            onBackClick = onBackClick,
            navigateToCompleteScreen = onNavigateToEmailSearchComplete,
            userType = it.arguments!!.getSerializable("type") as UserType,
            onSendMessageClick = onSendMessage,
            onVerifyCodeClick = onVerifyCode,
        )
    }

    composable(
        route = "${LoginRoute.email_search_complete}/{email}",
        arguments =
            listOf(
                navArgument("email") { type = NavType.StringType },
            ),
    ) {
        it.arguments!!.getString("email")?.let { email ->
            EmailSearchResultScreen(
                onBackClick = onBackClick,
                email = email,
                navigateToLoginRoute = onNavigateToLoginRoute,
            )
        }
    }

    composable(
        route = "${LoginRoute.PASSWORD_SEARCH}/{type}",
        arguments =
            listOf(
                navArgument("type") {
                    type = NavType.EnumType(UserType::class.java)
                },
            ),
    ) {
        PasswordResetScreen(
            onBackClick = onBackClick,
            userType = it.arguments!!.getSerializable("type") as UserType,
            imeHeight = imeHeight,
            navigateToLoginRoute = onNavigateToLoginRoute,
        )
    }

    composable(
        route = "${LoginRoute.password_search_auth}/{type}",
        arguments =
            listOf(
                navArgument("type") {
                    type = NavType.EnumType(UserType::class.java)
                },
            ),
    ) {
        EmailAuthForPasswordResetScreen(
            onBackClick = onBackClick,
            imeHeight = imeHeight,
            onNavigateToPasswordSearch = onNavigateToPasswordSearch,
            onNavigateToNoAccount = onNavigateToNoAccount,
            userType = it.arguments!!.getSerializable("type") as UserType,
        )
    }

    composable(
        route = "${LoginRoute.NO_ACCOUNT}/{accountType}",
        arguments =
            listOf(
                navArgument("accountType") {
                    type = NavType.EnumType(AccountType::class.java)
                },
            ),
    ) {
        NoAccountScreen(
            accountType = it.arguments!!.getSerializable("accountType") as AccountType,
            onNavigateToLoginRoute = onNavigateToLoginRoute,
        )
    }
}

object LoginRoute {
    const val ROUTE = "login"
    const val NORMAL_LOGIN = "normal_login"
    const val EMAIL_SEARCH = "email_search"
    const val PASSWORD_SEARCH = "password_search"
    const val email_search_complete = "email_search_complete"
    const val password_search_auth = "password_search_auth"
    const val NO_ACCOUNT = "no_account"
}
