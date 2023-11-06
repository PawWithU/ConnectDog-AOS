/*
 * Copyright 2021 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.relay.compose

import android.graphics.BlendMode as androidBlendMode
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

// Modifier that allows a rectangular drop shadow to be applied to a Composable.
//
// Inputs:
// * [color] determines the color and opacity of the shadow.
// * [borderRadius] determines the rounding of the rectangle's corners.
// * [blur] determines the shadow radius i.e. how blurry it is.
// * [offsetY] and [offsetX] determine the position of the shadow layer relative to the main layer.
// * [spread] determines how far the shadow spreads in every direction.
// * [blendMode] allows you to define how you want two layers to blend together.
//
// This modifier currently only has an effect when used in API level 28+ (Android 9+).
// The [blendMode] only has an effect in API level 29+ (Android 10+).
fun Modifier.relayDropShadow(
    color: Color,
    borderRadius: Dp = 0.dp,
    blur: Dp = 10.dp,
    offsetY: Dp = 5.dp,
    offsetX: Dp = 5.dp,
    spread: Dp = 0.dp,
    blendMode: BlendMode = BlendMode.SrcOver,
) = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
    // Handle API >= Pie (28)

    // Convert to an ARGB color int
    val shadowColor = color.toArgb()
    val transparent = color.copy(alpha = 0f).toArgb()

    this.drawBehind {
        this.drawIntoCanvas {
            val paint = Paint()
            val frameworkPaint = paint.asFrameworkPaint()
            frameworkPaint.color = transparent

            // setShadowLayer requires API 28+ to be hardware accelerated
            frameworkPaint.setShadowLayer(
                blur.toPx(),
                offsetX.toPx(),
                offsetY.toPx(),
                shadowColor,
            )
            // frameworkPaint.blendMode support requires API 29+
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                frameworkPaint.blendMode = convertBlendModeToAndroid(blendMode)
            }
            it.drawRoundRect(
                0f - spread.value,
                0f - spread.value,
                this.size.width + spread.value,
                this.size.height + spread.value,
                borderRadius.toPx(),
                borderRadius.toPx(),
                paint,
            )
        }
    }
} else {
    // TODO: Handle API level 21-27.
    //
    // Because setShadowLayer is not feasible to use on APIs where it is not hardware-accelerated,
    // we currently do not support shadows for lower API levels. Compose requires a minimum API
    // level of 21, so alternative support for API levels 21-27 is still being explored.
    Modifier
}

@RequiresApi(Build.VERSION_CODES.Q)
fun convertBlendModeToAndroid(composeBlendMode: BlendMode): androidBlendMode {
    // We currently only list the 16 blend modes that are supported in Figma
    return when (composeBlendMode) {
        BlendMode.SrcOver -> androidBlendMode.SRC_OVER
        BlendMode.Darken -> androidBlendMode.DARKEN
        BlendMode.Multiply -> androidBlendMode.MULTIPLY
        BlendMode.ColorBurn -> androidBlendMode.COLOR_BURN
        BlendMode.Lighten -> androidBlendMode.LIGHTEN
        BlendMode.Screen -> androidBlendMode.SCREEN
        BlendMode.ColorDodge -> androidBlendMode.COLOR_DODGE
        BlendMode.Overlay -> androidBlendMode.OVERLAY
        BlendMode.Softlight -> androidBlendMode.SOFT_LIGHT
        BlendMode.Hardlight -> androidBlendMode.HARD_LIGHT
        BlendMode.Difference -> androidBlendMode.DIFFERENCE
        BlendMode.Exclusion -> androidBlendMode.EXCLUSION
        BlendMode.Hue -> androidBlendMode.HUE
        BlendMode.Saturation -> androidBlendMode.SATURATION
        BlendMode.Color -> androidBlendMode.COLOR
        BlendMode.Luminosity -> androidBlendMode.LUMINOSITY
        // The default blend mode
        else -> androidBlendMode.SRC_OVER
    }
}
