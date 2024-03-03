package com.kusitms.connectdog.core.data.api.model.volunteer

data class SocialVolunteerSignUpBody(
    val nickname: String,
    val profileImageNum: Int,
    val isOptionAgr: Boolean = true
)
