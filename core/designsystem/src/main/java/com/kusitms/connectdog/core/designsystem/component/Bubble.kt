package com.kusitms.connectdog.core.designsystem.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kusitms.connectdog.core.designsystem.theme.PetOrange

@Composable
fun SpeechBubble(
    text: String,
    fontSize: Int,
    fontColor: Color,
    fontWeight: FontWeight,
) {
    Box {
        BubbleShape()
        Text(
            modifier = Modifier.align(Alignment.Center),
            text = text,
            fontSize = fontSize.sp,
            color = fontColor,
            fontWeight = fontWeight,
        )
    }
}

@Composable
private fun BubbleShape() {
    val density = LocalDensity.current
    val tailWidth = with(density) { 10.dp.toPx() }
    val tailHeight = with(density) { 9.dp.toPx() }
    val strokeWidth = with(density) { 1.dp.toPx() }

    Canvas(
        modifier =
            Modifier
                .height(28.dp)
                .fillMaxSize(),
    ) {
        val width = size.width
        val height = size.height

        val path =
            Path().apply {
                val cornerRadius = 100.dp.toPx()
                addRoundRect(RoundRect(0f, 0f, width, height, cornerRadius, cornerRadius))
            }

        val path2 =
            Path().apply {
                val tailStartX = (width / 2) - (tailWidth / 2)
                moveTo(tailStartX + 2f, height)
                lineTo(tailStartX + tailWidth - 2f, height)
            }

        val path3 =
            Path().apply {
                val tailStartX = (width / 2) - (tailWidth / 2)

                moveTo(tailStartX, height)
                lineTo(tailStartX + tailWidth / 2, height + tailHeight)
                lineTo(tailStartX + tailWidth, height)
            }

        drawPath(
            path = path,
            color = PetOrange,
            style = Stroke(width = strokeWidth),
        )

        drawPath(
            path = path2,
            color = Color.White,
            style = Stroke(width = strokeWidth + 2.dp.toPx()),
        )

        drawPath(
            path = path3,
            color = PetOrange,
            style = Stroke(width = strokeWidth),
        )
    }
}
