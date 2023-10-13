package com.kusitms.connectdog.core.data.repository

import com.kusitms.connectdog.core.model.Example

interface ExampleRepository {
    suspend fun getExample(): List<Example>
}
