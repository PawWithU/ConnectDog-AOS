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
import com.kusitms.connectdog.feature.home.screen.FilterSearchScreen
import com.kusitms.connectdog.feature.home.screen.HomeRoute
import com.kusitms.connectdog.feature.home.screen.ReviewScreen
import com.kusitms.connectdog.feature.home.screen.SearchScreen

private val TAG = "HomeNavigation"

fun NavController.navigateHome(navOptions: NavOptions) {
    navigate(HomeRoute.route, navOptions)
}

fun NavController.navigateSearch() {
    Log.d(TAG, "navigateSearch")
    navigate(HomeRoute.search)
}

fun NavController.navigateSearchWithFilter(filter: Filter) {
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

fun NavGraphBuilder.homeNavGraph(
    onBackClick: () -> Unit,
    onNavigateToSearch: () -> Unit,
    onNavigateToSearchWithFilter: (Filter) -> Unit,
    onNavigateToFilterSearch: () -> Unit,
    onNavigateToFilter: (Filter) -> Unit,
    onNavigateToReview: () -> Unit,
    onNavigateToDetail: () -> Unit,
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
            filter = filter ?: Filter(),
            onNavigateToFilter = onNavigateToFilter
        )
    }

    composable(route = HomeRoute.filter_search) {
        FilterSearchScreen(
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
        FilterSearchScreen(
            onBackClick = onBackClick,
            filterArg = filter,
            onNavigateToSearch = onNavigateToFilter
        )
    }


    composable(route = HomeRoute.review) {
        ReviewScreen(
            onBackClick = onBackClick
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
}
