package com.kusitms.connectdog.core.data.mapper

import com.kusitms.connectdog.core.data.api.model.ReviewResponseItem
import com.kusitms.connectdog.core.model.Review
import com.kusitms.connectdog.core.util.dateRangeFormat
import com.kusitms.connectdog.core.util.toLocalDate

internal fun ReviewResponseItem.toData(): Review {
    return Review(
        profileNum = profileImageNum,
        dogName = dogName,
        userName = volunteerNickname,
        contentUrl = mainImage,
        date = dateRangeFormat(startDate, endDate),
        location = "$departureLoc → $arrivalLoc",
        organization = intermediaryName,
        content = content
    )
}
