package com.kusitms.connectdog.core.designsystem.component

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import com.kusitms.connectdog.core.designsystem.R

@Composable
fun AnnouncementContent(
    date: String,
    organization : String,
    hasKennel: Boolean,
    style: TextStyle = MaterialTheme.typography.labelLarge
) {
    Content(
        textStyle = style,
        titleRes1 = R.string.date,
        titleRes2 = R.string.organization,
        titleRes3 = R.string.need_kennel,
        t1Value = date,
        t2Value = organization,
        t3Value = if (hasKennel) stringResource(id = R.string.has_kennel) else stringResource(id = R.string.has_not_kennel),
    )
}

@Composable
fun ReviewContent(
    date: String,
    location : String,
    organization: String,
    style: TextStyle = MaterialTheme.typography.labelLarge
) {
    Content(
        textStyle = style,
        titleRes1 = R.string.date,
        titleRes2 = R.string.location,
        titleRes3 = R.string.organization,
        t1Value = date,
        t2Value = location,
        t3Value = organization,
    )
}

@Composable
fun RecentContent(
    date: String,
    location: String,
    volunteer: String,
    style: TextStyle = MaterialTheme.typography.labelLarge
) {
    Content(
        textStyle = style,
        titleRes1 = R.string.date,
        titleRes2 = R.string.location,
        titleRes3 = R.string.volunteer,
        t1Value = date,
        t2Value = location,
        t3Value = volunteer
    )
}