package com.kusitms.connectdog.signup

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.kusitms.connectdog.core.util.Type
import com.kusitms.connectdog.signup.volunteer.SelectProfileImageScreen
import com.kusitms.connectdog.signup.volunteer.VolunteerProfileScreen
import com.kusitms.connectdog.signup.volunteer.VolunteerProfileViewModel

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

fun NavController.navigateCompleteSignUp() {
    navigate(SignUpRoute.complete_signup)
}

fun NavGraphBuilder.signUpGraph(
    onBackClick: () -> Unit,
    navigateToVolunteerProfile: () -> Unit,
    navigateToIntermediatorProfile: () -> Unit,
    navigateToRegisterEmail: (Type) -> Unit,
    navigateToRegisterPassword: (Type) -> Unit,
    navigateToSelectProfileImage: () -> Unit,
    navigateToCompleteSignUp: () -> Unit,
    navigateToVolunteer: () -> Unit,
    navigateToIntermediator: () -> Unit,
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
            navigateToIntermediatorProfile = navigateToIntermediatorProfile,
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
            onNavigateToRegisterPassword = navigateToRegisterPassword
        )
    }

    composable(
        route = "${SignUpRoute.register_password}/{type}",
        arguments = typeArgument
    ) {
        RegisterPasswordScreen(
            onBackClick = onBackClick,
            navigateToVolunteerProfile = navigateToVolunteerProfile
        )
    }

    composable(route = SignUpRoute.volunteer_profile) {
        VolunteerProfileScreen(
            onBackClick = onBackClick,
            onNavigateToSelectProfileImage = navigateToSelectProfileImage,
            onNavigateToCompleteSignUp = navigateToCompleteSignUp,
            viewModel = viewModel
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
        route = SignUpRoute.complete_signup
    ) {
        CompleteSignUpScreen()
    }
}

object SignUpRoute {
    const val route = "sign_up"
    const val volunteer_profile = "volunteer_profile"
    const val intermediator_profile = "intermediator_profile"
    const val register_email = "register_email"
    const val register_password = "register_password"
    const val profile_image = "profile_image"
    const val complete_signup = "complete_signup"
    const val volunteer = "volunteer"
    const val intermediator = "intermediator"
}
