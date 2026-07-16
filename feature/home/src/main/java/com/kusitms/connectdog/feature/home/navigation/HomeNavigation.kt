package com.kusitms.connectdog.feature.home.navigation

import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
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
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlin.reflect.typeOf

private val TAG = "HomeNavigation"

// ponytail: androidx.navigation only auto-generates NavType for primitives/enums, not arbitrary
// @Serializable data classes, so Filter needs an explicit JSON-backed NavType.
private val FilterNavType =
    object : NavType<Filter?>(isNullableAllowed = true) {
        override fun get(
            bundle: Bundle,
            key: String,
        ): Filter? = bundle.getString(key)?.let { Json.decodeFromString<Filter?>(it) }

        override fun parseValue(value: String): Filter? = Json.decodeFromString<Filter?>(value)

        override fun put(
            bundle: Bundle,
            key: String,
            value: Filter?,
        ) {
            bundle.putString(key, Json.encodeToString<Filter?>(value))
        }

        override fun serializeAsValue(value: Filter?): String = Uri.encode(Json.encodeToString<Filter?>(value))
    }

private val filterTypeMap = mapOf(typeOf<Filter?>() to FilterNavType)

fun NavController.navigateHome(navOptions: NavOptions) {
    navigate(HomeRoute.Home, navOptions)
}

fun NavController.navigateSearch() {
    Log.d(TAG, "navigateSearch")
    navigate(HomeRoute.Search()) {
        popUpTo(HomeRoute.Home) { inclusive = false }
    }
}

fun NavController.navigateSearchWithFilter(filter: Filter) {
    this.popBackStack()
    navigate(HomeRoute.Search(filter))
}

fun NavController.navigateFilterSearch() {
    navigate(HomeRoute.FilterSearch())
}

fun NavController.navigateFilter(filter: Filter) {
    Log.d(TAG, "navigateFilterSearchWithFilter()")
    navigate(HomeRoute.FilterSearch(filter))
}

fun NavController.navigateReview() = navigate(HomeRoute.Review)

fun NavController.navigateDetail(postId: Long) = navigate(HomeRoute.Detail(postId))

fun NavController.navigateApply(postId: Long) {
    navigate(HomeRoute.Apply(postId))
}

fun NavController.navigateComplete() {
    navigate(HomeRoute.Complete)
}

fun NavController.navigateIntermediatorProfile(intermediaryId: Long) {
    navigate(HomeRoute.IntermediatorProfile(intermediaryId))
}

fun NavController.navigateNotification() {
    navigate(HomeRoute.Notification)
}

fun NavController.navigateGuide() {
    navigate(HomeRoute.Guide)
}

inline fun <reified T : Any> NavController.navigateToHomeClearBackStack() {
    navigate(HomeRoute.Home) {
        popUpTo<T> { inclusive = true }
    }
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
    imeHeight: Int,
) {
    composable<HomeRoute.Home> {
        HomeRoute(
            onNavigateToSearch,
            onNavigateToFilterSearch,
            onNavigateToReview,
            onNavigateToDetail,
            onNavigateToNotification,
            onShowErrorSnackBar,
            onNavigateToReviewDetail,
            onNavigateToGuideScreen,
            finish,
        )
    }

    composable<HomeRoute.Search>(typeMap = filterTypeMap) { backStackEntry ->
        val route: HomeRoute.Search = backStackEntry.toRoute()
        Log.d(TAG, "homeNavGraph filter = ${route.filter}")
        SearchScreen(
            onBackClick = onBackClick,
            filterArg = route.filter ?: Filter(),
            onNavigateToFilter = onNavigateToFilter,
            onDetailClick = onNavigateToDetail,
        )
    }

    composable<HomeRoute.FilterSearch>(typeMap = filterTypeMap) { backStackEntry ->
        val route: HomeRoute.FilterSearch = backStackEntry.toRoute()
        Log.d(TAG, "homeNavGraph filter = ${route.filter}")
        FilterSearchRoute(
            onBackClick = onBackClick,
            onNavigateToSearch = onNavigateToSearchWithFilter,
            filterArg = route.filter ?: Filter(),
            imeHeight = imeHeight,
        )
    }

    composable<HomeRoute.Review> {
        ReviewScreen(
            onBackClick = onBackClick,
            onInterProfileClick = onNavigateToIntermediatorProfile,
        )
    }

    composable<HomeRoute.Detail> { backStackEntry ->
        val route: HomeRoute.Detail = backStackEntry.toRoute()
        DetailScreen(
            onBackClick = onBackClick,
            onApplyClick = { onNavigateToApply(it) },
            onIntermediatorProfileClick = onNavigateToIntermediatorProfile,
            postId = route.postId,
        )
    }

    composable<HomeRoute.Apply> { backStackEntry ->
        val route: HomeRoute.Apply = backStackEntry.toRoute()
        ApplyScreen(
            onBackClick = onBackClick,
            onClick = onNavigateToComplete,
            postId = route.postId,
            imeHeight = imeHeight,
        )
    }

    composable<HomeRoute.Complete> {
        CompleteApplyScreen(
            onClick = onNavigateToSearch,
        )
    }

    composable<HomeRoute.IntermediatorProfile> { backStackEntry ->
        val route: HomeRoute.IntermediatorProfile = backStackEntry.toRoute()
        IntermediatorProfileScreen(
            onBackClick = onBackClick,
            intermediaryId = route.intermediaryId,
            onDetailClick = onNavigateToDetail,
        )
    }

    composable<HomeRoute.Guide> {
        GuideScreen(
            onBackClick = onBackClick,
        )
    }

    composable<HomeRoute.Notification> {
//        NotificationScreen(
//            onClick = navigateNotification
//        )
    }
}

object HomeRoute {
    @Serializable
    data object Home

    @Serializable
    data class Search(val filter: Filter? = null)

    @Serializable
    data class FilterSearch(val filter: Filter? = null)

    @Serializable
    data object Review

    @Serializable
    data class Detail(val postId: Long)

    @Serializable
    data class Apply(val postId: Long)

    @Serializable
    data object Complete

    @Serializable
    data class IntermediatorProfile(val intermediaryId: Long)

    @Serializable
    data object Notification

    @Serializable
    data object Guide
}
