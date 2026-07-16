package com.kusitms.connectdog.signup.navigation

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navOptions
import androidx.navigation.toRoute
import com.kusitms.connectdog.core.util.UserType
import com.kusitms.connectdog.signup.screen.common.CertificationScreen
import com.kusitms.connectdog.signup.screen.common.CompleteSignUpScreen
import com.kusitms.connectdog.signup.screen.common.RegisterEmailScreen
import com.kusitms.connectdog.signup.screen.common.RegisterPasswordScreen
import com.kusitms.connectdog.signup.screen.common.SignUpRoute
import com.kusitms.connectdog.signup.screen.intermediator.IntermediatorInformationScreen
import com.kusitms.connectdog.signup.screen.intermediator.IntermediatorProfileScreen
import com.kusitms.connectdog.signup.screen.volunteer.SelectProfileImageScreen
import com.kusitms.connectdog.signup.screen.volunteer.VolunteerProfileScreen
import kotlinx.serialization.Serializable

fun NavController.navigateSignup(userType: UserType) = navigate(SignUpRoute.SignUp(userType))

fun NavController.navigateToIntermediatorProfile() = navigate(SignUpRoute.IntermediatorProfile)

fun NavController.navigateToCertification() = navigate(SignUpRoute.Certification)

fun NavController.navigateToVolunteerProfile() = navigate(SignUpRoute.VolunteerProfile)

fun NavController.navigateRegisterEmail() = navigate(SignUpRoute.RegisterEmail)

fun NavController.navigateRegisterPassword() = navigate(SignUpRoute.RegisterPassword)

fun NavController.navigateSelectProfileImage() = navigate(SignUpRoute.SelectProfileImage)

fun NavController.navigateIntermediatorInformation() = navigate(SignUpRoute.IntermediatorInformation)

fun NavController.navigateCompleteSignUp() =
    navigate(
        route = SignUpRoute.CompleteSignUp,
        navOptions =
            navOptions {
                popUpTo<SignUpRoute.SignUp> { inclusive = false }
            },
    )

@SuppressLint("UnrememberedGetBackStackEntry")
@RequiresApi(Build.VERSION_CODES.TIRAMISU)
fun NavGraphBuilder.signUpGraph(
    navController: NavHostController,
    onBackClick: () -> Unit,
    navigateToVolunteerProfile: () -> Unit,
    navigateToIntermediatorProfile: () -> Unit,
    navigateToIntermediatorInformation: () -> Unit,
    navigateToRegisterEmail: () -> Unit,
    navigateToRegisterPassword: () -> Unit,
    navigateToSelectProfileImage: () -> Unit,
    navigateToCompleteSignUp: () -> Unit,
    navigateToVolunteerHome: () -> Unit,
    navigateToIntermediatorHome: () -> Unit,
    navigateToCertification: () -> Unit,
    navigateToLogin: () -> Unit,
    onSendMessage: (String) -> Unit,
    onVerifyCode: (String, (Boolean) -> Unit) -> Unit,
    openWebBrowser: (String) -> Unit,
    imeHeight: Int,
) {
    composable<SignUpRoute.SignUp> { backStackEntry ->
        val route: SignUpRoute.SignUp = backStackEntry.toRoute()
        SignUpRoute(
            onBackClick = navigateToLogin,
            userType = route.userType,
            navigateToCertification = navigateToCertification,
            openWebBrowser = openWebBrowser,
            signUpViewModel = hiltViewModel(backStackEntry),
        )
    }

    composable<SignUpRoute.Certification> {
        CertificationScreen(
            onBackClick = onBackClick,
            onNavigateToRegisterEmail = navigateToRegisterEmail,
            onNavigateToVolunteerProfile = navigateToVolunteerProfile,
            onSendMessageClick = onSendMessage,
            onVerifyCodeClick = onVerifyCode,
            imeHeight = imeHeight,
            viewModel = hiltViewModel(navController.getBackStackEntry<SignUpRoute.SignUp>()),
        )
    }

    composable<SignUpRoute.RegisterEmail> {
        RegisterEmailScreen(
            onBackClick = onBackClick,
            onNavigateToRegisterPassword = navigateToRegisterPassword,
            imeHeight = imeHeight,
            viewModel = hiltViewModel(navController.getBackStackEntry<SignUpRoute.SignUp>()),
        )
    }

    composable<SignUpRoute.RegisterPassword> {
        RegisterPasswordScreen(
            onBackClick = onBackClick,
            onNavigateToIntermediatorProfile = navigateToIntermediatorProfile,
            onNavigateToVolunteerProfile = navigateToVolunteerProfile,
            imeHeight = imeHeight,
            viewModel = hiltViewModel(navController.getBackStackEntry<SignUpRoute.SignUp>()),
        )
    }

    composable<SignUpRoute.VolunteerProfile> {
        VolunteerProfileScreen(
            onBackClick = onBackClick,
            onNavigateToSelectProfileImage = navigateToSelectProfileImage,
            onNavigateToCompleteSignUp = navigateToCompleteSignUp,
            imeHeight = imeHeight,
            viewModel = hiltViewModel(navController.getBackStackEntry<SignUpRoute.SignUp>()),
        )
    }

    composable<SignUpRoute.IntermediatorProfile> {
        IntermediatorProfileScreen(
            onBackClick = onBackClick,
            imeHeight = imeHeight,
            navigateToIntermediatorInfo = navigateToIntermediatorInformation,
            viewModel = hiltViewModel(navController.getBackStackEntry<SignUpRoute.SignUp>()),
        )
    }

    composable<SignUpRoute.IntermediatorInformation> {
        IntermediatorInformationScreen(
            onBackClick = onBackClick,
            imeHeight = imeHeight,
            onNavigateToCompleteSignUp = navigateToCompleteSignUp,
            signUpViewModel = hiltViewModel(navController.getBackStackEntry<SignUpRoute.SignUp>()),
        )
    }

    composable<SignUpRoute.SelectProfileImage> {
        SelectProfileImageScreen(
            onBackClick = onBackClick,
            signUpViewModel = hiltViewModel(navController.getBackStackEntry<SignUpRoute.SignUp>()),
        )
    }

    composable<SignUpRoute.CompleteSignUp> {
        CompleteSignUpScreen(
            viewModel = hiltViewModel(navController.getBackStackEntry<SignUpRoute.SignUp>()),
            navigateToVolunteerHome = navigateToVolunteerHome,
            navigateToIntermediatorHome = navigateToIntermediatorHome,
        )
    }
}

object SignUpRoute {
    @Serializable
    data class SignUp(val userType: UserType)

    @Serializable
    data object VolunteerProfile

    @Serializable
    data object IntermediatorProfile

    @Serializable
    data object IntermediatorInformation

    @Serializable
    data object RegisterEmail

    @Serializable
    data object RegisterPassword

    @Serializable
    data object SelectProfileImage

    @Serializable
    data object CompleteSignUp

    @Serializable
    data object Certification
}
