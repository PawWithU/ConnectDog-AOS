package com.kusitms.connectdog.core.data.repository

import com.kusitms.connectdog.core.model.Application


interface ManagementRepository {
    suspend fun getApplicationWaiting(page: Int? = 0, size: Int? = 5): List<Application>
    suspend fun getApplicationInProgress(page: Int? = 0, size: Int? = 5): List<Application>
    suspend fun getApplicationCompleted(page: Int? = 0, size: Int? = 5): List<Application>
}