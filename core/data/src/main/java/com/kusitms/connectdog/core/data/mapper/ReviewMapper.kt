package com.kusitms.connectdog.core.data.mapper

import com.kusitms.connectdog.core.data.api.model.ReviewResponseItem
import com.kusitms.connectdog.core.model.Review
import com.kusitms.connectdog.core.util.toLocalDate

internal fun ReviewResponseItem.toData(): Review {
    val datePattern = "yyyy-MM-dd"
    val start = startDate.toLocalDate(datePattern)
    val end = endDate.toLocalDate(datePattern)
    return Review(
        profileUrl = mainImage, //todo profileUrl로 바뀔 예정
        dogName = dogName,
        userName = volunteerNickname,
        contentUrl = mainImage,
        date = "$start-$end",
        location = "$departureLoc->$arrivalLoc",
        organization = intermediaryName,
        content = content
    )
}