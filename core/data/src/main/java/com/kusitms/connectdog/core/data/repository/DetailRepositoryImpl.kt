package com.kusitms.connectdog.core.data.repository

import com.kusitms.connectdog.core.data.api.ApiService
import com.kusitms.connectdog.core.data.api.model.intermediator.IntermediatorInfoResponseItem
import com.kusitms.connectdog.core.data.api.model.volunteer.BookmarkResponseItem
import com.kusitms.connectdog.core.data.api.model.volunteer.NoticeDetailResponseItem
import javax.inject.Inject

internal class DetailRepositoryImpl @Inject constructor(
    private val api: ApiService
): DetailRepository {
    override suspend fun getNoticeDetail(postId: Long): NoticeDetailResponseItem {
        return api.getNoticeDetail(postId)
    }

    override suspend fun getIntermediatorInfo(intermediaryId: Long): IntermediatorInfoResponseItem {
        return api.getIntermediatorInfo(intermediaryId)
    }

    override suspend fun getIntermediatorReview(intermediaryId: Long): List<BookmarkResponseItem> {
        return api.getIntermediatorReview(intermediaryId)
    }
}