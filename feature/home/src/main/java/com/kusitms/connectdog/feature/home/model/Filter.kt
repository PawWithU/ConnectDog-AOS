package com.kusitms.connectdog.feature.home.model

data class Filter(
    val startLocation: String = "",
    val destLocation: String = "",
    val startDate: String = "",
    val endDate: String = "",
    var detail: Detail = Detail()
)

data class Detail(
    val dogSize: DogSize? = null,
    val hasKennel: Boolean? = null,
    val organization: String? = null
) {
    enum class DogSize {
        BIG, MIDDLE, SMALL;

        fun toDisplayName(): String {
            return when (this) {
                BIG -> "대형견"
                MIDDLE -> "중형견"
                SMALL -> "소형견"
            }
        }
    }
}

