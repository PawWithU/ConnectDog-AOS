package com.kusitms.connectdog.core.data.mapper.volunteer

import com.kusitms.connectdog.core.data.api.model.volunteer.AnnouncementHomeResponseItem
import com.kusitms.connectdog.core.data.api.model.volunteer.AnnouncementSearchResponseItem
import com.kusitms.connectdog.core.model.Announcement
import com.kusitms.connectdog.core.util.dateRangeFormat

internal fun AnnouncementHomeResponseItem.toData(): Announcement {
    return Announcement(
        imageUrl = this.mainImage,
        location = "${this.departureLoc} → ${this.arrivalLoc}",
        date = dateRangeFormat(startDate, endDate),
        organization = this.intermediaryName,
        hasKennel = this.isKennel,
        postId = this.postId
    )
}

internal fun AnnouncementSearchResponseItem.toData(): Announcement {
    return Announcement(
        imageUrl = this.mainImage,
        location = "${this.departureLoc} → ${this.arrivalLoc}",
        date = dateRangeFormat(startDate, endDate),
        organization = this.intermediaryName,
        hasKennel = this.isKennel,
        postId = this.postId
    )
}
