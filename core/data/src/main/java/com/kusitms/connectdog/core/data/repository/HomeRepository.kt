package com.kusitms.connectdog.core.data.repository

import com.kusitms.connectdog.core.model.Announcement
import com.kusitms.connectdog.core.model.Review

interface HomeRepository {
    suspend fun getAnnouncementList(): List<Announcement>

    suspend fun getAnnouncementListWithFilter(
        postStatus: String? = null,
        departureLoc: String? = null,
        arrivalLoc: String? = null,
        startDate: String? = "",
        endDate: String? = "",
        dogSize: String? = null,
        isKennel: Boolean? = null,
        intermediaryName: String? = null,
        orderCondition: String? = null,
        page: Int? = 0,
        size: Int? = 50
    ): List<Announcement>

    suspend fun getReviewList(page: Int? = 0, size: Int? = 5): List<Review>
}
