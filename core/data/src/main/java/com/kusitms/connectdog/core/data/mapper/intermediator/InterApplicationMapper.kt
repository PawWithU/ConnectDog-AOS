package com.kusitms.connectdog.core.data.mapper.intermediator

import com.kusitms.connectdog.core.data.api.model.intermediator.InterApplicationCompletedResponseItem
import com.kusitms.connectdog.core.data.api.model.intermediator.InterApplicationInProgressResponseItem
import com.kusitms.connectdog.core.data.api.model.intermediator.InterApplicationRecruitingResponseItem
import com.kusitms.connectdog.core.data.api.model.intermediator.InterApplicationWaitingResponseItem
import com.kusitms.connectdog.core.data.api.model.intermediator.InterProfileFindingResponseItem
import com.kusitms.connectdog.core.model.Announcement
import com.kusitms.connectdog.core.model.InterApplication
import com.kusitms.connectdog.core.util.dateRangeFormat

internal fun InterApplicationRecruitingResponseItem.toData(): InterApplication =
    InterApplication(
        imageUrl = mainImage,
        dogName = dogName,
        location = "${this.departureLoc} → ${this.arrivalLoc}",
        date = dateRangeFormat(startDate, endDate),
        postId = postId,
        postStatus = postStatus,
        pickUpTime = pickUpTime,
        isKennel = isKennel,
        dogSize = dogSize,
    )

internal fun InterApplicationWaitingResponseItem.toData(): InterApplication =
    InterApplication(
        imageUrl = mainImage,
        dogName = dogName,
        location = "${this.departureLoc} → ${this.arrivalLoc}",
        date = dateRangeFormat(startDate, endDate),
        postId = postId,
        pickUpTime = pickUpTime,
        isKennel = isKennel,
        dogSize = dogSize,
        applicationId = applicationId,
        applicationTime = applicationTime,
    )

internal fun InterApplicationInProgressResponseItem.toData(): InterApplication =
    InterApplication(
        imageUrl = mainImage,
        dogName = dogName,
        location = "${this.departureLoc} → ${this.arrivalLoc}",
        date = dateRangeFormat(startDate, endDate),
        postId = postId,
        pickUpTime = pickUpTime,
        isKennel = isKennel,
        dogSize = dogSize,
        applicationId = applicationId,
    )

internal fun InterApplicationCompletedResponseItem.toData(): InterApplication =
    InterApplication(
        imageUrl = mainImage,
        dogName = dogName,
        location = "${this.departureLoc} → ${this.arrivalLoc}",
        date = dateRangeFormat(startDate, endDate),
        postId = postId,
        pickUpTime = pickUpTime,
        isKennel = isKennel,
        dogSize = dogSize,
        reviewId = reviewId,
    )

internal fun InterProfileFindingResponseItem.toData(): Announcement =
    Announcement(
        imageUrl = mainImage,
        location = "${this.departureLoc} → ${this.arrivalLoc}",
        date = dateRangeFormat(startDate, endDate),
        isKennel = isKennel,
        postId = postId.toInt(),
        dogName = dogName,
        dogSize = dogSize,
        pickUpTime = pickUpTime,
    )
