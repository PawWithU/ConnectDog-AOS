package com.kusitms.connectdog.core.util

import java.time.LocalDate
import java.time.format.DateTimeFormatter

fun LocalDate.dateFormat(pattern: String) =
    this.format(DateTimeFormatter.ofPattern(pattern))

fun String.toLocalDate(pattern: String): LocalDate {
    val formatter = DateTimeFormatter.ofPattern(pattern)
    return LocalDate.parse(this, formatter)
}

fun dateRangeFormat(startDate: String, endDate: String): String {
    val datePattern = "yyyy-MM-dd"
    val uiPattern = "YY.MM.dd(E)"

    val start = startDate.toLocalDate(datePattern).dateFormat(uiPattern)
    val end = endDate.toLocalDate(datePattern).dateFormat(uiPattern)

    return "$start-$end"
}