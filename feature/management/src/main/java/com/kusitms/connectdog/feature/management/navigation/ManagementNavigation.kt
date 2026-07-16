package com.kusitms.connectdog.feature.management.navigation

import android.net.Uri
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.kusitms.connectdog.core.model.Application
import com.kusitms.connectdog.core.util.UserType
import com.kusitms.connectdog.feature.management.screen.CheckReviewScreen
import com.kusitms.connectdog.feature.management.screen.CreateReviewScreen
import com.kusitms.connectdog.feature.management.screen.ManagementRoute
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlin.reflect.typeOf

// ponytail: same NavType gap as Filter in feature:home - Application needs a JSON-backed NavType.
private val ApplicationNavType =
    object : NavType<Application>(isNullableAllowed = false) {
        override fun get(
            bundle: Bundle,
            key: String,
        ): Application? = bundle.getString(key)?.let { Json.decodeFromString<Application>(it) }

        override fun parseValue(value: String): Application = Json.decodeFromString(value)

        override fun put(
            bundle: Bundle,
            key: String,
            value: Application,
        ) {
            bundle.putString(key, Json.encodeToString<Application>(value))
        }

        override fun serializeAsValue(value: Application): String = Uri.encode(Json.encodeToString<Application>(value))
    }

private val applicationTypeMap = mapOf(typeOf<Application>() to ApplicationNavType)

fun NavController.navigateManagement(navOptions: NavOptions) {
    navigate(ManagementRoute.Management, navOptions)
}

fun NavController.navigateCreateReview(application: Application) {
    navigate(ManagementRoute.CreateReview(application))
}

fun NavController.navigateCheckReview(
    reviewId: Long,
    userType: UserType,
) {
    navigate(ManagementRoute.CheckReview(reviewId, userType))
}

fun NavGraphBuilder.managementNavGraph(
    onBackClick: () -> Unit,
    onNavigateToCreateReview: (Application) -> Unit,
    onNavigateToCheckReview: (Long, UserType) -> Unit,
    onNavigateToInterProfile: (Long) -> Unit,
    onNavigateToHome: () -> Unit,
    onNavigateToDetail: (Long) -> Unit,
    onShowErrorSnackbar: (throwable: Throwable?) -> Unit,
) {
    composable<ManagementRoute.Management> {
        ManagementRoute(
            onBackClick,
            onNavigateToCreateReview,
            onNavigateToCheckReview,
            onNavigateToHome,
            onNavigateToDetail,
            onShowErrorSnackbar,
        )
    }

    composable<ManagementRoute.CreateReview>(typeMap = applicationTypeMap) { backStackEntry ->
        val route: ManagementRoute.CreateReview = backStackEntry.toRoute()
        CreateReviewScreen(
            onBackClick = onBackClick,
            application = route.application,
        )
    }

    composable<ManagementRoute.CheckReview> { backStackEntry ->
        val route: ManagementRoute.CheckReview = backStackEntry.toRoute()
        CheckReviewScreen(
            onBackClick = onBackClick,
            userType = route.userType,
            reviewId = route.reviewId,
            onInterProfileClick = onNavigateToInterProfile,
        )
    }
}

object ManagementRoute {
    @Serializable
    data object Management

    @Serializable
    data class CreateReview(val application: Application)

    @Serializable
    data class CheckReview(val reviewId: Long, val userType: UserType)
}
