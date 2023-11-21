package com.kusitms.connectdog.core.data.mapper.intermediator

import com.kusitms.connectdog.core.data.api.model.intermediator.InterApplicationCompletedResponseItem
import com.kusitms.connectdog.core.data.api.model.intermediator.InterApplicationInProgressResponseItem
import com.kusitms.connectdog.core.data.api.model.intermediator.InterApplicationRecruitingResponseItem
import com.kusitms.connectdog.core.data.api.model.intermediator.InterApplicationWaitingResponseItem
import com.kusitms.connectdog.core.model.InterApplication
import com.kusitms.connectdog.core.util.dateRangeFormat

internal fun InterApplicationRecruitingResponseItem.toData(): InterApplication =
    InterApplication(
        imageUrl = mainImage,
        dogName = dogName,
        location = "${this.departureLoc} → ${this.arrivalLoc}",
        date = dateRangeFormat(startDate, endDate),
        volunteerName = volunteerName ?: "-",
        postId = postId,
        postStatus = postStatus,
    )

internal fun InterApplicationWaitingResponseItem.toData(): InterApplication =
    InterApplication(
        imageUrl = mainImage,
        dogName = dogName,
        location = "${this.departureLoc} → ${this.arrivalLoc}",
        date = dateRangeFormat(startDate, endDate),
        volunteerName = volunteerName,
        postId = postId,
        applicationTime = applicationTime,
        applicationId = applicationId
    )

internal fun InterApplicationInProgressResponseItem.toData(): InterApplication =
    InterApplication(
        imageUrl = mainImage,
        dogName = dogName,
        location = "${this.departureLoc} → ${this.arrivalLoc}",
        date = dateRangeFormat(startDate, endDate),
        volunteerName = volunteerName,
        postId = postId,
        applicationId = applicationId
    )

internal fun InterApplicationCompletedResponseItem.toData(): InterApplication =
    InterApplication(
        imageUrl = mainImage,
        dogName = dogName,
        location = "${this.departureLoc} → ${this.arrivalLoc}",
        date = dateRangeFormat(startDate, endDate),
        volunteerName = volunteerName,
        postId = postId,
        applicationId = applicationId,
        reviewId = reviewId,
        dogStatusId = dogStatusId
    )