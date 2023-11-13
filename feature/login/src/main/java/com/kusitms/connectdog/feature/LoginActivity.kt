package com.kusitms.connectdog.feature

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.kusitms.connectdog.core.designsystem.theme.ConnectDogTheme
import com.kusitms.connectdog.feature.login.EmailLoginScreen
import com.kusitms.connectdog.feature.login.LoginScreen
import com.kusitms.connectdog.feature.login.LoginTypeScreen
import com.kusitms.connectdog.feature.login.LoginViewModel
import com.kusitms.connectdog.feature.main.MainActivity
import com.kusitms.connectdog.feature.signup.CompleteSignUpScreen
import com.kusitms.connectdog.feature.signup.RegisterEmailScreen
import com.kusitms.connectdog.feature.signup.RegisterPasswordScreen
import com.kusitms.connectdog.feature.signup.VolunteerSignUpScreen
import com.kusitms.connectdog.feature.signup.TermsViewModel
import com.kusitms.connectdog.feature.signup.intermediator.IntermediatorProfileScreen
import com.kusitms.connectdog.feature.signup.volunteer.ProfileScreen
import com.kusitms.connectdog.feature.signup.volunteer.SelectProfileImageScreen
import com.kusitms.connectdog.feature.signup.volunteer.SelectProfileImageViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : ComponentActivity() {
    private val termsViewModel: TermsViewModel by viewModels()
    private val loginViewModel: LoginViewModel by viewModels()
    private val selectProfileImageViewModel: SelectProfileImageViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        actionBar?.hide()

        setContent {
            val navigator = rememberNavController()

            ConnectDogTheme {
                NavHost(navigator, startDestination = "login") {
                    composable("login") {
                        LoginScreen(navigator)
                    }
                    composable("selectLogin") {
                        LoginTypeScreen(navigator, loginViewModel, this@LoginActivity)
                    }
                    composable("emailLogin") {
                        EmailLoginScreen(title = "이동봉사자 로그인", navigator) { initMainActivity() }
                    }
                    composable("volunteerSignUp") {
                        VolunteerSignUpScreen(navigator, termsViewModel)
                    }
                    composable("profile") {
                        ProfileScreen(navigator, selectProfileImageViewModel)
                    }
                    composable("profileImageSelect") {
                        SelectProfileImageScreen(navigator, selectProfileImageViewModel)
                    }
                    composable("registerEmail") {
                        RegisterEmailScreen(navigator = navigator)
                    }
                    composable("registerPassword") {
                        RegisterPasswordScreen(navigator = navigator) {}
                    }
                    composable("completeSignUp") {
                        CompleteSignUpScreen { initMainActivity() }
                    }
                    composable("intermediatorSignUp") {
                        IntermediatorProfileScreen(navigator)
                    }
                }
            }
        }
    }

    private fun initMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}
