package com.kusitms.connectdog.feature.login

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.kusitms.connectdog.core.designsystem.theme.ConnectDogTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        actionBar?.hide()
        setContent {
            val navigator = rememberNavController()
            val viewModel: LoginViewModel by viewModels()

            ConnectDogTheme {
                NavHost(navigator, startDestination = "login") {
                    composable("login") {
                        LoginScreen(navigator)
                    }
                    composable("selectLogin") {
                        LoginTypeScreen(navigator, viewModel, this@LoginActivity)
                    }
                    composable("emailLogin") {
                        EmailLoginScreen(title = "이동봉사자 로그인", navigator)
                    }
                }
            }
        }
    }
}
