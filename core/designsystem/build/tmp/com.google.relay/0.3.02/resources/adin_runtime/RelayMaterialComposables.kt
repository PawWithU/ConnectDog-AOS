/*
 * Copyright 2022 Google LLC
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

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.FloatingActionButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

/**
 * This file provides wrappers around Material components such that we can
 * translate from Material component schemas cleanly into Compose
 * constructor APIs.
 */

// TODO: we probably want to put this in a different directory and ship it separately
// in the long term.
//
// E.g. `com.google.relay.compose.material` or even `com.google.relay.compose.material2`

// TODO: support remaining M2 components
// TODO: support M3 components

@Composable
fun MaterialButton(
    modifier: Modifier = Modifier,
    tapHandler: () -> Unit = {},
    padding: PaddingValues = PaddingValues(0.dp),
    borderRadius: Double = 0.0,
    backgroundColor: Color = Color.Transparent,
    content: @Composable () -> Unit
) {
    Button(
        modifier = modifier,
        contentPadding = padding,
        colors = ButtonDefaults.buttonColors(backgroundColor = backgroundColor),
        shape = getShape(radius = borderRadius),
        onClick = tapHandler,
    ) {
        content()
    }
}

@Composable
fun MaterialFloatingActionButton(
    modifier: Modifier = Modifier,
    tapHandler: () -> Unit = {},
    backgroundColor: Color = Color.Transparent,
    content: @Composable () -> Unit,
) {

    FloatingActionButton(
        modifier = modifier,
        backgroundColor = backgroundColor,
        onClick = tapHandler
    ) {
        content()
    }
}
