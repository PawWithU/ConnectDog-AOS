package com.kusitms.connectdog.core.data.mapper

import com.kusitms.connectdog.core.data.api.model.AnnouncementHomeResponseItem
import com.kusitms.connectdog.core.data.api.model.AnnouncementSearchResponseItem
import com.kusitms.connectdog.core.model.Announcement
import com.kusitms.connectdog.core.util.dateFormat
import com.kusitms.connectdog.core.util.toLocalDate

internal fun AnnouncementHomeResponseItem.toData(): Announcement {
    val datePattern = "yyyy-MM-dd"
    val uiPattern = "YY.MM.dd(E)"
    val start = this.startDate.toLocalDate(datePattern).dateFormat(uiPattern)
    val end = this.endDate.toLocalDate(datePattern).dateFormat(uiPattern)
    return Announcement(
        imageUrl = this.mainImage,
        location = "${this.departureLoc} → ${this.arrivalLoc}",
        date = "$start-$end",
        organization = this.intermediaryName,
        hasKennel = this.isKennel
    )
}

internal fun AnnouncementSearchResponseItem.toData(): Announcement {
    val datePattern = "yyyy-MM-dd"
    val uiPattern = "YY.MM.dd(E)"
    val start = this.startDate.toLocalDate(datePattern).dateFormat(uiPattern)
    val end = this.endDate.toLocalDate(datePattern).dateFormat(uiPattern)
    return Announcement(
        imageUrl = this.mainImage,
        location = "${this.departureLoc} → ${this.arrivalLoc}",
        date = "$start-$end",
        organization = this.intermediaryName,
        hasKennel = this.isKennel
    )
}
