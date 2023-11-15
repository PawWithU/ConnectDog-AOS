package com.kusitms.connectdog.core.designsystem.component

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kusitms.connectdog.core.designsystem.R
import com.kusitms.connectdog.core.designsystem.theme.Gray2
import com.kusitms.connectdog.core.designsystem.theme.Orange60
import kotlin.math.abs
import kotlinx.coroutines.launch
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ConnectDogCalendar(
    modifier: Modifier = Modifier,
    config: CalendarConfig = CalendarConfig(),
    startDate: LocalDate = LocalDate.now(),
    endDate: LocalDate = startDate,
    onSelectedDate: (LocalDate, LocalDate) -> Unit
) {
    val initialPage = (startDate.year - config.yearRange.first) * 12 + startDate.monthValue - 1
    Log.d("Calendar", "initialPage = $initialPage")
    var currentSelectedStartDate by remember { mutableStateOf(startDate) }
    var currentSelectedEndDate by remember { mutableStateOf(endDate) }
    var currentMonth by remember { mutableStateOf(YearMonth.now()) }
    var currentPage by remember { mutableIntStateOf(initialPage) }
    val pagerState = rememberPagerState(
        initialPage = initialPage,
        initialPageOffsetFraction = 0f,
        pageCount = { (config.yearRange.last - config.yearRange.first) * 12 }
    )
    Log.d("Calendar", "currentMonth = $currentMonth")

    LaunchedEffect(pagerState.currentPage) {
        val addMonth = (pagerState.currentPage - currentPage).toLong()
        currentMonth = currentMonth.plusMonths(addMonth)
        currentPage = pagerState.currentPage
    }

    LaunchedEffect(currentSelectedStartDate, currentSelectedEndDate) {
        onSelectedDate(currentSelectedStartDate, currentSelectedEndDate)
    }

    val scope = rememberCoroutineScope()

    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        CalendarHeader(
            yearMonth = currentMonth,
            modifier = Modifier.fillMaxWidth(),
            onClickLeftBtn = {
                currentMonth = currentMonth.minusMonths(1)
                currentPage -= 1
                scope.launch { pagerState.animateScrollToPage(currentPage) }
            },
            onClickRightBtn = {
                currentMonth = currentMonth.plusMonths(1)
                currentPage += 1
                scope.launch { pagerState.animateScrollToPage(currentPage) }
            }
        )
        HorizontalPager(state = pagerState) { page ->
            val date = LocalDate.of(config.yearRange.first + page / 12, page % 12 + 1, 1)
            if (page in pagerState.currentPage - 1..pagerState.currentPage + 1) {
                CalendarMonth(
                    currentDate = date,
                    selectedStartDate = currentSelectedStartDate,
                    selectedEndDate = currentSelectedEndDate,
                    onSelectedStartDate = {
                        currentSelectedStartDate = it
                        Log.d("Calendar", "currentSelectedStartDate = $currentSelectedStartDate")
                    },
                    onSelectedEndDate = {
                        currentSelectedEndDate = it
                        Log.d("Calendar", "currentSelectedEndDate = $currentSelectedEndDate")
                    }
                )
            }
        }
    }

}

@Composable
private fun CalendarMonth(
    modifier: Modifier = Modifier,
    currentDate: LocalDate,
    selectedStartDate: LocalDate,
    selectedEndDate: LocalDate,
    onSelectedStartDate: (LocalDate) -> Unit,
    onSelectedEndDate: (LocalDate) -> Unit,
) {
    val lastDay by remember { mutableIntStateOf(currentDate.lengthOfMonth()) }
    val firstDayOfWeek by remember { mutableIntStateOf(currentDate.dayOfWeek.value) } // 요일
    val days by remember { mutableStateOf(IntRange(1, lastDay).toList()) }

    Column(modifier = modifier) {
        DayOfWeekBar()
        LazyVerticalGrid(columns = GridCells.Fixed(7)) {
            for (i in 1 until firstDayOfWeek) {
                item {
                    Box(modifier = Modifier.size(41.dp))
                }
            }
            items(days) { day ->
                val date = currentDate.withDayOfMonth(day)
                val isSelectedStart = remember(selectedStartDate) {
                    selectedStartDate.compareTo(date) == 0
                }
                val isSelectedEnd = remember(selectedEndDate) {
                    selectedEndDate.compareTo(date) == 0
                }

                CalendarDay(
                    date = date,
                    isSelected = isSelectedStart or isSelectedEnd,
                    isInMiddle = date in selectedStartDate .. selectedEndDate,
                    onSelectedDate = {
                        if (it.isBefore(selectedStartDate)) onSelectedStartDate(it)
                        else if (it.isAfter(selectedEndDate)) onSelectedEndDate(it)
                        else if (dayDiff(it, selectedStartDate) < dayDiff(it, selectedEndDate)) onSelectedStartDate(it)
                        else onSelectedEndDate(it)
                        Log.d("Calendar", "CalendarMonth startDate = $selectedStartDate, endDate = $selectedEndDate, selected = $it")
                    }
                )
            }
        }
    }
}

@Composable
private fun DayOfWeekBar(
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier) {
        DayOfWeek.values().forEach {
            Text(
                text = it.getDayOfWeekKor(),
                style = MaterialTheme.typography.labelLarge,
                color = Gray2,
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
private fun CalendarDay(
    modifier: Modifier = Modifier,
    date: LocalDate,
    isSelected: Boolean,
    isInMiddle: Boolean,
    onSelectedDate: (LocalDate) -> Unit,
) {
    Box(
        modifier = modifier
            .size(41.dp)
            .clickable {
                onSelectedDate(date)
                Log.d("Calendar", "CalendarDay selectedDate = $date")
            },
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .matchParentSize()
                .clip(shape = if (isSelected) CircleShape else RectangleShape)
                .background(color = if (isSelected) MaterialTheme.colorScheme.primary else if (isInMiddle) Orange60 else  Color.Transparent)
        )
        Text(
            text = date.dayOfMonth.toString(),
            textAlign = TextAlign.Center,
            color = if (isSelected or isInMiddle) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface
        )
    }
}

@Composable
private fun CalendarHeader(
    modifier: Modifier = Modifier,
    yearMonth: YearMonth,
    onClickLeftBtn: () -> Unit,
    onClickRightBtn: () -> Unit
) {
    val headerMonth = yearMonth.dateFormat("yyyy년 M월")
    Row(
        modifier = modifier.padding(bottom = 20.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = onClickLeftBtn) {
            Icon(painter = painterResource(id = R.drawable.ic_left), contentDescription = "이전 달")
        }
        Text(text = headerMonth, style = MaterialTheme.typography.titleSmall, fontSize = 18.sp)
        IconButton(onClick = onClickRightBtn) {
            Icon(painter = painterResource(id = R.drawable.ic_right), contentDescription = "다음 달")
        }
    }
}

data class CalendarConfig(
    val yearRange: YearRange = YearRange(),
) {
    data class YearRange(
        val first: Int = 2023,
        val last: Int = 2030
    )
}

private fun YearMonth.dateFormat(pattern: String) =
    this.format(DateTimeFormatter.ofPattern(pattern))

private fun DayOfWeek.getDayOfWeekKor(): String {
    return when (this) {
        DayOfWeek.MONDAY -> "월"
        DayOfWeek.TUESDAY -> "화"
        DayOfWeek.WEDNESDAY -> "수"
        DayOfWeek.THURSDAY -> "목"
        DayOfWeek.FRIDAY -> "금"
        DayOfWeek.SATURDAY -> "토"
        DayOfWeek.SUNDAY -> "일"
    }
}

private fun dayDiff(date1: LocalDate, date2: LocalDate) : Long =
    abs(ChronoUnit.DAYS.between(date1, date2))