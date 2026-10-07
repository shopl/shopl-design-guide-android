package com.shopl.sdg.component.calendar

import androidx.annotation.StringRes
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.shopl.sdg.component.number_picker.SDGNumberPicker
import com.shopl.sdg.component.number_picker.SDGNumberPickerOption.OneOption
import com.shopl.sdg_common.ext.clickable
import com.shopl.sdg_common.foundation.SDGColor
import com.shopl.sdg_common.foundation.typography.SDGTypography
import com.shopl.sdg_common.ui.components.IOText
import com.shopl.sdg_common.ui.components.IOTypeface
import com.shopl.sdg_common.ui.components.SDGText
import com.shopl.sdg_resource.R
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.launch
import org.joda.time.DateTime
import org.joda.time.Days
import org.joda.time.format.DateTimeFormat
import kotlin.math.abs

/**
 * DateTime DayOfWeek는 minSDK 문제로 사용할 수 없음
 */
enum class SDGDayOfWeek(
    @StringRes val resource: Int
) {
    MONDAY(R.string.working_hours_mon),
    TUESDAY(R.string.working_hours_tue),
    WEDNESDAY(R.string.working_hours_wed),
    THURSDAY(R.string.working_hours_thu),
    FRIDAY(R.string.working_hours_fri),
    SATURDAY(R.string.working_hours_sat),
    SUNDAY(R.string.working_hours_sun);

    companion object {
        fun of(v: Int): SDGDayOfWeek = when (v) {
            0 -> MONDAY
            1 -> TUESDAY
            2 -> WEDNESDAY
            3 -> THURSDAY
            4 -> FRIDAY
            5 -> SATURDAY
            6 -> SUNDAY
            else -> SUNDAY
        }
    }
}

sealed class SDGCalendarDaySize(
    val calendarCellHeight: Dp
) {
    @Stable
    object Basic : SDGCalendarDaySize(
        calendarCellHeight = 38.dp
    )

    @Stable
    object Mini : SDGCalendarDaySize(
        calendarCellHeight = 32.dp
    )
}

/**
 * 기존 방식인 Single/Multiple/Period는 초기 선택값을 받고, 이후 날짜 선택은 달력 내부에서 관리한다.
 * 호출부에서 초기 선택값을 변경해도 달력의 선택 상태에는 반영되지 않는다.
 *
 * ExternalSelection은 날짜를 눌러도 선택 표시를 바로 변경하지 않고 onSelectDate로 알린다.
 * 호출부에서 선택값을 변경해 전달하면 달력의 선택 표시도 변경된다.
 */
sealed class SDGCalendarDayMode(
    open val inActivatedList: List<DateTime>?,
    open val disabledList: List<DateTime>?,
    open val bulletList: List<DateTime>?,
) {
    data class View(
        override val inActivatedList: List<DateTime>? = null,
        override val disabledList: List<DateTime>? = null,
        override val bulletList: List<DateTime>? = null,
    ) : SDGCalendarDayMode(inActivatedList, disabledList, bulletList)

    data class Single(
        val selected: DateTime? = null,
        override val inActivatedList: List<DateTime>? = null,
        override val disabledList: List<DateTime>? = null,
        override val bulletList: List<DateTime>? = null,
    ) : SDGCalendarDayMode(inActivatedList, disabledList, bulletList)

    data class Multiple(
        val selectedList: List<DateTime>? = null,
        override val inActivatedList: List<DateTime>? = null,
        override val disabledList: List<DateTime>? = null,
        override val bulletList: List<DateTime>? = null,
    ) : SDGCalendarDayMode(inActivatedList, disabledList, bulletList)

    data class Period(
        val selected: Pair<DateTime, DateTime>? = null,
        override val inActivatedList: List<DateTime>? = null,
        override val disabledList: List<DateTime>? = null,
        override val bulletList: List<DateTime>? = null,
        val maxCount: Int = 0,
        val fixedCount: Int = 0,
    ) : SDGCalendarDayMode(inActivatedList, disabledList, bulletList)

    /** 호출부의 단일 선택을 표시하며, null은 선택 없음이다. */
    data class SingleExternalSelection(
        val selected: DateTime? = null,
        override val inActivatedList: List<DateTime>? = null,
        override val disabledList: List<DateTime>? = null,
        override val bulletList: List<DateTime>? = null,
    ) : SDGCalendarDayMode(inActivatedList, disabledList, bulletList)

    /** 호출부의 다중 선택을 표시하며, 빈 목록은 선택 없음이다. */
    data class MultipleExternalSelection(
        val selectedDates: PersistentList<DateTime> = persistentListOf(),
        override val inActivatedList: List<DateTime>? = null,
        override val disabledList: List<DateTime>? = null,
        override val bulletList: List<DateTime>? = null,
    ) : SDGCalendarDayMode(inActivatedList, disabledList, bulletList)

    /** 호출부의 기간 선택을 표시한다. null은 선택 없음, 종료일이 null이면 첫 날짜만 선택한 상태다. */
    data class PeriodExternalSelection(
        val selected: Pair<DateTime, DateTime?>? = null,
        override val inActivatedList: List<DateTime>? = null,
        override val disabledList: List<DateTime>? = null,
        override val bulletList: List<DateTime>? = null,
        val maxCount: Int = 0,
        val fixedCount: Int = 0,
    ) : SDGCalendarDayMode(inActivatedList, disabledList, bulletList)
}

private val LocalSize = staticCompositionLocalOf<SDGCalendarDaySize> { SDGCalendarDaySize.Basic }
private val LocalMode = staticCompositionLocalOf<SDGCalendarDayMode> { SDGCalendarDayMode.Single() }
private val LocalToday = staticCompositionLocalOf<DateTime> { DateTime.now() }
private val LocalMinDate = staticCompositionLocalOf<DateTime?> { null }
private val LocalMaxDate = staticCompositionLocalOf<DateTime?> { null }
private val LocalInActivatedList = staticCompositionLocalOf<List<DateTime>?> { null }
private val LocalDisabledList = staticCompositionLocalOf<List<DateTime>?> { null }
private val LocalBulletList = staticCompositionLocalOf<List<DateTime>?> { null }

/**
 * weekStart = SDGDayOfWeek.of(SDGDB.getInstance().getInt(G.DB.DB_KEY_FIRST_DAY_OF_WEEK) - 1)
 * weekStart = SDGDayOfWeek.of(ClientConfig.weekStart)
 * onMonthChanged는 처음 표시할 때와 표시 월이 변경될 때 호출된다.
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SDGCalendarDay(
    modifier: Modifier = Modifier,
    size: SDGCalendarDaySize = SDGCalendarDaySize.Basic,
    mode: SDGCalendarDayMode,
    weekStart: SDGDayOfWeek,
    initDate: DateTime,
    today: DateTime,
    minDate: DateTime? = null,
    maxDate: DateTime? = null,
    dateProvider: DateProvider = DateProvider(weekStart),
    onMaxCountError: ((Int) -> Unit)? = null,
    onMonthChanged: (DateTime) -> Unit = {},
    onSelectDate: (List<DateTime>) -> Unit
) {

    var currentDate by remember { mutableStateOf(initDate) }
    val weekHeaders = rememberSaveable {
        mutableStateOf(dateProvider.getWeekHeaders())
    }

    var showMonthSelector by remember { mutableStateOf(false) }
    var monthSelectorDate by remember { mutableStateOf(currentDate) }

    val initialPage = Int.MAX_VALUE / 2
    val pagerState = rememberPagerState(
        initialPage = initialPage,
        initialPageOffsetFraction = 0f
    ) {
        Int.MAX_VALUE
    }

    val coroutineScope = rememberCoroutineScope()
    val externalSelectedDates = when (mode) {
        is SDGCalendarDayMode.SingleExternalSelection -> listOfNotNull(mode.selected)
        is SDGCalendarDayMode.MultipleExternalSelection -> mode.selectedDates
        is SDGCalendarDayMode.PeriodExternalSelection -> mode.selected?.let {
            listOfNotNull(it.first, it.second)
        }.orEmpty()

        else -> null
    }

    val internalSelectedDates = rememberSaveable {
        val list = mutableListOf<DateTime>()
        when (mode) {
            is SDGCalendarDayMode.View,
            is SDGCalendarDayMode.SingleExternalSelection,
            is SDGCalendarDayMode.MultipleExternalSelection,
            is SDGCalendarDayMode.PeriodExternalSelection -> {
            }

            is SDGCalendarDayMode.Single -> mode.selected?.let { list.add(it) }
            is SDGCalendarDayMode.Period -> mode.selected?.let {
                list.add(it.first)
                list.add(it.second)
            }

            is SDGCalendarDayMode.Multiple -> mode.selectedList?.let {
                list.addAll(it)
            }
        }
        mutableStateOf(list.toList())
    }

    LaunchedEffect(pagerState) {
        snapshotFlow { pagerState.currentPage }.collect { index ->
            currentDate = initDate.plusMonths(index - initialPage)
        }
    }

    LaunchedEffect(currentDate.year, currentDate.monthOfYear) {
        onMonthChanged(currentDate.withDayOfMonth(1).withTimeAtStartOfDay())
    }

    CompositionLocalProvider(
        LocalSize provides size,
        LocalMode provides mode,
        LocalToday provides today,
        LocalMinDate provides minDate?.withTime(0, 0, 0, 0),
        LocalMaxDate provides maxDate?.withTime(23, 59, 59, 59),
        LocalInActivatedList provides mode.inActivatedList,
        LocalDisabledList provides mode.disabledList,
        LocalBulletList provides mode.bulletList
    ) {

        Column(
            modifier = modifier
                .fillMaxWidth()
        ) {
            SDGCalendarHeader(
                year = if (showMonthSelector) monthSelectorDate.year else currentDate.year,
                month = if (showMonthSelector) monthSelectorDate.monthOfYear else currentDate.monthOfYear,
                showMonthSelector = showMonthSelector,
                onClickPrev = {
                    currentDate = currentDate.minusMonths(1)
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(pagerState.currentPage - 1)
                    }
                },
                onClickNext = {
                    currentDate = currentDate.plusMonths(1)
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(pagerState.currentPage + 1)
                    }
                },
                onClickDate = {
                    showMonthSelector = !showMonthSelector
                    if (!showMonthSelector && currentDate != monthSelectorDate) {
                        currentDate = monthSelectorDate
                        val distanceFromInitDate =
                            (currentDate.year - initDate.year) * 12 + (currentDate.monthOfYear - initDate.monthOfYear)
                        coroutineScope.launch {
                            pagerState.scrollToPage(initialPage + distanceFromInitDate)
                        }
                    }
                },
                onClickToday = {
                    currentDate = today
                    val distanceFromInitDate =
                        (currentDate.year - initDate.year) * 12 + (currentDate.monthOfYear - initDate.monthOfYear)
                    coroutineScope.launch {
                        pagerState.scrollToPage(initialPage + distanceFromInitDate)
                    }
                },
            )

            Box {
                Column {
                    SDGWeekHeader(
                        modifier = Modifier.padding(top = 20.dp),
                        weeks = weekHeaders.value,
                    )
                    SDGCalendarPager(
                        initDate = initDate,
                        selectedList = externalSelectedDates ?: internalSelectedDates.value,
                        dateProvider = dateProvider,
                        pagerState = pagerState,
                        initialPage = initialPage,
                        onMaxCountError = onMaxCountError,
                        onSelectDate = {
                            if (externalSelectedDates == null) internalSelectedDates.value = it
                            onSelectDate(it)
                        }
                    )
                }
                if (showMonthSelector) {
                    MonthSelector(
                        initDate = currentDate
                    ) {
                        monthSelectorDate = it
                    }
                }
            }
        }

    }

}

@Composable
private fun SDGCalendarHeader(
    year: Int,
    month: Int,
    showMonthSelector: Boolean,
    onClickPrev: () -> Unit,
    onClickNext: () -> Unit,
    onClickDate: () -> Unit,
    onClickToday: () -> Unit,
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
                modifier = Modifier
                    .clickable(hasRipple = false) {
                        //onClickDate()
                    },
                text = String.format("%04d.%02d", year, month),
                textColor = SDGColor.Neutral700,
                typography = SDGTypography.Title2SB
            )
            /*
            Image(
                modifier = Modifier
                    .size(12.dp)
                    .rotate(if (showMonthSelector) 0F else 180F)
                    .clickable(hasRipple = false) {
                        onClickDate()
                    },
                painter = painterResource(id = R.drawable.ic_common_triangle),
                contentDescription = null,
                colorFilter = ColorFilter.tint(color = SDGColor.Neutral700)
            )
            */
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

        if (!showMonthSelector) {
            SDGText(
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .clickable(hasRipple = false) {
                        onClickToday()
                    },
                text = stringResource(id = R.string.today),
                textColor = SDGColor.Neutral700,
                typography = SDGTypography.Body3SB,
                textDecoration = TextDecoration.Underline
            )
        }
    }

}

@Composable
private fun SDGWeekHeader(
    modifier: Modifier = Modifier,
    weeks: List<SDGDayOfWeek>,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(24.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        weeks.forEach {
            SDGWeekHeaderCell(
                week = it,
            )
        }
    }

}

@Composable
private fun RowScope.SDGWeekHeaderCell(
    week: SDGDayOfWeek,
) {
    IOText(
        modifier = Modifier.weight(1F),
        text = stringResource(id = week.resource),
        textColor = SDGColor.Neutral400,
        fontSize = 12.sp,
        textAlign = TextAlign.Center
    )
}

/** 기간 끝 날짜를 연·월·일로 비교하며, 최대 기간을 초과하면 오류 콜백만 호출한다. */
@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun SDGCalendarPager(
    initDate: DateTime,
    initialPage: Int,
    dateProvider: DateProvider,
    pagerState: PagerState,
    selectedList: List<DateTime>,
    onMaxCountError: ((Int) -> Unit)? = null,
    onSelectDate: (List<DateTime>) -> Unit
) {
    val mode = LocalMode.current
    val (maxCount, fixedCount) = when (mode) {
        is SDGCalendarDayMode.Period -> mode.maxCount to mode.fixedCount
        is SDGCalendarDayMode.PeriodExternalSelection -> mode.maxCount to mode.fixedCount
        else -> 0 to 0
    }

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
            val indexDate = initDate.plusMonths(index - initialPage)
            SDGCalendarBody(
                indexDate = indexDate,
                days = dateProvider.getGeneratedDays(date = indexDate).chunked(size = 7),
                selectedList = selectedList,
            ) {
                val list = selectedList.toMutableList()
                when (mode) {
                    is SDGCalendarDayMode.View -> {}
                    is SDGCalendarDayMode.Single,
                    is SDGCalendarDayMode.SingleExternalSelection -> {
                        if (list.isEmpty()) {
                            list.add(it)
                        } else if (list.first() != it) {
                            list.clear()
                            list.add(it)
                        }
                    }

                    is SDGCalendarDayMode.Multiple -> {
                        if (list.contains(it)) {
                            list.remove(it)
                        } else {
                            list.add(it)
                        }
                    }

                    is SDGCalendarDayMode.MultipleExternalSelection -> {
                        val isDeselected =
                            list.removeAll { selectedDate -> selectedDate.equalYMD(it) }
                        if (!isDeselected) {
                            list.add(it)
                        }
                    }

                    is SDGCalendarDayMode.Period,
                    is SDGCalendarDayMode.PeriodExternalSelection -> {
                        if (fixedCount > 1) {
                            list.clear()
                            list.add(it)
                            list.add(it.plusDays(fixedCount - 1))
                        } else {
                            when (list.size) {
                                2 -> {
                                    list.clear()
                                    list.add(it)
                                }

                                1 -> {
                                    val selectedDate = list[0].toLocalDate()
                                    val clickedDate = it.toLocalDate()
                                    val count =
                                        abs(Days.daysBetween(selectedDate, clickedDate).days) + 1
                                    if (maxCount in 1..<count) {
                                        onMaxCountError?.invoke(maxCount)
                                        return@SDGCalendarBody
                                    }
                                    if (clickedDate.isAfter(selectedDate)) {
                                        list.add(it)
                                    } else {
                                        list.add(0, it)
                                    }
                                }

                                0 -> {
                                    list.add(it)
                                }
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
    indexDate: DateTime,
    days: List<List<DateTime>>,
    selectedList: List<DateTime>,
    onClick: (DateTime) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        days.forEach {
            SDGCalendarBodyRow(
                modifier = Modifier.padding(top = 4.dp),
                indexDate = indexDate,
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
    indexDate: DateTime,
    days: List<DateTime>,
    selectedList: List<DateTime>,
    onClick: (DateTime) -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        days.forEach { day ->
            var hasBeforePeriod = false
            var hasAfterPeriod = false
            when (LocalMode.current) {
                is SDGCalendarDayMode.Period,
                is SDGCalendarDayMode.PeriodExternalSelection -> {
                    if (selectedList.size == 2) {
                        val start = selectedList[0]
                        val end = selectedList[1]
                        if (!start.equalYMD(end)) {
                            if (start.equalYMD(day)) {
                                hasAfterPeriod = true
                            } else if (end.equalYMD(day)) {
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
                isSelected = selectedList.firstOrNull { it.equalYMD(day) } != null,
                isToday = LocalToday.current.equalYMD(day),
                isThisMonth = indexDate.equalYM(day),
                enable = LocalMaxDate.current?.isAfter(day) ?: true
                        && LocalMinDate.current?.isBefore(day) ?: true
                        && LocalDisabledList.current?.firstOrNull { it.equalYMD(day) } == null,
                activate = LocalInActivatedList.current?.firstOrNull { it.equalYMD(day) } == null,
                bullet = LocalBulletList.current?.firstOrNull { it.equalYMD(day) } != null,
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
    isToday: Boolean,
    isThisMonth: Boolean,
    enable: Boolean,
    activate: Boolean,
    bullet: Boolean,
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
                    .height(LocalSize.current.calendarCellHeight)
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
                .height(LocalSize.current.calendarCellHeight)
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
                text = "${day.dayOfMonth}",
                textColor = when {
                    !enable -> SDGColor.Neutral250
                    isSelected -> SDGColor.Neutral0
                    !activate -> SDGColor.Neutral400
                    else -> SDGColor.Neutral700
                },
                fontSize = 14.sp,
                textAlign = TextAlign.Center,
                textDecoration = if (isToday) TextDecoration.Underline else null,
                typeface = if (isToday) IOTypeface.SEMI_BOLD else IOTypeface.REGULAR
            )
            if (bullet) {
                Spacer(
                    modifier = Modifier
                        .padding(
                            top = 8.dp,
                            end = 6.dp
                        )
                        .size(4.dp)
                        .clip(CircleShape)
                        .background(color = if (isSelected) SDGColor.Neutral0 else SDGColor.Primary300)
                        .align(Alignment.TopEnd)
                )
            }
        }

        if (!isThisMonth) {
            Spacer(
                modifier = Modifier
                    .height(LocalSize.current.calendarCellHeight)
                    .fillMaxWidth()
                    .background(color = SDGColor.Neutral0_a70)
            )
        }
    }
}

@Composable
private fun MonthSelector(
    initDate: DateTime,
    onChangeDate: (DateTime) -> Unit
) {

    var currentDate by remember { mutableStateOf(initDate) }

    Box(
        modifier = Modifier
            .padding(top = 20.dp)
            .fillMaxWidth()
            .height(24.dp + 4.dp * 6 + LocalSize.current.calendarCellHeight * 6 + 20.dp)
            .background(color = SDGColor.Neutral0)
            .clickable(hasRipple = false) { }
    ) {
        Box {
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(32.dp)
                    .background(
                        color = SDGColor.Neutral150,
                        shape = RoundedCornerShape(8.dp)
                    )
                    .align(Alignment.Center)
            )

            Row(
                modifier = Modifier.background(color = SDGColor.Transparent)
            ) {
                SDGNumberPicker(
                    option = OneOption(
                        value = currentDate.year,
                        rangeList = (1970..(1970 + 1000)).toPersistentList(),
                        onValueChange = {
                            currentDate = currentDate.withDate(
                                it,
                                currentDate.monthOfYear,
                                currentDate.dayOfMonth
                            )
                            onChangeDate(currentDate)
                        },
                        supportsInfiniteScroll = false
                    )
                )

                SDGNumberPicker(
                    option = OneOption(
                        value = currentDate.monthOfYear,
                        rangeList = (1..12).toPersistentList(),
                        onValueChange = {
                            currentDate =
                                currentDate.withDate(currentDate.year, it, currentDate.dayOfMonth)
                            onChangeDate(currentDate)
                        },
                        supportsInfiniteScroll = false
                    )
                )
            }
        }
    }
}


/**
 * weekStart = SDGDayOfWeek.of(SDGDB.getInstance().getInt(G.DB.DB_KEY_FIRST_DAY_OF_WEEK) - 1)
 */
class DateProvider(
    private val weekStart: SDGDayOfWeek,
) {

    val maxCount = 42

    fun getGeneratedDays(date: DateTime): List<DateTime> {
        var date = date.withDate(date.year, date.monthOfYear, 1)
        val offset = getDayOfWeekStartOffset(date = date)
        val days = mutableListOf<DateTime>()
        if (offset > 0) { //prev month
            for (i in 1..offset) {
                days.add(date.minusDays((offset + 1) - i))
            }
        }

        for (day in 0..31) { //this month
            days.add(date)
            if (date.plusDays(1).monthOfYear == date.monthOfYear) {
                date = date.plusDays(1)
            } else {
                break
            }
        }

        //next month
        val size = maxCount - days.size
        for (diff in 1..size) {
            days.add(date.plusDays(diff))
        }
        return days.toList()
    }

    fun getWeekHeaders(): List<SDGDayOfWeek> {
        val dayOfWeek = weekStart.ordinal
        val headers = mutableListOf<SDGDayOfWeek>()
        for (d in dayOfWeek..6) {
            headers.add(SDGDayOfWeek.of(d))
        }
        if (dayOfWeek > 0) {
            for (d in 0 until dayOfWeek) {
                headers.add(SDGDayOfWeek.of(d))
            }
        }
        return headers.toList()
    }

    private fun getDayOfWeekStartOffset(date: DateTime): Int {
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

    fun printMonth(date: DateTime) {
        val formatter = DateTimeFormat.forPattern("MM.dd(EEE)")
        var header = ""
        getWeekHeaders().forEach {
            header += "$it "
        }
        getGeneratedDays(date).chunked(7).forEach { week ->
            var days = ""
            week.forEach { dateTime ->
                days += "${formatter.print(dateTime)} "
            }
        }
    }
}

fun DateTime.equalY(compare: DateTime): Boolean = compare.year == year
fun DateTime.equalYM(compare: DateTime): Boolean =
    equalY(compare) && compare.monthOfYear == monthOfYear

fun DateTime.equalYMD(compare: DateTime): Boolean =
    equalYM(compare) && compare.dayOfMonth == dayOfMonth

fun DateTime.equalW(compare: DateTime): Boolean = compare.weekOfWeekyear == weekOfWeekyear

@Composable
@Preview
private fun SDGCalendarPreview01() {
    Surface(Modifier.fillMaxSize()) {
        SDGCalendarDay(
            weekStart = SDGDayOfWeek.SUNDAY,
            initDate = DateTime.now(),
            today = DateTime.now(),
            mode = SDGCalendarDayMode.Period(
                inActivatedList = listOf(DateTime.now().minusDays(1)),
                disabledList = listOf(DateTime.now().plusDays(1)),
                bulletList = listOf(DateTime.now(), DateTime.now().plusDays(2)),
            ),
        ) {}
    }
}

@Composable
@Preview(widthDp = 1000)
private fun SDGCalendarPreview02() {
    Surface(Modifier.fillMaxSize()) {
        SDGCalendarDay(
            weekStart = SDGDayOfWeek.SUNDAY,
            initDate = DateTime.now(),
            today = DateTime.now(),
            mode = SDGCalendarDayMode.Multiple()
        ) {}
    }
}

@Composable
@Preview(widthDp = 250)
private fun SDGCalendarPreview03() {
    Surface(Modifier.fillMaxSize()) {
        SDGCalendarDay(
            weekStart = SDGDayOfWeek.SUNDAY,
            initDate = DateTime.now(),
            today = DateTime.now().minusDays(5),
            size = SDGCalendarDaySize.Mini,
            mode = SDGCalendarDayMode.Period(),
            minDate = DateTime.now().minusDays(1).withTime(0, 0, 0, 0),
            maxDate = DateTime.now().plusDays(1).withTime(23, 59, 59, 59)
        ) {}
    }
}
