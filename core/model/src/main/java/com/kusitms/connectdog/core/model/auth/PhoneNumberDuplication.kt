package com.kusitms.connectdog.core.model.auth

data class PhoneNumberDuplication(
    val isDuplicated: Boolean,
    val socialType: String?,
    val email: String?
)
