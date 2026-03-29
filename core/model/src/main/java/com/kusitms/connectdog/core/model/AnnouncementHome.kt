package com.kusitms.connectdog.core.model

data class AnnouncementHome(
    val imageUrl: String,
    val location: String,
    val date: String,
    val postId: Int,
    val dogName: String,
    val pickUpTime: String?
) {
    companion object {
        fun loading() = AnnouncementHome(
            imageUrl = "",
            location = "이동봉사 위치",
            date = "YY.mm.dd(요일)",
            postId = -1,
            dogName = "",
            pickUpTime = ""
        )
    }
}
