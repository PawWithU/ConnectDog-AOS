/*
 * Copyright 2020 The Android Open Source Project
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

import androidx.compose.foundation.BorderStroke
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.CacheDrawScope
import androidx.compose.ui.draw.DrawResult
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.geometry.isSimple
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.*
import androidx.compose.ui.node.Ref
import androidx.compose.ui.platform.debugInspectorInfo
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.toSize
import kotlin.math.ceil
import kotlin.math.max
import kotlin.math.min

/**
 * This file was cloned from androidx/compose/foundation/Border.kt, with slight
 * modifications for Relay.
 */

/**
 * Where to render border stroke relative to the nominal bounds of an element.
 */
enum class BorderAlignment {
    Inside,
    Center,
    Outside
}

/**
 * Modify element to add border with appearance specified with a [border] and a [shape] and clip it.
 *
 * @sample androidx.compose.foundation.samples.BorderSample()
 *
 * @param border [BorderStroke] class that specifies border appearance, such as size and color
 * @param shape shape of the border
 */
fun Modifier.relayBorder(
    border: BorderStroke,
    shape: Shape = RectangleShape,
    alignment: BorderAlignment = BorderAlignment.Inside
) =
    relayBorder(width = border.width, brush = border.brush, shape = shape, alignment = alignment)

/**
 * Modify element to add border with appearance specified with a [width], a [color] and a [shape]
 * and clip it.
 *
 * @sample androidx.compose.foundation.samples.BorderSampleWithDataClass()
 *
 * @param width width of the border. Use [Dp.Hairline] for a hairline border.
 * @param color color to paint the border with
 * @param shape shape of the border
 */
fun Modifier.relayBorder(
    width: Dp,
    color: Color,
    shape: Shape = RectangleShape,
    alignment: BorderAlignment = BorderAlignment.Inside
) =
    relayBorder(width, SolidColor(color), shape, alignment = alignment)

/**
 * Modify element to add border with appearance specified with a [width], a [brush] and a [shape]
 * and clip it.
 *
 * @sample androidx.compose.foundation.samples.BorderSampleWithBrush()
 *
 * @param width width of the border. Use [Dp.Hairline] for a hairline border.
 * @param brush brush to paint the border with
 * @param shape shape of the border
 */
fun Modifier.relayBorder(
    width: Dp,
    brush: Brush,
    shape: Shape,
    alignment: BorderAlignment = BorderAlignment.Inside
): Modifier = composed(
    factory = {
        // BorderCache object that is lazily allocated depending on the type of shape
        // This object is only used for generic shapes and rounded rectangles with different corner
        // radius sizes.
        val borderCacheRef = remember { Ref<BorderCache>() }
        this.then(
            Modifier.drawWithCache {
                val hasValidBorderParams = width.toPx() >= 0f && size.minDimension > 0f
                if (!hasValidBorderParams) {
                    drawContentWithoutBorder()
                } else {
                    val strokeWidthPx = min(
                        if (width == Dp.Hairline) 1f else ceil(width.toPx()),
                        ceil(size.minDimension / 2)
                    )
                    val halfStroke = strokeWidthPx / 2
                    val topLeft = Offset(halfStroke, halfStroke)
                    val borderSize = Size(
                        size.width - strokeWidthPx,
                        size.height - strokeWidthPx
                    )
                    // The stroke is larger than the drawing area so just draw a full shape instead
                    val fillArea = (strokeWidthPx * 2) > size.minDimension
                    when (val outline = shape.createOutline(size, layoutDirection, this)) {
                        is Outline.Generic ->
                            drawGenericBorder(
                                borderCacheRef,
                                brush,
                                outline,
                                fillArea,
                                strokeWidthPx
                            )
                        is Outline.Rounded ->
                            drawRoundRectBorder(
                                borderCacheRef,
                                brush,
                                outline,
                                topLeft,
                                borderSize,
                                fillArea,
                                strokeWidthPx,
                                alignment
                            )
                        is Outline.Rectangle ->
                            drawRectBorder(
                                brush,
                                topLeft,
                                borderSize,
                                fillArea,
                                strokeWidthPx,
                                alignment
                            )
                    }
                }
            }
        )
    },
    inspectorInfo = debugInspectorInfo {
        name = "border"
        properties["width"] = width
        if (brush is SolidColor) {
            properties["color"] = brush.value
            value = brush.value
        } else {
            properties["brush"] = brush
        }
        properties["shape"] = shape
    }
)

private fun Ref<BorderCache>.obtain(): BorderCache =
    this.value ?: BorderCache().also { value = it }

/**
 * Helper object that handles lazily allocating and re-using objects
 * to render the border into an offscreen ImageBitmap
 */
private data class BorderCache(
    private var imageBitmap: ImageBitmap? = null,
    private var canvas: androidx.compose.ui.graphics.Canvas? = null,
    private var canvasDrawScope: CanvasDrawScope? = null,
    private var borderPath: Path? = null
) {
    inline fun CacheDrawScope.drawBorderCache(
        borderSize: IntSize,
        config: ImageBitmapConfig,
        block: DrawScope.() -> Unit
    ): ImageBitmap {

        var targetImageBitmap = imageBitmap
        var targetCanvas = canvas
        // If we previously had allocated a full Argb888 ImageBitmap but are only requiring
        // an alpha mask, just re-use the same ImageBitmap instead of allocating a new one
        val compatibleConfig = targetImageBitmap?.config == ImageBitmapConfig.Argb8888 ||
            config == targetImageBitmap?.config
        if (targetImageBitmap == null ||
            targetCanvas == null ||
            size.width > targetImageBitmap.width ||
            size.height > targetImageBitmap.height ||
            !compatibleConfig
        ) {
            targetImageBitmap = ImageBitmap(
                borderSize.width,
                borderSize.height,
                config = config
            ).also {
                imageBitmap = it
            }
            targetCanvas = androidx.compose.ui.graphics.Canvas(targetImageBitmap).also {
                canvas = it
            }
        }

        val targetDrawScope = canvasDrawScope ?: CanvasDrawScope().also { canvasDrawScope = it }
        val drawSize = borderSize.toSize()
        targetDrawScope.draw(
            this,
            layoutDirection,
            targetCanvas,
            drawSize
        ) {
            // Clear the previously rendered portion within this ImageBitmap as we could
            // be re-using it
            drawRect(
                color = Color.Black,
                size = drawSize,
                blendMode = BlendMode.Clear
            )
            block()
        }
        targetImageBitmap.prepareToDraw()
        return targetImageBitmap
    }

    fun obtainPath(): Path =
        borderPath ?: Path().also { borderPath = it }
}

/**
 * Border implementation for invalid parameters that just draws the content
 * as the given border parameters are infeasible (ex. negative border width)
 */
private fun CacheDrawScope.drawContentWithoutBorder(): DrawResult =
    onDrawWithContent {
        drawContent()
    }

/**
 * Border implementation for generic paths. Note it is possible to be given paths
 * that do not make sense in the context of a border (ex. a figure 8 path or a non-enclosed
 * shape) We do not handle that here as we expect developers to give us enclosed, non-overlapping
 * paths
 */
private fun CacheDrawScope.drawGenericBorder(
    borderCacheRef: Ref<BorderCache>,
    brush: Brush,
    outline: Outline.Generic,
    fillArea: Boolean,
    strokeWidth: Float
): DrawResult =
    if (fillArea) {
        onDrawWithContent {
            drawContent()
            drawPath(outline.path, brush = brush)
        }
    } else {
        // Optimization, if we are only drawing a solid color border, we only need an alpha8 mask
        // as we can draw the mask with a tint.
        // Otherwise we need to allocate a full ImageBitmap and draw it normally
        val config: ImageBitmapConfig
        val colorFilter: ColorFilter?
        if (brush is SolidColor) {
            config = ImageBitmapConfig.Alpha8
            colorFilter = ColorFilter.tint(brush.value)
        } else {
            config = ImageBitmapConfig.Argb8888
            colorFilter = null
        }

        val pathBounds = outline.path.getBounds()
        val borderCache = borderCacheRef.obtain()
        // Create a mask path that includes a rectangle with the original path cut out of it
        val maskPath = borderCache.obtainPath().apply {
            reset()
            addRect(pathBounds)
            op(this, outline.path, PathOperation.Difference)
        }

        val cacheImageBitmap: ImageBitmap
        val pathBoundsSize = IntSize(
            ceil(pathBounds.width).toInt(),
            ceil(pathBounds.height).toInt()
        )
        with(borderCache) {
            // Draw into offscreen bitmap with the size of the path
            // We need to draw into this intermediate bitmap to act as a layer
            // and make sure that the clearing logic does not generate underdraw
            // into the target we are rendering into
            cacheImageBitmap = drawBorderCache(
                pathBoundsSize,
                config
            ) {
                // Paths can have offsets, so translate to keep the drawn path
                // within the bounds of the mask bitmap
                translate(-pathBounds.left, -pathBounds.top) {
                    // Draw the path with a stroke width twice the provided value.
                    // Because strokes are centered, this will draw both and inner and outer stroke
                    // with the desired stroke width
                    drawPath(path = outline.path, brush = brush, style = Stroke(strokeWidth * 2))

                    // Scale the canvas slightly to cover the background that may be visible
                    // after clearing the outer stroke
                    scale(
                        (size.width + 1) / size.width,
                        (size.height + 1) / size.height
                    ) {
                        // Remove the outer stroke by clearing the inverted mask path
                        drawPath(path = maskPath, brush = brush, blendMode = BlendMode.Clear)
                    }
                }
            }
        }

        onDrawWithContent {
            drawContent()
            translate(pathBounds.left, pathBounds.top) {
                drawImage(cacheImageBitmap, srcSize = pathBoundsSize, colorFilter = colorFilter)
            }
        }
    }

/**
 * Border implementation for simple rounded rects and those with different corner
 * radii
 */
private fun CacheDrawScope.drawRoundRectBorder(
    borderCacheRef: Ref<BorderCache>,
    brush: Brush,
    outline: Outline.Rounded,
    topLeft: Offset,
    borderSize: Size,
    fillArea: Boolean,
    strokeWidth: Float,
    alignment: BorderAlignment
): DrawResult {
    return if (outline.roundRect.isSimple) {
        val cornerRadius = outline.roundRect.topLeftCornerRadius
        val halfStroke = strokeWidth / 2
        val borderStroke = Stroke(strokeWidth)
        onDrawWithContent {
            drawContent()
            when {
                alignment == BorderAlignment.Center -> {
                    drawRoundRect(
                        brush = brush,
                        topLeft = Offset.Zero,
                        size = Size(
                            width = borderSize.width + strokeWidth,
                            height = borderSize.height + strokeWidth
                        ),
                        cornerRadius = cornerRadius,
                        style = borderStroke
                    )
                }
                // To achieve the proper inside and outside corner
                // radii and to tightly circumscribe the rounded
                // rectangle draw the border centered on the rounded
                // rectangle's boundry with twice the requested stroke
                // width and the clip the border at the rectangle's edge.
                alignment == BorderAlignment.Outside -> {
                    val roundedRecPath = Path()
                    roundedRecPath.addOutline(outline)
                    clipPath(
                        path = roundedRecPath,
                        clipOp = ClipOp.Difference
                    ) {
                        drawRoundRect(
                            brush = brush,
                            topLeft = Offset.Zero,
                            size = Size(
                                width = borderSize.width + strokeWidth,
                                height = borderSize.height + strokeWidth
                            ),
                            cornerRadius = cornerRadius,
                            style = Stroke(strokeWidth * 2)
                        )
                    }
                }
                fillArea -> {
                    // If the drawing area is smaller than the stroke being drawn
                    // drawn all around it just draw a filled in rounded rect
                    drawRoundRect(brush, cornerRadius = cornerRadius)
                }
                cornerRadius.x < halfStroke -> {
                    // If the corner radius is smaller than half of the stroke width
                    // then the interior curvature of the stroke will be a sharp edge
                    // In this case just draw a normal filled in rounded rect with the
                    // desired corner radius but clipping out the interior rectangle
                    clipRect(
                        strokeWidth,
                        strokeWidth,
                        size.width - strokeWidth,
                        size.height - strokeWidth,
                        clipOp = ClipOp.Difference
                    ) {
                        drawRoundRect(brush, cornerRadius = cornerRadius)
                    }
                }
                else -> {
                    // Otherwise draw a stroked rounded rect with the corner radius
                    // shrunk by half of the stroke width. This will ensure that the
                    // outer curvature of the rounded rectangle will have the desired
                    // corner radius.
                    drawRoundRect(
                        brush = brush,
                        topLeft = topLeft,
                        size = borderSize,
                        cornerRadius = cornerRadius.shrink(halfStroke),
                        style = borderStroke
                    )
                }
            }
        }
    } else {
        val path = borderCacheRef.obtain().obtainPath()
        val roundedRectPath = createRoundRectPath(path, outline.roundRect, strokeWidth, fillArea)
        onDrawWithContent {
            drawContent()
            drawPath(roundedRectPath, brush = brush)
        }
    }
}

/**
 * Border implementation for rectangular borders
 */
private fun CacheDrawScope.drawRectBorder(
    brush: Brush,
    topLeft: Offset,
    borderSize: Size,
    fillArea: Boolean,
    strokeWidthPx: Float,
    alignment: BorderAlignment
): DrawResult {
    if (alignment == BorderAlignment.Center) {
        return onDrawWithContent {
            drawContent()
            drawRect(
                brush = brush,
                topLeft = Offset(
                    x = topLeft.x - strokeWidthPx / 2,
                    y = topLeft.y - strokeWidthPx / 2
                ),
                size = Size(
                    width = borderSize.width + strokeWidthPx,
                    height = borderSize.height + strokeWidthPx
                ),
                style = Stroke(strokeWidthPx)
            )
        }
    } else if (alignment == BorderAlignment.Outside) {
        return onDrawWithContent {
            drawContent()
            drawRect(
                brush = brush,
                topLeft = Offset(
                    x = topLeft.x - strokeWidthPx,
                    y = topLeft.y - strokeWidthPx
                ),
                size = Size(
                    width = borderSize.width + strokeWidthPx * 2,
                    height = borderSize.height + strokeWidthPx * 2
                ),
                style = Stroke(strokeWidthPx)
            )
        }
    } else { // BorderAlignment.Inside
        // If we are drawing a rectangular stroke, just offset it by half the stroke
        // width as strokes are always drawn centered on their geometry.
        // If the border is larger than the drawing area, just fill the area with a
        // solid rectangle
        val rectTopLeft = if (fillArea) Offset.Zero else topLeft
        val size = if (fillArea) size else borderSize
        val style = if (fillArea) Fill else Stroke(strokeWidthPx)
        return onDrawWithContent {
            drawContent()
            drawRect(
                brush = brush,
                topLeft = rectTopLeft,
                size = size,
                style = style
            )
        }
    }
}

/**
 * Helper method that creates a round rect with the inner region removed
 * by the given stroke width
 */
private fun createRoundRectPath(
    targetPath: Path,
    roundedRect: RoundRect,
    strokeWidth: Float,
    fillArea: Boolean
): Path =
    targetPath.apply {
        reset()
        addRoundRect(roundedRect)
        if (!fillArea) {
            val insetPath = Path().apply {
                addRoundRect(createInsetRoundedRect(strokeWidth, roundedRect))
            }
            op(this, insetPath, PathOperation.Difference)
        }
    }

private fun createInsetRoundedRect(
    widthPx: Float,
    roundedRect: RoundRect
) = RoundRect(
    left = widthPx,
    top = widthPx,
    right = roundedRect.width - widthPx,
    bottom = roundedRect.height - widthPx,
    topLeftCornerRadius = roundedRect.topLeftCornerRadius.shrink(widthPx),
    topRightCornerRadius = roundedRect.topRightCornerRadius.shrink(widthPx),
    bottomLeftCornerRadius = roundedRect.bottomLeftCornerRadius.shrink(widthPx),
    bottomRightCornerRadius = roundedRect.bottomRightCornerRadius.shrink(widthPx)
)

/**
 * Helper method to shrink the corner radius by the given value, clamping to 0
 * if the resultant corner radius would be negative
 */
private fun CornerRadius.shrink(value: Float): CornerRadius = CornerRadius(
    max(0f, this.x - value),
    max(0f, this.y - value)
)
