package com.kusitms.connectdog.signup

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.kusitms.connectdog.core.util.Type
import com.kusitms.connectdog.signup.screen.CompleteSignUpScreen
import com.kusitms.connectdog.signup.screen.RegisterEmailScreen
import com.kusitms.connectdog.signup.screen.RegisterPasswordScreen
import com.kusitms.connectdog.signup.screen.SignUpRoute
import com.kusitms.connectdog.signup.screen.intermediator.IntermediatorInformationScreen
import com.kusitms.connectdog.signup.screen.intermediator.IntermediatorProfileScreen
import com.kusitms.connectdog.signup.screen.volunteer.SelectProfileImageScreen
import com.kusitms.connectdog.signup.screen.volunteer.VolunteerProfileScreen
import com.kusitms.connectdog.signup.viewmodel.VolunteerProfileViewModel

fun NavController.navigateToIntermediatorProfile() {
    navigate(SignUpRoute.intermediator_profile)
}

fun NavController.navigateToVolunteerProfile() {
    navigate(SignUpRoute.volunteer_profile)
}

fun NavController.navigateRegisterEmail(type: Type) {
    navigate("${SignUpRoute.register_email}/$type")
}

fun NavController.navigateRegisterPassword(type: Type) {
    navigate("${SignUpRoute.register_password}/$type")
}

fun NavController.navigateSelectProfileImage() {
    navigate(SignUpRoute.profile_image)
}

fun NavController.navigateCompleteSignUp(type: Type) {
    navigate("${SignUpRoute.complete_signup}/$type")
}

fun NavController.navigateIntermediatorInformation() {
    navigate(SignUpRoute.intermediator_information)
}

fun NavGraphBuilder.signUpGraph(
    onBackClick: () -> Unit,
    navigateToVolunteerProfile: () -> Unit,
    navigateToIntermediatorProfile: () -> Unit,
    navigateToIntermediatorInformation: () -> Unit,
    navigateToRegisterEmail: (Type) -> Unit,
    navigateToRegisterPassword: (Type) -> Unit,
    navigateToSelectProfileImage: () -> Unit,
    navigateToCompleteSignUp: (Type) -> Unit,
    navigateToVolunteer: () -> Unit,
    navigateToIntermediator: () -> Unit,
    imeHeight: Int,
    viewModel: VolunteerProfileViewModel
) {
    val typeArgument = listOf(
        navArgument("type") {
            type = NavType.EnumType(Type::class.java)
        }
    )

    composable(
        route = "${SignUpRoute.route}/{type}",
        arguments = typeArgument
    ) {
        SignUpRoute(
            onBackClick = onBackClick,
            type = it.arguments!!.getSerializable("type") as Type,
            navigateToVolunteerProfile = navigateToVolunteerProfile,
            navigateToIntermediatorInformation = navigateToIntermediatorInformation,
            navigateToRegisterEmail = navigateToRegisterEmail
        )
    }

    composable(
        route = "${SignUpRoute.register_email}/{type}",
        arguments = typeArgument
    ) {
        RegisterEmailScreen(
            onBackClick = onBackClick,
            type = it.arguments!!.getSerializable("type") as Type,
            onNavigateToRegisterPassword = navigateToRegisterPassword,
            imeHeight = imeHeight
        )
    }

    composable(
        route = "${SignUpRoute.register_password}/{type}",
        arguments = typeArgument
    ) {
        RegisterPasswordScreen(
            onBackClick = onBackClick,
            onNavigateToIntermediatorProfile = navigateToIntermediatorProfile,
            onNavigateToVolunteerProfile = navigateToVolunteerProfile,
            type = it.arguments!!.getSerializable("type") as Type,
            imeHeight = imeHeight
        )
    }

    composable(route = SignUpRoute.volunteer_profile) {
        VolunteerProfileScreen(
            onBackClick = onBackClick,
            onNavigateToSelectProfileImage = navigateToSelectProfileImage,
            onNavigateToCompleteSignUp = navigateToCompleteSignUp,
            imeHeight = imeHeight,
            viewModel = viewModel
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

    composable(
        route = SignUpRoute.profile_image
    ) {
        SelectProfileImageScreen(
            onBackClick = onBackClick,
            viewModel = viewModel
        )
    }

    composable(
        route = "${SignUpRoute.complete_signup}/{type}",
        arguments = typeArgument
    ) {
        CompleteSignUpScreen(
            type = it.arguments!!.getSerializable("type") as Type,
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
