package com.shopl.sdg.component.number_picker

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.shopl.sdg.component.number_picker.SDGNumberPickerOption.OneOption
import com.shopl.sdg.component.number_picker.SDGNumberPickerOption.TwoOption
import com.shopl.sdg_common.foundation.SDGColor
import com.shopl.sdg_common.foundation.SDGCornerRadius
import com.shopl.sdg_common.foundation.typography.SDGTypography
import com.shopl.sdg_common.ui.components.SDGText
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.launch
import kotlin.math.abs
import kotlin.math.min
import kotlin.math.round

private const val NOT_STOPPED_SCROLL_INDEX = -1
private const val ITEM_HEIGHT = 40
private const val PICKER_HEIGHT = 200
private const val VISIBLE_COUNT = PICKER_HEIGHT / ITEM_HEIGHT
private const val CENTER_INDEX = VISIBLE_COUNT / 2
private const val VIRTUAL_ITEM_COUNT = Int.MAX_VALUE

/**
 * SDG - Component - Number Picker
 *
 * 스피너 형식으로 숫자 값을 선택하는 컴포넌트
 *
 * @param option 넘버 피커 타입 [SDGNumberPickerOption]
 *
 * @see <a href="https://www.figma.com/design/qWVshatQ9eqoIn4fdEZqWy/SDG?node-id=18805-226&m=dev">Figma</a>
 */
@Composable
fun SDGNumberPicker(
    option: SDGNumberPickerOption,
    marginValues: PaddingValues = PaddingValues()
) {
    when (option) {
        is OneOption -> SDGOneOptionNumberPicker(option = option, marginValues = marginValues)
        is TwoOption -> SDGTwoOptionNumberPicker(option = option, marginValues = marginValues)
    }
}

@Composable
private fun SDGOneOptionNumberPicker(option: OneOption, marginValues: PaddingValues) {
    Box(modifier = Modifier.padding(marginValues)) {
        SDGNumberPickerBody(
            value = option.value,
            rangeList = option.rangeList,
            onValueChange = option.onValueChange,
            width = option.width,
            supportsInfiniteScroll = option.supportsInfiniteScroll,
        )
    }
}

@Composable
private fun SDGTwoOptionNumberPicker(option: TwoOption, marginValues: PaddingValues) {
    Box(
        modifier = Modifier.padding(marginValues),
        contentAlignment = Alignment.Center
    ) {
        HighlightingBox()

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            val leftModifier = if (option.left.width == 0.dp) {
                Modifier.weight(1f)
            } else {
                Modifier.width(option.left.width)
            }

            Box(modifier = leftModifier) {
                SDGNumberPickerBody(
                    value = option.left.value,
                    rangeList = option.left.rangeList,
                    onValueChange = option.left.onValueChange,
                    width = option.left.width,
                    supportsInfiniteScroll = option.left.supportsInfiniteScroll,
                )
            }

            val rightModifier = if (option.right.width == 0.dp) {
                Modifier.weight(1f)
            } else {
                Modifier.width(option.right.width)
            }

            Box(modifier = rightModifier) {
                SDGNumberPickerBody(
                    value = option.right.value,
                    rangeList = option.right.rangeList,
                    onValueChange = option.right.onValueChange,
                    width = option.right.width,
                    supportsInfiniteScroll = option.right.supportsInfiniteScroll,
                )
            }
        }
    }
}

@Composable
private fun SDGNumberPickerBody(
    value: Int,
    rangeList: PersistentList<Int>,
    onValueChange: (Int) -> Unit,
    width: Dp = 0.dp,
    supportsInfiniteScroll: Boolean = true,
) {
    val useInfiniteScroll = supportsInfiniteScroll && rangeList.size >= VISIBLE_COUNT

    if (useInfiniteScroll) {
        InfinitePickerBody(
            value = value,
            rangeList = rangeList,
            onValueChange = onValueChange,
            width = width,
        )
    } else {
        FinitePickerBody(
            value = value,
            rangeList = rangeList,
            onValueChange = onValueChange,
            width = width,
        )
    }
}

/**
 * 제한된 항목 수를 사용하는 스크롤 피커 Body
 * 5개 이하일 경우 더미뷰 처리로 인해 무한 스크롤을 방지하기 위함
 *
 * lazy 리스트에서 중심 항목을 감지해 값 변화를 전달하고, 필요시 선택된 값으로 스크롤을 이동
 */
@Composable
private fun FinitePickerBody(
    value: Int,
    rangeList: PersistentList<Int>,
    onValueChange: (Int) -> Unit,
    width: Dp = 0.dp,
) {
    val lazyListState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    val snapBehavior = rememberSnapFlingBehavior(lazyListState)

    val itemHeightDp = ITEM_HEIGHT.dp
    val pickerHeightDp = PICKER_HEIGHT.dp
    val verticalPadding = (pickerHeightDp - itemHeightDp) / 2

    var isEditing by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        val index = rangeList.indexOf(value)
        if (index != -1) {
            lazyListState.scrollToItem(index)
        }
    }

    val centerInfo by remember {
        derivedStateOf {
            val layoutInfo = lazyListState.layoutInfo
            if (layoutInfo.visibleItemsInfo.isEmpty()) {
                null
            } else {
                val viewportCenter = layoutInfo.viewportStartOffset + layoutInfo.viewportSize.height / 2
                layoutInfo.visibleItemsInfo.minByOrNull { abs((it.offset + it.size / 2) - viewportCenter) }
            }
        }
    }

    LaunchedEffect(lazyListState.isScrollInProgress) {
        if (!lazyListState.isScrollInProgress) {
            val index = centerInfo?.index
            if (index != null) {
                val selectedValue = rangeList.getOrNull(index)
                if (selectedValue != null && selectedValue != value) {
                    onValueChange(selectedValue)
                }
            }
        } else if (isEditing) {
            isEditing = false
        }
    }

    LaunchedEffect(value) {
        val index = rangeList.indexOf(value)
        if (index != -1 && index != centerInfo?.index) {
            lazyListState.animateScrollToItem(index)
        }
    }

    Box(
        modifier = Modifier
            .then(if (width == 0.dp) Modifier.fillMaxWidth() else Modifier.width(width))
            .height(pickerHeightDp),
        contentAlignment = Alignment.Center
    ) {
        HighlightingBox()

        LazyColumn(
            state = lazyListState,
            flingBehavior = snapBehavior,
            horizontalAlignment = Alignment.CenterHorizontally,
            contentPadding = PaddingValues(vertical = verticalPadding)
        ) {
            items(rangeList.size, key = { rangeList[it] }) { index ->
                val isCenter = centerInfo?.index == index

                FinitePickerItem(
                    value = rangeList[index],
                    isCenter = isCenter,
                    onClick = {
                        coroutineScope.launch {
                            lazyListState.animateScrollToItem(index)
                        }
                    }
                )
            }
        }
    }
}

@Composable
private fun FinitePickerItem(
    value: Int,
    isCenter: Boolean,
    onClick: () -> Unit,
) {
    val selectedColor = SDGColor.Neutral700
    val unSelectedColor = SDGColor.Neutral400
    val color = if (isCenter) selectedColor else unSelectedColor

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxWidth()
            .height(ITEM_HEIGHT.dp)
            .clickable(onClick = onClick)
    ) {
        SDGText(
            text = value.toString(),
            typography = SDGTypography.Title2R,
            textColor = color
        )
    }
}

/**
 * 무한 스크롤을 통해 연속적으로 값을 노출하는 피커
 */
@Composable
private fun InfinitePickerBody(
    value: Int,
    rangeList: PersistentList<Int>,
    onValueChange: (Int) -> Unit,
    width: Dp = 0.dp,
) {
    val displayList = remember(rangeList) {
        if (rangeList.size < VISIBLE_COUNT && rangeList.isNotEmpty()) {
            val padding = List(VISIBLE_COUNT - rangeList.size) { null }
            (rangeList + padding).toPersistentList()
        } else {
            rangeList.toPersistentList()
        }
    }

    var suppressNextValueScroll by remember { mutableStateOf(false) }

    val rangeSize = displayList.size

    val baseCenterIndex = remember(rangeSize) {
        if (rangeSize == 0) 0 else (VIRTUAL_ITEM_COUNT / 2 / rangeSize) * rangeSize
    }

    val initialTopIndex = remember {
        val actualRangeIndex = displayList.indexOf(value)
        if (actualRangeIndex == -1) {
            0
        } else {
            val desiredCenterBaseIndex = baseCenterIndex + actualRangeIndex
            (desiredCenterBaseIndex - CENTER_INDEX).coerceIn(0, VIRTUAL_ITEM_COUNT - 1)
        }
    }

    val lazyListState = rememberLazyListState(initialFirstVisibleItemIndex = initialTopIndex)
    val coroutineScope = rememberCoroutineScope()
    val snapBehavior = rememberSnapFlingBehavior(lazyListState)
    val itemHeightPx = with(LocalDensity.current) { ITEM_HEIGHT.dp.roundToPx() }

    val highlightingTopIndex by remember {
        derivedStateOf {
            val firstVisibleItemIndex = lazyListState.firstVisibleItemIndex
            val firstVisibleItemScrollOffsetPx = lazyListState.firstVisibleItemScrollOffset
            if (firstVisibleItemScrollOffsetPx > itemHeightPx / 2) {
                firstVisibleItemIndex + 1
            } else {
                firstVisibleItemIndex
            }
        }
    }

    Box(
        modifier = Modifier
            .then(if (width == 0.dp) Modifier.fillMaxWidth() else Modifier.width(width))
            .height(PICKER_HEIGHT.dp),
        contentAlignment = Alignment.Center
    ) {
        LaunchedEffect(value, displayList) {
            if (suppressNextValueScroll) {
                suppressNextValueScroll = false
                return@LaunchedEffect
            }

            val actualRangeIndex = displayList.indexOf(value)
            if (actualRangeIndex < 0) return@LaunchedEffect

            val desiredCenterBaseIndex = baseCenterIndex + actualRangeIndex

            val currentCenterIndex = (highlightingTopIndex + CENTER_INDEX)
                .coerceIn(0, VIRTUAL_ITEM_COUNT - 1)

            val centerIndexDelta = currentCenterIndex.toLong() - desiredCenterBaseIndex.toLong()
            val wrapCyclesOffset = round(
                centerIndexDelta.toDouble() / rangeSize.toDouble()
            ).toLong()
            val desiredCenterIndex = (desiredCenterBaseIndex.toLong() + wrapCyclesOffset * rangeSize)
                .coerceIn(0L, (VIRTUAL_ITEM_COUNT - 1).toLong())

            val targetTopIndex = (desiredCenterIndex - CENTER_INDEX)
                .toInt()
                .coerceIn(0, VIRTUAL_ITEM_COUNT - 1)

            if (targetTopIndex != lazyListState.firstVisibleItemIndex) {
                lazyListState.animateScrollToItem(targetTopIndex)
            }
        }

        val settledTopIndex by remember {
            derivedStateOf {
                if (lazyListState.isScrollInProgress) NOT_STOPPED_SCROLL_INDEX else highlightingTopIndex
            }
        }

        LaunchedEffect(settledTopIndex) {
            if (settledTopIndex != NOT_STOPPED_SCROLL_INDEX && rangeSize > 0) {
                val centerGlobalIndex = settledTopIndex + CENTER_INDEX
                val mappedRangeIndex = ((centerGlobalIndex.toLong() % rangeSize) + rangeSize) % rangeSize
                val mappedValue = displayList[mappedRangeIndex.toInt()]

                if (mappedValue == null) {
                    val targetIndex = (0 until rangeList.size).minByOrNull {
                        val dist1 = abs(it - mappedRangeIndex.toInt())
                        val dist2 = rangeSize - dist1
                        min(dist1, dist2)
                    }

                    if (targetIndex != null) {
                        val targetValue = displayList[targetIndex]
                        if (targetValue != null && targetValue != value) {
                            onValueChange(targetValue)
                        }
                    }
                } else if (mappedValue != value) {
                    suppressNextValueScroll = true
                    onValueChange(mappedValue)
                }
            }
        }

        HighlightingBox()

        LazyColumn(
            state = lazyListState,
            flingBehavior = snapBehavior,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            items(VIRTUAL_ITEM_COUNT) { virtualIndex ->
                InfinitePickerItem(
                    index = virtualIndex,
                    highlightingIndex = highlightingTopIndex,
                    rangeList = displayList,
                    onClick = {
                        val rangeSize = displayList.size
                        if (rangeSize == 0) return@InfinitePickerItem

                        val realIndex = (((virtualIndex % rangeSize) + rangeSize) % rangeSize)
                        if (displayList[realIndex] == null) return@InfinitePickerItem

                        val targetTopIndex = (virtualIndex - CENTER_INDEX)
                            .coerceIn(0, VIRTUAL_ITEM_COUNT - 1)

                        coroutineScope.launch {
                            lazyListState.animateScrollToItem(targetTopIndex)
                        }
                    }
                )
            }
        }
    }
}

/**
 * 무한 스크롤 피커에서 반복적으로 렌더링되는 항목
 */
@Composable
private fun InfinitePickerItem(
    index: Int,
    highlightingIndex: Int,
    rangeList: PersistentList<Int?>,
    onClick: () -> Unit,
) {
    val rangeSize = rangeList.size
    if (rangeSize == 0) {
        Box(modifier = Modifier.height(ITEM_HEIGHT.dp))
        return
    }

    val realIndex = (((index % rangeSize) + rangeSize) % rangeSize)
    val currentValue = rangeList[realIndex]

    val selectedColor = SDGColor.Neutral700
    val unSelectedColor = SDGColor.Neutral400
    val distanceFromCenter = abs(index - (highlightingIndex + CENTER_INDEX))
    val maxDistance = 1f
    val colorLerpFraction = min(distanceFromCenter / maxDistance, 1f)
    val color = lerp(selectedColor, unSelectedColor, colorLerpFraction)

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxWidth()
            .height(ITEM_HEIGHT.dp)
            .clickable(onClick = onClick)
    ) {
        SDGText(
            text = currentValue?.toString().orEmpty(),
            typography = SDGTypography.Title2R,
            textColor = color
        )
    }
}

@Composable
private fun HighlightingBox() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(ITEM_HEIGHT.dp)
            .clip(RoundedCornerShape(SDGCornerRadius.Radius8))
            .background(SDGColor.Neutral150)
    )
}

@Preview(showBackground = true)
@Composable
private fun PreviewSDGNumberPicker() {
    var value by remember { mutableIntStateOf(5) }

    SDGNumberPicker(
        option = OneOption(
            value = value,
            rangeList = (1..10).toPersistentList(),
            onValueChange = { value = it },
            supportsInfiniteScroll = true
        )
    )
}

@Preview(showBackground = true)
@Composable
private fun PreviewSDGNumberPickerTwoOption() {
    var leftValue by remember { mutableIntStateOf(5) }
    var rightValue by remember { mutableIntStateOf(15) }

    SDGNumberPicker(
        option = TwoOption(
            left = TwoOption.OptionModel(
                value = leftValue,
                rangeList = (0..9).toPersistentList(),
                onValueChange = { leftValue = it },
                supportsInfiniteScroll = true
            ),
            right = TwoOption.OptionModel(
                value = rightValue,
                rangeList = (10..99 step 5).toPersistentList(),
                onValueChange = { rightValue = it },
                supportsInfiniteScroll = false
            )
        )
    )
}
