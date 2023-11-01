package com.kusitms.connectdog.core.designsystem.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val LightColorScheme =
    lightColorScheme(
        primary = PetOrange,
        onPrimary = Color.White,
        secondary = PetOrange,
        secondaryContainer = Color.White,
        background = Color.White,
        onBackground = Color.Black,
        primaryContainer = Orange_container,
        surface = Color.White,
        onSurface = Gray1,
        error = Red1,
        outline = Gray5
    /* Other default colors to override
    surface = Color(0xFFFFFBFE),
    onSecondary = Color.White,
    onTertiary = Color.White,
    onSurface = Color(0xFF1C1B1F),
     */
    )

@Composable
fun ConnectDogTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme =
        when {
            dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
                val context = LocalContext.current
                if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
            }
            else -> LightColorScheme
        }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
        }
    }

    MaterialTheme(
        colorScheme = LightColorScheme, // todo 확인필요 (colorScheme으로 하면 lightColorScheme 적용이 안되더라고요ㅜㅜ)
        typography = Typography,
        content = content
    )
}
