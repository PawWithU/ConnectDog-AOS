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

import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement as ComposeArrangement
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.GenericShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.debugInspectorInfo
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.intl.LocaleList
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.toLowerCase
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.google.relay.compose.ColumnScopeInstanceImpl.weight
import com.google.relay.compose.RowScopeInstanceImpl.weight
import java.util.Locale as JavaLocale
import java.util.regex.Pattern

// Wrapper for any Composable using the same core properties in a consistent
// way.
@Composable
private fun RelayBaseComposable(
    modifier: Modifier = Modifier,
    padding: PaddingValues = PaddingValues(0.dp),
    clip: Clip = Clip.None,
    clipToParent: Boolean = true,
    radius: Double = 0.0,
    elevation: Double = 0.0,
    backgroundColor: Color = Color.Transparent,
    strokeWidth: Double = 0.0,
    strokeColor: Color = Color.Transparent,
    borderAlignment: BorderAlignment = BorderAlignment.Inside,
    content: @Composable (Modifier) -> Unit,
) {
    val shape = getShape(clip, radius)
    val border = if (strokeWidth == 0.0) null else BorderStroke(strokeWidth.dp, strokeColor)
    val contentModifier = Modifier.thenIf(padding != PaddingValues(0.dp)) {
        padding(padding)
    }

    RelaySurface(
        modifier = modifier,
        shape = shape,
        clip = clipToParent,
        color = backgroundColor,
        border = border,
        borderAlignment = borderAlignment,
        elevation = elevation.dp,
    ) {
        content(contentModifier)
    }
}

// Text item wrapper that applies all base composable properties. Default values
// correspond to internal translation defaults, for parity between what is
// seen in Relay and on the target device.
@Composable
fun RelayText(
    modifier: Modifier = Modifier,
    content: AnnotatedString = AnnotatedString("Text Value"),
    fontSize: TextUnit = 14.sp,
    height: TextUnit = 1.em,
    letterSpacing: TextUnit = 0.sp,
    fontWeight: FontWeight? = FontWeight.W400,
    underline: Boolean = false,
    strikethrough: Boolean = false,
    italic: Boolean = false,
    maxLines: Int = 1,
    overflow: TextOverflow = TextOverflow.Visible,
    color: Color = Color.Black,
    textAlign: TextAlign = TextAlign.Center,
    case: Case = Case.None,
    fontFamily: FontFamily? = FontFamily.Default, // Roboto on most modern Android devices
    padding: PaddingValues = PaddingValues(0.dp),
    clip: Clip = Clip.None,
    radius: Double = 0.0,
    elevation: Double = 0.0,
    style: TextStyle = LocalTextStyle.current,
) {
    // Modify content according to the semantics of VariantCase
    fun maybeApplyCase(content: AnnotatedString, charCase: Case): AnnotatedString {
        return when (charCase) {
            Case.Upper -> content.toUpperCase(localeList = LocaleList(Locale.current))
            Case.Lower -> content.toLowerCase(localeList = LocaleList(Locale.current))
            else -> content
        }
    }

    val underlineDecoration = if (underline) TextDecoration.Underline else TextDecoration.None
    val strikethroughDecoration =
        if (strikethrough) TextDecoration.LineThrough else TextDecoration.None
    val combinedDecoration = TextDecoration.combine(
        listOf(
            underlineDecoration,
            strikethroughDecoration
        )
    )
    val fontStyle = if (italic) FontStyle.Italic else FontStyle.Normal
    val maxTextLines = if (maxLines == -1) Int.MAX_VALUE else maxLines

    RelayBaseComposable(
        modifier = modifier,
        padding = padding,
        clip = clip,
        clipToParent = false,
        radius = radius,
        elevation = elevation,
    ) {
        Text(
            maybeApplyCase(content, case),
            modifier = it,
            maxLines = maxTextLines,
            overflow = overflow,
            lineHeight = height,
            fontSize = fontSize,
            fontWeight = fontWeight,
            fontStyle = fontStyle,
            fontFamily = fontFamily,
            textDecoration = combinedDecoration,
            letterSpacing = letterSpacing,
            color = color,
            textAlign = textAlign,
            style = style,
        )
    }
}

// Text composible that is equivalent to the text composable above
// except that it takes a plain text [String] as content rather than an
// [AnnotatedString].
// The plain text string is immediately converted to an [AnnotatedString]
// and passed in to the other text composable. This composible was created
// for the convience of the caller and is modeled after the approach taken
// by the Compose library.
@Composable
fun RelayText(
    modifier: Modifier = Modifier,
    content: String = "Text Value",
    fontSize: TextUnit = 14.sp,
    height: TextUnit = 1.em,
    letterSpacing: TextUnit = 0.sp,
    fontWeight: FontWeight? = FontWeight.W400,
    underline: Boolean = false,
    strikethrough: Boolean = false,
    italic: Boolean = false,
    maxLines: Int = 1,
    overflow: TextOverflow = TextOverflow.Visible,
    color: Color = Color.Black,
    textAlign: TextAlign = TextAlign.Center,
    case: Case = Case.None,
    fontFamily: FontFamily? = FontFamily.Default, // Roboto on most modern Android devices
    padding: PaddingValues = PaddingValues(0.dp),
    clip: Clip = Clip.None,
    radius: Double = 0.0,
    elevation: Double = 0.0,
    style: TextStyle = LocalTextStyle.current,
) {
    // Modify [String] content according to the semantics of VariantCase
    fun maybeApplyCase(content: String, case: Case): String {
        val WHITESPACE_INCLUDE_DELIMITERS = Pattern.compile("((?=\\s)|(?<=\\s))")
        return when (case) {
            Case.Upper -> content.uppercase(JavaLocale.getDefault())
            Case.Lower -> content.lowercase(JavaLocale.getDefault())
            Case.Capitalized -> content.replaceFirstChar { it.uppercase(JavaLocale.getDefault()) }
            // Split the string on whitespace and include the whitespace
            // in the resulting list. Capitalize each list element and re-join.
            // This will capitalize the first letter of each word in the string
            // while preserving the whitespace as it originally existed.
            // (i.e. don't compress whitespace into a single space, swap
            // spaces for tabs, etc.)
            Case.Title -> WHITESPACE_INCLUDE_DELIMITERS.split(content)
                .map { it.replaceFirstChar() { it.uppercase(JavaLocale.getDefault()) } }
                .joinToString("")
            else -> content
        }
    }

    RelayText(
        modifier = modifier,
        content = AnnotatedString(maybeApplyCase(content, case)),
        fontSize = fontSize,
        height = height,
        letterSpacing = letterSpacing,
        fontWeight = fontWeight,
        underline = underline,
        strikethrough = strikethrough,
        italic = italic,
        maxLines = maxLines,
        overflow = overflow,
        color = color,
        textAlign = textAlign,
        case = Case.None,
        fontFamily = fontFamily,
        padding = padding,
        clip = clip,
        radius = radius,
        elevation = elevation,
        style = style
    )
}

@Composable
fun RelayImage(
    image: Painter = EmptyPainter(),
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Crop,
    radius: Double = 0.0,
    padding: PaddingValues = PaddingValues(0.dp),
    clip: Clip = Clip.None,
    clipToParent: Boolean = true,
    elevation: Double = 0.0,
    backgroundColor: Color = Color.Transparent,
    strokeWidth: Double = 0.0,
    strokeColor: Color = Color.Transparent,
    borderAlignment: BorderAlignment = BorderAlignment.Inside,
) {
    RelayBaseComposable(
        modifier = modifier,
        padding = padding,
        clip = clip,
        clipToParent = clipToParent,
        radius = radius,
        elevation = elevation,
        backgroundColor = backgroundColor,
        strokeWidth = strokeWidth,
        strokeColor = strokeColor,
        borderAlignment = borderAlignment,
    ) {
        // TODO: Include a contentDescription for accessibility
        // as required by Image.
        Image(image, "image description", modifier = it, contentScale = contentScale)
    }
}

@Composable
fun RelayVector(
    vector: Painter = EmptyPainter(),
    modifier: Modifier = Modifier,
    padding: PaddingValues = PaddingValues(0.dp),
    clip: Clip = Clip.None,
    elevation: Double = 0.0,
    backgroundColor: Color = Color.Transparent,
) {
    RelayBaseComposable(
        modifier = modifier,
        padding = padding,
        clip = clip,
        elevation = elevation,
        backgroundColor = backgroundColor,
    ) {
        // TODO: Include a contentDescription for accessibility
        // as required by Image.
        Image(vector, "shape description", modifier = it)
    }
}

@Composable
fun RelayContainer(
    // Shared properties
    modifier: Modifier = Modifier,
    padding: PaddingValues = PaddingValues(all = 0.0.dp),
    radius: Double = 0.0,
    elevation: Double = 0.0,
    strokeWidth: Double = 0.0,
    borderAlignment: BorderAlignment = BorderAlignment.Inside,
    strokeColor: Color = Color.Transparent,
    clip: Clip = Clip.None,
    clipToParent: Boolean = true,
    backgroundColor: Color = Color.Transparent,
    // Container properties
    isStructured: Boolean = true,
    arrangement: RelayContainerArrangement = RelayContainerArrangement.Column,
    itemSpacing: Double = 0.0,
    mainAxisAlignment: MainAxisAlignment = MainAxisAlignment.Center,
    crossAxisAlignment: CrossAxisAlignment = CrossAxisAlignment.Center,
    scrollable: Boolean = false,
    scrollAnchor: ScrollAnchor = ScrollAnchor.Start,
    scrollPadding: PaddingValues = PaddingValues(all = 0.0.dp),
    content: @Composable RelayContainerScope.() -> Unit,
) {
    RelayBaseComposable(
        modifier = modifier,
        padding = padding,
        clip = clip,
        clipToParent = clipToParent,
        radius = radius,
        elevation = elevation,
        backgroundColor = backgroundColor,
        strokeWidth = strokeWidth,
        borderAlignment = borderAlignment,
        strokeColor = strokeColor,
    ) {
        if (isStructured) {
            when (arrangement) {
                RelayContainerArrangement.Row -> {
                    val horizontalScrollModifier =
                        if (scrollable) it
                            .horizontalScroll(
                                enabled = scrollable,
                                state = ScrollState(0),
                                reverseScrolling = scrollAnchor == ScrollAnchor.End
                            )
                            .padding(scrollPadding)
                        else it
                    RelayRow(
                        modifier = horizontalScrollModifier,
                        // Note that the arrangement param for RelayRow and RelayColumn takes a value of
                        // type`Arrangement.Horizontal` or `Arrangement.Vertical` respectively,
                        // so logic cannot be shared between both.
                        horizontalArrangement = if (itemSpacing != 0.0) {
                            ComposeArrangement.spacedBy(
                                itemSpacing.dp,
                                when (mainAxisAlignment) {
                                    MainAxisAlignment.Center ->
                                        Alignment.CenterHorizontally
                                    MainAxisAlignment.Start ->
                                        Alignment.Start
                                    MainAxisAlignment.End ->
                                        Alignment.End
                                    // SpaceEvenly, SpaceBetween, and SpaceAround are not valid
                                    // when item spacing is applied, so we make them Alignment.Center
                                    MainAxisAlignment.SpaceEvenly ->
                                        Alignment.CenterHorizontally
                                    MainAxisAlignment.SpaceBetween ->
                                        Alignment.CenterHorizontally
                                    MainAxisAlignment.SpaceAround ->
                                        Alignment.CenterHorizontally
                                }
                            )
                        } else when (mainAxisAlignment) {
                            MainAxisAlignment.Center ->
                                ComposeArrangement.Center
                            MainAxisAlignment.Start ->
                                ComposeArrangement.Start
                            MainAxisAlignment.End ->
                                ComposeArrangement.End
                            MainAxisAlignment.SpaceEvenly ->
                                ComposeArrangement.SpaceEvenly
                            MainAxisAlignment.SpaceBetween ->
                                ComposeArrangement.SpaceBetween
                            MainAxisAlignment.SpaceAround ->
                                ComposeArrangement.SpaceAround
                        },
                        // Note that the cross alignment for RelayRow and RelayColumn takes a value of type
                        // `Alignment.Vertical` or `Alignment.Horizontal` respectively, so logic
                        // cannot be shared between both.
                        verticalAlignment = when (crossAxisAlignment) {
                            CrossAxisAlignment.Center ->
                                Alignment.CenterVertically
                            CrossAxisAlignment.Start ->
                                Alignment.Top
                            CrossAxisAlignment.End ->
                                Alignment.Bottom
                            // TODO: Find a way to more accurately convey these
                            //  cross axis alignment options
                            CrossAxisAlignment.Stretch -> Alignment.CenterVertically
                            CrossAxisAlignment.Baseline -> Alignment.CenterVertically
                        }
                    ) {
                        RowScopeInstance.content()
                    }
                }
                RelayContainerArrangement.Column -> {
                    val verticalScrollModifier =
                        if (scrollable) it
                            .verticalScroll(
                                enabled = scrollable,
                                state = ScrollState(0),
                                reverseScrolling = scrollAnchor == ScrollAnchor.End
                            )
                            .padding(scrollPadding)
                        else it
                    RelayColumn(
                        modifier = verticalScrollModifier,
                        verticalArrangement = if (itemSpacing != 0.0) {
                            ComposeArrangement.spacedBy(
                                itemSpacing.dp,
                                when (mainAxisAlignment) {
                                    MainAxisAlignment.Center ->
                                        Alignment.CenterVertically
                                    MainAxisAlignment.Start ->
                                        Alignment.Top
                                    MainAxisAlignment.End ->
                                        Alignment.Bottom
                                    // SpaceEvenly, SpaceBetween, and SpaceAround are not valid
                                    // when item spacing is applied, so we make them Alignment.Center
                                    MainAxisAlignment.SpaceEvenly ->
                                        Alignment.CenterVertically
                                    MainAxisAlignment.SpaceBetween ->
                                        Alignment.CenterVertically
                                    MainAxisAlignment.SpaceAround ->
                                        Alignment.CenterVertically
                                }
                            )
                        } else when (mainAxisAlignment) {
                            MainAxisAlignment.Center ->
                                ComposeArrangement.Center
                            MainAxisAlignment.Start ->
                                ComposeArrangement.Top
                            MainAxisAlignment.End ->
                                ComposeArrangement.Bottom
                            MainAxisAlignment.SpaceEvenly ->
                                ComposeArrangement.SpaceEvenly
                            MainAxisAlignment.SpaceBetween ->
                                ComposeArrangement.SpaceBetween
                            MainAxisAlignment.SpaceAround ->
                                ComposeArrangement.SpaceAround
                        },
                        horizontalAlignment = when (crossAxisAlignment) {
                            CrossAxisAlignment.Center ->
                                Alignment.CenterHorizontally
                            CrossAxisAlignment.Start ->
                                Alignment.Start
                            CrossAxisAlignment.End ->
                                Alignment.End
                            // TODO: Find a way to more accurately convey these
                            // cross axis alignment options
                            CrossAxisAlignment.Stretch -> Alignment.CenterHorizontally
                            CrossAxisAlignment.Baseline -> Alignment.CenterHorizontally
                        }
                    ) {
                        ColumnScopeInstance.content()
                    }
                }
            }
        } else {
            RelayBox(
                modifier = it
            ) {
                BoxScopeInstance.content()
            }
        }
    }
}

// Painter that draws nothing.
class EmptyPainter() : Painter() {
    override val intrinsicSize: Size get() = Size.Unspecified
    override fun DrawScope.onDraw() {}
}

// Scope for the children of [VariantContainer].
@LayoutScopeMarker
@Immutable
interface RelayContainerScope {
    fun Modifier.boxAlign(alignment: Alignment, offset: DpOffset): Modifier
    fun Modifier.columnWeight(weight: Float, fill: Boolean = false): Modifier
    fun Modifier.rowWeight(weight: Float, fill: Boolean = false): Modifier
}

// The type of scope a container uses.
internal enum class ScopeType {
    Row,
    Column,
    Box
}

@Stable
fun Modifier.tappable(
    onTap: (() -> Unit)? = null,
    onDoubleTap: (() -> Unit)? = null,
    onLongPress: (() -> Unit)? = null,
) = pointerInput(Unit) {
    detectTapGestures(
        onTap = { _ -> onTap?.invoke() },
        onDoubleTap = { _ -> onDoubleTap?.invoke() },
        onLongPress = { _ -> onLongPress?.invoke() }
    )
}

internal open class ContainerScopeImpl(val scope: ScopeType) : RelayContainerScope {

    @Stable
    override fun Modifier.boxAlign(alignment: Alignment, offset: DpOffset) =
        this
            .offset(x = offset.x, y = offset.y)
            .then(
                BoxChildData(
                    alignment = alignment,
                    matchParentSize = false,
                    inspectorInfo =
                    debugInspectorInfo {
                        name = "align"
                        value = alignment
                    }
                )
            )

    @Stable
    override fun Modifier.columnWeight(weight: Float, fill: Boolean) =
        this.then(Modifier.addWeight(ScopeType.Column, weight = weight, fill = fill))

    @Stable
    override fun Modifier.rowWeight(weight: Float, fill: Boolean) =
        this.then(Modifier.addWeight(ScopeType.Row, weight = weight, fill = fill))

    private fun Modifier.addWeight(
        matchingScope: ScopeType,
        weight: Float,
        fill: Boolean = false
    ): Modifier {
        return this.thenIf(scope == matchingScope) {
            LayoutWeightImpl(
                weight = weight,
                fill = false,
                inspectorInfo =
                debugInspectorInfo {
                    name = "weight"
                    value = weight
                    properties["weight"] = weight
                    properties["fill"] = fill
                }
            )
        }
    }
}

internal object RowScopeInstance : ContainerScopeImpl(ScopeType.Row)
internal object ColumnScopeInstance : ContainerScopeImpl(ScopeType.Column)
internal object BoxScopeInstance : ContainerScopeImpl(ScopeType.Box)

// Convert clip and radius into the corresponding Shape. Radius will only
// have an effect if clip is VariantClip.None.
fun getShape(clip: Clip = Clip.None, radius: Double): Shape {
    return when (clip) {
        Clip.Circle -> GenericShape { size: Size, _: LayoutDirection ->
            val shortEdgeLength = minOf(size.width, size.height)
            addOval(
                Rect(
                    Offset(
                        size.width / 2 - shortEdgeLength / 2,
                        size.height / 2 - shortEdgeLength / 2
                    ),
                    Size(shortEdgeLength, shortEdgeLength)
                )
            )
        }
        Clip.Oval -> CircleShape
        else -> RoundedCornerShape(radius.dp)
    }
}

// Helper function which checks a condition before applying a Modifier to an existing Modifier.
// This allows us to omit no-op modifiers, in line with Compose's guidance (see b/184681312).
inline fun Modifier.thenIf(condition: Boolean, factory: Modifier.() -> Modifier) =
    if (condition) factory() else this
