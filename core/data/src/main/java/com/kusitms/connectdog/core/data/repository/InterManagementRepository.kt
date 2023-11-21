package com.kusitms.connectdog.core.data.repository

import com.kusitms.connectdog.core.model.InterApplication

interface InterManagementRepository {
    suspend fun getApplicationRecruiting(page: Int? = 0, size: Int? = 5): List<InterApplication>
    suspend fun getApplicationWaiting(page: Int? = 0, size: Int? = 5): List<InterApplication>
    suspend fun getApplicationInProgress(page: Int? = 0, size: Int? = 5): List<InterApplication>
    suspend fun getApplicationCompleted(page: Int? = 0, size: Int? = 5): List<InterApplication>
}