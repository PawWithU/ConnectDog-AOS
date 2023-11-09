package com.kusitms.connectdog.core.data.repository

import com.kusitms.connectdog.core.data.api.ApiService
import com.kusitms.connectdog.core.model.Announcement
import javax.inject.Inject

internal class HomeRepositoryImpl @Inject constructor(
    private val api: ApiService
) : HomeRepository {
    override suspend fun getAnnouncementList(): List<Announcement> {
        TODO("Not yet implemented")
    }
}