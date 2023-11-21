package com.kusitms.connectdog.feature.intermediator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.kusitms.connectdog.core.designsystem.theme.ConnectDogTheme
import com.kusitms.connectdog.feature.intermediator.screen.IntermediatorHomeScreen

class IntermediatorActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        actionBar?.hide()

        setContent {
            val navigator = rememberNavController()
            ConnectDogTheme {
                NavHost(navigator, startDestination = "home") {
                    composable("home") {
                        IntermediatorHomeScreen(
                            onNotificationClick = { /*TODO*/ },
                            onSettingClick = {}
                        )
                    }
                }
            }
        }
    }
}
