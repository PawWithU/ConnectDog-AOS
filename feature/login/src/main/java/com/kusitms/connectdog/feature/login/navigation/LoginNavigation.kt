package com.kusitms.connectdog.feature.login.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.kusitms.connectdog.core.util.AccountType
import com.kusitms.connectdog.core.util.UserType
import com.kusitms.connectdog.feature.login.screen.EmailAuthForPasswordResetScreen
import com.kusitms.connectdog.feature.login.screen.EmailSearchResultScreen
import com.kusitms.connectdog.feature.login.screen.EmailSearchScreen
import com.kusitms.connectdog.feature.login.screen.LoginRoute
import com.kusitms.connectdog.feature.login.screen.NoAccountScreen
import com.kusitms.connectdog.feature.login.screen.NormalLoginScreen
import com.kusitms.connectdog.feature.login.screen.PasswordResetScreen
import kotlinx.serialization.Serializable

fun NavController.navigateToLoginRoute() {
    navigate(LoginRoute.Login) {
        popUpTo(graph.id) {
            inclusive = true
        }
    }
}

fun NavController.navigateNormalLogin(userType: UserType) {
    navigate(LoginRoute.NormalLogin(userType))
}

fun NavController.navigateEmailSearch(userType: UserType) {
    navigate(LoginRoute.EmailSearch(userType))
}

fun NavController.navigateEmailSearchComplete(email: String) {
    navigate(LoginRoute.EmailSearchComplete(email))
}

fun NavController.navigatePasswordSearchAuth(userType: UserType) {
    navigate(LoginRoute.PasswordSearchAuth(userType))
}

fun NavController.navigatePasswordSearch(userType: UserType) {
    navigate(LoginRoute.PasswordSearch(userType))
}

fun NavController.navigateToNoAccount(accountType: AccountType) {
    navigate(LoginRoute.NoAccount(accountType))
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
    composable<LoginRoute.Login> {
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

    composable<LoginRoute.NormalLogin> { backStackEntry ->
        val route: LoginRoute.NormalLogin = backStackEntry.toRoute()
        NormalLoginScreen(
            onBackClick = onBackClick,
            onNavigateToSignUp = onNavigateToSignup,
            onNavigateToVolunteerHome = onNavigateToVolunteer,
            onNavigateToEmailSearch = onNavigateToEmailSearch,
            onNavigateToPasswordSearch = onNavigateToPasswordSearchAuth,
            imeHeight = imeHeight,
        )
    }

    composable<LoginRoute.EmailSearch> { backStackEntry ->
        val route: LoginRoute.EmailSearch = backStackEntry.toRoute()
        EmailSearchScreen(
            imeHeight = imeHeight,
            onBackClick = onBackClick,
            navigateToCompleteScreen = onNavigateToEmailSearchComplete,
            userType = route.userType,
            onSendMessageClick = onSendMessage,
            onVerifyCodeClick = onVerifyCode,
        )
    }

    composable<LoginRoute.EmailSearchComplete> { backStackEntry ->
        val route: LoginRoute.EmailSearchComplete = backStackEntry.toRoute()
        EmailSearchResultScreen(
            onBackClick = onBackClick,
            email = route.email,
            navigateToLoginRoute = onNavigateToLoginRoute,
        )
    }

    composable<LoginRoute.PasswordSearch> { backStackEntry ->
        val route: LoginRoute.PasswordSearch = backStackEntry.toRoute()
        PasswordResetScreen(
            onBackClick = onBackClick,
            userType = route.userType,
            imeHeight = imeHeight,
            navigateToLoginRoute = onNavigateToLoginRoute,
        )
    }

    composable<LoginRoute.PasswordSearchAuth> { backStackEntry ->
        val route: LoginRoute.PasswordSearchAuth = backStackEntry.toRoute()
        EmailAuthForPasswordResetScreen(
            onBackClick = onBackClick,
            imeHeight = imeHeight,
            onNavigateToPasswordSearch = onNavigateToPasswordSearch,
            onNavigateToNoAccount = onNavigateToNoAccount,
            userType = route.userType,
        )
    }

    composable<LoginRoute.NoAccount> { backStackEntry ->
        val route: LoginRoute.NoAccount = backStackEntry.toRoute()
        NoAccountScreen(
            accountType = route.accountType,
            onNavigateToLoginRoute = onNavigateToLoginRoute,
        )
    }
}

object LoginRoute {
    @Serializable
    data object Login

    @Serializable
    data class NormalLogin(val userType: UserType)

    @Serializable
    data class EmailSearch(val userType: UserType)

    @Serializable
    data class PasswordSearch(val userType: UserType)

    @Serializable
    data class EmailSearchComplete(val email: String)

    @Serializable
    data class PasswordSearchAuth(val userType: UserType)

    @Serializable
    data class NoAccount(val accountType: AccountType)
}
