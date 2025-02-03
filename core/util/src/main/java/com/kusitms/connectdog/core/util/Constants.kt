package com.kusitms.connectdog.core.util

import androidx.annotation.DrawableRes

enum class UserType(
    @DrawableRes val topBarTitleRes: Int,
) {
    SOCIAL_VOLUNTEER(R.string.volunteer_signup),
    NORMAL_VOLUNTEER(R.string.volunteer_signup),
    INTERMEDIATOR(R.string.intermediator_signup)
}

enum class SocialType {
    GUEST,
    VOLUNTEER
}

enum class AccountType {
    EMAIL, PASSWORD
}