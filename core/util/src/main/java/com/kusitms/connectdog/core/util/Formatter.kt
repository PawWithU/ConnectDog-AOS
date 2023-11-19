package com.kusitms.connectdog.core.util

import java.time.LocalDate
import java.time.format.DateTimeFormatter

fun LocalDate.dateFormat(pattern: String) =
    this.format(DateTimeFormatter.ofPattern(pattern))

fun String.toLocalDate(pattern: String): LocalDate {
    val formatter = DateTimeFormatter.ofPattern(pattern)
    return LocalDate.parse(this, formatter)
}