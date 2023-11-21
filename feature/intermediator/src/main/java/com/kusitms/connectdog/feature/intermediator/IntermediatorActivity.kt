package com.kusitms.connectdog.feature.intermediator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.kusitms.connectdog.core.designsystem.theme.ConnectDogTheme
import com.kusitms.connectdog.feature.intermediator.navigation.IntermediatorRoute
import com.kusitms.connectdog.feature.intermediator.navigation.navigateInterManagement
import com.kusitms.connectdog.feature.intermediator.screen.InterManagementRoute
import com.kusitms.connectdog.feature.intermediator.screen.IntermediatorHomeScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class IntermediatorActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        actionBar?.hide()

        setContent {
            val navigator = rememberNavController()
            ConnectDogTheme {
                NavHost(navigator, startDestination = IntermediatorRoute.home) {
                    composable(IntermediatorRoute.home) {
                        IntermediatorHomeScreen(
                            onNotificationClick = { /*TODO*/ },
                            onSettingClick = {},
                            onDataClick = { index ->
                                navigator.navigateInterManagement(index)
                            }
                        )
                    }
                    composable(
                        "${IntermediatorRoute.management}?tabIndex={tabIndex}",
                        arguments = listOf(navArgument("tabIndex") { defaultValue = 0 })
                    ) {
                        InterManagementRoute(
                            onBackClick = { navigator.popBackStack() },
                            tabIndex = it.arguments?.getInt("tabIndex") ?: 0
                        )
                    }
                }
            }
        }
    }
}
