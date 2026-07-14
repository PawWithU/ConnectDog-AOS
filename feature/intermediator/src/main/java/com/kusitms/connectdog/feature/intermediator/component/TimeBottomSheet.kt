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
import com.kusitms.connectdog.core.designsystem.component.ConnectDogTopAppBar
import com.kusitms.connectdog.core.designsystem.component.DayTime
import com.kusitms.connectdog.core.designsystem.component.TimeWheelPicker
import com.kusitms.connectdog.core.designsystem.component.TopAppBarNavigationType
import com.kusitms.connectdog.feature.intermediator.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimeBottomSheet(
    minute: Int?,
    hour: Int?,
    dayTime: DayTime?,
    updateHour: (Int) -> Unit,
    updateMinute: (Int) -> Unit,
    updateDayTime: (DayTime) -> Unit,
    sheetState: SheetState,
    onDismissClick: () -> Unit,
) {
    ConnectDogBottomSheet(
        sheetState = sheetState,
        onDismissRequest = onDismissClick,
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            var h by remember { mutableStateOf(hour) }
            var m by remember { mutableStateOf(minute) }
            var d by remember { mutableStateOf(dayTime) }
            ConnectDogTopAppBar(
                titleRes = R.string.calendar_title,
                navigationType = TopAppBarNavigationType.CLOSE,
                navigationIconContentDescription = "close",
                onNavigationClick = onDismissClick,
                modifier = Modifier.padding(horizontal = 20.dp),
            )
            TimeWheelPicker(
                m = minute,
                h = hour,
                d = dayTime,
                updateHour = { h = it },
                updateMinute = { m = it },
                updateDayTime = { d = it },
            )
            Spacer(modifier = Modifier.height(24.dp))
            ConnectDogBottomButton(
                onClick = {
                    updateHour(h ?: 0)
                    updateMinute(m ?: 0)
                    updateDayTime(d ?: DayTime.AM)
                    onDismissClick()
                },
                content = "선택 완료",
                modifier = Modifier.padding(horizontal = 20.dp),
            )
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}
