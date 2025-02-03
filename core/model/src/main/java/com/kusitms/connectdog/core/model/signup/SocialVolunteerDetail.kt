package com.kusitms.connectdog.core.model.signup

data class SocialVolunteerDetail(
    val nickname: String,
    val profileImageNum: Int,
    val isOptionAgr: Boolean = true,
    val phone: String,
    val name: String
)
