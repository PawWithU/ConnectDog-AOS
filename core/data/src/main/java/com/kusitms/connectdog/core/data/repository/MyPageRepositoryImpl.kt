package com.kusitms.connectdog.core.data.repository

import com.kusitms.connectdog.core.data.api.ApiService
import com.kusitms.connectdog.core.data.api.InterApiService
import com.kusitms.connectdog.core.data.api.model.MyInfoResponseItem
import com.kusitms.connectdog.core.data.api.model.intermediator.IntermediatorAccountInfo
import com.kusitms.connectdog.core.data.api.model.volunteer.BadgeResponse
import com.kusitms.connectdog.core.data.api.model.volunteer.BookmarkResponseItem
import com.kusitms.connectdog.core.data.api.model.volunteer.UserInfoResponse
import com.kusitms.connectdog.core.data.api.model.volunteer.VolunteerAccountInfo
import com.kusitms.connectdog.core.model.signup.IsDuplicated
import com.kusitms.connectdog.core.model.signup.Nickname
import javax.inject.Inject

internal class MyPageRepositoryImpl @Inject constructor(
    private val api: ApiService,
    private val interApi: InterApiService
) : MyPageRepository {
    override suspend fun getMyInfo(): MyInfoResponseItem {
        return api.getMyInfo()
    }

    override suspend fun getUserInfo(): UserInfoResponse {
        return api.getUserInfo()
    }

    override suspend fun getBadge(): List<BadgeResponse> {
        return api.getBadge()
    }

    override suspend fun getBookmarkData(): List<BookmarkResponseItem> {
        return api.getBookmarkData()
    }

    override suspend fun postNickname(nickname: Nickname): IsDuplicated {
        return api.postNickname(nickname)
    }

    override suspend fun updateUserInfo(userInfo: UserInfoResponse) {
        return api.updateUserInfo(userInfo)
    }

    override suspend fun updateNotification() {
        api.patchNotification()
    }

    override suspend fun getVolunteerAccountInfo(): VolunteerAccountInfo {
        return api.getVolunteerAccountInfo()
    }

    override suspend fun getInterAccountInfo(): IntermediatorAccountInfo {
        return interApi.getInterAccountInfo()
    }

    override suspend fun volunteerWithdraw() {
        api.volunteerWithdraw()
    }

    override suspend fun interWithdraw() {
        interApi.interWithdraw()
    }
}
