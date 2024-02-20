package com.kusitms.connectdog.core.util

private val profileImageList = listOf(
    R.drawable.ic_profile_1,
    R.drawable.ic_profile_2,
    R.drawable.ic_profile_3,
    R.drawable.ic_profile_4,
    R.drawable.ic_profile_5,
    R.drawable.ic_profile_6,
    R.drawable.ic_profile_7,
    R.drawable.ic_profile_8,
    R.drawable.ic_profile_9,
)

fun getProfileImageId(num: Int): Int = profileImageList[num]