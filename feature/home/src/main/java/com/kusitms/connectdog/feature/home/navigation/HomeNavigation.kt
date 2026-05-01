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
import com.kusitms.connectdog.feature.home.screen.ApplyScreen
import com.kusitms.connectdog.feature.home.screen.CompleteApplyScreen
import com.kusitms.connectdog.feature.home.screen.DetailScreen
import com.kusitms.connectdog.feature.home.screen.FilterSearchRoute
import com.kusitms.connectdog.feature.home.screen.GuideScreen
import com.kusitms.connectdog.feature.home.screen.HomeRoute
import com.kusitms.connectdog.feature.home.screen.IntermediatorProfileScreen
import com.kusitms.connectdog.feature.home.screen.ReviewScreen
import com.kusitms.connectdog.feature.home.screen.SearchScreen

private val TAG = "HomeNavigation"

fun NavController.navigateHome(navOptions: NavOptions) {
    navigate(HomeRoute.route, navOptions)
}

fun NavController.navigateSearch() {
    Log.d(TAG, "navigateSearch")
    navigate(HomeRoute.search) {
        popUpTo(HomeRoute.route) { inclusive = false }
    }
}

fun NavController.navigateSearchWithFilter(filter: Filter) {
    this.popBackStack()
    Log.d(TAG, "navigateSearchWithFilter()")
    val filterJson = localDateGson.toJson(filter)
    navigate("${HomeRoute.search}/$filterJson")
}

fun NavController.navigateFilterSearch() {
    navigate(HomeRoute.filter_search)
}

fun NavController.navigateFilter(filter: Filter) {
    Log.d(TAG, "navigateFilterSearchWithFilter()")
    val filterJson = localDateGson.toJson(filter)
    navigate("${HomeRoute.filter_search}/$filterJson")
}

fun NavController.navigateReview() {
    navigate(HomeRoute.review)
}

fun NavController.navigateDetail(postId: Long) {
    navigate("${HomeRoute.detail}/$postId")
}

fun NavController.navigateApply(postId: Long) {
    navigate("${HomeRoute.apply}/$postId")
}

fun NavController.navigateComplete() {
    navigate(HomeRoute.complete)
}

fun NavController.navigateIntermediatorProfile(intermediaryId: Long) {
    navigate("${HomeRoute.intermediatorProfile}/$intermediaryId")
}

fun NavController.navigateNotification() {
    navigate(HomeRoute.notification)
}

fun NavController.navigateGuide() {
    navigate(HomeRoute.guide)
}

fun NavGraphBuilder.homeNavGraph(
    onBackClick: () -> Unit,
    onNavigateToSearch: () -> Unit,
    onNavigateToSearchWithFilter: (Filter) -> Unit,
    onNavigateToFilterSearch: () -> Unit,
    onNavigateToFilter: (Filter) -> Unit,
    onNavigateToReview: () -> Unit,
    onNavigateToDetail: (Long) -> Unit,
    onNavigateToApply: (Long) -> Unit,
    onNavigateToComplete: () -> Unit,
    onNavigateToIntermediatorProfile: (Long) -> Unit,
    onNavigateToNotification: () -> Unit,
    onNavigateToGuideScreen: () -> Unit,
    onNavigateToReviewDetail: (Long) -> Unit,
    onSendMessage: (String) -> Unit,
    onVerifyCode: (String, (Boolean) -> Unit) -> Unit,
    onShowErrorSnackBar: (throwable: Throwable?) -> Unit,
    finish: () -> Unit,
    imeHeight: Int
) {
    composable(route = HomeRoute.route) {
        HomeRoute(
            onNavigateToSearch,
            onNavigateToFilterSearch,
            onNavigateToReview,
            onNavigateToDetail,
            onNavigateToNotification,
            onShowErrorSnackBar,
            onNavigateToReviewDetail,
            onNavigateToGuideScreen,
            finish
        )
    }

    composable(route = HomeRoute.search) {
        SearchScreen(onBackClick = onBackClick, onNavigateToFilter = onNavigateToFilter, onDetailClick = onNavigateToDetail)
    }

    composable(
        route = "${HomeRoute.search}/{filter}",
        arguments = listOf(
            navArgument("filter") {
                type = NavType.StringType
            }
        )
    ) { backStackEntry ->
        val filterJson = backStackEntry.arguments?.getString("filter")
        val filter = localDateGson.fromJson(filterJson, Filter::class.java)
        Log.d(TAG, "homeNavGraph filter = $filter")
        SearchScreen(
            onBackClick = onBackClick,
            filterArg = filter ?: Filter(),
            onNavigateToFilter = onNavigateToFilter,
            onDetailClick = onNavigateToDetail
        )
    }

    composable(route = HomeRoute.filter_search) {
        FilterSearchRoute(
            onBackClick = onBackClick,
            onNavigateToSearch = onNavigateToSearchWithFilter,
            imeHeight = imeHeight
        )
    }

    composable(
        route = "${HomeRoute.filter_search}/{filter}",
        arguments = listOf(
            navArgument("filter") {
                type = NavType.StringType
            }
        )
    ) { backStackEntry ->
        val filterJson = backStackEntry.arguments?.getString("filter")
        val filter = localDateGson.fromJson(filterJson, Filter::class.java)
        Log.d(TAG, "homeNavGraph filter = $filter")
        FilterSearchRoute(
            onBackClick = onBackClick,
            onNavigateToSearch = onNavigateToSearchWithFilter,
            filterArg = filter,
            imeHeight = imeHeight
        )
    }

    composable(route = HomeRoute.review) {
        ReviewScreen(
            onBackClick = onBackClick,
            onInterProfileClick = onNavigateToIntermediatorProfile
        )
    }
    composable(
        route = "${HomeRoute.detail}/{postId}",
        arguments = listOf(
            navArgument("postId") {
                type = NavType.LongType
            }
        )
    ) {
        DetailScreen(
            onBackClick = onBackClick,
            onApplyClick = { onNavigateToApply(it) },
            onIntermediatorProfileClick = onNavigateToIntermediatorProfile,
            postId = it.arguments!!.getLong("postId")
        )
    }

    composable(
        route = "${HomeRoute.apply}/{postId}",
        arguments = listOf(
            navArgument("postId") {
                type = NavType.LongType
            }
        )
    ) {
        ApplyScreen(
            onBackClick = onBackClick,
            onClick = onNavigateToComplete,
            postId = it.arguments!!.getLong("postId"),
            imeHeight = imeHeight
        )
    }

    composable(route = HomeRoute.complete) {
        CompleteApplyScreen(
            onClick = onNavigateToSearch
        )
    }

    composable(
        route = "${HomeRoute.intermediatorProfile}/{intermediaryId}",
        arguments = listOf(
            navArgument("intermediaryId") {
                type = NavType.LongType
            }
        )
    ) {
        IntermediatorProfileScreen(
            onBackClick = onBackClick,
            intermediaryId = it.arguments!!.getLong("intermediaryId"),
            onDetailClick = onNavigateToDetail
        )
    }

    composable(route = HomeRoute.guide) {
        GuideScreen(
            onBackClick = onBackClick
        )
    }

    composable(route = HomeRoute.notification) {
//        NotificationScreen(
//            onClick = navigateNotification
//        )
    }
}

object HomeRoute {
    const val route = "home"
    const val main = "main"
    const val search = "search"
    const val filter_search = "filter_search"
    const val review = "review"
    const val detail = "detail"
    const val apply = "apply"
    const val complete = "complete"
    const val intermediatorProfile = "intermediatorProfile"
    const val notification = "notification"
    const val guide = "guide"
}
