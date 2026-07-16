package com.kusitms.connectdog.feature.intermediator.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.kusitms.connectdog.core.util.UserType
import com.kusitms.connectdog.feature.intermediator.screen.AnnouncementManageScreen
import com.kusitms.connectdog.feature.intermediator.screen.CompleteCreateScreen
import com.kusitms.connectdog.feature.intermediator.screen.CreateApplicationDogScreen
import com.kusitms.connectdog.feature.intermediator.screen.CreateApplicationInfoScreen
import com.kusitms.connectdog.feature.intermediator.screen.InterHomeScreen
import com.kusitms.connectdog.feature.intermediator.screen.InterManagementRoute
import com.kusitms.connectdog.feature.intermediator.screen.InterProfileEditScreen
import com.kusitms.connectdog.feature.intermediator.screen.InterProfileScreen
import com.kusitms.connectdog.feature.intermediator.viewmodel.CreateApplicationViewModel
import kotlinx.serialization.Serializable

fun NavController.navigateInterHome() {
    navigate(IntermediatorRoute.InterHome) {
        popUpTo(graph.id) {
            inclusive = true
        }
    }
}

fun NavController.navigateInterManagement(tabIndex: Int) {
    navigate(IntermediatorRoute.Management(tabIndex))
}

fun NavController.navigateInterProfile() {
    navigate(IntermediatorRoute.InterProfile)
}

fun NavController.navigateToCreateAnnouncementScreen() {
    navigate(IntermediatorRoute.CreateAnnouncement)
}

fun NavController.navigateToInterProfileEdit(url: String) {
    navigate(IntermediatorRoute.InterProfileEdit(url))
}

fun NavController.navigateToCreateDog() {
    navigate(IntermediatorRoute.CreateApplicationDog)
}

fun NavController.navigateToAnnouncementManagement(postId: Long) {
    navigate(IntermediatorRoute.AnnouncementManagement(postId))
}

fun NavController.navigateToCreateComplete() {
    navigate(IntermediatorRoute.CreateComplete)
}

fun NavGraphBuilder.intermediatorNavGraph(
    imeHeight: Int,
    createApplicationViewModel: CreateApplicationViewModel,
    onBackClick: () -> Unit,
    onSettingClick: (UserType) -> Unit,
    onNotificationClick: () -> Unit,
    onProfileClick: () -> Unit,
    onManagementClick: (Int) -> Unit,
    onNavigateToCreateAnnouncement: () -> Unit,
    onNavigateToInterProfileEdit: (String) -> Unit,
    onNavigateToReview: (Long, UserType) -> Unit,
    onNavigateToAnnouncementManagement: (Long) -> Unit,
    onNavigateToCreateDog: () -> Unit,
    onNavigateToInterHome: () -> Unit,
    onNavigateToCreateComplete: () -> Unit,
) {
    composable<IntermediatorRoute.InterHome> {
        InterHomeScreen(
            onNotificationClick = onNotificationClick,
            onSettingClick = onSettingClick,
            onManageClick = onManagementClick,
            onProfileClick = onProfileClick,
            onNavigateToCreateAnnouncementScreen = onNavigateToCreateAnnouncement,
        )
    }

    composable<IntermediatorRoute.Management> { backStackEntry ->
        val route: IntermediatorRoute.Management = backStackEntry.toRoute()
        InterManagementRoute(
            onBackClick = onBackClick,
            tabIndex = route.tabIndex,
            onNavigateToReview = onNavigateToReview,
            onNavigateToAnnouncementManagement = onNavigateToAnnouncementManagement,
        )
    }

    composable<IntermediatorRoute.InterProfile> {
        InterProfileScreen(
            onBackClick = onBackClick,
            onNavigateToInterProfileEdit = onNavigateToInterProfileEdit,
            onNavigateToAnnouncementManagement = onNavigateToAnnouncementManagement,
        )
    }

    composable<IntermediatorRoute.CreateAnnouncement> {
        CreateApplicationInfoScreen(
            onBackClick = onBackClick,
            navigateToCreateDog = onNavigateToCreateDog,
            imeHeight = imeHeight,
            viewModel = createApplicationViewModel,
        )
    }

    composable<IntermediatorRoute.InterProfileEdit> { backStackEntry ->
        val route: IntermediatorRoute.InterProfileEdit = backStackEntry.toRoute()
        InterProfileEditScreen(
            imeHeight = imeHeight,
            onBackClick = onBackClick,
            profileImage = route.profileImage,
        )
    }

    composable<IntermediatorRoute.CreateApplicationDog> {
        CreateApplicationDogScreen(
            imeHeight = imeHeight,
            onBackClick = onBackClick,
            viewModel = createApplicationViewModel,
            onNavigateToCreateComplete = onNavigateToCreateComplete,
        )
    }

    composable<IntermediatorRoute.AnnouncementManagement> { backStackEntry ->
        val route: IntermediatorRoute.AnnouncementManagement = backStackEntry.toRoute()
        AnnouncementManageScreen(
            postId = route.postId,
            onBackClick = onBackClick,
            onIntermediatorProfileClick = {},
        )
    }

    composable<IntermediatorRoute.CreateComplete> {
        CompleteCreateScreen {
            onNavigateToInterHome()
        }
    }
}

object IntermediatorRoute {
    @Serializable
    data object InterHome

    @Serializable
    data class Management(val tabIndex: Int = 0)

    @Serializable
    data object InterProfile

    @Serializable
    data object CreateAnnouncement

    @Serializable
    data class InterProfileEdit(val profileImage: String)

    @Serializable
    data object CreateApplicationDog

    @Serializable
    data class AnnouncementManagement(val postId: Long)

    @Serializable
    data object CreateComplete
}
