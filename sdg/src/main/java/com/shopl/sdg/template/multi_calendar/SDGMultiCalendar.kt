package com.shopl.sdg.template.multi_calendar

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.shopl.sdg.component.button.SDGButtonFontWeight
import com.shopl.sdg.component.button.ghost.SDGGhostButtonSize
import com.shopl.sdg.component.calendar.SDGCalendarDay
import com.shopl.sdg.component.calendar.SDGCalendarDayMode
import com.shopl.sdg.component.calendar.SDGCalendarMonth
import com.shopl.sdg.component.calendar.SDGCalendarMonthMode
import com.shopl.sdg.component.calendar.SDGCalendarWeek
import com.shopl.sdg.component.calendar.SDGCalendarWeekMode
import com.shopl.sdg.component.calendar.SDGDayOfWeek
import com.shopl.sdg.component.calendar.WeekDateTime
import com.shopl.sdg.component.calendar.equalY
import com.shopl.sdg.component.calendar.equalYM
import com.shopl.sdg.component.tab.SDGLineFixedTab
import com.shopl.sdg.component.util.button.ghost.SDGGhostButton
import com.shopl.sdg_common.ext.distanceDays
import com.shopl.sdg_common.ext.withColor
import com.shopl.sdg_common.foundation.SDGColor
import com.shopl.sdg_common.foundation.typography.SDGTypography
import com.shopl.sdg_common.ui.components.SDGModalBottomSheet
import com.shopl.sdg_common.ui.components.SDGText
import com.shopl.sdg_resource.R
import kotlinx.coroutines.launch
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat

val dayFormatter01 = DateTimeFormat.forPattern("yyyy.MM.dd")
val dayFormatter02 = DateTimeFormat.forPattern("MM.dd")
val dayFormatter03 = DateTimeFormat.forPattern("yyyy.MM.dd(EEE)")
val monthFormatter01 = DateTimeFormat.forPattern("yyyy.MM")
val monthFormatter02 = DateTimeFormat.forPattern("MM")

sealed class SDGMultiCalendarModalType(open val maxCount: Int) {
    data class Day(override val maxCount: Int = 0) : SDGMultiCalendarModalType(maxCount = maxCount)
    data class Week(override val maxCount: Int = 0) : SDGMultiCalendarModalType(maxCount = maxCount)
    data class Month(override val maxCount: Int = 0) : SDGMultiCalendarModalType(maxCount = maxCount)
}

/**
 * SDG - Template - Multi Calendar
 *
 * 년/월/일을 선택하는 바텀시트 형태의 템플릿
 *
 * @see <a href="https://www.figma.com/design/qWVshatQ9eqoIn4fdEZqWy/SDG?node-id=8099-26619&t=bxGNIkIINBnEHuIK-4">Figma</a>
 */
@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun SDGMultiCalendarModal(
    weekStart: SDGDayOfWeek,
    initDate: DateTime,
    today: DateTime,
    maxDate: DateTime? = null,
    types: List<SDGMultiCalendarModalType> = listOf(
        SDGMultiCalendarModalType.Day(),
        SDGMultiCalendarModalType.Week(),
        SDGMultiCalendarModalType.Month(),
    ),
    currentType: SDGMultiCalendarModalType = types.first(),
    currentStartDate: DateTime? = null,
    currentEndDate: DateTime? = null,
    currentStartWeekDate: WeekDateTime? = null,
    currentEndWeekDate: WeekDateTime? = null,
    onClickCancel: () -> Unit,
    onClickConfirm: (SDGMultiCalendarModalType, DateTime, DateTime) -> Unit,
    onClickConfirmWeek: ((SDGMultiCalendarModalType, WeekDateTime, WeekDateTime) -> Unit)? = null,
    onMaxCountError: ((SDGMultiCalendarModalType) -> Unit)? = null,
) {
    require(types.isNotEmpty())

    val scope = rememberCoroutineScope()
    val sheetState: SheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )
    val tabs = listOf(
        stringResource(id = R.string.text_day),
        stringResource(id = R.string.date_type_weekly),
        stringResource(id = R.string.date_type_monthly),
    )
    val pagerState = rememberPagerState(
        initialPage = types.indexOf(currentType),
        initialPageOffsetFraction = 0f
    ) { tabs.size }

    var selectedTabIndex by remember { mutableIntStateOf(types.indexOf(currentType)) }

    var startDay by remember { mutableStateOf(if (currentType is SDGMultiCalendarModalType.Day) currentStartDate else null) }
    var endDay by remember { mutableStateOf(if (currentType is SDGMultiCalendarModalType.Day) currentEndDate else null) }
    var startWeek by remember { mutableStateOf(if (currentType is SDGMultiCalendarModalType.Week) currentStartWeekDate else null) }
    var endWeek by remember { mutableStateOf(if (currentType is SDGMultiCalendarModalType.Week) currentEndWeekDate else null) }
    var startMonth by remember { mutableStateOf(if (currentType is SDGMultiCalendarModalType.Month) currentStartDate else null) }
    var endMonth by remember { mutableStateOf(if (currentType is SDGMultiCalendarModalType.Month) currentEndDate else null) }

    LaunchedEffect(pagerState) {
        snapshotFlow { pagerState.currentPage }.collect { index ->
            selectedTabIndex = index
        }
    }
    SDGModalBottomSheet(
        onDismissRequest = onClickCancel,
        sheetState = sheetState,
    ) {
        Column(
            modifier = Modifier
                .padding(
                    top = 8.dp
                ),
        ) {
            when (types.size) {
                1 -> {
                    TypeCalendar(
                        modifier = Modifier
                            .padding(
                                horizontal = 16.dp,
                                vertical = 24.dp
                            ),
                        type = types.first(),
                        weekStart = weekStart,
                        initDate = initDate,
                        today = today,
                        maxDate = maxDate,
                        startDay = startDay,
                        endDay = endDay,
                        startWeek = startWeek,
                        endWeek = endWeek,
                        startMonth = startMonth,
                        endMonth = endMonth,
                        onMaxCountError = onMaxCountError,
                        onSelectDateDay = {
                            when (it.size) {
                                1 -> {
                                    startDay = it.first()
                                    endDay = null
                                }

                                2 -> {
                                    startDay = it.first()
                                    endDay = it.last()
                                }
                            }
                        },
                        onSelectDateWeek = {
                            when (it.size) {
                                1 -> {
                                    startWeek = it.first()
                                    endWeek = null
                                }

                                2 -> {
                                    startWeek = it.first()
                                    endWeek = it.last()
                                }
                            }
                        },
                        onSelectDateMonth = {
                            when (it.size) {
                                1 -> {
                                    startMonth = it.first()
                                    endMonth = null
                                }

                                2 -> {
                                    startMonth = it.first()
                                    endMonth = it.last()
                                }
                            }
                        }

                    )
                }

                else -> {
                    SDGLineFixedTab(
                        tabTitles = tabs,
                        selectedTabIndex = selectedTabIndex,
                        onClick = { index ->
                            selectedTabIndex = index
                            scope.launch {
                                pagerState.scrollToPage(index)
                            }
                        }
                    )
                    HorizontalPager(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentSize(),
                        state = pagerState,
                        userScrollEnabled = false,
                        reverseLayout = false,
                        pageSize = PageSize.Fill,
                        key = null,
                    ) { index ->
                        TypeCalendar(
                            modifier = Modifier
                                .padding(
                                    horizontal = 16.dp,
                                    vertical = 24.dp
                                ),
                            type = types[index],
                            weekStart = weekStart,
                            initDate = initDate,
                            today = today,
                            maxDate = maxDate,
                            startDay = startDay,
                            endDay = endDay,
                            startWeek = startWeek,
                            endWeek = endWeek,
                            startMonth = startMonth,
                            endMonth = endMonth,
                            onMaxCountError = onMaxCountError,
                            onSelectDateDay = {
                                when (it.size) {
                                    1 -> {
                                        startDay = it.first()
                                        endDay = null
                                    }

                                    2 -> {
                                        startDay = it.first()
                                        endDay = it.last()
                                    }
                                }
                            },
                            onSelectDateWeek = {
                                when (it.size) {
                                    1 -> {
                                        startWeek = it.first()
                                        endWeek = null
                                    }

                                    2 -> {
                                        startWeek = it.first()
                                        endWeek = it.last()
                                    }
                                }
                            },
                            onSelectDateMonth = {
                                when (it.size) {
                                    1 -> {
                                        startMonth = it.first()
                                        endMonth = null
                                    }

                                    2 -> {
                                        startMonth = it.first()
                                        endMonth = it.last()
                                    }
                                }
                            }

                        )
                    }
                }
            }
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(color = SDGColor.Neutral150)
            )
            Row(
                modifier = Modifier.padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                SDGGhostButton(
                    weight = 1F,
                    size = SDGGhostButtonSize.Large,
                    label = stringResource(id = R.string.dialog_common_btn_cancel),
                    onClick = { onClickCancel() },
                    labelColor = SDGColor.Neutral700,
                )

                Spacer(
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                        .width(1.dp)
                        .height(18.dp)
                        .background(color = SDGColor.Neutral200)
                )

                SDGGhostButton(
                    weight = 1F,
                    size = SDGGhostButtonSize.Large,
                    label = stringResource(id = R.string.dialog_common_btn_ok),
                    labelColor = SDGColor.Neutral700,
                    labelWeight = SDGButtonFontWeight.SB,
                    onClick = {
                        when (val type = types[selectedTabIndex]) {
                            is SDGMultiCalendarModalType.Day -> {
                                val start = startDay ?: return@SDGGhostButton
                                onClickConfirm(type, start, endDay ?: start)
                            }

                            is SDGMultiCalendarModalType.Week -> {
                                val start = startWeek ?: return@SDGGhostButton
                                onClickConfirmWeek?.invoke(type, start, endWeek ?: start)
                            }

                            is SDGMultiCalendarModalType.Month -> {
                                val start = startMonth ?: return@SDGGhostButton
                                val end = endMonth?.dayOfMonth()?.withMaximumValue() ?: start.dayOfMonth().withMaximumValue()
                                onClickConfirm(type, start, end)
                            }
                        }
                    }
                )
            }
        }

    }

}

@Composable
private fun TypeCalendar(
    modifier: Modifier = Modifier,
    type: SDGMultiCalendarModalType,
    weekStart: SDGDayOfWeek,
    initDate: DateTime,
    today: DateTime,
    maxDate: DateTime? = null,
    startDay: DateTime? = null,
    endDay: DateTime? = null,
    startWeek: WeekDateTime? = null,
    endWeek: WeekDateTime? = null,
    startMonth: DateTime? = null,
    endMonth: DateTime? = null,
    onMaxCountError: ((SDGMultiCalendarModalType) -> Unit)? = null,
    onSelectDateDay: (List<DateTime>) -> Unit,
    onSelectDateWeek: (List<WeekDateTime>) -> Unit,
    onSelectDateMonth: (List<DateTime>) -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
    ) {
        when (type) {
            is SDGMultiCalendarModalType.Day -> {
                if (type.maxCount == 1) {
                    DaySingleCalendar(
                        modifier = Modifier
                            .height(320.dp),
                        weekStart = weekStart,
                        initDate = initDate,
                        today = today,
                        maxDate = maxDate,
                        startDay = startDay,
                        onSelectDate = onSelectDateDay,
                    )
                } else {
                    DayPeriodCalendar(
                        modifier = Modifier
                            .height(320.dp),
                        weekStart = weekStart,
                        initDate = initDate,
                        today = today,
                        maxDate = maxDate,
                        type = type,
                        startDay = startDay,
                        endDay = endDay,
                        onMaxCountError = onMaxCountError,
                        onSelectDate = onSelectDateDay,
                    )
                }
            }

            is SDGMultiCalendarModalType.Week -> {
                if (type.maxCount == 1) {
                    WeekSingleCalendar(
                        modifier = Modifier
                            .height(320.dp),
                        initDate = initDate,
                        maxDate = maxDate,
                        startWeek = startWeek,
                        onSelectDate = onSelectDateWeek,
                    )
                } else {
                    WeekPeriodCalendar(
                        modifier = Modifier
                            .height(320.dp),
                        initDate = initDate,
                        maxDate = maxDate,
                        type = type,
                        startWeek = startWeek,
                        endWeek = endWeek,
                        onMaxCountError = onMaxCountError,
                        onSelectDate = onSelectDateWeek,
                    )
                }
            }

            is SDGMultiCalendarModalType.Month -> {
                if (type.maxCount == 1) {
                    MonthSingleCalendar(
                        modifier = Modifier
                            .height(320.dp),
                        initDate = initDate,
                        maxDate = maxDate,
                        startMonth = startMonth,
                        onSelectDate = onSelectDateMonth,
                    )
                } else {
                    MonthPeriodCalendar(
                        modifier = Modifier
                            .height(320.dp),
                        initDate = initDate,
                        maxDate = maxDate,
                        type = type,
                        startMonth = startMonth,
                        endMonth = endMonth,
                        onMaxCountError = onMaxCountError,
                        onSelectDate = onSelectDateMonth,
                    )
                }
            }
        }
    }
}

@Composable
private fun DayPeriodCalendar(
    modifier: Modifier = Modifier,
    weekStart: SDGDayOfWeek,
    initDate: DateTime,
    today: DateTime,
    maxDate: DateTime? = null,
    type: SDGMultiCalendarModalType.Day,
    startDay: DateTime? = null,
    endDay: DateTime? = null,
    onMaxCountError: ((SDGMultiCalendarModalType.Day) -> Unit)? = null,
    onSelectDate: (List<DateTime>) -> Unit,
) {
    SDGCalendarDay(
        modifier = modifier,
        weekStart = weekStart,
        initDate = initDate,
        today = today,
        maxDate = maxDate,
        mode = SDGCalendarDayMode.Period(
            maxCount = type.maxCount,
            selected = if (startDay != null && endDay != null) {
                startDay to endDay
            } else null
        ),
        onMaxCountError = {
            onMaxCountError?.invoke(type)
        },
        onSelectDate = onSelectDate
    )
    var label = ""
    startDay?.let { start ->
        label += dayFormatter01.print(start)
        endDay?.let { end ->
            label += "~"
            label += if (start.equalY(end)) {
                dayFormatter02.print(end)
            } else {
                dayFormatter01.print(end)
            }
            label += "(${stringResource(R.string.some_days, "${start.distanceDays(end)}")})"
        } ?: run {
            label += "~${stringResource(id = R.string.select)}"
        }
    }
    SDGText(
        modifier = Modifier
            .padding(top = 20.dp)
            .fillMaxWidth(),
        text = label.withColor(SDGColor.Neutral300, stringResource(id = R.string.select)),
        textColor = SDGColor.Primary300,
        typography = SDGTypography.Body1SB,
        textAlign = TextAlign.Center
    )
}

@Composable
private fun DaySingleCalendar(
    modifier: Modifier = Modifier,
    weekStart: SDGDayOfWeek,
    initDate: DateTime,
    today: DateTime,
    maxDate: DateTime? = null,
    startDay: DateTime? = null,
    onSelectDate: (List<DateTime>) -> Unit,
) {
    SDGCalendarDay(
        modifier = modifier,
        weekStart = weekStart,
        initDate = initDate,
        today = today,
        maxDate = maxDate,
        mode = SDGCalendarDayMode.Single(
            selected = startDay
        ),
        onSelectDate = onSelectDate
    )
    val label = startDay?.let { dayFormatter03.print(it) } ?: ""
    SDGText(
        modifier = Modifier
            .padding(top = 20.dp)
            .fillMaxWidth(),
        text = label.withColor(SDGColor.Neutral300, stringResource(id = R.string.select)),
        textColor = SDGColor.Primary300,
        typography = SDGTypography.Body1SB,
        textAlign = TextAlign.Center
    )
}

@Composable
private fun WeekPeriodCalendar(
    modifier: Modifier = Modifier,
    initDate: DateTime,
    maxDate: DateTime? = null,
    type: SDGMultiCalendarModalType.Week,
    startWeek: WeekDateTime? = null,
    endWeek: WeekDateTime? = null,
    onMaxCountError: ((SDGMultiCalendarModalType.Week) -> Unit)? = null,
    onSelectDate: (List<WeekDateTime>) -> Unit,
) {
    SDGCalendarWeek(
        modifier = modifier,
        initDate = initDate,
        mode = SDGCalendarWeekMode.Period(
            maxCount = type.maxCount,
            selected = if (startWeek != null && endWeek != null) {
                startWeek to endWeek
            } else null
        ),
        maxDate = maxDate,
        onMaxCountError = {
            onMaxCountError?.invoke(type)
        },
        onSelectDate = onSelectDate,
    )
    var label = ""
    startWeek?.let { start ->
        label += String.format("W%02d", start.weekOfWeekYear)
        endWeek?.let { end ->
            label += "~"
            label += if (start.dateTime.equalY(end.dateTime)) {
                String.format("W%02d", end.weekOfWeekYear)
            } else {
                String.format("%d.W%02d", end.dateTime.year, end.weekOfWeekYear)
            }
        } ?: run {
            label += "~${stringResource(id = R.string.select)}"
        }
    }
    SDGText(
        modifier = Modifier
            .padding(top = 20.dp)
            .fillMaxWidth(),
        text = label.withColor(SDGColor.Neutral300, stringResource(id = R.string.select)),
        textColor = SDGColor.Primary300,
        typography = SDGTypography.Body1SB,
        textAlign = TextAlign.Center
    )
}

@Composable
private fun WeekSingleCalendar(
    modifier: Modifier = Modifier,
    initDate: DateTime,
    maxDate: DateTime? = null,
    startWeek: WeekDateTime? = null,
    onSelectDate: (List<WeekDateTime>) -> Unit,
) {
    SDGCalendarWeek(
        modifier = modifier,
        initDate = initDate,
        mode = SDGCalendarWeekMode.Single(
            selected = startWeek
        ),
        maxDate = maxDate,
        onSelectDate = onSelectDate,
    )
    val label = startWeek?.let {
        String.format("W%02d", it.weekOfWeekYear)
    } ?: ""
    SDGText(
        modifier = Modifier
            .padding(top = 20.dp)
            .fillMaxWidth(),
        text = label.withColor(SDGColor.Neutral300, stringResource(id = R.string.select)),
        textColor = SDGColor.Primary300,
        typography = SDGTypography.Body1SB,
        textAlign = TextAlign.Center
    )
}

@Composable
private fun MonthSingleCalendar(
    modifier: Modifier = Modifier,
    initDate: DateTime,
    maxDate: DateTime? = null,
    startMonth: DateTime? = null,
    onSelectDate: (List<DateTime>) -> Unit,
) {
    SDGCalendarMonth(
        modifier = modifier,
        initDate = initDate,
        mode = SDGCalendarMonthMode.Single(
            selected = startMonth
        ),
        maxDate = maxDate,
        onSelectDate = onSelectDate,
    )
    var label = ""
    startMonth?.let { start ->
        label += monthFormatter01.print(start)
    }
    SDGText(
        modifier = Modifier
            .padding(top = 20.dp)
            .fillMaxWidth(),
        text = label,
        textColor = SDGColor.Primary300,
        typography = SDGTypography.Body1SB,
        textAlign = TextAlign.Center
    )
}

@Composable
private fun MonthPeriodCalendar(
    modifier: Modifier = Modifier,
    initDate: DateTime,
    maxDate: DateTime? = null,
    type: SDGMultiCalendarModalType.Month,
    startMonth: DateTime? = null,
    endMonth: DateTime? = null,
    onMaxCountError: ((SDGMultiCalendarModalType.Month) -> Unit)? = null,
    onSelectDate: (List<DateTime>) -> Unit,
) {
    SDGCalendarMonth(
        modifier = modifier,
        initDate = initDate,
        mode = SDGCalendarMonthMode.Period(
            selected = if (startMonth != null && endMonth != null) {
                startMonth to endMonth
            } else null
        ),
        maxDate = maxDate,
        onMaxCountError = {
            onMaxCountError?.invoke(type)
        },
        onSelectDate = onSelectDate,
    )
    var label = ""
    startMonth?.let { start ->
        label += monthFormatter01.print(start)
        endMonth?.let { end ->
            if (!start.equalYM(end)) {
                label += "~"
                label += if (start.equalY(end)) {
                    monthFormatter02.print(end)
                } else {
                    monthFormatter01.print(end)
                }
            }
        }
    }
    SDGText(
        modifier = Modifier
            .padding(top = 20.dp)
            .fillMaxWidth(),
        text = label,
        textColor = SDGColor.Primary300,
        typography = SDGTypography.Body1SB,
        textAlign = TextAlign.Center
    )
}

@Preview
@Composable
private fun PreviewCalendarDay() {
    var startDay by remember { mutableStateOf<DateTime?>(null) }
    var endDay by remember { mutableStateOf<DateTime?>(null) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = SDGColor.Neutral0
            )
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            SDGText(
                text = "DaySingleCalendar",
                textColor = SDGColor.Neutral700,
                typography = SDGTypography.Body1SB
            )
            TypeCalendar(
                type = SDGMultiCalendarModalType.Day(maxCount = 1),
                weekStart = SDGDayOfWeek.SUNDAY,
                initDate = DateTime.now(),
                today = DateTime.now(),
                startDay = startDay,
                onSelectDateDay = {
                    when (it.size) {
                        1 -> {
                            startDay = it.first()
                            endDay = null
                        }

                        2 -> {
                            startDay = it.first()
                            endDay = it.last()
                        }
                    }
                },
                onSelectDateWeek = {},
                onSelectDateMonth = {}
            )
        }
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            SDGText(
                text = "DayPeriodCalendar",
                textColor = SDGColor.Neutral700,
                typography = SDGTypography.Body1SB
            )
            TypeCalendar(
                type = SDGMultiCalendarModalType.Day(maxCount = 365),
                weekStart = SDGDayOfWeek.SUNDAY,
                initDate = DateTime.now(),
                today = DateTime.now(),
                startDay = startDay,
                endDay = endDay,
                onSelectDateDay = {
                    when (it.size) {
                        1 -> {
                            startDay = it.first()
                            endDay = null
                        }

                        2 -> {
                            startDay = it.first()
                            endDay = it.last()
                        }
                    }
                },
                onSelectDateWeek = {},
                onSelectDateMonth = {}
            )
        }
    }

}

@Preview
@Composable
private fun PreviewCalendarWeek() {
    var startWeek by remember { mutableStateOf<WeekDateTime?>(null) }
    var endWeek by remember { mutableStateOf<WeekDateTime?>(null) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = SDGColor.Neutral0
            )
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            SDGText(
                text = "WeekSingleCalendar",
                textColor = SDGColor.Neutral700,
                typography = SDGTypography.Body1SB
            )
            TypeCalendar(
                type = SDGMultiCalendarModalType.Week(maxCount = 1),
                weekStart = SDGDayOfWeek.SUNDAY,
                initDate = DateTime.now(),
                today = DateTime.now(),
                startWeek = startWeek,
                onSelectDateDay = {},
                onSelectDateWeek = {
                    when (it.size) {
                        1 -> {
                            startWeek = it.first()
                            endWeek = null
                        }

                        2 -> {
                            startWeek = it.first()
                            endWeek = it.last()
                        }
                    }
                },
                onSelectDateMonth = {}
            )
        }
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            SDGText(
                text = "WeekPeriodCalendar",
                textColor = SDGColor.Neutral700,
                typography = SDGTypography.Body1SB
            )
            TypeCalendar(
                type = SDGMultiCalendarModalType.Week(maxCount = 52),
                weekStart = SDGDayOfWeek.SUNDAY,
                initDate = DateTime.now(),
                today = DateTime.now(),
                startWeek = startWeek,
                endWeek = endWeek,
                onSelectDateDay = {},
                onSelectDateWeek = {
                    when (it.size) {
                        1 -> {
                            startWeek = it.first()
                            endWeek = null
                        }

                        2 -> {
                            startWeek = it.first()
                            endWeek = it.last()
                        }
                    }
                },
                onSelectDateMonth = {}
            )
        }
    }

}

@Preview
@Composable
private fun PreviewCalendarMonth() {
    var startMonth by remember { mutableStateOf<DateTime?>(null) }
    var endMonth by remember { mutableStateOf<DateTime?>(null) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = SDGColor.Neutral0
            )
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            SDGText(
                text = "MonthSingleCalendar",
                textColor = SDGColor.Neutral700,
                typography = SDGTypography.Body1SB
            )
            TypeCalendar(
                type = SDGMultiCalendarModalType.Month(maxCount = 1),
                weekStart = SDGDayOfWeek.SUNDAY,
                initDate = DateTime.now(),
                today = DateTime.now(),
                startMonth = startMonth,
                onSelectDateDay = {},
                onSelectDateWeek = {},
                onSelectDateMonth = {
                    when (it.size) {
                        1 -> {
                            startMonth = it.first()
                            endMonth = null
                        }

                        2 -> {
                            startMonth = it.first()
                            endMonth = it.last()
                        }
                    }
                }
            )
        }
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            SDGText(
                text = "MonthPeriodCalendar",
                textColor = SDGColor.Neutral700,
                typography = SDGTypography.Body1SB
            )
            TypeCalendar(
                type = SDGMultiCalendarModalType.Month(maxCount = 12),
                weekStart = SDGDayOfWeek.SUNDAY,
                initDate = DateTime.now(),
                today = DateTime.now(),
                startMonth = startMonth,
                endMonth = endMonth,
                onSelectDateDay = {},
                onSelectDateWeek = {},
                onSelectDateMonth = {
                    when (it.size) {
                        1 -> {
                            startMonth = it.first()
                            endMonth = null
                        }

                        2 -> {
                            startMonth = it.first()
                            endMonth = it.last()
                        }
                    }
                }
            )
        }
    }

}

