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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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

sealed class SDGCalendarMonthSize(
    val calendarCellHeight: Dp
) {
    @Stable
    object Basic : SDGCalendarMonthSize(
        calendarCellHeight = 72.dp
    )

    @Stable
    object Mini : SDGCalendarMonthSize(
        calendarCellHeight = 60.dp
    )
}

sealed class SDGCalendarMonthMode {
    data class Single(val selected: DateTime? = null) : SDGCalendarMonthMode()
    data class Period(val selected: Pair<DateTime, DateTime>? = null, val maxCount: Int = 0) : SDGCalendarMonthMode()
}

private val sizeComposableLocal = staticCompositionLocalOf<SDGCalendarMonthSize> { SDGCalendarMonthSize.Basic }
private val modeComposableLocal = staticCompositionLocalOf<SDGCalendarMonthMode> { SDGCalendarMonthMode.Single() }
private val maxDateComposableLocal = staticCompositionLocalOf<DateTime?> { null }

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SDGCalendarMonth(
    modifier: Modifier = Modifier,
    size: SDGCalendarMonthSize = SDGCalendarMonthSize.Basic,
    mode: SDGCalendarMonthMode,
    initDate: DateTime,
    maxDate: DateTime? = null,
    onMaxCountError: ((Int) -> Unit)? = null,
    onSelectDate: (List<DateTime>) -> Unit
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

    val selectedList = rememberSaveable {
        val list = mutableListOf<DateTime>()
        when (mode) {
            is SDGCalendarMonthMode.Single -> mode.selected?.let { list.add(it) }
            is SDGCalendarMonthMode.Period -> mode.selected?.let {
                list.add(it.first)
                list.add(it.second)
            }
        }
        mutableStateOf(list.toList())
    }

    LaunchedEffect(pagerState) {
        snapshotFlow { pagerState.currentPage }.collect { index ->
            currentDate = initDate.plusYears(index - initialPage)
        }
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
                selectedList = selectedList.value,
                pagerState = pagerState,
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
                typography = SDGTypography.Title2SB,
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
    selectedList: List<DateTime>,
    pagerState: PagerState,
    onMaxCountError: ((Int) -> Unit)? = null,
    onSelectDate: (List<DateTime>) -> Unit
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
            val indexDate = initDate.plusYears(index - initialPage)
            val days = mutableListOf<DateTime>()
            repeat(12) {
                days.add(indexDate.withDate(indexDate.year, it + 1, 1))
            }
            SDGCalendarBody(
                days = days.chunked(4),
                selectedList = selectedList,
            ) {
                val list = selectedList.toMutableList()
                when (mode) {
                    is SDGCalendarMonthMode.Single -> {
                        if (list.isEmpty()) {
                            list.add(it)
                        } else if (list.first() != it) {
                            list.clear()
                            list.add(it)
                        }
                    }

                    is SDGCalendarMonthMode.Period -> {
                        when (list.size) {
                            2 -> {
                                list.clear()
                                list.add(it)
                            }

                            1 -> {
                                val count = abs((list[0].year * 12 + list[0].monthOfYear) - (it.year * 12 + it.monthOfYear)) + 1
                                if (mode.maxCount in 1..<count) {
                                    onMaxCountError?.invoke(mode.maxCount)
                                } else {
                                    if (it.isAfter(list[0])) {
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
    days: List<List<DateTime>>,
    selectedList: List<DateTime>,
    onClick: (DateTime) -> Unit
) {
    Column(
        modifier = Modifier
            .padding(top = 20.dp)
            .fillMaxWidth()
    ) {
        days.forEach {
            SDGCalendarBodyRow(
                modifier = Modifier.padding(top = 4.dp),
                days = it,
                selectedList = selectedList,
                onClick = onClick
            )
        }
    }
}

@Composable
private fun SDGCalendarBodyRow(
    modifier: Modifier = Modifier,
    days: List<DateTime>,
    selectedList: List<DateTime>,
    onClick: (DateTime) -> Unit
) {
    Row(
        modifier = modifier
            .padding(bottom = 18.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        days.forEach { day ->
            var hasBeforePeriod = false
            var hasAfterPeriod = false
            when (modeComposableLocal.current) {
                is SDGCalendarMonthMode.Period -> {
                    if (selectedList.size == 2) {
                        val start = selectedList[0]
                        val end = selectedList[1]
                        if (start != end) {
                            if (start == day) {
                                hasAfterPeriod = true
                            } else if (end == day) {
                                hasBeforePeriod = true
                            } else if (day.isAfter(start) && day.isBefore(end)) {
                                hasBeforePeriod = true
                                hasAfterPeriod = true
                            }
                        }
                    }
                }

                else -> {}
            }
            SDGCalendarBodyCell(
                day = day,
                isSelected = selectedList.firstOrNull { day.equalYM(it) } != null,
                enable = maxDateComposableLocal.current?.isAfter(day) ?: true,
                activate = true,
                hasBeforePeriod = hasBeforePeriod,
                hasAfterPeriod = hasAfterPeriod,
                onClick = onClick
            )
        }
    }
}

@Composable
private fun RowScope.SDGCalendarBodyCell(
    day: DateTime,
    isSelected: Boolean,
    enable: Boolean,
    activate: Boolean,
    hasBeforePeriod: Boolean,
    hasAfterPeriod: Boolean,
    onClick: (DateTime) -> Unit
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
                        onClick(day)
                    }
                }
        ) {
            IOText(
                modifier = Modifier
                    .align(Alignment.Center),
                text = "${day.monthOfYear}",
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
@Preview
private fun SDGCalendarPreview01() {
    Surface(Modifier.fillMaxSize()) {
        SDGCalendarMonth(
            initDate = DateTime.now(),
            mode = SDGCalendarMonthMode.Single(),
        ) {}
    }
}

//@Composable
//@Preview(widthDp = 1000)
//private fun SDGCalendarPreview02() {
//    Surface(Modifier.fillMaxSize()) {
//        SDGCalendarMonth(
//            initDate = DateTime.now(),
//            today = DateTime.now(),
//            mode = SDGCalendarDayMode.Multiple()
//        ) {}
//    }
//}

@Composable
@Preview(widthDp = 250)
private fun SDGCalendarPreview03() {
    Surface(Modifier.fillMaxSize()) {
        SDGCalendarMonth(
            initDate = DateTime.now(),
            mode = SDGCalendarMonthMode.Period(),
            maxDate = DateTime.now().plusDays(1).withTime(23, 59, 59, 59)
        ) {}
    }
}