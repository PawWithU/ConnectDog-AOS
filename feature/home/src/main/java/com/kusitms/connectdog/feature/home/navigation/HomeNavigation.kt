package com.kusitms.connectdog.feature.home.navigation

import android.util.Log
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.kusitms.connectdog.core.util.localDateGson
import com.kusitms.connectdog.feature.home.model.Filter
import com.kusitms.connectdog.feature.home.screen.FilterSearchRoute
import com.kusitms.connectdog.feature.home.screen.ApplyScreen
import com.kusitms.connectdog.feature.home.screen.CertificationScreen
import com.kusitms.connectdog.feature.home.screen.CompleteApplyScreen
import com.kusitms.connectdog.feature.home.screen.DetailScreen
import com.kusitms.connectdog.feature.home.screen.HomeRoute
import com.kusitms.connectdog.feature.home.screen.ReviewScreen
import com.kusitms.connectdog.feature.home.screen.SearchScreen

private val TAG = "HomeNavigation"

fun NavController.navigateHome(navOptions: NavOptions) {
    navigate(HomeRoute.route, navOptions)
}

fun NavController.navigateSearch() {
    Log.d(TAG, "navigateSearch")
    //navigate(HomeRoute.search)
    navigate(HomeRoute.search) {
        popUpTo(HomeRoute.route) { inclusive = false }
    }
}

fun NavController.navigateSearchWithFilter(filter: Filter) {
    this.popBackStack()
    Log.d(TAG, "navigateSearchWithFilter()")
    val filterJson = localDateGson.toJson(filter)
    navigate("${HomeRoute.search}/${filterJson}")
}

fun NavController.navigateFilterSearch() {
    navigate(HomeRoute.filter_search)
}

fun NavController.navigateFilter(filter: Filter) {
    Log.d(TAG, "navigateFilterSearchWithFilter()")
    val filterJson = localDateGson.toJson(filter)
    navigate("${HomeRoute.filter_search}/${filterJson}")
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

fun NavGraphBuilder.homeNavGraph(
    onBackClick: () -> Unit,
    onNavigateToSearch: () -> Unit,
    onNavigateToSearchWithFilter: (Filter) -> Unit,
    onNavigateToFilterSearch: () -> Unit,
    onNavigateToFilter: (Filter) -> Unit,
    onNavigateToReview: () -> Unit,
    onNavigateToDetail: () -> Unit,
    onNavigateToCertification: () -> Unit,
    onNavigateToApply: () -> Unit,
    onNavigateToComplete: () -> Unit,
    onShowErrorSnackBar: (throwable: Throwable?) -> Unit
) {
    composable(route = HomeRoute.route) {
        HomeRoute(
            onBackClick,
            onNavigateToSearch,
            onNavigateToFilterSearch,
            onNavigateToReview,
            onNavigateToDetail,
            onShowErrorSnackBar
        )
    }

    composable(route = HomeRoute.search) {
        SearchScreen(onBackClick = onBackClick, onNavigateToFilter = onNavigateToFilter)
    }

    composable(
        route = "${HomeRoute.search}/{filter}",
        arguments = listOf(navArgument("filter") {
            type = NavType.StringType
        })
    ) { backStackEntry ->
        val filterJson = backStackEntry.arguments?.getString("filter")
        val filter = localDateGson.fromJson(filterJson, Filter::class.java)
        Log.d(TAG, "homeNavGraph filter = $filter")
        SearchScreen(
            onBackClick = onBackClick,
            filterArg = filter ?: Filter(),
            onNavigateToFilter = onNavigateToFilter
        )
    }

    composable(route = HomeRoute.filter_search) {
        FilterSearchRoute(
            onBackClick = onBackClick,
            onNavigateToSearch = onNavigateToSearchWithFilter
        )
    }

    composable(
        route = "${HomeRoute.filter_search}/{filter}",
        arguments = listOf(navArgument("filter") {
            type = NavType.StringType
        })
    ) { backStackEntry ->
        val filterJson = backStackEntry.arguments?.getString("filter")
        val filter = localDateGson.fromJson(filterJson, Filter::class.java)
        Log.d(TAG, "homeNavGraph filter = $filter")
        FilterSearchRoute(
            onBackClick = onBackClick,
            filterArg = filter,
            onNavigateToSearch = onNavigateToSearchWithFilter
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
            onCertificationClick = onNavigateToCertification
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
}

object HomeRoute {
    const val route = "home"
    const val main = "main"
    const val search = "search"
    const val filter_search = "filter_search"
    const val review = "review"
    const val detail = "detail"
    const val certification = "certification"
    const val apply = "apply"
    const val complete = "complete"
}
