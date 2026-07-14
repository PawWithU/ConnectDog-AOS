package com.kusitms.connectdog.core.data.mapper

import com.kusitms.connectdog.core.data.api.model.VolunteerResponse
import com.kusitms.connectdog.core.model.Volunteer

internal fun VolunteerResponse.toData(): Volunteer =
    Volunteer(
        volunteerName = volunteerName,
        phone = phone,
        createdDate = createdDate,
        content = content,
        id = id,
    )
