package com.shopl.sdg.component.number_picker

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.shopl.sdg_common.foundation.SDGColor
import com.shopl.sdg_common.foundation.SDGCornerRadius
import com.shopl.sdg_common.foundation.typography.SDGTypography
import com.shopl.sdg_common.ui.components.SDGText

/**
 * SDG - Component - Number Picker
 *
 * 어떤 특정한 값의 숫자를 스피너로 선택하는 컴포넌트
 *
 * @param range 피커에 표시할 정수 범위
 * @param onValueChange 선택된 값이 변경될 때 호출되는 콜백
 * @param width 피커의 너비. 지정하지 않으면 사용 가능한 전체 너비를 채움
 *
 * @see <a href="">Figma</a>
 */
private const val NOT_STOPPED_SCROLL_INDEX = -1

@Composable
fun SDGNumberPicker(
    value: Int,
    range: Iterable<Int>,
    onValueChange: (Int) -> Unit,
    width: Dp = 0.dp,
    textColor: Color = SDGColor.Neutral700
) {
    require(range.count() >= 3) { "범위는 최소 3이상이 필요합니다." }
    require(range.contains(value)) { "value($value)는 반드시 범위 안에 존재하는 값이어야 합니다." }

    val rangeList = remember(range) { range.toList() }
    val rangeStringList = remember(rangeList) { rangeList.map { it.toString() } }

    Box(
        modifier = Modifier.background(SDGColor.Neutral0),
        contentAlignment = Alignment.Center
    ) {
        val lazyListState = rememberLazyListState()
        val snapBehavior = rememberSnapFlingBehavior(lazyListState)

        LaunchedEffect(value) {
            val targetIndex = rangeList.indexOf(value).coerceAtLeast(0)

            if (lazyListState.firstVisibleItemIndex != targetIndex) {
                lazyListState.animateScrollToItem(targetIndex)
            }
        }

        val scrollStoppedIndex by remember {
            derivedStateOf {
                if (lazyListState.isScrollInProgress) NOT_STOPPED_SCROLL_INDEX else lazyListState.firstVisibleItemIndex
            }
        }

        LaunchedEffect(scrollStoppedIndex) {
            if (scrollStoppedIndex != NOT_STOPPED_SCROLL_INDEX) {
                val newValue = rangeList.getOrNull(scrollStoppedIndex)
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
            modifier = Modifier
                .then(
                    if (width == 0.dp) {
                        Modifier.fillMaxWidth()
                    } else {
                        Modifier.width(width)
                    }
                )
                .height(150.dp)
        ) {
            item { CenteringSpacer() }

            items(rangeStringList.size) { index ->
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                ) {
                    SDGText(
                        text = rangeStringList[index],
                        typography = SDGTypography.Title2R,
                        textColor = textColor
                    )
                }
            }

            item { CenteringSpacer() }
        }
    }
}

@Composable
private fun HighlightingBox() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .clip(RoundedCornerShape(SDGCornerRadius.Radius8))
            .background(SDGColor.Neutral150)
    )
}

/**
 * 리스트의 시작과 끝에 빈 공간을 추가하여 첫 항목과 마지막 항목이 중앙에 올 수 있도록 함
 */
@Composable
private fun CenteringSpacer() {
    Spacer(modifier = Modifier.height(50.dp))
}