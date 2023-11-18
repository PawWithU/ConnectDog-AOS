package com.kusitms.connectdog.feature.home.navigation

import android.util.Log
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.kusitms.connectdog.feature.home.screen.ApplyScreen
import com.kusitms.connectdog.feature.home.screen.CertificationScreen
import com.kusitms.connectdog.feature.home.screen.CompleteApplyScreen
import com.kusitms.connectdog.feature.home.screen.DetailScreen
import com.kusitms.connectdog.feature.home.screen.HomeRoute
import com.kusitms.connectdog.feature.home.screen.IntermediatorProfileScreen
import com.kusitms.connectdog.feature.home.screen.ReviewScreen
import com.kusitms.connectdog.feature.home.screen.SearchScreen

fun NavController.navigateHome(navOptions: NavOptions) {
    navigate(HomeRoute.route, navOptions)
}

fun NavController.navigateSearch() {
    Log.d("SearchScreen", "navigateSearch")
    navigate(HomeRoute.search) {
        popUpTo(HomeRoute.route) { inclusive = false }
    }
}

fun NavController.navigateReview() {
    navigate(HomeRoute.review)
}

fun NavController.navigateDetail() {
    navigate(HomeRoute.detail)
}

fun NavController.navigateCertification() {
    navigate(HomeRoute.certification)
}

fun NavController.navigateApply() {
    navigate(HomeRoute.apply)
}

fun NavController.navigateComplete() {
    navigate(HomeRoute.complete)
}

fun NavController.navigateIntermediatorProfile() {
    navigate(HomeRoute.intermediatorProfile)
}

fun NavGraphBuilder.homeNavGraph(
    onBackClick: () -> Unit,
    onNavigateToSearch: () -> Unit,
    onNavigateToReview: () -> Unit,
    onNavigateToDetail: () -> Unit,
    onNavigateToCertification: () -> Unit,
    onNavigateToApply: () -> Unit,
    onNavigateToComplete: () -> Unit,
    onNavigateToIntermediatorProfile: () -> Unit,
    onShowErrorSnackBar: (throwable: Throwable?) -> Unit
) {
    composable(route = HomeRoute.route) {
        HomeRoute(
            onBackClick,
            onNavigateToSearch,
            onNavigateToReview,
            onNavigateToDetail,
            onShowErrorSnackBar
        )
    }

    composable(route = HomeRoute.search) {
        SearchScreen(
            onBackClick = onBackClick
        )
    }

    composable(route = HomeRoute.review) {
        ReviewScreen(
            onBackClick = onBackClick
        )
    }

    composable(route = HomeRoute.detail) {
        DetailScreen(
            onBackClick = onBackClick,
            onCertificationClick = onNavigateToCertification,
            onIntermediatorProfileClick = onNavigateToIntermediatorProfile
        )
    }

    composable(route = HomeRoute.certification) {
        CertificationScreen(
            onBackClick = onBackClick,
            onApplyClick = onNavigateToApply
        )
    }

    composable(route = HomeRoute.apply) {
        ApplyScreen(
            onBackClick = onBackClick,
            onClick = onNavigateToComplete
        )
    }

    composable(route = HomeRoute.complete) {
        CompleteApplyScreen(
            onClick = onNavigateToSearch
        )
    }

    composable(route = HomeRoute.intermediatorProfile) {
        IntermediatorProfileScreen(
            onBackClick = onBackClick
        )
    }
}

object HomeRoute {
    const val route = "home"
    const val main = "main"
    const val search = "search"
    const val review = "review"
    const val detail = "detail"
    const val certification = "certification"
    const val apply = "apply"
    const val complete = "complete"
    const val intermediatorProfile = "intermediatorProfile"
}
