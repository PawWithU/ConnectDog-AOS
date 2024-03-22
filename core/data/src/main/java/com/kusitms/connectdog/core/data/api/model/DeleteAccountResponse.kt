package com.kusitms.connectdog.core.data.api.model

data class DeleteAccountResponse(
    val code: Int,
    val isSuccess: Boolean,
    val message: String,
    val result: Result
)

data class Result(
    val userId: Long
)
