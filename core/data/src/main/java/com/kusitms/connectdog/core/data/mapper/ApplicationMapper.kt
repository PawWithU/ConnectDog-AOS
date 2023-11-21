package com.kusitms.connectdog.core.data.mapper

import com.kusitms.connectdog.core.data.api.model.ApplicationCompletedResponseItem
import com.kusitms.connectdog.core.data.api.model.ApplicationInProgressResponseItem
import com.kusitms.connectdog.core.data.api.model.ApplicationWaitingResponseItem
import com.kusitms.connectdog.core.model.Application
import com.kusitms.connectdog.core.util.dateFormat
import com.kusitms.connectdog.core.util.dateRangeFormat
import com.kusitms.connectdog.core.util.toLocalDate

internal fun ApplicationWaitingResponseItem.toData(): Application {
    return Application(
        imageUrl = mainImage,
        location = "${this.departureLoc} → ${this.arrivalLoc}",
        date = dateRangeFormat(startDate, endDate),
        organization = intermediaryName,
        hasKennel = isKennel,
        postId = postId,
        applicationId = applicationId,
        reviewId = -1
    )
}

internal fun ApplicationInProgressResponseItem.toData(): Application {
    return Application(
        imageUrl = mainImage,
        location = "${this.departureLoc} → ${this.arrivalLoc}",
        date = dateRangeFormat(startDate, endDate),
        organization = intermediaryName,
        hasKennel = isKennel,
        postId = postId
    )
}

internal fun ApplicationCompletedResponseItem.toData(): Application {
    return Application(
        imageUrl = mainImage,
        location = "${this.departureLoc} → ${this.arrivalLoc}",
        date = dateRangeFormat(startDate, endDate),
        organization = intermediaryName,
        hasKennel = isKennel,
        postId = postId,
        reviewId = reviewId,
        dogStatusId = dogStatusId
    )
}
