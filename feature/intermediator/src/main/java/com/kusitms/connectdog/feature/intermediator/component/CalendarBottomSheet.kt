package com.kusitms.connectdog.feature.intermediator.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kusitms.connectdog.core.designsystem.component.ConnectDogBottomButton
import com.kusitms.connectdog.core.designsystem.component.ConnectDogBottomSheet
import com.kusitms.connectdog.core.designsystem.component.ConnectDogCalendar
import com.kusitms.connectdog.core.designsystem.component.ConnectDogTopAppBar
import com.kusitms.connectdog.core.designsystem.component.TopAppBarNavigationType
import com.kusitms.connectdog.core.designsystem.component.text.TextWithIcon
import com.kusitms.connectdog.feature.intermediator.R
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalendarBottomSheet(
    sheetState: SheetState,
    start: LocalDate?,
    end: LocalDate?,
    onDismissClick: () -> Unit,
    onStartDateChanged: (LocalDate) -> Unit,
    onEndDateChanged: (LocalDate) -> Unit,
) {
    ConnectDogBottomSheet(
        sheetState = sheetState,
        onDismissRequest = onDismissClick,
    ) {
        Content(
            start = start,
            end = end,
            onDismissClick = onDismissClick,
            onStartDateChanged = onStartDateChanged,
            onEndDateChanged = onEndDateChanged,
        )
    }
}

@Composable
private fun Content(
    start: LocalDate?,
    end: LocalDate?,
    onDismissClick: () -> Unit,
    onStartDateChanged: (LocalDate) -> Unit,
    onEndDateChanged: (LocalDate) -> Unit,
) {
    var startDate by remember { mutableStateOf(start ?: LocalDate.now()) }
    var endDate by remember { mutableStateOf(end ?: LocalDate.now()) }

    Column(
        modifier =
            Modifier
                .padding(horizontal = 20.dp)
                .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        ConnectDogTopAppBar(
            titleRes = R.string.calendar_title,
            navigationType = TopAppBarNavigationType.CLOSE,
            navigationIconContentDescription = "close",
            onNavigationClick = onDismissClick,
        )
        Spacer(modifier = Modifier.height(15.dp))
        TextWithIcon(
            text =
                if (startDate == endDate) {
                    "$startDate"
                } else {
                    "$startDate - $endDate"
                },
            spacer = 12,
            size = 16,
            iconId = com.kusitms.connectdog.core.designsystem.R.drawable.ic_clock,
        )
        Spacer(modifier = Modifier.height(15.dp))
        ConnectDogCalendar(
            startDate = startDate,
            endDate = endDate,
        ) { start, end ->
            startDate = start
            endDate = end
        }
        Spacer(modifier = Modifier.height(42.dp))
        ConnectDogBottomButton(
            onClick = {
                onStartDateChanged(startDate)
                onEndDateChanged(endDate)
                onDismissClick()
            },
            content = "적용하기",
        )
        Spacer(modifier = Modifier.height(32.dp))
    }
}
