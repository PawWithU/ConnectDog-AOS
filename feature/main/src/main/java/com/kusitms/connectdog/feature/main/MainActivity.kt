package com.kusitms.connectdog.feature.main

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsAnimationCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.messaging.FirebaseMessaging
import com.kusitms.connectdog.core.designsystem.theme.ConnectDogTheme
import dagger.hilt.android.AndroidEntryPoint
import org.orbitmvi.orbit.compose.collectAsState
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var verificationId: String
    private var imeHeight by mutableIntStateOf(0)
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)
        askNotificationPermission()
        initializeFcmToken()
        auth = FirebaseAuth.getInstance()

        installSplashScreen()
        imeListener()

        setContent {
            val uiState by viewModel.collectAsState()
            uiState.appMode?.let { appMode ->
                val navigator: MainNavigator = rememberMainNavigator(mode = appMode)
                ConnectDogTheme {
                    MainScreen(
                        navigator = navigator,
                        mode = appMode,
                        imeHeight = imeHeight,
                        sendVerificationCode = { sendVerificationCode("+82${it.substring(1)}") },
                        verifyCode = { code, callback -> verifyCode(code) { callback(it) } },
                        openWebBrowser = { url -> openWebBrowser(url) },
                        finish = { finishActivity() },
                    )
                }
            }
        }
    }

    private fun signInWithPhoneAuthCredential(
        credential: PhoneAuthCredential,
        callback: (Boolean) -> Unit,
    ) {
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "인증에 성공했습니다.", Toast.LENGTH_SHORT).show()
                    callback(true)
                } else {
                    Toast.makeText(this, "인증에 실패했습니다.", Toast.LENGTH_SHORT).show()
                    callback(false)
                }
            }
    }

    private fun verifyCode(
        code: String,
        callback: (Boolean) -> Unit,
    ) {
        val credential = PhoneAuthProvider.getCredential(verificationId, code)
        signInWithPhoneAuthCredential(credential) { isSuccess -> callback(isSuccess) }
    }

    private fun sendVerificationCode(phoneNumber: String) {
        val options =
            PhoneAuthOptions.newBuilder(auth)
                .setPhoneNumber(phoneNumber)
                .setTimeout(60L, TimeUnit.SECONDS)
                .setActivity(this@MainActivity)
                .setCallbacks(
                    object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                        override fun onVerificationCompleted(credential: PhoneAuthCredential) {}

                        override fun onVerificationFailed(e: FirebaseException) {}

                        override fun onCodeSent(
                            verificationId: String,
                            token: PhoneAuthProvider.ForceResendingToken,
                        ) {
                            Toast.makeText(this@MainActivity, "인증번호를 전송했습니다", Toast.LENGTH_SHORT).show()
                            this@MainActivity.verificationId = verificationId
                        }
                    },
                )
                .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    private fun imeListener() {
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING)
        ViewCompat.setWindowInsetsAnimationCallback(
            window.decorView.rootView,
            object : WindowInsetsAnimationCompat.Callback(DISPATCH_MODE_STOP) {
                override fun onProgress(
                    insets: WindowInsetsCompat,
                    runningAnimations: MutableList<WindowInsetsAnimationCompat>,
                ): WindowInsetsCompat {
                    val imeHeight = insets.getInsets(WindowInsetsCompat.Type.ime()).bottom
                    val sysBarInsets = insets.getInsets(WindowInsetsCompat.Type.systemBars())

                    val updatedHeight =
                        if (imeHeight - sysBarInsets.bottom < 0) 0 else imeHeight - sysBarInsets.bottom
                    return insets
                }
            },
        )

        ViewCompat.setOnApplyWindowInsetsListener(window.decorView.rootView) { view, windowInsets ->
            val density = resources.displayMetrics.density
            val imeHeight =
                (windowInsets.getInsets(WindowInsetsCompat.Type.ime()).bottom / density).toInt()
            if (imeHeight != 0) {
                this@MainActivity.imeHeight = imeHeight - 50
            } else {
                this@MainActivity.imeHeight = 0
            }
            ViewCompat.onApplyWindowInsets(view, windowInsets)
        }
    }

    private fun initializeFcmToken() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener(
            OnCompleteListener { task ->
                if (!task.isSuccessful) return@OnCompleteListener
                val token = task.result
                viewModel.updateFcmToken(token)
            },
        )
    }

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission(),
        ) { isGranted ->
            if (isGranted) {
            } else {
            }
        }

    private fun askNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.POST_NOTIFICATIONS,
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(
                        this,
                        Manifest.permission.POST_NOTIFICATIONS,
                    )
                ) {
                    // 이미 권한을 거절한 경우 권한 설정 화면으로 이동
                } else {
                    // 처음 권한 요청을 할 경우
                    registerForActivityResult(ActivityResultContracts.RequestPermission()) {
                    }.launch(Manifest.permission.POST_NOTIFICATIONS)
                }
            }
        }
    }

    private fun openWebBrowser(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        this@MainActivity.startActivity(intent)
    }

    private fun finishActivity() {
        this.finish()
    }
}
