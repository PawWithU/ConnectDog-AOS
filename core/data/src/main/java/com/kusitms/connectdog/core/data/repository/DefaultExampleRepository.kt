package com.kusitms.connectdog.core.data.repository

import com.kusitms.connectdog.core.data.api.ApiService
import com.kusitms.connectdog.core.data.mapper.toData
import com.kusitms.connectdog.core.model.Example
import javax.inject.Inject

internal class DefaultExampleRepository @Inject constructor(
    private val api: ApiService
) : ExampleRepository {
    override suspend fun getExample(): List<Example> {
        return api.getExample().map { it.toData() }
    }
}
