package com.kusitms.connectdog.feature.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.kusitms.connectdog.core.designsystem.theme.ConnectDogTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navigator: MainNavigator = rememberMainNavigator()
            ConnectDogTheme {
                MainScreen(navigator = navigator)
            }
        }
    }
}
