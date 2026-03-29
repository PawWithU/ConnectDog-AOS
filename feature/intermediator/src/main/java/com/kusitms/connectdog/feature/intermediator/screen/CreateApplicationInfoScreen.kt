package com.kusitms.connectdog.feature.intermediator.screen

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.LocalMinimumInteractiveComponentEnforcement
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kusitms.connectdog.core.designsystem.component.ConnectDogBottomButton
import com.kusitms.connectdog.core.designsystem.component.ConnectDogDialogButton
import com.kusitms.connectdog.core.designsystem.component.ConnectDogTextField
import com.kusitms.connectdog.core.designsystem.component.ConnectDogTopAppBar
import com.kusitms.connectdog.core.designsystem.component.DayTime
import com.kusitms.connectdog.core.designsystem.component.LocationContent
import com.kusitms.connectdog.core.designsystem.component.SelectKennel
import com.kusitms.connectdog.core.designsystem.component.TopAppBarNavigationType
import com.kusitms.connectdog.core.designsystem.theme.Gray1
import com.kusitms.connectdog.core.designsystem.theme.Gray11
import com.kusitms.connectdog.core.designsystem.theme.Gray2
import com.kusitms.connectdog.core.designsystem.theme.Gray4
import com.kusitms.connectdog.core.designsystem.theme.Gray5
import com.kusitms.connectdog.core.designsystem.theme.Gray7
import com.kusitms.connectdog.core.designsystem.theme.Gray90
import com.kusitms.connectdog.feature.intermediator.R
import com.kusitms.connectdog.feature.intermediator.component.CalendarBottomSheet
import com.kusitms.connectdog.feature.intermediator.component.TimeBottomSheet
import com.kusitms.connectdog.feature.intermediator.viewmodel.CreateApplicationViewModel
import java.time.LocalDate

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun CreateApplicationInfoScreen(
    onBackClick: () -> Unit,
    navigateToCreateDog: () -> Unit,
    viewModel: CreateApplicationViewModel,
    imeHeight: Int
) {
    val focusManager = LocalFocusManager.current
    val interactionSource = remember { MutableInteractionSource() }

    BackHandler {
        onBackClick()
        viewModel.clear()
    }

    Scaffold(
        modifier = Modifier
            .clickable(
                onClick = { focusManager.clearFocus() },
                indication = null,
                interactionSource = interactionSource
            ),
        topBar = {
            ConnectDogTopAppBar(
                titleRes = R.string.create_announcement,
                navigationType = TopAppBarNavigationType.BACK,
                onNavigationClick = {
                    viewModel.clear()
                    onBackClick()
                }
            )
        },
        bottomBar = {
            BottomBar(
                navigateToCreateDog = navigateToCreateDog,
                imeHeight = imeHeight,
                viewModel = viewModel
            )
        }
    ) {
        Content(
            imeHeight = imeHeight,
            viewModel = viewModel
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Content(
    imeHeight: Int,
    viewModel: CreateApplicationViewModel
) {
    var isScheduleSheetOpen by rememberSaveable { mutableStateOf(false) }
    var isTimeSheetOpen by rememberSaveable { mutableStateOf(false) }

    val scheduleSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val timeSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    val startDate = viewModel.startDate.collectAsState()
    val endDate = viewModel.endDate.collectAsState()

    val departure = viewModel.departure.collectAsState()
    val destination = viewModel.destination.collectAsState()

    val hour = viewModel.hour.collectAsState()
    val minute = viewModel.minute.collectAsState()
    val dayTime = viewModel.dayTime.collectAsState()

    val isKennel = viewModel.isKennel.collectAsState()

    val isAdjustableTime = viewModel.isAdjustableTime.collectAsState()
    val isAdjustableSchedule = viewModel.isAdjustableSchedule.collectAsState()

    val scrollState = rememberScrollState()

    LaunchedEffect(imeHeight) {
        if (imeHeight != 0) scrollState.animateScrollTo(scrollState.maxValue)
    }
    val density = LocalDensity.current
    val imeHeightDp = with(density) { (imeHeight).toDp() }.coerceAtLeast(0.dp)

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 32.dp, bottom = 120.dp)
                .verticalScroll(scrollState)
                .imePadding()
        ) {
            Spacer(modifier = Modifier.height(48.dp))
            LinearProgressIndicator(
                progress = { 0.5f },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .height(6.dp)
                    .clip(RoundedCornerShape(50.dp)),
                color = Gray90,
                trackColor = Gray11,
            )
            Spacer(modifier = Modifier.height(32.dp))
            Text(
                modifier = Modifier.padding(horizontal = 20.dp),
                text = stringResource(id = R.string.create_announcement_title),
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
            Location(
                departure = departure.value,
                destination = destination.value,
                updateDeparture = viewModel::updateDeparture,
                updateDestination = viewModel::updateDestination
            )
            Divider(
                Modifier
                    .height(8.dp)
                    .fillMaxWidth(),
                color = Gray7
            )
            Spacer(modifier = Modifier.height(32.dp))
            Schedule(
                onClick = { isScheduleSheetOpen = true },
                startDate = startDate.value,
                endDate = endDate.value,
                isAdjustable = isAdjustableSchedule.value,
                updateIsAdjustable = viewModel::updateIsAdjustableSchedule
            )
            Divider(
                Modifier
                    .height(8.dp)
                    .fillMaxWidth(),
                color = Gray7
            )
            Spacer(modifier = Modifier.height(20.dp))
            Time(
                onClick = { isTimeSheetOpen = true },
                hour = hour.value,
                minute = minute.value,
                dayTime = dayTime.value,
                isAdjustable = isAdjustableTime.value,
                updateIsAdjustable = viewModel::updateIsAdjustableTime
            )
            Spacer(modifier = Modifier.height(20.dp))
            Divider(
                Modifier
                    .height(8.dp)
                    .fillMaxWidth(),
                color = Gray7
            )
            Spacer(modifier = Modifier.height(20.dp))
            Kennel(
                isKennel = isKennel.value,
                updateHasKennel = viewModel::updateIsKennel
            )
            Spacer(modifier = Modifier.height(32.dp))
            Divider(
                Modifier
                    .height(8.dp)
                    .fillMaxWidth(),
                color = Gray7
            )
            Spacer(modifier = Modifier.height(32.dp))
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                text = stringResource(id = R.string.create_announcement_subtitle_5),
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp
            )
            Spacer(modifier = Modifier.height(12.dp))
            ConnectDogTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .padding(bottom = imeHeightDp),
                height = 244,
                text = viewModel.content,
                onTextChanged = viewModel::updateSignificant,
                placeholder = "- 이동봉사의 목적\n- 동물의 사연\n- 이동봉사 상세 시간, 장소\n- (해외) 항공편 조건, 추가 안내 사항"
            )
        }
    }

    if (isScheduleSheetOpen) {
        CalendarBottomSheet(
            sheetState = scheduleSheetState,
            start = startDate.value,
            end = endDate.value,
            onDismissClick = { isScheduleSheetOpen = false },
            onStartDateChanged = viewModel::updateStartDate,
            onEndDateChanged = viewModel::updateEndDate
        )
    }

    if (isTimeSheetOpen) {
        TimeBottomSheet(
            sheetState = timeSheetState,
            onDismissClick = { isTimeSheetOpen = false },
            hour = hour.value,
            minute = minute.value,
            dayTime = dayTime.value,
            updateHour = viewModel::updateHour,
            updateMinute = viewModel::updateMinute,
            updateDayTime = viewModel::updateDayTime
        )
    }
}

@Composable
private fun Location(
    destination: String?,
    departure: String?,
    updateDeparture: (String) -> Unit,
    updateDestination: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 40.dp, bottom = 32.dp, start = 20.dp, end = 20.dp)
    ) {
        Text(
            text = stringResource(id = R.string.create_announcement_subtitle_1),
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp
        )
        Spacer(modifier = Modifier.height(20.dp))
        LocationContent(
            departureLocation = departure,
            destinationLocation = destination
        ) { st, end ->
            st?.let { updateDeparture(it) }
            end?.let { updateDestination(it) }
        }
    }
}

@Composable
private fun Schedule(
    onClick: () -> Unit,
    startDate: LocalDate?,
    endDate: LocalDate?,
    isAdjustable: Boolean,
    updateIsAdjustable: (Boolean) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
    ) {
        Text(
            text = stringResource(id = R.string.create_announcement_subtitle_2),
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp
        )
        Spacer(modifier = Modifier.height(12.dp))
        ConnectDogDialogButton(
            onClick = onClick,
            text = if (startDate == null || endDate == null) {
                "날짜/기간 선택"
            } else if (startDate == endDate) {
                "$startDate"
            } else {
                "$startDate ~ $endDate"
            },
            textColor = if (startDate == null || endDate == null) {
                Gray4
            } else {
                Gray1
            }
        )
        Spacer(modifier = Modifier.height(12.dp))
        AdjustableButton(
            title = "조정 가능",
            isChecked = isAdjustable,
            updateIsChecked = updateIsAdjustable
        )
        Spacer(modifier = Modifier.height(20.dp))
    }
}

@Composable
private fun Time(
    hour: Int?,
    minute: Int?,
    dayTime: DayTime?,
    onClick: () -> Unit,
    isAdjustable: Boolean,
    updateIsAdjustable: (Boolean) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
    ) {
        Text(
            text = stringResource(id = R.string.create_announcement_subtitle_3),
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp
        )
        Spacer(modifier = Modifier.height(12.dp))
        ConnectDogDialogButton(
            onClick = { if (!isAdjustable) onClick() },
            text = if (hour != null && minute != null && dayTime != null && !isAdjustable) {
                "${dayTime.time} ${String.format("%02d", hour)}:${String.format("%02d", minute)}"
            } else if (isAdjustable) {
                "시간 미정"
            } else {
                "시간 선택"
            },
            textColor = if ((hour != null && minute != null && dayTime != null) || isAdjustable) {
                Gray1
            } else {
                Gray4
            }
        )
        Spacer(modifier = Modifier.height(12.dp))
        AdjustableButton(
            title = "시간 미정",
            isChecked = isAdjustable,
            updateIsChecked = updateIsAdjustable
        )
    }
}

@Composable
private fun Kennel(
    isKennel: Boolean?,
    updateHasKennel: (Boolean) -> Unit
) {
    var hasKennel by remember { mutableStateOf(isKennel) }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
    ) {
        Text(
            text = stringResource(id = R.string.create_announcement_subtitle_4),
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp
        )
        Spacer(modifier = Modifier.height(12.dp))
        SelectKennel(hasKennel) {
            hasKennel = it
            updateHasKennel(it)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AdjustableButton(
    title: String,
    isChecked: Boolean,
    updateIsChecked: (Boolean) -> Unit
) {
    var checked by remember { mutableStateOf(isChecked) }
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        CompositionLocalProvider(LocalMinimumInteractiveComponentEnforcement provides false) {
            Checkbox(
                checked = isChecked,
                onCheckedChange = {
                    checked = it
                    updateIsChecked(it)
                },
                colors = CheckboxDefaults.colors(uncheckedColor = Gray5)
            )
        }
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = title,
            color = Gray2,
            fontSize = 14.sp
        )
    }
}

@Composable
private fun BottomBar(
    imeHeight: Int,
    viewModel: CreateApplicationViewModel,
    navigateToCreateDog: () -> Unit
) {
    val isKennel by viewModel.isKennel.collectAsStateWithLifecycle()
    val departure by viewModel.departure.collectAsStateWithLifecycle()
    val destination by viewModel.destination.collectAsStateWithLifecycle()
    val startDate by viewModel.startDate.collectAsStateWithLifecycle()
    val endDate by viewModel.endDate.collectAsStateWithLifecycle()
    val dayTime by viewModel.dayTime.collectAsStateWithLifecycle()
    val hour by viewModel.hour.collectAsStateWithLifecycle()
    val minute by viewModel.minute.collectAsStateWithLifecycle()

    ConnectDogBottomButton(
        modifier = Modifier.padding(
            top = 24.dp,
            bottom = (24 + imeHeight).dp,
            start = 20.dp,
            end = 20.dp
        ),
        onClick = navigateToCreateDog,
        content = "다음",
        enabled = isKennel != null && departure != null && destination != null &&
            startDate != null &&
            endDate != null &&
            dayTime != null && hour != null && minute != null
    )
}
