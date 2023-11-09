package com.kusitms.connectdog.core.data.repository

import com.kusitms.connectdog.core.model.Announcement

interface HomeRepository {
    suspend fun getAnnouncementList(): List<Announcement>
}