package com.kusitms.connectdog.feature.home.screen

import android.util.Log
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kusitms.connectdog.core.designsystem.component.ConnectDogCalendar
import com.kusitms.connectdog.core.designsystem.component.ConnectDogExpandableCard
import com.kusitms.connectdog.core.designsystem.component.ConnectDogTopAppBar
import com.kusitms.connectdog.core.designsystem.component.TopAppBarNavigationType
import com.kusitms.connectdog.core.designsystem.component.dateFormat
import com.kusitms.connectdog.core.designsystem.theme.Gray2
import com.kusitms.connectdog.core.designsystem.theme.Gray3
import com.kusitms.connectdog.core.designsystem.theme.Gray4
import com.kusitms.connectdog.feature.home.HomeViewModel
import com.kusitms.connectdog.feature.home.R
import com.kusitms.connectdog.feature.home.component.SearchOrganization
import com.kusitms.connectdog.feature.home.component.SelectDogSize
import com.kusitms.connectdog.feature.home.component.SelectKennel
import com.kusitms.connectdog.feature.home.model.Detail
import com.kusitms.connectdog.feature.home.model.Filter
import java.time.LocalDate

@Composable
internal fun FilterSearchScreen(
    onBackClick: () -> Unit,
    viewModel: HomeViewModel = hiltViewModel(),
    onNavigateToSearch: () -> Unit
) {
    val filter by viewModel.filter.collectAsStateWithLifecycle()

    Column {
        TopAppBar(onBackClick)
        Spacer(modifier = Modifier.size(14.dp))
        LocationCard()
        ScheduleCard(filter.startDate, filter.endDate){ start, end ->
            viewModel.setFilter(start, end)
        }
        DetailCard(
            viewModel,
            filter.detail
        ) { dogSize: Detail.DogSize?, hasKennel: Boolean?, organization: String? ->
            viewModel.setFilter(Detail(dogSize, hasKennel, organization))
            Log.d("FilterSearch", "${filter.detail}")
        }
    }
}

@Composable
private fun TopAppBar(
    onBackClick: () -> Unit
) {
    ConnectDogTopAppBar(
        titleRes = R.string.filter_app_bar_title,
        navigationType = TopAppBarNavigationType.CLOSE,
        navigationIconContentDescription = "닫기",
        onNavigationClick = { onBackClick() }
    )
}

@Composable
private fun LocationCard() {
    var isExpended by remember { mutableStateOf(true) }
    ConnectDogExpandableCard(
        modifier = Modifier.fillMaxWidth(),
        isExpended = isExpended,
        onClick = { isExpended = !isExpended },
        defaultContent = {
            DefaultCardContent(titleRes = R.string.filter_location, content = null)
        },
        expandedContent = {
            ExpandedCardContent(
                titleRes = R.string.filter_location,
                spacer = 40,
                onClickSkip = { /*TODO*/ },
                onClickNext = {}
            ) { LocationContent() }
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

    var startDate: LocalDate by remember { mutableStateOf(start ?: LocalDate.now()) }
    var endDate: LocalDate by remember { mutableStateOf(end ?: LocalDate.now()) }

    var content by remember { mutableStateOf(if (start != null && end != null) dateRangeDisplay(start, end) else "")  }

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
                },
                onClickNext = {
                    onClickNext(startDate, endDate)
                    content = dateRangeDisplay(startDate, endDate)
                    isExpended = false
                }) {
                ConnectDogCalendar(
                    startDate = startDate,
                    endDate =  endDate
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
    viewModel: HomeViewModel,
    detail: Detail,
    onClickNext: (Detail.DogSize?, Boolean?, String?) -> Unit,
) {
    var isExpended by remember { mutableStateOf(false) }

    var dogSize by remember { mutableStateOf(detail.dogSize) }
    var hasKennel by remember { mutableStateOf(detail.hasKennel) }
    var organization by remember { mutableStateOf(detail.organization) }

    var detailContent by remember { mutableStateOf("") }

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
                    detailContent = viewModel.detailContentDisplay(dogSize, hasKennel, organization)
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
            text = if (content.isNullOrEmpty()) stringResource(id = R.string.filter_choose) else content,
            style = MaterialTheme.typography.titleMedium,
            fontSize = 14.sp,
            color = Gray3
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

@Composable
private fun LocationContent() {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Image(
            painter = painterResource(id = R.drawable.img_location_path),
            contentDescription = "출발지-도착지"
        )
        Spacer(modifier = Modifier.size(12.dp))
        Column {
            SelectLocation(
                titleRes = R.string.filter_departure,
                place = null,
                placeholderRes = R.string.filter_select_departure
            )
            Spacer(modifier = Modifier.size(30.dp))
            SelectLocation(
                titleRes = R.string.filter_destination,
                place = null,
                placeholderRes = R.string.filter_select_destination
            )
        }
    }
}

@Composable
private fun SelectLocation(
    modifier: Modifier = Modifier,
    @StringRes titleRes: Int,
    place: String?,
    @StringRes placeholderRes: Int
) {
    Column(modifier = modifier) {
        Text(
            text = stringResource(id = titleRes),
            style = MaterialTheme.typography.titleMedium,
            fontSize = 14.sp
        )
        Spacer(modifier = Modifier.size(4.dp))
        Text(
            text = place ?: stringResource(id = placeholderRes),
            style = MaterialTheme.typography.bodyLarge,
            color = Gray4,
            modifier = Modifier.padding(vertical = 8.dp)
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