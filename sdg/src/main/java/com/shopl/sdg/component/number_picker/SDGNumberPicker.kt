package com.shopl.sdg.component.number_picker

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Box
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
import com.shopl.sdg_common.foundation.SDGColor
import com.shopl.sdg_common.foundation.SDGCornerRadius
import com.shopl.sdg_common.foundation.typography.SDGTypography
import com.shopl.sdg_common.ui.components.SDGText
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.toPersistentList
import kotlin.math.abs
import kotlin.math.min

private const val NOT_STOPPED_SCROLL_INDEX = -1
private const val ITEM_HEIGHT = 40
private const val PICKER_HEIGHT = 200
private const val VISIBLE_COUNT = PICKER_HEIGHT / ITEM_HEIGHT
private const val CENTER_INDEX = VISIBLE_COUNT / 2
val DUMMY = null

/**
 * SDG - Component - Number Picker
 *
 * 어떤 특정한 값의 숫자를 스피너로 선택하는 컴포넌트
 *
 * @param range 피커에 표시할 정수 범위
 *
 * 원본 리스트:   [0, 1, 2, 3, 4]
 *
 * 앞/뒤에 중앙 정렬을 위한 리스트: [null, null, 0, 1, 2, 3, 4, null, null]
 *
 * @param onValueChange 선택된 값이 변경될 때 호출되는 콜백
 * @param width 피커의 너비. 지정하지 않으면 사용 가능한 전체 너비를 채움
 * @param isEditMode 직접 입력 가능 여부 (기본값: false)
 *
 * @see <a href="https://www.figma.com/design/qWVshatQ9eqoIn4fdEZqWy/SDG?node-id=18805-226&m=dev">Figma</a>
 */
@Composable
fun SDGNumberPicker(
    value: Int,
    range: IntRange,
    onValueChange: (Int) -> Unit,
    width: Dp = 0.dp,
    isEditMode: Boolean = false,
) {
    require(range.count() >= VISIBLE_COUNT) { "범위는 최소 $VISIBLE_COUNT 이상이 필요합니다." }
    require(range.contains(value)) { "value($value)는 반드시 범위 안에 존재하는 값이어야 합니다." }

    var isFirstScrollDone by remember { mutableStateOf(false) }
    var isEditing by remember { mutableStateOf(false) }
    var editingValue by remember { mutableStateOf(TextFieldValue("")) }

    val rangeList = remember(range) { range.toList() }
    val paddedRangeList = addCenterPadding(rangeList, CENTER_INDEX)
    val paddedRangeStringList = paddedRangeList.map { it?.toString().orEmpty() }

    val lazyListState = rememberLazyListState()
    val snapBehavior = rememberSnapFlingBehavior(lazyListState)
    val itemHeightPx = with(LocalDensity.current) { ITEM_HEIGHT.dp.roundToPx() }

    val highlightingIndex by remember {
        derivedStateOf {
            val scrollIndex = lazyListState.firstVisibleItemIndex
            val scrollOffset = lazyListState.firstVisibleItemScrollOffset
            if (scrollOffset > itemHeightPx / 2) scrollIndex + 1 else scrollIndex
        }
    }

    LaunchedEffect(lazyListState.isScrollInProgress) {
        if (lazyListState.isScrollInProgress && isEditing) {
            isEditing = false
        }
    }

    Box(
        modifier = Modifier
            .then(
                if (width == 0.dp) Modifier.fillMaxWidth()
                else Modifier.width(width)
            )
            .height(PICKER_HEIGHT.dp),
        contentAlignment = Alignment.Center
    ) {
        LaunchedEffect(value, rangeList) {
            val actualIndex = rangeList.indexOf(value)
            val targetIndex = actualIndex + CENTER_INDEX
            val scrollTo = (targetIndex - CENTER_INDEX).coerceIn(0, paddedRangeList.size - VISIBLE_COUNT)

            if (!isFirstScrollDone) {
                lazyListState.scrollToItem(scrollTo)
                isFirstScrollDone = true
            } else {
                lazyListState.animateScrollToItem(scrollTo)
            }
        }

        val scrollStoppedIndex by remember {
            derivedStateOf {
                if (lazyListState.isScrollInProgress) NOT_STOPPED_SCROLL_INDEX else highlightingIndex
            }
        }

        LaunchedEffect(scrollStoppedIndex) {
            if (scrollStoppedIndex != NOT_STOPPED_SCROLL_INDEX) {
                val newValue = paddedRangeList.getOrNull(scrollStoppedIndex + CENTER_INDEX)
                if (newValue != null && newValue != value) {
                    onValueChange(newValue)
                }
            }
        }

        HighlightingBox()

        LazyColumn(
            state = lazyListState,
            flingBehavior = snapBehavior,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            items(paddedRangeStringList.size) { index ->
                SDGNumberPickerItem(
                    index = index,
                    highlightingIndex = highlightingIndex,
                    centerIndex = CENTER_INDEX,
                    paddedRangeList = paddedRangeList.toPersistentList(),
                    paddedRangeStringList = paddedRangeStringList.toPersistentList(),
                    isEditing = isEditing,
                    setEditing = { editing ->
                        isEditing = editing
                        if (!editing) editingValue = TextFieldValue("")
                    },
                    editingValue = editingValue,
                    setEditingValue = { editingValue = it },
                    value = value,
                    onValueChange = onValueChange,
                    rangeList = rangeList.toPersistentList(),
                    isEditMode = isEditMode
                )
            }
        }
    }
}

/**
 * 넘버피커 내부에서 각 아이템을 렌더링하는 컴포저블
 *
 * @param highlightingIndex 현재 스크롤 기준 중앙 아이템 인덱스
 * @param centerIndex 중앙 포커싱을 위한 패딩 카운트
 * @param isEditing 현재 아이템이 입력 중인지 여부
 * @param setEditing 입력모드 변경 콜백
 * @param editingValue TextField 입력값(TextFieldValue, 커서 위치 포함)
 * @param setEditingValue 입력값 변경 콜백
 * @param rangeList 선택 가능한 값 리스트
 * @param isEditMode 편집모드 활성화 여부
 */
@Suppress("SameParameterValue")
@Composable
private fun SDGNumberPickerItem(
    index: Int,
    highlightingIndex: Int,
    centerIndex: Int,
    paddedRangeList: PersistentList<Int?>,
    paddedRangeStringList: PersistentList<String>,
    isEditing: Boolean,
    setEditing: (Boolean) -> Unit,
    editingValue: TextFieldValue,
    setEditingValue: (TextFieldValue) -> Unit,
    value: Int,
    onValueChange: (Int) -> Unit,
    rangeList: PersistentList<Int>,
    isEditMode: Boolean
) {
    val isCenterItem = (index == highlightingIndex + centerIndex)
    val currentValue = paddedRangeList[index]
    val selectedColor = SDGColor.Neutral700
    val unSelectedColor = SDGColor.Neutral400
    val distanceFromCenter = abs(index - (highlightingIndex + centerIndex))
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
                if (
                    isEditMode &&
                    isCenterItem &&
                    currentValue != DUMMY &&
                    !isEditing
                ) Modifier.clickable {
                    val text = currentValue.toString()
                    setEditingValue(TextFieldValue(text, selection = TextRange(text.length)))
                    setEditing(true)
                }
                else Modifier
            )
    ) {
        if (currentValue != null) {
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
                    text = paddedRangeStringList[index],
                    typography = SDGTypography.Title2R,
                    textColor = color
                )
            }
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

/**
 * NumberPicker에서 range의 양 끝값도 항상 중앙에 위치할 수 있도록,
 * 앞/뒤에 'CENTER_INDEX' 개수만큼 null(더미)을 추가함
 * 이 null은 실제 값이 아니라 빈 줄(Spacer) 역할을 하며,
 * 포커싱 계산에서 필수적으로 필요
 */
@Suppress("SameParameterValue")
private fun <T> addCenterPadding(list: List<T>, centerCount: Int): List<T?> {
    return List(centerCount) { DUMMY } + list + List(centerCount) { DUMMY }
}

@Preview(showBackground = true)
@Composable
private fun PreviewSDGNumberPicker() {
    var value by remember { mutableIntStateOf(5) }

    SDGNumberPicker(
        value = value,
        range = 1..10,
        onValueChange = { value = it }
    )
}