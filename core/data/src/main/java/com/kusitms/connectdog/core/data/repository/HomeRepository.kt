package com.kusitms.connectdog.core.data.repository

import com.kusitms.connectdog.core.data.api.model.FcmTokenRequestBody
import com.kusitms.connectdog.core.model.Announcement
import com.kusitms.connectdog.core.model.AnnouncementHome
import com.kusitms.connectdog.core.model.Review

interface HomeRepository {
    suspend fun getReviewList(
        page: Int? = 0,
        size: Int? = 100,
    ): List<Review>

    suspend fun getAnnouncementList(): List<AnnouncementHome>

    suspend fun postFcmToken(fcmToken: FcmTokenRequestBody)

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
        size: Int? = 50,
    ): List<Announcement>
}
