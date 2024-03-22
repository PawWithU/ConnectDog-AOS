package com.kusitms.connectdog.core.data.api.model.volunteer

data class NormalVolunteerSignUpBody(
    val email: String,
    val password: String,
    val nickname: String,
    val profileImageNum: Int,
    val isOptionAgr: Boolean = true
)
