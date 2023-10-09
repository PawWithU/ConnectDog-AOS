package com.kusitms.connectdog.core.data.mapper

import com.kusitms.connectdog.core.data.api.model.ExampleResponse
import com.kusitms.connectdog.core.model.Example

internal fun ExampleResponse.toData(): Example = Example(
    name = name,
    imageUrl = imageUrl,
    homepage = homepage,
    grade = when (grade) {
        ExampleResponse.Grade.PLATINUM -> Example.Grade.PLATINUM
        ExampleResponse.Grade.GOLD -> Example.Grade.GOLD
    }
)