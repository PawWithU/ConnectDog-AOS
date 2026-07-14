package com.kusitms.connectdog.signup.navigation

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
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

fun NavController.navigateSignup(userType: UserType) = navigate("${SignUpRoute.ROUTE}/$userType")

fun NavController.navigateToIntermediatorProfile() = navigate(SignUpRoute.INTERMEDIATOR_PROFILE)

fun NavController.navigateToCertification() = navigate(SignUpRoute.CERTIFICATION)

fun NavController.navigateToVolunteerProfile() = navigate(SignUpRoute.VOLUNTEER_PROFILE)

fun NavController.navigateRegisterEmail() = navigate(SignUpRoute.REGISTER_EMAIL)

fun NavController.navigateRegisterPassword() = navigate(SignUpRoute.REGISTER_PASSWORD)

fun NavController.navigateSelectProfileImage() = navigate(SignUpRoute.SELECT_PROFILE_IMAGE)

fun NavController.navigateIntermediatorInformation() = navigate(SignUpRoute.INTERMEDIATOR_INFORMATION)

fun NavController.navigateCompleteSignUp() =
    navigate(
        route = SignUpRoute.COMPLETE_SIGNUP,
        navOptions =
            NavOptions.Builder()
                .setPopUpTo(SignUpRoute.ROUTE, false)
                .build(),
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
    val signUpRoute = "${SignUpRoute.ROUTE}/{userType}"

    composable(
        route = signUpRoute,
        arguments =
            listOf(
                navArgument("userType") {
                    type = NavType.EnumType(UserType::class.java)
                },
            ),
    ) {
        SignUpRoute(
            onBackClick = navigateToLogin,
            userType = it.arguments?.getSerializable("userType", UserType::class.java) ?: UserType.NORMAL_VOLUNTEER,
            navigateToCertification = navigateToCertification,
            openWebBrowser = openWebBrowser,
            signUpViewModel = hiltViewModel(it),
        )
    }

    composable(route = SignUpRoute.CERTIFICATION) {
        CertificationScreen(
            onBackClick = onBackClick,
            onNavigateToRegisterEmail = navigateToRegisterEmail,
            onNavigateToVolunteerProfile = navigateToVolunteerProfile,
            onSendMessageClick = onSendMessage,
            onVerifyCodeClick = onVerifyCode,
            imeHeight = imeHeight,
            viewModel = hiltViewModel(navController.getBackStackEntry(signUpRoute)),
        )
    }

    composable(route = SignUpRoute.REGISTER_EMAIL) {
        RegisterEmailScreen(
            onBackClick = onBackClick,
            onNavigateToRegisterPassword = navigateToRegisterPassword,
            imeHeight = imeHeight,
            viewModel = hiltViewModel(navController.getBackStackEntry(signUpRoute)),
        )
    }

    composable(route = SignUpRoute.REGISTER_PASSWORD) {
        RegisterPasswordScreen(
            onBackClick = onBackClick,
            onNavigateToIntermediatorProfile = navigateToIntermediatorProfile,
            onNavigateToVolunteerProfile = navigateToVolunteerProfile,
            imeHeight = imeHeight,
            viewModel = hiltViewModel(navController.getBackStackEntry(signUpRoute)),
        )
    }

    composable(route = SignUpRoute.VOLUNTEER_PROFILE) {
        VolunteerProfileScreen(
            onBackClick = onBackClick,
            onNavigateToSelectProfileImage = navigateToSelectProfileImage,
            onNavigateToCompleteSignUp = navigateToCompleteSignUp,
            imeHeight = imeHeight,
            viewModel = hiltViewModel(navController.getBackStackEntry(signUpRoute)),
        )
    }

    composable(route = SignUpRoute.INTERMEDIATOR_PROFILE) {
        IntermediatorProfileScreen(
            onBackClick = onBackClick,
            imeHeight = imeHeight,
            navigateToIntermediatorInfo = navigateToIntermediatorInformation,
            viewModel = hiltViewModel(navController.getBackStackEntry(signUpRoute)),
        )
    }

    composable(route = SignUpRoute.INTERMEDIATOR_INFORMATION) {
        IntermediatorInformationScreen(
            onBackClick = onBackClick,
            imeHeight = imeHeight,
            onNavigateToCompleteSignUp = navigateToCompleteSignUp,
            signUpViewModel = hiltViewModel(navController.getBackStackEntry(signUpRoute)),
        )
    }

    composable(route = SignUpRoute.SELECT_PROFILE_IMAGE) {
        SelectProfileImageScreen(
            onBackClick = onBackClick,
            signUpViewModel = hiltViewModel(navController.getBackStackEntry(signUpRoute)),
        )
    }

    composable(
        route = SignUpRoute.COMPLETE_SIGNUP,
    ) {
        CompleteSignUpScreen(
            viewModel = hiltViewModel(navController.getBackStackEntry(signUpRoute)),
            navigateToVolunteerHome = navigateToVolunteerHome,
            navigateToIntermediatorHome = navigateToIntermediatorHome,
        )
    }
}

object SignUpRoute {
    const val ROUTE = "sign_up"
    const val VOLUNTEER_PROFILE = "volunteer_profile"
    const val INTERMEDIATOR_PROFILE = "intermediator_profile"
    const val INTERMEDIATOR_INFORMATION = "intermediator_information"
    const val REGISTER_EMAIL = "register_email"
    const val REGISTER_PASSWORD = "register_password"
    const val SELECT_PROFILE_IMAGE = "profile_image"
    const val COMPLETE_SIGNUP = "complete_signup"
    const val CERTIFICATION = "certification"
}
