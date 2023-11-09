package com.kusitms.connectdog.core.model

data class Review(
    val profileUrl: String,
    val dogName: String,
    val userName: String,
    val contentUrl: String,
    val date: String,
    val location: String,
    val organization: String,
    val content: String
)