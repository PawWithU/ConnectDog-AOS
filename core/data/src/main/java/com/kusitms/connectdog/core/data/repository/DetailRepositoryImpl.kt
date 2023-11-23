package com.kusitms.connectdog.core.data.repository

import com.kusitms.connectdog.core.data.api.ApiService
import com.kusitms.connectdog.core.data.api.model.ReviewResponseItem
import com.kusitms.connectdog.core.data.api.model.intermediator.IntermediatorInfoResponseItem
import com.kusitms.connectdog.core.data.api.model.volunteer.BookmarkResponseItem
import com.kusitms.connectdog.core.data.api.model.volunteer.NoticeDetailResponseItem
import com.kusitms.connectdog.core.data.mapper.toData
import com.kusitms.connectdog.core.model.Review
import javax.inject.Inject

internal class DetailRepositoryImpl @Inject constructor(
    private val api: ApiService
) : DetailRepository {
    override suspend fun getNoticeDetail(postId: Long): NoticeDetailResponseItem {
        return api.getNoticeDetail(postId)
    }

    override suspend fun getIntermediatorInfo(intermediaryId: Long): IntermediatorInfoResponseItem {
        return api.getIntermediatorInfo(intermediaryId)
    }

    override suspend fun getIntermediatorNotice(intermediaryId: Long): List<BookmarkResponseItem> {
        return api.getIntermediatorNotice(intermediaryId)
    }

    override suspend fun getIntermediatorReview(
        page: Int?,
        size: Int?,
        intermediaryId: Long
    ): List<Review> {
        return api.getIntermediatorReview(intermediaryId,page ?: 0, size ?: 0).map { it.toData() }
    }

    override suspend fun postBookmark(postId: Long) {
        api.postBookmark(postId)
    }

    override suspend fun deleteBookmark(postId: Long) {
        api.deleteBookmark(postId)
    }
}
