package com.kusitms.connectdog.core.designsystem.component.text

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp
import com.kusitms.connectdog.core.designsystem.theme.Gray1
import com.kusitms.connectdog.core.designsystem.theme.PetOrange
import kotlinx.collections.immutable.ImmutableList

@Composable
private fun rememberDecorationAnnotatedString(
    text: String,
    decorationTexts: ImmutableList<String>,
    decorationStyle: SpanStyle,
): AnnotatedString {
    return remember(
        key1 = text,
        key2 = decorationTexts,
        key3 = decorationStyle,
    ) {
        buildAnnotatedString {
            append(
                text = text,
            )
            decorationTexts.forEach { annotatedText ->
                val annotatedStartIndex =
                    text.indexOf(
                        string = annotatedText,
                    )
                if (annotatedStartIndex != -1) {
                    addStyle(
                        style = decorationStyle,
                        start = annotatedStartIndex,
                        end = annotatedStartIndex + annotatedText.length,
                    )
                }
            }
        }
    }
}

@Composable
fun HighlightText(
    modifier: Modifier = Modifier,
    text: String,
    highlightTexts: ImmutableList<String>,
    color: Color = Gray1,
    highlightColor: Color = PetOrange,
    align: TextAlign = TextAlign.Start,
    overflow: TextOverflow = TextOverflow.Ellipsis,
    style: TextStyle,
    decorationStyle: SpanStyle =
        SpanStyle(
            fontWeight = FontWeight.SemiBold,
            letterSpacing = 0.sp,
            color = highlightColor,
        ),
) {
    Text(
        modifier = modifier,
        text =
            rememberDecorationAnnotatedString(
                text = text,
                decorationTexts = highlightTexts,
                decorationStyle = decorationStyle,
            ),
        style = style,
        overflow = overflow,
        color = color,
        textAlign = align,
    )
}
