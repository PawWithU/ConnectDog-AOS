package com.kusitms.connectdog.core.data.mapper

import com.kusitms.connectdog.core.data.api.model.ApplicationCompletedResponseItem
import com.kusitms.connectdog.core.data.api.model.ApplicationInProgressResponseItem
import com.kusitms.connectdog.core.data.api.model.ApplicationWaitingResponseItem
import com.kusitms.connectdog.core.model.Application
import com.kusitms.connectdog.core.util.dateFormat
import com.kusitms.connectdog.core.util.toLocalDate

internal fun ApplicationWaitingResponseItem.toData(): Application {
    val datePattern = "yyyy-MM-dd"
    val uiPattern = "YY.MM.dd(E)"
    val start = this.startDate.toLocalDate(datePattern).dateFormat(uiPattern)
    val end = this.endDate.toLocalDate(datePattern).dateFormat(uiPattern)

    return Application(
        imageUrl = mainImage,
        location = "${this.departureLoc} → ${this.arrivalLoc}",
        date = "$start-$end",
        organization = intermediaryName,
        hasKennel = isKennel,
        postId = postId,
        reviewId = -1
    )
}

internal fun ApplicationInProgressResponseItem.toData(): Application {
    val datePattern = "yyyy-MM-dd"
    val uiPattern = "YY.MM.dd(E)"
    val start = this.startDate.toLocalDate(datePattern).dateFormat(uiPattern)
    val end = this.endDate.toLocalDate(datePattern).dateFormat(uiPattern)

    return Application(
        imageUrl = mainImage,
        location = "${this.departureLoc} → ${this.arrivalLoc}",
        date = "$start-$end",
        organization = intermediaryName,
        hasKennel = isKennel,
        postId = postId,
        reviewId = -1
    )
}

internal fun ApplicationCompletedResponseItem.toData(): Application {
    val datePattern = "yyyy-MM-dd"
    val uiPattern = "YY.MM.dd(E)"
    val start = this.startDate.toLocalDate(datePattern).dateFormat(uiPattern)
    val end = this.endDate.toLocalDate(datePattern).dateFormat(uiPattern)

    return Application(
        imageUrl = mainImage,
        location = "${this.departureLoc} → ${this.arrivalLoc}",
        date = "$start-$end",
        organization = intermediaryName,
        hasKennel = isKennel,
        postId = postId,
        reviewId = reviewId
    )
}