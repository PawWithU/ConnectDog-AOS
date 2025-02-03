package com.kusitms.connectdog.core.model.signup

data class NormalVolunteerDetail(
    val email: String,
    val password: String,
    val nickname: String,
    val profileImageNum: Int,
    val isOptionAgr: Boolean = true,
    val phone: String,
    val name: String
)
