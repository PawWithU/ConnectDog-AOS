package com.kusitms.connectdog.core.data.repository

import com.kusitms.connectdog.core.data.api.model.ReviewResponseItem
import com.kusitms.connectdog.core.data.api.model.intermediator.IntermediatorInfoResponseItem
import com.kusitms.connectdog.core.data.api.model.volunteer.BookmarkResponseItem
import com.kusitms.connectdog.core.data.api.model.volunteer.NoticeDetailResponseItem
import com.kusitms.connectdog.core.model.Review

interface DetailRepository {
    suspend fun getNoticeDetail(postId: Long): NoticeDetailResponseItem
    suspend fun getIntermediatorInfo(intermediaryId: Long): IntermediatorInfoResponseItem
    suspend fun getIntermediatorNotice(intermediaryId: Long): List<BookmarkResponseItem>
    suspend fun getIntermediatorReview(page: Int? = 0, size: Int? = 0, intermediaryId: Long): List<Review>
    suspend fun postBookmark(postId: Long)
    suspend fun deleteBookmark(postId: Long)
}
