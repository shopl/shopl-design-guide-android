package com.shopl.sdg.component.number_picker

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.shopl.sdg_common.foundation.SDGColor
import com.shopl.sdg_common.foundation.SDGCornerRadius
import com.shopl.sdg_common.foundation.typography.SDGTypography
import com.shopl.sdg_common.ui.components.SDGText
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
 *
 * @see <a href="https://www.figma.com/design/qWVshatQ9eqoIn4fdEZqWy/SDG?node-id=18805-226&m=dev">Figma</a>
 */
@Composable
fun SDGNumberPicker(
    value: Int,
    range: Iterable<Int>,
    onValueChange: (Int) -> Unit,
    width: Dp = 0.dp,
) {
    require(range.count() >= VISIBLE_COUNT) { "범위는 최소 $VISIBLE_COUNT 이상이 필요합니다." }
    require(range.contains(value)) { "value($value)는 반드시 범위 안에 존재하는 값이어야 합니다." }

    var isFirstScrollDone by remember { mutableStateOf(false) }

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
                val distanceFromCenter = abs(index - (highlightingIndex + CENTER_INDEX))

                val maxDistance = 1f

                val colorLerpFraction = min(distanceFromCenter / maxDistance, 1f)

                val selectedColor = SDGColor.Neutral700
                val unSelectedColor = SDGColor.Neutral400

                val color = lerp(selectedColor, unSelectedColor, colorLerpFraction)

                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(ITEM_HEIGHT.dp)
                ) {
                    if (paddedRangeList[index] != DUMMY) {
                        SDGText(
                            text = paddedRangeStringList[index],
                            typography = SDGTypography.Title2R,
                            textColor = color
                        )
                    }
                }
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
 * 앞/뒤에 'CENTER_INDEX' 개수만큼 null(더미)을 추가합니다.
 * 이 null은 실제 값이 아니라 빈 줄(Spacer) 역할을 하며,
 * 포커싱 계산에서 필수적으로 필요합니다.
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