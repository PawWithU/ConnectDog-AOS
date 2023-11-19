package com.kusitms.connectdog.core.data.repository

import com.kusitms.connectdog.core.data.api.ApiService
import com.kusitms.connectdog.core.data.mapper.toData
import com.kusitms.connectdog.core.model.Announcement
import com.kusitms.connectdog.core.model.Review
import javax.inject.Inject

internal class HomeRepositoryImpl @Inject constructor(
    private val api: ApiService
) : HomeRepository {
    override suspend fun getAnnouncementList(): List<Announcement> {
        return api.getAnnouncementPostsHome().map {it.toData()}
    }

    override suspend fun getAnnouncementListWithFilter(): List<Announcement> {
        TODO("Not yet implemented")
    }

    override suspend fun getReviewList(page: Int?, size: Int?): List<Review> {
        return api.getReviewsHome(page ?: 1, size ?: 5).map { it.toData() }
    }
}
