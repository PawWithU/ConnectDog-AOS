package com.kusitms.connectdog.core.data.repository

import com.kusitms.connectdog.core.data.api.InterApiService
import com.kusitms.connectdog.core.data.mapper.intermediator.toData
import com.kusitms.connectdog.core.data.mapper.toData
import com.kusitms.connectdog.core.data.mapper.volunteer.toData
import com.kusitms.connectdog.core.model.InterApplication
import com.kusitms.connectdog.core.model.Volunteer
import javax.inject.Inject

internal class InterManagementRepositoryImpl @Inject constructor(
    private val api: InterApiService
) : InterManagementRepository {
    override suspend fun getApplicationRecruiting(page: Int?, size: Int?): List<InterApplication> {
        return api.getApplicationRecruiting(page, size).map { it.toData() }
    }

    override suspend fun getApplicationWaiting(page: Int?, size: Int?): List<InterApplication> {
        return api.getApplicationWaiting(page, size).map { it.toData() }
    }

    override suspend fun getApplicationInProgress(page: Int?, size: Int?): List<InterApplication> {
        return api.getApplicationProgressing(page, size).map { it.toData() }
    }

    override suspend fun getApplicationCompleted(page: Int?, size: Int?): List<InterApplication> {
        return api.getApplicationCompleted(page, size).map { it.toData() }
    }

    override suspend fun getApplicationVolunteer(applicationId: Long): Volunteer {
        return api.getApplicationVolunteer(applicationId).toData()
    }
}
