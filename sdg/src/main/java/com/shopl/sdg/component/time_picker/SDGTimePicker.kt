package com.shopl.sdg.component.time_picker

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.shopl.sdg.component.time_picker.SDGTimePickerOption.OneOption
import com.shopl.sdg.component.time_picker.SDGTimePickerOption.TwoOption
import com.shopl.sdg_common.foundation.SDGColor
import com.shopl.sdg_common.foundation.SDGCornerRadius
import com.shopl.sdg_common.foundation.spacing.SDGSpacing.Spacing4
import com.shopl.sdg_common.foundation.typography.SDGTypography
import com.shopl.sdg_common.ui.components.SDGText
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.toPersistentList
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
 * SDG - Time Picker
 *
 * 스피너 형식의 숫자 선택기를 활용해 시간 값을 선택하는 컴포넌트
 *
 * @param option 타임 피커 타입 [SDGTimePickerOption]
 *
 * 무한 스크롤 되는 피커
 * 원본 리스트: [0,1,2,3,4]
 * -> 4, 0, 1, 2, 3, 4, 0, 1, 2 . . .
 *
 * @see <a href="https://www.figma.com/design/qWVshatQ9eqoIn4fdEZqWy/SDG?node-id=8610-20836&m=dev">Figma</a>
 */
@Composable
fun SDGTimePicker(option: SDGTimePickerOption) {
    when (option) {
        is OneOption -> SDGOneOptionTimePicker(option)
        is TwoOption -> SDGTwoOptionTimePicker(option)
    }
}

/**
 * 1개의 값만 선택
 */
@Composable
private fun SDGOneOptionTimePicker(option: OneOption) {
    SDGTimePickerBody(
        value = option.value,
        range = option.range,
        onValueChange = option.onValueChange,
        width = option.width,
        isEditMode = true
    )
}

/**
 * 2개의 조합된 값 선택
 */
@Composable
private fun SDGTwoOptionTimePicker(option: TwoOption) {
    Box(contentAlignment = Alignment.Center) {

        HighlightingBox()

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(Spacing4)
        ) {
            Box(modifier = Modifier.weight(1f)) {
                SDGTimePickerBody(
                    value = option.left.value,
                    range = option.left.range,
                    onValueChange = option.left.onValueChange,
                    isEditMode = true,
                )
            }

            SDGText(
                text = ":",
                typography = SDGTypography.Body1R,
                textColor = SDGColor.Neutral700,
            )

            Box(modifier = Modifier.weight(1f)) {
                SDGTimePickerBody(
                    value = option.right.value,
                    range = option.right.range,
                    onValueChange = option.right.onValueChange,
                    isEditMode = true
                )
            }
        }
    }
}

/**
 * @param onValueChange 선택된 값이 변경될 때 호출되는 콜백
 * @param width 피커의 너비. 지정하지 않으면 사용 가능한 전체 너비를 채움
 * @param isEditMode 직접 입력 가능 여부 (기본값: true)
 */
@Composable
private fun SDGTimePickerBody(
    value: Int,
    range: IntRange,
    onValueChange: (Int) -> Unit,
    width: Dp = 0.dp,
    isEditMode: Boolean = true,
) {
    require(range.count() >= VISIBLE_COUNT) { "범위는 최소 $VISIBLE_COUNT 이상이 필요합니다." }
    require(range.contains(value)) { "value($value)는 반드시 범위 안에 존재하는 값이어야 합니다." }

    var isEditing by remember { mutableStateOf(false) }
    var editingValue by remember { mutableStateOf(TextFieldValue("")) }

    var suppressNextValueScroll by remember { mutableStateOf(false) }

    val rangeList = remember(range) { range.toList() }
    val rangeSize = rangeList.size

    val baseCenterIndex = remember(rangeSize) {
        (VIRTUAL_ITEM_COUNT / 2 / rangeSize) * rangeSize
    }

    val initialTopIndex = remember {
        val actualRangeIndex = rangeList.indexOf(value)
        if (actualRangeIndex == -1) {
            0
        } else {
            val desiredCenterBaseIndex = baseCenterIndex + actualRangeIndex
            (desiredCenterBaseIndex - CENTER_INDEX).coerceIn(0, VIRTUAL_ITEM_COUNT - 1)
        }
    }

    val lazyListState = rememberLazyListState(initialFirstVisibleItemIndex = initialTopIndex)
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

    LaunchedEffect(lazyListState.isScrollInProgress) {
        if (lazyListState.isScrollInProgress && isEditing) {
            isEditing = false
        }
    }

    Box(
        modifier = Modifier
            .then(if (width == 0.dp) Modifier.fillMaxWidth() else Modifier.width(width))
            .height(PICKER_HEIGHT.dp),
        contentAlignment = Alignment.Center
    ) {
        LaunchedEffect(value, rangeList) {
            if (suppressNextValueScroll) {
                suppressNextValueScroll = false
                return@LaunchedEffect
            }

            val actualRangeIndex = rangeList.indexOf(value)
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
            if (settledTopIndex != NOT_STOPPED_SCROLL_INDEX) {
                val centerGlobalIndex = settledTopIndex + CENTER_INDEX
                val mappedRangeIndex = ((centerGlobalIndex.toLong() % rangeSize) + rangeSize) % rangeSize
                val mappedValue = rangeList[mappedRangeIndex.toInt()]
                if (mappedValue != value) {
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
                SDGTimePickerBodyItem(
                    index = virtualIndex,
                    highlightingIndex = highlightingTopIndex,
                    rangeList = rangeList.toPersistentList(),
                    isEditing = isEditing,
                    setEditing = { isEditingNow ->
                        isEditing = isEditingNow
                        if (!isEditingNow) editingValue = TextFieldValue("")
                    },
                    editingValue = editingValue,
                    setEditingValue = { editingValue = it },
                    value = value,
                    onValueChange = onValueChange,
                    isEditMode = isEditMode
                )
            }
        }
    }
}

@Composable
private fun SDGTimePickerBodyItem(
    index: Int,
    highlightingIndex: Int,
    rangeList: PersistentList<Int>,
    isEditing: Boolean,
    setEditing: (Boolean) -> Unit,
    editingValue: TextFieldValue,
    setEditingValue: (TextFieldValue) -> Unit,
    value: Int,
    onValueChange: (Int) -> Unit,
    isEditMode: Boolean
) {
    val rangeSize = rangeList.size
    val isCenterItem = (index == highlightingIndex + CENTER_INDEX)

    val realIndex = (((index % rangeSize) + rangeSize) % rangeSize)
    val currentValue = rangeList[realIndex]

    val selectedColor = SDGColor.Neutral700
    val unSelectedColor = SDGColor.Neutral400
    val distanceFromCenter = abs(index - (highlightingIndex + CENTER_INDEX))
    val maxDistance = 1f
    val colorLerpFraction = min(distanceFromCenter / maxDistance, 1f)
    val color = lerp(selectedColor, unSelectedColor, colorLerpFraction)

    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(isEditing) {
        if (isEditing && isCenterItem) {
            focusRequester.requestFocus()
        }
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxWidth()
            .height(ITEM_HEIGHT.dp)
            .then(
                if (isEditMode && isCenterItem && !isEditing)
                    Modifier.clickable {
                        val text = currentValue.toString()
                        setEditingValue(TextFieldValue(text, selection = TextRange(text.length)))
                        setEditing(true)
                    }
                else Modifier
            )
    ) {
        if (isEditMode && isCenterItem && isEditing) {
            BasicTextField(
                value = editingValue,
                onValueChange = { newInput ->
                    val inputStr = newInput.text
                    if (inputStr.all { it.isDigit() } && inputStr.toIntOrNull() in rangeList || inputStr.isBlank()) {
                        setEditingValue(newInput.copy(selection = TextRange(inputStr.length)))
                    }
                },
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        val newValue = editingValue.text.toIntOrNull()
                        if (newValue != null && newValue in rangeList) {
                            setEditing(false)
                            if (newValue != value) {
                                onValueChange(newValue)
                            }
                        }
                    }
                ),
                textStyle = SDGTypography.Title2R.style.copy(
                    color = selectedColor,
                    textAlign = TextAlign.Center
                ),
                modifier = Modifier
                    .focusRequester(focusRequester)
                    .fillMaxWidth()
                    .height(ITEM_HEIGHT.dp)
            ) { innerTextField ->
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    innerTextField()
                }
            }
        } else {
            SDGText(
                text = currentValue.toString(),
                typography = SDGTypography.Title2R,
                textColor = color
            )
        }
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
private fun PreviewSDGTimePickerOneOption() {
    var value by remember { mutableIntStateOf(10) }

    SDGTimePicker(
        option = OneOption(
            value = value,
            range = 0..23,
            onValueChange = { value = it },
        )
    )
}

@Preview(showBackground = true)
@Composable
private fun PreviewSDGTimePickerTwoOption() {
    var hour by remember { mutableIntStateOf(11) }
    var minute by remember { mutableIntStateOf(30) }

    SDGTimePicker(
        option = TwoOption(
            left = TwoOption.OptionModel(
                value = hour,
                range = 0..23,
                onValueChange = { hour = it },
            ),
            right = TwoOption.OptionModel(
                value = minute,
                range = 0..59,
                onValueChange = { minute = it },
            )
        )
    )
}
