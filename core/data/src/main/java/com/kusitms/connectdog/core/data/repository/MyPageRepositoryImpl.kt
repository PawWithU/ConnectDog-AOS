package com.kusitms.connectdog.core.data.repository

import com.kusitms.connectdog.core.data.api.ApiService
import com.kusitms.connectdog.core.data.api.model.MyInfoResponseItem
import javax.inject.Inject

internal class MyPageRepositoryImpl @Inject constructor(
    private val api: ApiService
) : MyPageRepository {
    override suspend fun getMyInfo(): MyInfoResponseItem {
        return api.getMyInfo()
    }
}
