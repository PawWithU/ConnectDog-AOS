package com.kusitms.connectdog.feature.home.model

import java.time.LocalDate

data class Filter(
    var departure: String = "",
    var arrival: String = "",
    var startDate: LocalDate? = null,
    var endDate: LocalDate? = null,
    var detail: Detail = Detail(),
) {
    fun isNotEmpty(): Boolean {
        return departure.isNotEmpty() || arrival.isNotEmpty() || startDate != null || endDate != null || detail.isNotEmpty()
    }
}

data class Detail(
    val dogSize: DogSize? = null,
    val hasKennel: Boolean? = null,
    val organization: String? = null,
) {
    fun isNotEmpty(): Boolean {
        return dogSize != null || hasKennel != null || organization != null
    }

    enum class DogSize {
        BIG,
        MIDDLE,
        SMALL,
        ;

        fun toDisplayName(): String {
            return when (this) {
                BIG -> "대형"
                MIDDLE -> "중형"
                SMALL -> "소형"
            }
        }
    }
}
