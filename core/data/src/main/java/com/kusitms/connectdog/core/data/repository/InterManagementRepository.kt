package com.kusitms.connectdog.core.data.repository

import com.kusitms.connectdog.core.data.api.model.intermediator.IntermediatorProfileInfoResponseItem
import com.kusitms.connectdog.core.model.ConnectDogResult
import com.kusitms.connectdog.core.model.InterApplication
import com.kusitms.connectdog.core.model.Volunteer

interface InterManagementRepository {
    suspend fun getIntermediatorProfileInfo(): IntermediatorProfileInfoResponseItem
    suspend fun getApplicationRecruiting(page: Int? = 0, size: Int? = 5): List<InterApplication>
    suspend fun getApplicationWaiting(page: Int? = 0, size: Int? = 5): List<InterApplication>
    suspend fun getApplicationInProgress(page: Int? = 0, size: Int? = 5): List<InterApplication>
    suspend fun getApplicationCompleted(page: Int? = 0, size: Int? = 5): List<InterApplication>
    suspend fun getApplicationVolunteer(applicationId: Long): Volunteer

    suspend fun confirmApplicationVolunteer(applicationId: Long): ConnectDogResult
    suspend fun rejectApplicationVolunteer(applicationId: Long): ConnectDogResult

    suspend fun completeApllication(applicationId: Long): ConnectDogResult
}
