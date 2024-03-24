package com.kusitms.connectdog.signup

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.kusitms.connectdog.core.util.UserType
import com.kusitms.connectdog.signup.screen.CompleteSignUpScreen
import com.kusitms.connectdog.signup.screen.RegisterEmailScreen
import com.kusitms.connectdog.signup.screen.RegisterPasswordScreen
import com.kusitms.connectdog.signup.screen.SignUpRoute
import com.kusitms.connectdog.signup.screen.intermediator.IntermediatorInformationScreen
import com.kusitms.connectdog.signup.screen.intermediator.IntermediatorProfileScreen
import com.kusitms.connectdog.signup.screen.volunteer.SelectProfileImageScreen
import com.kusitms.connectdog.signup.screen.volunteer.VolunteerProfileScreen
import com.kusitms.connectdog.signup.viewmodel.SignUpViewModel
import com.kusitms.connectdog.signup.viewmodel.VolunteerProfileViewModel

fun NavController.navigateSignup(userType: UserType) {
    navigate("${SignUpRoute.route}/$userType")
}

fun NavController.navigateToIntermediatorProfile() {
    navigate(SignUpRoute.intermediator_profile)
}

fun NavController.navigateToVolunteerProfile(userType: UserType) {
    navigate("${SignUpRoute.volunteer_profile}/$userType")
}

fun NavController.navigateRegisterEmail(userType: UserType) {
    navigate("${SignUpRoute.register_email}/$userType")
}

fun NavController.navigateRegisterPassword(userType: UserType) {
    navigate("${SignUpRoute.register_password}/$userType")
}

fun NavController.navigateSelectProfileImage() {
    navigate(SignUpRoute.profile_image)
}

fun NavController.navigateCompleteSignUp(userType: UserType) {
    navigate("${SignUpRoute.complete_signup}/$userType")
}

fun NavController.navigateIntermediatorInformation() {
    navigate(SignUpRoute.intermediator_information)
}

fun NavGraphBuilder.signUpGraph(
    onBackClick: () -> Unit,
    navigateToVolunteerProfile: (UserType) -> Unit,
    navigateToIntermediatorProfile: () -> Unit,
    navigateToIntermediatorInformation: () -> Unit,
    navigateToRegisterEmail: (UserType) -> Unit,
    navigateToRegisterPassword: (UserType) -> Unit,
    navigateToSelectProfileImage: () -> Unit,
    navigateToCompleteSignUp: (UserType) -> Unit,
    navigateToVolunteer: () -> Unit,
    navigateToIntermediator: () -> Unit,
    imeHeight: Int,
    signUpViewModel: SignUpViewModel,
    profileViewModel: VolunteerProfileViewModel
) {
    val userTypeArgument = listOf(
        navArgument("type") {
            type = NavType.EnumType(UserType::class.java)
        }
    )

    composable(
        route = "${SignUpRoute.route}/{type}",
        arguments = userTypeArgument
    ) {
        SignUpRoute(
            onBackClick = onBackClick,
            userType = it.arguments!!.getSerializable("type") as UserType,
            navigateToVolunteerProfile = navigateToVolunteerProfile,
            navigateToIntermediatorInformation = navigateToIntermediatorInformation,
            navigateToRegisterEmail = navigateToRegisterEmail
        )
    }

    composable(
        route = "${SignUpRoute.register_email}/{type}",
        arguments = userTypeArgument
    ) {
        RegisterEmailScreen(
            onBackClick = onBackClick,
            userType = it.arguments!!.getSerializable("type") as UserType,
            onNavigateToRegisterPassword = navigateToRegisterPassword,
            signUpViewModel = signUpViewModel,
            imeHeight = imeHeight
        )
    }

    composable(
        route = "${SignUpRoute.register_password}/{type}",
        arguments = userTypeArgument
    ) {
        RegisterPasswordScreen(
            onBackClick = onBackClick,
            onNavigateToIntermediatorProfile = navigateToIntermediatorProfile,
            onNavigateToVolunteerProfile = navigateToVolunteerProfile,
            userType = it.arguments!!.getSerializable("type") as UserType,
            signUpViewModel = signUpViewModel,
            imeHeight = imeHeight
        )
    }

    composable(
        route = "${SignUpRoute.volunteer_profile}/{type}",
        arguments = userTypeArgument
    ) {
        VolunteerProfileScreen(
            onBackClick = onBackClick,
            onNavigateToSelectProfileImage = navigateToSelectProfileImage,
            onNavigateToCompleteSignUp = navigateToCompleteSignUp,
            imeHeight = imeHeight,
            signUpViewModel = signUpViewModel,
            viewModel = profileViewModel,
            userType = it.arguments!!.getSerializable("type") as UserType
        )
    }

    composable(route = SignUpRoute.intermediator_profile) {
        IntermediatorProfileScreen(
            onBackClick = onBackClick,
            imeHeight = imeHeight,
            navigateToCompleteSignUp = navigateToCompleteSignUp
        )
    }

    composable(route = SignUpRoute.intermediator_information) {
        IntermediatorInformationScreen(
            onBackClick = onBackClick,
            imeHeight = imeHeight,
            onNavigateToRegisterEmail = navigateToRegisterEmail
        )
    }

    composable(route = SignUpRoute.profile_image) {
        SelectProfileImageScreen(
            onBackClick = onBackClick,
            viewModel = profileViewModel
        )
    }

    composable(
        route = "${SignUpRoute.complete_signup}/{type}",
        arguments = userTypeArgument
    ) {
        CompleteSignUpScreen(
            userType = it.arguments!!.getSerializable("type") as UserType,
            navigateToVolunteer = navigateToVolunteer,
            navigateToIntermediator = navigateToIntermediator
        )
    }
}

object SignUpRoute {
    const val route = "sign_up"
    const val volunteer_profile = "volunteer_profile"
    const val intermediator_profile = "intermediator_profile"
    const val intermediator_information = "intermediator_information"
    const val register_email = "register_email"
    const val register_password = "register_password"
    const val profile_image = "profile_image"
    const val complete_signup = "complete_signup"
    const val volunteer = "volunteer"
    const val intermediator = "intermediator"
}
