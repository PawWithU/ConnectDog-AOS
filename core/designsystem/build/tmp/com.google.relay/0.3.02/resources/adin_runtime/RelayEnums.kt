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

// How a group's children should be laid out.
enum class RelayContainerArrangement {
    Row,
    Column,
}

// How an item should be clipped. An item with Clip.None may still be
// clipped if its border radius is non-zero, resulting in a rounded rectangle.
enum class Clip {
    Circle,
    Oval,
    None,
}

// How the capitalization of a text item's content should be modified.
enum class Case {
    Upper,
    Lower,
    Capitalized,
    Title,
    None,
}

// The anchor to scroll from for a scrollable item.
enum class ScrollAnchor {
    Start,
    End
}

// How the children should be placed along the cross axis in a flexible layout.
enum class CrossAxisAlignment {
    // Place the children with their start edge aligned with the start side of
    // the cross axis.
    Start,

    // Place the children as close to the end of the cross axis as possible.
    End,

    // Place the children so that their centers align with the middle of the
    // cross axis.
    Center,

    // Require the children to fill the cross axis.
    Stretch,

    // Place the children along the cross axis such that their baselines match.
    //
    // Because baselines are always horizontal, this alignment is intended for
    // horizontal main axes. If the main axis is vertical, then this value is
    // treated like [Start].
    Baseline
}

// How the children should be placed along the main axis in a flexible layout.
enum class MainAxisAlignment {
    // Place the children as close to the start of the main axis as possible.
    Start,

    // Place the children as close to the end of the main axis as possible.
    //
    // If this value is used in a horizontal direction, a [TextDirection] must be
    // available to determine if the end is the left or the right.
    //
    // If this value is used in a vertical direction, a [VerticalDirection] must be
    // available to determine if the end is the top or the bottom.
    End,

    // Place the children as close to the middle of the main axis as possible.
    Center,

    // Place the free space evenly between the children.
    SpaceBetween,

    // Place the free space evenly between the children as well as half of that
    // space before and after the first and last child.
    SpaceAround,

    // Place the free space evenly between the children as well as before and
    // after the first and last child.
    SpaceEvenly,
}

// How the item should align itself in relation to its
// positioned parent, either horizontally or vertically.
enum class Attachment {
    Start,
    Center,
    End,
}
