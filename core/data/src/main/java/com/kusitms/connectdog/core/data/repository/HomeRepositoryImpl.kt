package com.kusitms.connectdog.core.data.repository

import com.kusitms.connectdog.core.data.api.ApiService
import com.kusitms.connectdog.core.data.mapper.toData
import com.kusitms.connectdog.core.data.mapper.volunteer.toData
import com.kusitms.connectdog.core.model.Announcement
import com.kusitms.connectdog.core.model.Review
import javax.inject.Inject

internal class HomeRepositoryImpl @Inject constructor(
    private val api: ApiService
) : HomeRepository {
    override suspend fun getAnnouncementList(): List<Announcement> {
        return api.getAnnouncementPostsHome().map { it.toData() }
    }

    override suspend fun getAnnouncementListWithFilter(
        postStatus: String?,
        departureLoc: String?,
        arrivalLoc: String?,
        startDate: String?,
        endDate: String?,
        dogSize: String?,
        isKennel: Boolean?,
        intermediaryName: String?,
        orderCondition: String?,
        page: Int?,
        size: Int?
    ): List<Announcement> {
        var depart = departureLoc
        if (depart != null) {
            if ("전체" in depart) depart = depart.take(2)
        }
        var dest = arrivalLoc
        if (dest != null) {
            if ("전체" in dest) dest = dest.take(2)
        }
        return api.getAnnouncementFilterPosts(
            postStatus,
            depart, dest,
            startDate, endDate,
            dogSize, isKennel, intermediaryName,
            orderCondition,
            page ?: 0, size ?: 50
        ).map { it.toData() }
    }

    override suspend fun getReviewList(page: Int?, size: Int?): List<Review> {
        return api.getReviewsHome(page ?: 0, size ?: 5).map { it.toData() }
    }
}
