package com.kusitms.connectdog.core.util

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

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

    return "$start - $end"
}

fun String.calDateTimeDifference(): String {
    val givenDateTime = LocalDateTime.parse(this, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))
    val currentDateTime = LocalDateTime.now()

    return when {
        ChronoUnit.DAYS.between(givenDateTime, currentDateTime) > 0 -> "${ChronoUnit.DAYS.between(givenDateTime, currentDateTime)}일"
        ChronoUnit.HOURS.between(givenDateTime, currentDateTime) > 0 -> "${ChronoUnit.HOURS.between(givenDateTime, currentDateTime)}시간"
        else -> "${ChronoUnit.MINUTES.between(givenDateTime, currentDateTime)}분"
    }
}