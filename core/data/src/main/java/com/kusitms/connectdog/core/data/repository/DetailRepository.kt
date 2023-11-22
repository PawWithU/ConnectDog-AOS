package com.kusitms.connectdog.core.data.repository

import com.kusitms.connectdog.core.data.api.model.intermediator.IntermediatorInfoResponseItem
import com.kusitms.connectdog.core.data.api.model.volunteer.BookmarkResponseItem
import com.kusitms.connectdog.core.data.api.model.volunteer.NoticeDetailResponseItem

interface DetailRepository {
    suspend fun getNoticeDetail(postId: Long): NoticeDetailResponseItem
    suspend fun getIntermediatorInfo(intermediaryId: Long): IntermediatorInfoResponseItem
    suspend fun getIntermediatorReview(intermediaryId: Long): List<BookmarkResponseItem>
}