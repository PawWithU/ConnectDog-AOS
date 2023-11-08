package com.kusitms.connectdog.core.designsystem.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.kusitms.connectdog.core.designsystem.R

val fonts =
    FontFamily(
        Font(R.font.pretendard_bold, weight = FontWeight.Bold),
        Font(R.font.pretendard_semibold, weight = FontWeight.SemiBold),
        Font(R.font.pretendard_medium, weight = FontWeight.Medium),
        Font(R.font.pretendard_regular),
        Font(R.font.pretendard_extralight, weight = FontWeight.ExtraLight),
    )

val Typography =
    Typography(
        titleLarge =
            TextStyle(
                fontFamily = fonts,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                lineHeight = 28.sp,
                letterSpacing = 0.sp,
            ),
        titleMedium =
            TextStyle(
                fontFamily = fonts,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                lineHeight = 28.sp,
                letterSpacing = 0.sp,
            ),
        titleSmall =
            TextStyle(
                fontFamily = fonts,
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp,
                lineHeight = 20.sp,
                letterSpacing = 0.sp,
            ),
        bodyLarge =
            TextStyle(
                fontFamily = fonts,
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp,
                lineHeight = 24.sp,
                letterSpacing = 0.5.sp,
            ),
        bodyMedium =
            TextStyle(
                fontFamily = fonts,
                fontWeight = FontWeight.Normal,
                fontSize = 14.sp,
                lineHeight = 20.sp,
                letterSpacing = 0.5.sp,
            ),
        labelLarge =
            TextStyle(
                fontFamily = fonts,
                fontWeight = FontWeight.Normal,
                fontSize = 12.sp,
            ),
        labelMedium =
            TextStyle(
                fontFamily = fonts,
                fontWeight = FontWeight.Normal,
                fontSize = 10.sp,
                lineHeight = 16.sp,
                letterSpacing = 0.5.sp,
            ),
        labelSmall =
            TextStyle(
                fontFamily = fonts,
                fontWeight = FontWeight.ExtraLight,
                fontSize = 10.sp,
                lineHeight = 16.sp,
                letterSpacing = 0.5.sp,
            ),
    )
