package com.kusitms.connectdog.core.designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kusitms.connectdog.core.designsystem.theme.Gray1
import com.kusitms.connectdog.core.designsystem.theme.Gray9
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch

enum class DayTime(val time: String) { AM("오전"), PM("오후") }

const val loopingRealCount = 1000000000
const val halfLoopingRealCount = loopingRealCount / 2

fun getLoopingStartIndex(
    startIndex: Int,
    count: Int,
) = halfLoopingRealCount - halfLoopingRealCount % count + startIndex

@Composable
fun TimeWheelPicker(
    h: Int?,
    m: Int?,
    d: DayTime?,
    updateDayTime: (DayTime) -> Unit,
    updateHour: (Int) -> Unit,
    updateMinute: (Int) -> Unit,
) {
    var hour by remember { mutableIntStateOf(h ?: 0) }
    var minute by remember { mutableIntStateOf(m ?: 0) }
    var dayTime by remember { mutableStateOf(d ?: DayTime.AM) }
    val itemHeight = 40.dp
    Box(modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp), contentAlignment = Alignment.Center) {
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Spacer(modifier = Modifier.width(70.dp))
            DayTimePicker(
                modifier = Modifier.weight(1F),
                itemHeight = itemHeight,
                currentDayTime = dayTime,
                onDayTimeChange = {
                    dayTime = it
                    updateDayTime(it)
                },
            )
            LoopingNumberPicker(
                modifier = Modifier.weight(1F),
                range = 1..12,
                currentNumber = hour,
                itemHeight = itemHeight,
                onCurrentNumberChange = {
                    hour = it
                    updateHour(it)
                },
            )
            LoopingNumberPicker(
                modifier = Modifier.weight(1F),
                range = 0..59 step 5,
                currentNumber = minute,
                itemHeight = itemHeight,
                onCurrentNumberChange = {
                    minute = it
                    updateMinute(it)
                },
            )
            Spacer(modifier = Modifier.width(70.dp))
        }
        HoursBackground(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .height(itemHeight),
        )
    }
}

@Composable
private fun DayTimePicker(
    modifier: Modifier = Modifier,
    itemHeight: Dp,
    currentDayTime: DayTime,
    onDayTimeChange: (DayTime) -> Unit,
) {
    val state = rememberLazyListState(currentDayTime.ordinal)
    val scope = rememberCoroutineScope()
    VerticalWheelPicker(
        modifier = modifier,
        state = state,
        count = 2,
        itemHeight = itemHeight,
        visibleItemCount = 3,
        onScrollFinish = { index ->
            onDayTimeChange(DayTime.values()[index % 2])
        },
    ) { index ->
        val dayTime = DayTime.values()[index % 2]
        val text = dayTime.time
        Box(
            modifier =
                Modifier
                    .height(itemHeight),
            contentAlignment = Alignment.Center,
        ) {
            Box(
                modifier =
                    Modifier
                        .fillMaxSize()
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null,
                            onClick = { scope.launch { state.animateScrollToItem(index) } },
                        ),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = text,
                    style =
                        TextStyle(
                            fontSize = if (dayTime == currentDayTime) 23.sp else 18.sp,
                            fontWeight = FontWeight.Medium,
                            color = if (dayTime == currentDayTime) Gray1 else Gray9,
                        ),
                )
            }
        }
    }
}

@Composable
private fun LoopingNumberPicker(
    modifier: Modifier = Modifier,
    range: IntProgression,
    currentNumber: Int,
    itemHeight: Dp,
    onCurrentNumberChange: (Int) -> Unit,
) {
//    require(currentNumber in range)
    val rangeList = range.toList()
    val startIndex = remember { rangeList.indexOf(currentNumber) }
    val count = rangeList.size
    val realStartIndex = getLoopingStartIndex(startIndex, count)
    val state = rememberLazyListState(realStartIndex)
    val scope = rememberCoroutineScope()
    var currentIndex by remember { mutableIntStateOf(realStartIndex) }
    VerticalWheelPicker(
        modifier = modifier,
        state = state,
        count = loopingRealCount,
        itemHeight = itemHeight,
        visibleItemCount = 7,
        onScrollFinish = { index ->
            currentIndex = index
            onCurrentNumberChange(rangeList[index % count])
        },
        content = { index ->
            val number = rangeList[index % count]
            val currentText = currentIndex == index
            val differ1 = currentIndex == index - 1 || currentIndex == index + 1
            val differ2 = currentIndex == index - 2 || currentIndex == index + 2

            Box(
                modifier =
                    Modifier
                        .height(itemHeight),
                contentAlignment = Alignment.Center,
            ) {
                Box(
                    modifier =
                        Modifier
                            .fillMaxSize()
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null,
                                onClick = { scope.launch { state.animateScrollToItem(index) } },
                            ),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        text = String.format("%02d", number),
                        style =
                            TextStyle(
                                fontSize =
                                    if (currentText) {
                                        23.sp
                                    } else if (differ1) {
                                        18.sp
                                    } else if (differ2) {
                                        14.sp
                                    } else {
                                        12.sp
                                    },
                                fontWeight = if (currentIndex == index) FontWeight(500) else FontWeight(400),
                                color = if (currentIndex == index) Color.Black else Color.Gray,
                            ),
                    )
                }
            }
        },
    )
}

@Composable
private fun HoursBackground(modifier: Modifier = Modifier) {
    Box(
        modifier =
            modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .background(Color(0f, 0f, 0f, 0.1f)),
    )
}

@Composable
fun VerticalWheelPicker(
    modifier: Modifier = Modifier,
    count: Int,
    state: LazyListState = rememberLazyListState(),
    itemHeight: Dp,
    visibleItemCount: Int,
    onScrollFinish: (index: Int) -> Unit,
    content: @Composable (index: Int) -> Unit,
) {
    val itemHalfHeightToPx = with(LocalDensity.current) { itemHeight.toPx() / 2 }

    val currentOnScrollFinish by rememberUpdatedState(onScrollFinish)

    LaunchedEffect(state.isScrollInProgress) {
        if (!state.isScrollInProgress && state.firstVisibleItemScrollOffset != 0) {
            if (state.firstVisibleItemScrollOffset < itemHalfHeightToPx) {
                state.animateScrollToItem(state.firstVisibleItemIndex)
            } else if (state.firstVisibleItemScrollOffset > itemHalfHeightToPx) {
                state.animateScrollToItem(state.firstVisibleItemIndex + 1)
            }
        }
    }

    LaunchedEffect(state) {
        snapshotFlow { state.isScrollInProgress }
            .filter { !it && state.firstVisibleItemScrollOffset == 0 }
            .drop(1)
            .collect { currentOnScrollFinish(state.firstVisibleItemIndex) }
    }

    LazyColumn(
        modifier = modifier.height(itemHeight * visibleItemCount),
        state = state,
        contentPadding = PaddingValues(vertical = itemHeight * (visibleItemCount / 2)),
    ) {
        items(count = count, key = { it }) { index ->
            content(index)
        }
    }
}
