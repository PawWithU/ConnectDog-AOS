package com.kusitms.connectdog.core.data.repository

import com.kusitms.connectdog.core.data.api.model.MyInfoResponseItem
import com.kusitms.connectdog.core.data.api.model.volunteer.BadgeResponse
import com.kusitms.connectdog.core.data.api.model.volunteer.BookmarkResponseItem
import com.kusitms.connectdog.core.data.api.model.volunteer.UserInfoResponse

interface MyPageRepository {
    suspend fun getMyInfo(): MyInfoResponseItem
    suspend fun getUserInfo(): UserInfoResponse
    suspend fun getBadge(): List<BadgeResponse>
    suspend fun getBookmarkData(): List<BookmarkResponseItem>
}
