package com.kusitms.connectdog.feature.home

import com.kusitms.connectdog.core.model.Example

sealed interface ExampleUiState {
    object Loading : ExampleUiState
    object Empty : ExampleUiState
    data class Examples(
        val examples: List<Example>
    ): ExampleUiState {
        val platinumCount: Int
            get() = examples.count { it.grade == Example.Grade.PLATINUM }

        val goldCount: Int
            get() = examples.count { it.grade == Example.Grade.GOLD }
    }
}