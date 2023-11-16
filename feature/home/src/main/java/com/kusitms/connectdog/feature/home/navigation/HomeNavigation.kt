package com.kusitms.connectdog.feature.home.navigation

import android.util.Log
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.kusitms.connectdog.feature.home.screen.FilterSearchScreen
import com.kusitms.connectdog.feature.home.screen.HomeRoute
import com.kusitms.connectdog.feature.home.screen.ReviewScreen
import com.kusitms.connectdog.feature.home.screen.SearchScreen

fun NavController.navigateHome(navOptions: NavOptions) {
    navigate(HomeRoute.route, navOptions)
}

fun NavController.navigateSearch() {
    Log.d("SearchScreen", "navigateSearch")
    navigate(HomeRoute.search)
}

fun NavController.navigateFilterSearch() {
    navigate(HomeRoute.filter_search)
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
    onNavigateToFilterSearch: () -> Unit,
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
        SearchScreen(
            onBackClick = onBackClick
        )
    }

    composable(
        route = "${HomeRoute.search}/{filter}",
        arguments = listOf(navArgument("filter") {type = NavType.ReferenceType})
    ) {
        SearchScreen(onBackClick = onBackClick)
    }

    composable(route = HomeRoute.filter_search) {
        FilterSearchScreen(
            onBackClick = onBackClick,
            onNavigateToSearch = onNavigateToSearch
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
