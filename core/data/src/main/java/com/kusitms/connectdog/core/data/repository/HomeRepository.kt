package com.kusitms.connectdog.core.data.repository

import com.kusitms.connectdog.core.model.Announcement
import com.kusitms.connectdog.core.model.Review

interface HomeRepository {
    suspend fun getAnnouncementList(): List<Announcement>

    suspend fun getAnnouncementListWithFilter(): List<Announcement>

    suspend fun getReviewList(): List<Review>
}
