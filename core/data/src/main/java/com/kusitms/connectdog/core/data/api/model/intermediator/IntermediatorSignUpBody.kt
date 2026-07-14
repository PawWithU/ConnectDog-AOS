package com.kusitms.connectdog.core.data.api.model.intermediator

data class IntermediatorSignUpBody(
    val isOptionAgr: Boolean,
    val realName: String,
    val phone: String,
    val email: String,
    val password: String,
    val name: String,
    val intro: String,
    val url: String,
    val contact: String,
)
