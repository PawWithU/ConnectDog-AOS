package com.kusitms.connectdog.core.data.repository

import com.kusitms.connectdog.core.data.api.model.intermediator.CreateApplicationDto
import java.io.File

interface InterHomeRepository {
    suspend fun createApplication(
        body: CreateApplicationDto,
        images: List<File>,
    )
}
