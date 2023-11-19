package com.kusitms.connectdog.core.util

import java.time.LocalDate
import java.time.format.DateTimeFormatter

fun LocalDate.dateFormat(pattern: String) =
    this.format(DateTimeFormatter.ofPattern(pattern))