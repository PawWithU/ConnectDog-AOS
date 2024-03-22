package com.kusitms.connectdog.core.data.repository

import com.kusitms.connectdog.core.data.api.ApiService
import com.kusitms.connectdog.core.data.api.model.DeleteAccountResponse
import com.kusitms.connectdog.core.data.api.model.IsDuplicateNicknameResponse
import com.kusitms.connectdog.core.data.api.model.MyInfoResponseItem
import com.kusitms.connectdog.core.data.api.model.volunteer.BadgeResponse
import com.kusitms.connectdog.core.data.api.model.volunteer.BookmarkResponseItem
import com.kusitms.connectdog.core.data.api.model.volunteer.IsDuplicateNicknameBody
import com.kusitms.connectdog.core.data.api.model.volunteer.UserInfoResponse
import javax.inject.Inject

internal class MyPageRepositoryImpl @Inject constructor(
    private val api: ApiService
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

    override suspend fun deleteAccount(): DeleteAccountResponse {
        return api.deleteAccount()
    }

    override suspend fun postNickname(nickname: IsDuplicateNicknameBody): IsDuplicateNicknameResponse {
        return api.postNickname(nickname)
    }

    override suspend fun updateUserInfo(userInfo: UserInfoResponse) {
        return api.updateUserInfo(userInfo)
    }
}
