package com.kusitms.connectdog.core.model

data class Example(
    val name: String,
    val imageUrl: String,
    val homepage: String,
    val grade: Grade
) {
    enum class Grade(val priority: Int) {
        PLATINUM(0),
        GOLD(1)
    }
}
