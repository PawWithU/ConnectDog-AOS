package com.kusitms.connectdog.core.data.repository

import com.kusitms.connectdog.core.data.api.model.MyInfoResponseItem
import com.kusitms.connectdog.core.data.api.model.intermediator.IntermediatorAccountInfo
import com.kusitms.connectdog.core.data.api.model.volunteer.BadgeResponse
import com.kusitms.connectdog.core.data.api.model.volunteer.BookmarkResponseItem
import com.kusitms.connectdog.core.data.api.model.volunteer.UserInfoResponse
import com.kusitms.connectdog.core.data.api.model.volunteer.VolunteerAccountInfo
import com.kusitms.connectdog.core.model.signup.IsDuplicated
import com.kusitms.connectdog.core.model.signup.Nickname

interface MyPageRepository {
    suspend fun getMyInfo(): MyInfoResponseItem

    suspend fun getUserInfo(): UserInfoResponse

    suspend fun getBadge(): List<BadgeResponse>

    suspend fun getBookmarkData(): List<BookmarkResponseItem>

    suspend fun postNickname(nickname: Nickname): IsDuplicated

    suspend fun updateUserInfo(userInfo: UserInfoResponse)

    suspend fun updateNotification()

    suspend fun getVolunteerAccountInfo(): VolunteerAccountInfo

    suspend fun getInterAccountInfo(): IntermediatorAccountInfo

    suspend fun volunteerWithdraw()

    suspend fun interWithdraw()
}
