package com.shopl.sdg.component.calendar

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.shopl.sdg_common.ext.clickable
import com.shopl.sdg_common.foundation.SDGColor
import com.shopl.sdg_common.foundation.typography.SDGTypography
import com.shopl.sdg_common.ui.components.IOText
import com.shopl.sdg_common.ui.components.SDGText
import com.shopl.sdg_resource.R
import kotlinx.coroutines.launch
import org.joda.time.DateTime
import kotlin.math.abs

sealed class SDGCalendarWeekSize(
    val calendarCellHeight: Dp
) {
    @Stable
    object Basic : SDGCalendarWeekSize(
        calendarCellHeight = 52.dp
    )
}

sealed class SDGCalendarWeekMode {
    data class Single(val selected: WeekDateTime? = null) : SDGCalendarWeekMode()
    data class Period(val selected: Pair<WeekDateTime, WeekDateTime>? = null, val maxCount: Int = 0) : SDGCalendarWeekMode()
}

private val sizeComposableLocal = staticCompositionLocalOf<SDGCalendarWeekSize> { SDGCalendarWeekSize.Basic }
private val modeComposableLocal = staticCompositionLocalOf<SDGCalendarWeekMode> { SDGCalendarWeekMode.Single() }
private val maxDateComposableLocal = staticCompositionLocalOf<DateTime?> { null }

data class WeekDateTime(
    val dateTime: DateTime,
    val weekOfWeekYear: Int
) {
    fun getWeekStartDateTime(weekStart: SDGDayOfWeek): DateTime {
        val offset = getDayOfWeekStartOffset(date = dateTime, weekStart = weekStart)
        return dateTime.minusDays(offset)
    }

    fun getWeekEndDateTime(weekStart: SDGDayOfWeek): DateTime {
        return getWeekStartDateTime(weekStart).plusDays(6)
    }

    private fun getDayOfWeekStartOffset(date: DateTime, weekStart: SDGDayOfWeek): Int {
        val firstDayOfWeek: Int = weekStart.ordinal + 1
        val week = date.dayOfWeek()
        val max = week.maximumValue
        var result = week.get()
        if (firstDayOfWeek == max) {
            return if (result == max) 0 else result
        } else {
            result -= firstDayOfWeek
            if (result < 0) result += max
        }
        return result
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SDGCalendarWeek(
    modifier: Modifier = Modifier,
    size: SDGCalendarWeekSize = SDGCalendarWeekSize.Basic,
    mode: SDGCalendarWeekMode,
    initDate: DateTime,
    maxDate: DateTime? = null,
    onMaxCountError: ((Int) -> Unit)? = null,
    onSelectDate: (List<WeekDateTime>) -> Unit
) {

    var currentDate by remember { mutableStateOf(initDate) }

    val initialPage = Int.MAX_VALUE / 2
    val pagerState = rememberPagerState(
        initialPage = initialPage,
        initialPageOffsetFraction = 0f
    ) {
        Int.MAX_VALUE
    }

    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(pagerState) {
        snapshotFlow { pagerState.currentPage }.collect { index ->
            currentDate = initDate.plusYears(index - initialPage)
        }
    }

    val selectedList = rememberSaveable {
        val list = mutableListOf<WeekDateTime>()
        when (mode) {
            is SDGCalendarWeekMode.Single -> mode.selected?.let { list.add(it) }
            is SDGCalendarWeekMode.Period -> mode.selected?.let {
                list.add(it.first)
                list.add(it.second)
            }
        }
        mutableStateOf(list.toList())
    }

    CompositionLocalProvider(
        sizeComposableLocal provides size,
        modeComposableLocal provides mode,
        maxDateComposableLocal provides maxDate,
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth()
        ) {
            SDGCalendarHeader(
                year = currentDate.year,
                onClickPrev = {
                    currentDate = currentDate.minusYears(1)
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(pagerState.currentPage - 1)
                    }
                },
                onClickNext = {
                    currentDate = currentDate.plusYears(1)
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(pagerState.currentPage + 1)
                    }
                },
            )
            SDGCalendarPager(
                initDate = initDate,
                initialPage = initialPage,
                pagerState = pagerState,
                selectedList = selectedList.value,
                onMaxCountError = onMaxCountError,
                onSelectDate = {
                    selectedList.value = it
                    onSelectDate(it)
                }
            )
        }

    }

}

@Composable
private fun SDGCalendarHeader(
    year: Int,
    onClickPrev: () -> Unit,
    onClickNext: () -> Unit,
) {

    Box(
        modifier = Modifier.fillMaxWidth(),
    ) {
        Row(
            modifier = Modifier.align(Alignment.Center),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                modifier = Modifier
                    .padding(end = 20.dp)
                    .size(24.dp)
                    .clip(shape = RoundedCornerShape(8.dp))
                    .clickable(hasRipple = true, rippleColor = SDGColor.Neutral400) {
                        onClickPrev()
                    },
                painter = painterResource(id = R.drawable.ic_common_prev),
                contentDescription = null,
                colorFilter = ColorFilter.tint(color = SDGColor.Neutral700)
            )
            SDGText(
                text = String.format("%04d", year),
                textColor = SDGColor.Neutral700,
                typography = SDGTypography.Title2SB
            )
            Image(
                modifier = Modifier
                    .padding(start = 20.dp)
                    .size(24.dp)
                    .clip(shape = RoundedCornerShape(8.dp))
                    .clickable(hasRipple = true, rippleColor = SDGColor.Neutral400) {
                        onClickNext()
                    },
                painter = painterResource(id = R.drawable.ic_common_next),
                contentDescription = null,
                colorFilter = ColorFilter.tint(color = SDGColor.Neutral700)
            )

        }
    }

}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun SDGCalendarPager(
    initDate: DateTime,
    initialPage: Int,
    pagerState: PagerState,
    selectedList: List<WeekDateTime>,
    onMaxCountError: ((Int) -> Unit)? = null,
    onSelectDate: (List<WeekDateTime>) -> Unit
) {
    val mode = modeComposableLocal.current

    HorizontalPager(
        modifier = Modifier,
        state = pagerState,
        pageSpacing = 0.dp,
        userScrollEnabled = true,
        reverseLayout = false,
        contentPadding = PaddingValues(0.dp),
        pageSize = PageSize.Fill,
        key = null,
    ) { index ->
        if (index != 0) {
            val year = initDate.plusYears(index - initialPage).year
            val days = mutableListOf<WeekDateTime>()

            var firstWeek = DateTime().withYear(year).withWeekOfWeekyear(1)
            if (firstWeek.dayOfYear > 7) {
                firstWeek = firstWeek.minusDays(7)
            }
            repeat(firstWeek.weekOfWeekyear().maximumValue) { index ->
                days.add(
                    WeekDateTime(
                        dateTime = firstWeek.plusDays(index * 7),
                        weekOfWeekYear = index + 1
                    )
                )
            }
            SDGCalendarBody(
                days = days.chunked(5),
                selectedList = selectedList,
            ) {
                val list = selectedList.toMutableList()
                when (mode) {
                    is SDGCalendarWeekMode.Single -> {
                        if (list.isEmpty()) {
                            list.add(it)
                        } else if (list.first() != it) {
                            list.clear()
                            list.add(it)
                        }
                    }

                    is SDGCalendarWeekMode.Period -> {
                        when (list.size) {
                            2 -> {
                                list.clear()
                                list.add(it)
                            }

                            1 -> {
                                val first = list[0].dateTime
                                val selected = it.dateTime
                                val count = abs((first.year * 52 + first.weekOfWeekyear) - (selected.year * 52 + selected.weekOfWeekyear)) + 1
                                if (mode.maxCount in 1..<count) {
                                    onMaxCountError?.invoke(mode.maxCount)
                                } else {
                                    if (selected.isAfter(first)) {
                                        list.add(it)
                                    } else {
                                        list.add(0, it)
                                    }
                                }
                            }

                            0 -> {
                                list.add(it)
                            }
                        }
                    }
                }
                onSelectDate(list)
            }
        }
    }
}

@Composable
private fun SDGCalendarBody(
    days: List<List<WeekDateTime>>,
    selectedList: List<WeekDateTime>,
    onClick: (WeekDateTime) -> Unit
) {
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .padding(top = 20.dp)
            .fillMaxWidth()
            .verticalScroll(scrollState)
    ) {
        days.forEachIndexed { index, row ->
            SDGCalendarBodyRow(
                modifier = Modifier.padding(top = 4.dp),
                days = row,
                selectedList = selectedList,
                onClick = onClick
            )
        }
    }
}

@Composable
private fun SDGCalendarBodyRow(
    modifier: Modifier = Modifier,
    days: List<WeekDateTime>,
    selectedList: List<WeekDateTime>,
    onClick: (WeekDateTime) -> Unit
) {
    Row(
        modifier = modifier
            .padding(bottom = 18.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        repeat(5) { index ->
            if (index < days.size) {
                val day = days[index]
                var hasBeforePeriod = false
                var hasAfterPeriod = false
                when (modeComposableLocal.current) {
                    is SDGCalendarWeekMode.Period -> {
                        if (selectedList.size == 2) {
                            val start = selectedList[0]
                            val end = selectedList[1]
                            if (start.weekOfWeekYear != end.weekOfWeekYear) {
                                if (start.dateTime.year == day.dateTime.year && start.weekOfWeekYear == day.weekOfWeekYear) {
                                    hasAfterPeriod = true
                                } else if (end.dateTime.year == day.dateTime.year && end.weekOfWeekYear == day.weekOfWeekYear) {
                                    hasBeforePeriod = true
                                } else if (start.dateTime <= day.dateTime && day.dateTime <= end.dateTime) {
                                    hasBeforePeriod = true
                                    hasAfterPeriod = true
                                }
                            }
                        }
                    }

                    else -> {}
                }
                SDGCalendarBodyCell(
                    weekDateTime = day,
                    isSelected = selectedList.firstOrNull { day.dateTime.equalYMD(it.dateTime) } != null,
                    enable = maxDateComposableLocal.current?.isAfter(day.dateTime) ?: true,
                    activate = true,
                    hasBeforePeriod = hasBeforePeriod,
                    hasAfterPeriod = hasAfterPeriod,
                    onClick = onClick
                )
            } else {
                SDGCalendarBodyEmptyCell()
            }
        }
    }
}

@Composable
private fun RowScope.SDGCalendarBodyCell(
    weekDateTime: WeekDateTime,
    isSelected: Boolean,
    enable: Boolean,
    activate: Boolean,
    hasBeforePeriod: Boolean,
    hasAfterPeriod: Boolean,
    onClick: (WeekDateTime) -> Unit
) {
    Box(
        modifier = Modifier
            .weight(1F)
    ) {

        if (hasBeforePeriod || hasAfterPeriod) {
            Row(
                modifier = Modifier
                    .height(sizeComposableLocal.current.calendarCellHeight)
            ) {
                Spacer(
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(1F)
                        .background(
                            color = if (hasBeforePeriod) {
                                if (activate) {
                                    SDGColor.Primary300_a10
                                } else {
                                    SDGColor.Neutral350_a10
                                }
                            } else {
                                SDGColor.Transparent
                            }
                        )
                )
                Spacer(
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(1F)
                        .background(
                            color = if (hasAfterPeriod) {
                                if (activate) {
                                    SDGColor.Primary300_a10
                                } else {
                                    SDGColor.Neutral350_a10
                                }
                            } else {
                                SDGColor.Transparent
                            }
                        )
                )
            }
        }

        Box(
            modifier = Modifier
                .height(sizeComposableLocal.current.calendarCellHeight)
                .aspectRatio(1F)
                .then(
                    if (isSelected) {
                        Modifier.background(
                            color = if (activate) SDGColor.Primary300 else SDGColor.Neutral350,
                            shape = CircleShape
                        )
                    } else {
                        Modifier
                    }
                )
                .align(Alignment.Center)
                .clickable(hasRipple = false) {
                    if (enable) {
                        onClick(weekDateTime)
                    }
                }
        ) {
            IOText(
                modifier = Modifier
                    .align(Alignment.Center),
                text = "W${String.format("%02d", weekDateTime.weekOfWeekYear)}",
                textColor = when {
                    !enable -> SDGColor.Neutral250
                    isSelected -> SDGColor.Neutral0
                    !activate -> SDGColor.Neutral400
                    else -> SDGColor.Neutral700
                },
                fontSize = 14.sp,
                textAlign = TextAlign.Center,
            )
        }
    }
}

@Composable
private fun RowScope.SDGCalendarBodyEmptyCell() {
    Box(
        modifier = Modifier
            .weight(1F)
    ) { }
}

@Composable
@Preview
private fun SDGCalendarPreview01() {
    Surface(Modifier.fillMaxSize()) {
        SDGCalendarWeek(
            initDate = DateTime.now(),
            mode = SDGCalendarWeekMode.Single(),
        ) {}
    }
}

//@Composable
//@Preview(widthDp = 1000)
//private fun SDGCalendarPreview02() {
//    Surface(Modifier.fillMaxSize()) {
//        SDGCalendarWeek(
//            initDate = DateTime.now(),
//            today = DateTime.now(),
//            mode = SDGCalendarDayMode.Multiple()
//        ) {}
//    }
//}

@Composable
@Preview(widthDp = 250, heightDp = 360)
private fun SDGCalendarPreview03() {
    Surface(Modifier.fillMaxSize()) {
        SDGCalendarWeek(
            initDate = DateTime.now(),
            mode = SDGCalendarWeekMode.Period(),
            maxDate = DateTime.now().plusDays(1).withTime(23, 59, 59, 59)
        ) {}
    }
}