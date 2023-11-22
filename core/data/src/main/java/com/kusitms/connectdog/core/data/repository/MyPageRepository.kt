package com.kusitms.connectdog.core.data.repository

import com.kusitms.connectdog.core.data.api.model.MyInfoResponseItem

interface MyPageRepository {
    suspend fun getMyInfo(): MyInfoResponseItem
}
