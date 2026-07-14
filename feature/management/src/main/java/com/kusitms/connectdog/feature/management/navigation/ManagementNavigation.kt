package com.kusitms.connectdog.feature.management.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.google.gson.Gson
import com.kusitms.connectdog.core.model.Application
import com.kusitms.connectdog.core.util.UserType
import com.kusitms.connectdog.feature.management.screen.CheckReviewScreen
import com.kusitms.connectdog.feature.management.screen.CreateReviewScreen
import com.kusitms.connectdog.feature.management.screen.ManagementRoute

fun NavController.navigateManagement(navOptions: NavOptions) {
    navigate(ManagementRoute.route, navOptions)
}

fun NavController.navigateCreateReview(application: String) {
    navigate("${ManagementRoute.create_review}/$application")
}

fun NavController.navigateCheckReview(
    reviewId: Long,
    userType: UserType,
) {
    navigate("${ManagementRoute.check_review}/$reviewId/$userType")
}

fun NavGraphBuilder.managementNavGraph(
    onBackClick: () -> Unit,
    onNavigateToCreateReview: (Application) -> Unit,
    onNavigateToCheckReview: (Long, UserType) -> Unit,
    onNavigateToInterProfile: (Long) -> Unit,
    onNavigateToHome: (String) -> Unit,
    onNavigateToDetail: (Long) -> Unit,
    onShowErrorSnackbar: (throwable: Throwable?) -> Unit,
) {
    composable(route = ManagementRoute.route) {
        ManagementRoute(
            onBackClick,
            onNavigateToCreateReview,
            onNavigateToCheckReview,
            onNavigateToHome,
            onNavigateToDetail,
            onShowErrorSnackbar,
        )
    }

    composable(
        route = "${ManagementRoute.create_review}/{application}",
        arguments =
            listOf(
                navArgument("application") { type = NavType.StringType },
            ),
    ) {
        val applicationJson = it.arguments?.getString("application")
        val application = Gson().fromJson(applicationJson, Application::class.java)
        CreateReviewScreen(
            onBackClick = onBackClick,
            application = application,
        )
    }

    composable(
        route = "${ManagementRoute.check_review}/{reviewId}/{userType}",
        arguments =
            listOf(
                navArgument("reviewId") { type = NavType.LongType },
                navArgument("userType") { type = NavType.EnumType(UserType::class.java) },
            ),
    ) {
        val postId = it.arguments!!.getLong("reviewId")
        val userType = it.arguments!!.getSerializable("userType") as UserType
        CheckReviewScreen(
            onBackClick = onBackClick,
            userType = userType,
            reviewId = postId,
            onInterProfileClick = onNavigateToInterProfile,
        )
    }
}

object ManagementRoute {
    const val route = "management"
    const val create_review = "create_review"
    const val check_review = "check_review"
}
