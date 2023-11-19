package com.kusitms.connectdog.feature.home.screen

import android.util.Log
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kusitms.connectdog.core.designsystem.component.ConnectDogCalendar
import com.kusitms.connectdog.core.designsystem.component.ConnectDogExpandableCard
import com.kusitms.connectdog.core.designsystem.component.ConnectDogIconBottomButton
import com.kusitms.connectdog.core.designsystem.component.ConnectDogTopAppBar
import com.kusitms.connectdog.core.designsystem.component.TopAppBarNavigationType
import com.kusitms.connectdog.core.designsystem.theme.Gray2
import com.kusitms.connectdog.core.designsystem.theme.Gray3
import com.kusitms.connectdog.core.designsystem.theme.Gray4
import com.kusitms.connectdog.core.designsystem.theme.Gray8
import com.kusitms.connectdog.core.util.dateFormat
import com.kusitms.connectdog.feature.home.HomeViewModel
import com.kusitms.connectdog.feature.home.R
import com.kusitms.connectdog.feature.home.component.RegionBottomSheet
import com.kusitms.connectdog.feature.home.component.RegionType
import com.kusitms.connectdog.feature.home.component.SearchOrganization
import com.kusitms.connectdog.feature.home.component.SelectDogSize
import com.kusitms.connectdog.feature.home.component.SelectKennel
import com.kusitms.connectdog.feature.home.model.Detail
import com.kusitms.connectdog.feature.home.model.Filter
import kotlinx.coroutines.flow.collectLatest
import java.time.LocalDate

private val TAG = "FilterSearch"
@Composable
internal fun FilterSearchScreen(
    onBackClick: () -> Unit,
    viewModel: HomeViewModel = hiltViewModel(),
    onNavigateToSearch: (Filter) -> Unit
) {
    val filter by viewModel.filter.collectAsStateWithLifecycle()
    Log.d(TAG, "filter = $filter")

    val scrollState = rememberScrollState()

    Column(modifier = Modifier.background(color = Gray8)) {
        Column(
            modifier = Modifier
                .verticalScroll(scrollState)
                .height(1400.dp)
                .weight(1f),
        ) {
            TopAppBar(Gray8, onBackClick)
            Spacer(modifier = Modifier.size(14.dp))
            LocationCard(filter.departure, filter.arrival) { depart, dest ->
                viewModel.setFilter(depart, dest)
                Log.d("FilterSearch", "screen : ${filter.departure}, ${filter.arrival}")
            }
            ScheduleCard(filter.startDate, filter.endDate) { start, end ->
                viewModel.setFilter(start, end)
            }
            DetailCard(
                filter.detail,
                onSelectDogSize = {},
                onSelectKennel = {},
                onSelectOrganization = {},
            ) { dogSize: Detail.DogSize?, hasKennel: Boolean?, organization: String? ->
                viewModel.setFilter(Detail(dogSize, hasKennel, organization))
                Log.d("FilterSearch", "${filter.detail}")
            }
        }

        BottomBar(
            modifier = Modifier
                .padding(24.dp)
                .wrapContentSize(),
            onClickRefresh = {
                viewModel.clearFilter()
            },
            onClickSearch = { onNavigateToSearch(filter) }
        )
    }
}

@Composable
private fun TopAppBar(
    containerColor: Color,
    onBackClick: () -> Unit
) {
    ConnectDogTopAppBar(
        containerColor = containerColor,
        titleRes = R.string.filter_app_bar_title,
        navigationType = TopAppBarNavigationType.CLOSE,
        navigationIconContentDescription = "닫기",
        onNavigationClick = { onBackClick() }
    )
}

@Composable
private fun LocationCard(
    depart: String? = null,
    dest: String? = null,
    onSelectedRegion: (String, String) -> Unit
) {
    var isExpended by remember { mutableStateOf(true) }

    var departure = depart
    Log.d("FilterSearch", "LocationCard : departure = $departure, depart = $depart")
    var destination = dest
    var content =
        if (!depart.isNullOrEmpty() && !dest.isNullOrEmpty()) "$depart -> $dest"
        else ""

    ConnectDogExpandableCard(
        modifier = Modifier.fillMaxWidth(),
        isExpended = isExpended,
        onClick = { isExpended = !isExpended },
        defaultContent = {
            DefaultCardContent(titleRes = R.string.filter_location, content = content)
        },
        expandedContent = {
            ExpandedCardContent(
                titleRes = R.string.filter_location,
                spacer = 40,
                onClickSkip = {
                    isExpended = false
                },
                onClickNext = {
                    isExpended = false
                    if (departure != null && destination != null) {
                        content = "$departure -> $destination"
                        onSelectedRegion(departure!!, destination!!)
                    }
                }
            ) {
                LocationContent(
                    departureLocation = departure,
                    destinationLocation = destination,
                ) { st, end ->
                    departure = st
                    destination = end
                }
            }
        }
    )
}

@Composable
private fun ScheduleCard(
    start: LocalDate?,
    end: LocalDate?,
    onClickNext: (LocalDate, LocalDate) -> Unit
) {
    var isExpended by remember { mutableStateOf(false) }

    var startDate: LocalDate =start ?: LocalDate.now()
    var endDate: LocalDate = end ?: LocalDate.now()
    var content =
            if (start != null && end != null) dateRangeDisplay(start, end) else ""


    ConnectDogExpandableCard(
        isExpended = isExpended,
        onClick = { isExpended = !isExpended },
        defaultContent = {
            DefaultCardContent(titleRes = R.string.filter_schedule, content = content)
        }, expandedContent = {
            ExpandedCardContent(
                modifier = Modifier.wrapContentHeight(),
                titleRes = R.string.filter_schedule,
                spacer = 20,
                onClickSkip = {
                    isExpended = false
                    startDate = start ?: LocalDate.now()
                    endDate = end ?: LocalDate.now()
                },
                onClickNext = {
                    onClickNext(startDate, endDate)
                    content = dateRangeDisplay(startDate, endDate)
                    isExpended = false
                }) {
                ConnectDogCalendar(
                    startDate = startDate,
                    endDate = endDate
                ) { start, end ->
                    Log.d("FilterSearch", "start = $start - end = $end")
                    startDate = start
                    endDate = end
                }
            }
        })
}

@Composable
private fun DetailCard(
    detail: Detail,
    onSelectDogSize: (Detail.DogSize) -> Unit,
    onSelectKennel: (Boolean) -> Unit,
    onSelectOrganization: (String) -> Unit,
    onClickNext: (Detail.DogSize?, Boolean?, String?) -> Unit,
) {
    var isExpended by remember { mutableStateOf(false) }

    var dogSize = detail.dogSize
    var hasKennel = detail.hasKennel
    var organization = detail.organization
    val detailContent = detailContentDisplay(detail.dogSize, detail.hasKennel, detail.organization)



    ConnectDogExpandableCard(
        isExpended = isExpended,
        onClick = { isExpended = !isExpended },
        defaultContent = {
            DefaultCardContent(titleRes = R.string.filter_detail, content = detailContent)
        },
        expandedContent = {
            ExpandedCardContent(
                modifier = Modifier.wrapContentHeight(),
                titleRes = R.string.filter_detail,
                spacer = 30,
                onClickSkip = { isExpended = false },
                onClickNext = {
                    isExpended = false
                    onClickNext(dogSize, hasKennel, organization)
                }) {
                Column {
                    DetailContent(titleRes = R.string.filter_dog_size) {
                        SelectDogSize(dogSize) {
                            dogSize = it
                        }
                    }
                    Spacer(modifier = Modifier.size(30.dp))
                    DetailContent(titleRes = R.string.filter_kennel) {
                        SelectKennel(hasKennel) { hasKennel = it }
                    }
                    Spacer(modifier = Modifier.size(30.dp))
                    DetailContent(titleRes = R.string.filter_organization) {
                        SearchOrganization(organizationText = organization) {
                            organization = it
                        }
                    }
                }
            }
        })
}

@Composable
private fun DefaultCardContent(
    @StringRes titleRes: Int,
    content: String?
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .defaultMinSize(minHeight = 57.dp)
            .padding(16.dp)
    ) {
        Text(
            text = stringResource(id = titleRes),
            style = MaterialTheme.typography.titleMedium,
            fontSize = 14.sp
        )
        Text(
            modifier = Modifier.width(200.dp),
            text = if (content.isNullOrEmpty()) stringResource(id = R.string.filter_choose) else content,
            style = MaterialTheme.typography.titleMedium,
            fontSize = 14.sp,
            color = Gray3,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
            textAlign = TextAlign.End
        )
    }
}

@Composable
private fun ExpandedCardContent(
    modifier: Modifier = Modifier,
    @StringRes titleRes: Int,
    spacer: Int,
    onClickSkip: () -> Unit,
    onClickNext: () -> Unit,
    content: @Composable () -> Unit,
) {
    Column(
        modifier = modifier.padding(20.dp)
    ) {
        Text(
            text = stringResource(id = titleRes),
            style = MaterialTheme.typography.titleLarge,
            fontSize = 18.sp
        )
        Spacer(modifier = Modifier.size(spacer.dp))
        content()
        Spacer(modifier = Modifier.size(spacer.dp))
        DialogBottomButton(onClickSkip = onClickSkip, onClickNext = onClickNext)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun LocationContent(
    departureLocation: String? = "",
    destinationLocation: String? = "",
    onSelectedRegion: (String?, String?) -> Unit
) {
    val departureSheetState = rememberModalBottomSheetState()
    var isDepartureSheetOpen by rememberSaveable {
        mutableStateOf(false)
    }
    val destinationSheetState = rememberModalBottomSheetState()
    var isDestinationSheetOpen by rememberSaveable {
        mutableStateOf(false)
    }

    var departure by remember { mutableStateOf(departureLocation) }
    var destination by remember { mutableStateOf(destinationLocation) }

    Row(verticalAlignment = Alignment.CenterVertically) {
        Image(
            painter = painterResource(id = R.drawable.img_location_path),
            contentDescription = "출발지-도착지"
        )
        Spacer(modifier = Modifier.size(12.dp))
        Column {
            SelectLocation(
                titleRes = R.string.filter_departure,
                place = departure,
                placeholderRes = R.string.filter_select_departure
            ) {
                isDepartureSheetOpen = true
            }
            Spacer(modifier = Modifier.size(30.dp))
            SelectLocation(
                titleRes = R.string.filter_destination,
                place = destination,
                placeholderRes = R.string.filter_select_destination
            ) {
                isDestinationSheetOpen = true
            }
        }
    }

    if (isDepartureSheetOpen) {
        RegionBottomSheet(
            sheetState = departureSheetState,
            regionType = RegionType.DEPARTURE,
            onDismissRequest = { isDepartureSheetOpen = false }
        ) {
            Log.d("FilterSearch", "departure = $it")
            departure = it
            isDepartureSheetOpen = false
            onSelectedRegion(departure, destination)
        }
    }

    if (isDestinationSheetOpen) {
        RegionBottomSheet(
            sheetState = destinationSheetState,
            regionType = RegionType.DESTINATION,
            onDismissRequest = { isDestinationSheetOpen = false }
        ) {
            Log.d("FilterSearch", "destination = $it")
            destination = it
            isDestinationSheetOpen = false
            onSelectedRegion(departure, destination)
        }
    }
}

@Composable
private fun SelectLocation(
    modifier: Modifier = Modifier,
    @StringRes titleRes: Int,
    place: String?,
    @StringRes placeholderRes: Int,
    onClick: () -> Unit
) {
    Column(modifier = modifier.clickable { onClick() }) {
        Text(
            text = stringResource(id = titleRes),
            style = MaterialTheme.typography.titleMedium,
            fontSize = 14.sp
        )
        Spacer(modifier = Modifier.size(4.dp))
        Text(
            text = if (place.isNullOrEmpty()) stringResource(id = placeholderRes) else place,
            style = MaterialTheme.typography.bodyLarge,
            color = Gray4,
            modifier = Modifier
                .padding(vertical = 8.dp)
        )
        Divider(modifier = Modifier.fillMaxWidth())
    }
}

@Composable
private fun DialogBottomButton(
    onClickSkip: () -> Unit,
    onClickNext: () -> Unit,
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        Text(
            text = stringResource(id = R.string.filter_skip),
            style = MaterialTheme.typography.titleSmall,
            fontSize = 14.sp,
            modifier = Modifier.clickable { onClickSkip() },
            color = Gray2
        )
        Button(
            onClick = onClickNext,
            shape = RoundedCornerShape(6.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            ),
            modifier = Modifier.size(width = 104.dp, height = 37.dp)
        ) {
            Text(
                text = stringResource(id = R.string.filter_next),
                style = MaterialTheme.typography.titleSmall,
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onPrimary
            )
        }
    }
}

@Composable
private fun DetailContent(
    @StringRes titleRes: Int,
    content: @Composable () -> Unit
) {
    Column {
        Text(
            text = stringResource(id = titleRes),
            style = MaterialTheme.typography.titleMedium,
            fontSize = 12.sp
        )
        Spacer(modifier = Modifier.size(8.dp))
        content()
    }
}

@Composable
private fun BottomBar(
    modifier: Modifier = Modifier,
    onClickRefresh: () -> Unit,
    onClickSearch: () -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .wrapContentSize()
                .padding(end = 16.5.dp)
                .clickable { onClickRefresh() },
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier.size(24.dp),
                painter = painterResource(id = R.drawable.ic_refresh),
                contentDescription = stringResource(id = R.string.filter_button_refresh)
            )
            Text(
                text = stringResource(id = R.string.filter_button_refresh),
                style = MaterialTheme.typography.titleSmall,
                fontSize = 14.sp,
            )
        }

        ConnectDogIconBottomButton(
            modifier = Modifier.fillMaxWidth(1f),
            iconId = R.drawable.ic_search,
            contentDescription = stringResource(id = R.string.filter_button_search),
            onClick = onClickSearch,
            content = stringResource(id = R.string.filter_button_search)
        )
    }

}


@Preview
@Composable
private fun FilterSearchScreenPreview() {
    FilterSearchScreen({}, hiltViewModel(), {})
}


/**
 * UI display
 */
private fun dateRangeDisplay(startDate: LocalDate, endDate: LocalDate): String {
    val datePattern = "M월 dd일"
    if (startDate == endDate) return startDate.dateFormat(datePattern)
    return startDate.dateFormat(datePattern) + " - " + endDate.dateFormat(datePattern)
}

private fun detailContentDisplay(
    dogSize: Detail.DogSize?,
    hasKennel: Boolean?,
    organization: String?
): String {
    val dogSizeDisplayName = dogSize?.toDisplayName().orEmpty()
    val kennelStatus = if (hasKennel == null) "" else if (hasKennel) "켄넬 O" else "켄넬 X"
    val organizationText = organization.orEmpty()

    return buildString {
        append(dogSizeDisplayName)
        if (dogSize != null && hasKennel != null) append("-")
        append(kennelStatus)
        if (this.isNotEmpty() && organization != null) append("-")
        if (organizationText.isNotEmpty()) append(organizationText)
    }
}