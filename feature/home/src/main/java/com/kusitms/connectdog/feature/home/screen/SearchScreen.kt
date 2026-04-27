package com.kusitms.connectdog.feature.home.screen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Tune
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kusitms.connectdog.core.designsystem.component.AnnouncementContent
import com.kusitms.connectdog.core.designsystem.component.ConnectDogTopAppBar
import com.kusitms.connectdog.core.designsystem.component.Empty
import com.kusitms.connectdog.core.designsystem.component.Loading
import com.kusitms.connectdog.core.designsystem.component.TopAppBarNavigationType
import com.kusitms.connectdog.core.designsystem.theme.Gray1
import com.kusitms.connectdog.core.designsystem.theme.Gray3
import com.kusitms.connectdog.core.designsystem.theme.Gray4
import com.kusitms.connectdog.core.designsystem.theme.Gray5
import com.kusitms.connectdog.core.model.Announcement
import com.kusitms.connectdog.core.util.dateFormat
import com.kusitms.connectdog.feature.home.R
import com.kusitms.connectdog.feature.home.SearchViewModel
import com.kusitms.connectdog.feature.home.model.Detail
import com.kusitms.connectdog.feature.home.model.Filter
import com.kusitms.connectdog.feature.home.state.SearchAnnouncementUiState
import java.time.LocalDate

private val TAG = "SearchScreen"

@Composable
internal fun SearchScreen(
    onBackClick: () -> Unit,
    filterArg: Filter? = Filter(),
    viewModel: SearchViewModel = hiltViewModel(),
    onDetailClick: (Long) -> Unit,
    onNavigateToFilter: (Filter) -> Unit
) {
    viewModel.setFilter(filterArg!!)
    val filter by viewModel.filter.collectAsStateWithLifecycle()
    Log.d(TAG, "filter = $filter")

    val announcementUiState by viewModel.announcementUiState.collectAsStateWithLifecycle()
    val isByDeadline by viewModel.isDeadlineOrder.collectAsStateWithLifecycle()

    Column {
        TopAppBar { onBackClick() }
        FilterHeader(
            filter = filter,
            onClick = { onNavigateToFilter(filter) }
        )
        AnnouncementContent(
            uiState = announcementUiState,
            sortBtn = {
                SortButton(
                    modifier = Modifier
                        .padding(top = 20.dp, start = 20.dp, end = 20.dp)
                        .fillMaxWidth(),
                    isByDeadline = isByDeadline,
                    count = it
                ) { viewModel.changeOrderCondition() }
            },
            onClick = onDetailClick
        )
    }
}

@Composable
private fun TopAppBar(
    onBackClick: () -> Unit
) {
    ConnectDogTopAppBar(
        titleRes = R.string.search_app_bar_title,
        navigationType = TopAppBarNavigationType.BACK,
        navigationIconContentDescription = "Back",
        onNavigationClick = { onBackClick() }
    )
}

@Composable
private fun FilterHeader(
    filter: Filter,
    onClick: () -> Unit
) {
    val departureText = filter.departure.ifEmpty { stringResource(id = R.string.filter_select_departure) }
    val arrivalText = filter.arrival.ifEmpty { stringResource(id = R.string.filter_select_destination) }
    val dateText = if (filter.startDate != null && filter.endDate != null) {
        dateRangeDisplay(filter.startDate!!, filter.endDate!!)
    } else {
        stringResource(id = R.string.filter_schedule)
    }

    val departureSelected = filter.departure.isNotEmpty()
    val arrivalSelected = filter.arrival.isNotEmpty()
    val dateSelected = filter.startDate != null

    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .fillMaxWidth()
            .border(width = 1.dp, color = Gray5, shape = RoundedCornerShape(12.dp))
            .clickable { onClick() }
    ) {
        // Location row
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Outlined.LocationOn,
                contentDescription = null,
                tint = if (departureSelected || arrivalSelected) MaterialTheme.colorScheme.primary else Gray4,
                modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = departureText,
                style = MaterialTheme.typography.bodyMedium,
                fontSize = 14.sp,
                color = if (departureSelected) Gray1 else Gray4,
                modifier = Modifier.weight(1f)
            )
            Box(
                modifier = Modifier
                    .width(1.dp)
                    .height(16.dp)
                    .background(Gray5)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = arrivalText,
                style = MaterialTheme.typography.bodyMedium,
                fontSize = 14.sp,
                color = if (arrivalSelected) Gray1 else Gray4,
                modifier = Modifier.weight(1f)
            )
            Icon(
                painter = painterResource(id = R.drawable.ic_expand_down),
                contentDescription = null,
                tint = if (departureSelected || arrivalSelected) MaterialTheme.colorScheme.primary else Gray4,
                modifier = Modifier.size(16.dp)
            )
        }

        Divider(color = Gray5, thickness = 1.dp)

        // Schedule row
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Outlined.CalendarMonth,
                contentDescription = null,
                tint = if (dateSelected) MaterialTheme.colorScheme.primary else Gray4,
                modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = dateText,
                style = MaterialTheme.typography.bodyMedium,
                fontSize = 14.sp,
                color = if (dateSelected) Gray1 else Gray4,
                modifier = Modifier.weight(1f)
            )
            Icon(
                painter = painterResource(id = R.drawable.ic_expand_down),
                contentDescription = null,
                tint = if (dateSelected) MaterialTheme.colorScheme.primary else Gray4,
                modifier = Modifier.size(16.dp)
            )
        }

        Divider(color = Gray5, thickness = 1.dp)

        // Detail row
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Icon(
                imageVector = Icons.Outlined.Tune,
                contentDescription = null,
                tint = if (filter.detail.isNotEmpty()) MaterialTheme.colorScheme.primary else Gray4,
                modifier = Modifier.size(18.dp)
            )
            Box(
                modifier = Modifier
                    .width(1.dp)
                    .height(16.dp)
                    .background(Gray5)
            )
            DetailChip(
                label = filter.detail.dogSize?.toDisplayName() ?: stringResource(id = R.string.filter_dog_size),
                isSelected = filter.detail.dogSize != null
            )
            DetailChip(
                label = when (filter.detail.hasKennel) {
                    true -> stringResource(id = R.string.filter_kennel_no_need)
                    false -> stringResource(id = R.string.filter_kennel_need)
                    null -> stringResource(id = R.string.filter_kennel)
                },
                isSelected = filter.detail.hasKennel != null
            )
            DetailChip(
                label = filter.detail.organization?.ifEmpty { null }
                    ?: stringResource(id = R.string.filter_organization),
                isSelected = !filter.detail.organization.isNullOrEmpty()
            )
        }
    }
}

@Composable
private fun DetailChip(
    label: String,
    isSelected: Boolean
) {
    val color = if (isSelected) MaterialTheme.colorScheme.primary else Gray4
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .border(width = 1.dp, color = color, shape = CircleShape)
            .padding(horizontal = 10.dp, vertical = 4.dp)
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            fontSize = 12.sp,
            color = color,
            maxLines = 1
        )
        Spacer(modifier = Modifier.width(4.dp))
        Icon(
            painter = painterResource(id = R.drawable.ic_expand_down),
            contentDescription = null,
            tint = color,
            modifier = Modifier.size(12.dp)
        )
    }
}

@Composable
private fun SortButton(
    modifier: Modifier = Modifier,
    isByDeadline: Boolean = true,
    count: Int,
    onClick: () -> Unit
) {
    Row(modifier = modifier.fillMaxWidth()) {
        val annotatedString = buildAnnotatedString {
            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                append("${count}개")
            }
            append("의 공고")
        }

        Text(
            text = annotatedString,
            color = Gray3,
            fontSize = 14.sp
        )
        Spacer(modifier = Modifier.weight(1f))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End,
            modifier = Modifier.clickable { onClick() }
        ) {
            Text(
                text = if (isByDeadline) {
                    stringResource(id = R.string.search_sort_end)
                } else {
                    stringResource(id = R.string.search_sort_recent)
                },
                style = MaterialTheme.typography.bodyMedium,
                color = Gray1
            )
            Spacer(modifier = Modifier.width(4.dp))
            Icon(
                painter = painterResource(id = R.drawable.ic_sort),
                contentDescription = "정렬",
                tint = Gray1
            )
        }
    }
}

@Composable
private fun AnnouncementContent(
    uiState: SearchAnnouncementUiState,
    sortBtn: @Composable (Int) -> Unit,
    onClick: (Long) -> Unit
) {
    when (uiState) {
        is SearchAnnouncementUiState.SearchAnnouncements -> {
            AnnouncementList(list = uiState.announcements, sortBtn = sortBtn, onClick = onClick)
        }

        is SearchAnnouncementUiState.Empty -> {
            Empty(
                titleRes = R.string.filter_no_announcement,
                descriptionRes = R.string.filter_no_announcement_description
            )
        }

        is SearchAnnouncementUiState.Loading -> Loading()
    }
}

@Composable
private fun AnnouncementList(
    list: List<Announcement>,
    sortBtn: @Composable (Int) -> Unit,
    onClick: (Long) -> Unit
) {
    LazyColumn {
        item {
            sortBtn(list.size)
        }
        items(list) {
            AnnouncementContent(
                postId = it.postId,
                imageUrl = it.imageUrl,
                dogName = it.dogName,
                location = it.location,
                isKennel = it.isKennel,
                dogSize = it.dogSize,
                date = it.date,
                pickUpTime = it.pickUpTime,
                onClick = onClick
            )
        }
    }
}

// @Composable
// private fun AnnouncementLoading(
//    sortBtn: @Composable () -> Unit
// ) {
//    val list = List(10) {
//        Announcement("", "이동봉사 위치", "YY.mm.dd(요일)", -1, "강아지 이름", "", "", false)
//    }
//    LazyColumn {
//        item {
//            sortBtn()
//        }
//        items(list) {
//            AnnouncementContent(announcement = it) {}
//        }
//    }
// }

// @Preview
// @Composable
// private fun SearchScreenPreview() {
//    ConnectDogTheme {
//        Column {
//            TopAppBar { }
//            SearchBar(
//                modifier = Modifier
//                    .padding(horizontal = 13.dp, vertical = 6.dp)
//                    .fillMaxWidth()
//            ) {} // todo 검색 popup
//            FilterBar(
//                modifier = Modifier.padding(start = 13.dp, end = 13.dp, top = 4.dp),
//                filter = Filter(),
//                onClick = { /*TODO*/ }
//            )
//            AnnouncementContent(
//                uiState = AnnouncementUiState.Loading,
//                sortBtn = {
// //                    SortButton(
// //                        modifier = Modifier
// //                            .padding(top = 20.dp, end = 20.dp)
// //                            .fillMaxWidth(),
// //                        isByDeadline = true
// //                    ) { /*todo sort*/ }
//                },
//                onClick = {}
//            )
//        }
//    }
// }

/**
 * UI display
 */
private fun dateRangeDisplay(startDate: LocalDate, endDate: LocalDate): String {
    val datePattern = "M월 dd일"
    if (startDate == endDate) return startDate.dateFormat(datePattern)
    return startDate.dateFormat(datePattern) + " - " + endDate.dateFormat(datePattern)
}
