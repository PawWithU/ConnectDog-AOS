package com.kusitms.connectdog.feature.main

import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsAnimationCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import com.kusitms.connectdog.core.designsystem.theme.ConnectDogTheme
import com.kusitms.connectdog.core.util.Mode
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var verificationId: String
    private var isKeyboardShowing = false
    private var imeHeight by mutableIntStateOf(0)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)
        auth = FirebaseAuth.getInstance()

        // 로그인, 봉사자, 중개자 모드 설정
        val type = Mode.LOGIN

        installSplashScreen()
        imeListener()
        setContent {
            val navigator: MainNavigator = rememberMainNavigator(type = type)
            ConnectDogTheme {
                MainScreen(
                    navigator = navigator,
                    mode = type,
                    imeHeight = imeHeight,
                    sendVerificationCode = { sendVerificationCode("+82${it.substring(1)}") },
                    verifyCode = { verifyCode(it) },
                    finish = { finish() }
                )
            }
        }
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential): Boolean {
        var isCertified = false
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    isCertified = true
                    Toast.makeText(this, "인증에 성공했습니다.", Toast.LENGTH_SHORT).show()
                } else {
                    isCertified = false
                    Toast.makeText(this, "인증에 실패했습니다.", Toast.LENGTH_SHORT).show()
                }
            }
        return isCertified
    }

    private fun verifyCode(code: String): Boolean {
        val credential = PhoneAuthProvider.getCredential(verificationId, code)
        return signInWithPhoneAuthCredential(credential)
    }

    private fun sendVerificationCode(phoneNumber: String) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
            phoneNumber,
            60,
            TimeUnit.SECONDS,
            this,
            object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                    signInWithPhoneAuthCredential(credential)
                }

                override fun onVerificationFailed(e: FirebaseException) {
                    Log.d("testtt", e.toString())
                }

                override fun onCodeSent(
                    verificationId: String,
                    token: PhoneAuthProvider.ForceResendingToken
                ) {
                    Toast.makeText(this@MainActivity, "인증번호를 전송했습니다", Toast.LENGTH_SHORT).show()
                    this@MainActivity.verificationId = verificationId
                }
            }
        )
    }

    private fun imeListener() {
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING)
        ViewCompat.setWindowInsetsAnimationCallback(
            window.decorView.rootView,
            object : WindowInsetsAnimationCompat.Callback(DISPATCH_MODE_STOP) {
                override fun onProgress(
                    insets: WindowInsetsCompat,
                    runningAnimations: MutableList<WindowInsetsAnimationCompat>
                ): WindowInsetsCompat {
                    val imeHeight = insets.getInsets(WindowInsetsCompat.Type.ime()).bottom
                    val sysBarInsets = insets.getInsets(WindowInsetsCompat.Type.systemBars())

                    val updatedHeight =
                        if (imeHeight - sysBarInsets.bottom < 0) 0 else imeHeight - sysBarInsets.bottom
                    Log.d("saqa", updatedHeight.toString())
                    return insets
                }
            }
        )

        ViewCompat.setOnApplyWindowInsetsListener(window.decorView.rootView) { view, windowInsets ->
            val density = resources.displayMetrics.density
            val imeHeight = (windowInsets.getInsets(WindowInsetsCompat.Type.ime()).bottom / density).toInt()
            Log.d("tewq", imeHeight.toString())
            if (imeHeight != 0) {
                this@MainActivity.imeHeight = imeHeight - 20
            } else {
                this@MainActivity.imeHeight = 0
            }

            ViewCompat.onApplyWindowInsets(view, windowInsets)
        }
    }
}
