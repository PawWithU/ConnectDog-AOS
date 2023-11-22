package com.kusitms.connectdog.core.data.mapper

import com.kusitms.connectdog.core.data.api.model.Response
import com.kusitms.connectdog.core.model.ConnectDogResult

internal fun Response.toData(): ConnectDogResult = ConnectDogResult(isSuccess)
