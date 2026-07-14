package com.kusitms.connectdog.core.data.mapper.volunteer

import com.kusitms.connectdog.core.data.api.model.volunteer.ApplicationCompletedResponseItem
import com.kusitms.connectdog.core.data.api.model.volunteer.ApplicationInProgressResponseItem
import com.kusitms.connectdog.core.data.api.model.volunteer.ApplicationWaitingResponseItem
import com.kusitms.connectdog.core.model.Application
import com.kusitms.connectdog.core.util.dateRangeFormat

internal fun ApplicationWaitingResponseItem.toData(): Application {
    return Application(
        imageUrl = mainImage,
        location = "${this.departureLoc} → ${this.arrivalLoc}",
        date = dateRangeFormat(startDate, endDate),
        hasKennel = isKennel,
        postId = postId,
        applicationId = applicationId,
        dogName = dogName,
        dogSize = dogSize,
        pickUpTime = pickUpTime,
    )
}

internal fun ApplicationInProgressResponseItem.toData(): Application {
    return Application(
        imageUrl = mainImage,
        location = "${this.departureLoc} → ${this.arrivalLoc}",
        date = dateRangeFormat(startDate, endDate),
        hasKennel = isKennel,
        postId = postId,
        applicationId = applicationId,
        dogName = dogName,
        dogSize = dogSize,
        pickUpTime = pickUpTime,
    )
}

internal fun ApplicationCompletedResponseItem.toData(): Application {
    return Application(
        imageUrl = mainImage,
        location = "${this.departureLoc} → ${this.arrivalLoc}",
        date = dateRangeFormat(startDate, endDate),
        hasKennel = isKennel,
        postId = postId,
        reviewId = reviewId,
        dogName = dogName,
        dogSize = dogSize,
        pickUpTime = pickUpTime,
    )
}
