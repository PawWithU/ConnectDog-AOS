package com.kusitms.connectdog.core.data.repository

import com.google.gson.Gson
import com.kusitms.connectdog.core.data.api.InterApiService
import com.kusitms.connectdog.core.data.api.model.intermediator.CreateApplicationDto
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import javax.inject.Inject

internal class InterHomeRepositoryImpl
    @Inject
    constructor(
        private val api: InterApiService,
    ) : InterHomeRepository {
        override suspend fun createApplication(
            body: CreateApplicationDto,
            images: List<File>,
        ) {
            val jsonBody =
                RequestBody.create(
                    "application/json; charset=utf-8".toMediaTypeOrNull(),
                    Gson().toJson(body),
                )

            val files =
                images.map { file ->
                    val fileBody = RequestBody.create("multipart/form-data".toMediaTypeOrNull(), file)
                    MultipartBody.Part.createFormData("files", file.name, fileBody)
                }

            api.postApplication(jsonBody, files)
        }
    }
