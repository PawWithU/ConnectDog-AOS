package com.kusitms.connectdog.core.model

data class Filter(
    val startLocation: String = "",
    val destLocation: String = "",
    val startDate: String = "",
    val endDate: String = "",
    val detail: Detail? = null
)

data class Detail(
    val dogSize: DogSize? = null,
    val hasKennel: Boolean? = null,
    val organization: String? = null
) {
    enum class DogSize {
        BIG, MID, SMALL
    }
}