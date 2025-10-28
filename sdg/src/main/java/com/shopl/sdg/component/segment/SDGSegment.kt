package com.shopl.sdg.component.segment

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.TabPosition
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.shopl.sdg_common.ext.clickable
import com.shopl.sdg_common.ext.dropShadow
import com.shopl.sdg_common.foundation.SDGColor
import com.shopl.sdg_common.foundation.spacing.SDGSpacing.Spacing4
import com.shopl.sdg_common.foundation.typography.SDGTypography
import com.shopl.sdg_common.ui.components.SDGText
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf

/**
 * DesignSystem - Segment
 * 2~3개의 항목 중 필수로 1개를 선택하는 컴포넌트
 *
 * @param type [SDGSegmentType] – 타입에 따라 라벨의 개수가 결정
 * @param line [SDGSegmentTextLine] – 라벨의 텍스트 레이아웃 스타일을 지정
 * @param labels 표시할 라벨 문자열 리스트
 * @param selectedIndex – 현재 선택된 라벨의 인덱스 (선택 없음은 null)
 * @param backgroundColor – 세그먼트 컨트롤의 배경 색상
 * @param onLabelClick 라벨 클릭 시 호출되는 콜백
 */
@Composable
fun SDGSegment(
    type: SDGSegmentType,
    line: SDGSegmentTextLine,
    labels: PersistentList<String>,
    selectedIndex: Int?,
    backgroundColor: Color,
    onLabelClick: (Int) -> Unit,
) {
    require(labels.size == type.requiredLabelCount) {
        "라벨 개수를 Type과 맞춰주세요. (라벨 필요 개수: ${type.requiredLabelCount})"
    }
    if (selectedIndex != null) {
        require(selectedIndex in 0..labels.lastIndex) {
            "라벨 개수에 맞는 인덱스를 입력하세요. (라벨 개수: ${labels.size} / 선택한 인덱스: $selectedIndex) "
        }
    }

    Box(
        modifier = Modifier.clip(RoundedCornerShape(12.dp))
    ) {
        TabRow(
            modifier = Modifier.clip(RoundedCornerShape(8.dp)),
            selectedTabIndex = selectedIndex ?: 0,
            containerColor = backgroundColor,
            indicator = {
                if (selectedIndex != null) {
                    SDGSegmentIndicator(
                        tabPositions = it,
                        selectedIndex = selectedIndex
                    )
                }
            },
            divider = {}
        ) {
            labels.forEachIndexed { index, label ->
                SDGSegmentUnit(
                    line = line,
                    label = label,
                    selected = selectedIndex == index,
                    onLabelClick = {
                        onLabelClick(index)
                    }
                )
            }
        }
    }
}

@Composable
private fun SDGSegmentUnit(
    line: SDGSegmentTextLine,
    label: String,
    selected: Boolean,
    onLabelClick: () -> Unit
) {
    when (line) {
        SDGSegmentTextLine.TWO_LINE -> {
            SDGTwoLineSegmentUnit(
                label = label,
                selected = selected,
                onLabelClick = onLabelClick
            )
        }

        SDGSegmentTextLine.ONE_LINE -> {
            SDGOneLineSegmentUnit(
                label = label,
                selected = selected,
                onLabelClick = onLabelClick
            )
        }
    }
}

@Composable
private fun SDGTwoLineSegmentUnit(
    modifier: Modifier = Modifier,
    label: String,
    selected: Boolean,
    onLabelClick: () -> Unit
) {
    Box(
        modifier = modifier
            .zIndex(1f)
            .sizeIn(minWidth = 50.dp, minHeight = 58.dp)
            .clip(RoundedCornerShape(8.dp))
            .clickable(hasRipple = false) { onLabelClick() }
            .background(Color.Transparent)
            .padding(Spacing4),
        contentAlignment = Alignment.Center
    ) {
        SDGText(
            modifier = Modifier.padding(horizontal = Spacing4),
            text = label,
            typography = SDGTypography.Body2R,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            textColor = if (selected) SDGColor.Neutral700 else SDGColor.Neutral500
        )
    }
}

@Composable
private fun SDGOneLineSegmentUnit(
    modifier: Modifier = Modifier,
    label: String,
    selected: Boolean,
    onLabelClick: () -> Unit
) {
    Box(
        modifier = modifier
            .zIndex(1f)
            .sizeIn(minWidth = 32.dp, minHeight = 40.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(Color.Transparent)
            .padding(Spacing4)
            .clickable(hasRipple = false) { onLabelClick() },
        contentAlignment = Alignment.Center
    ) {
        SDGText(
            modifier = Modifier.padding(horizontal = Spacing4),
            text = label,
            typography = SDGTypography.Body2R,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            textColor = if (selected) SDGColor.Neutral700 else SDGColor.Neutral500
        )
    }
}

@Composable
private fun SDGSegmentIndicator(
    tabPositions: List<TabPosition>,
    selectedIndex: Int,
) {
    val transition = updateTransition(targetState = selectedIndex, label = "indicator")
    val indicatorLeft by transition.animateDp(
        label = "",
        transitionSpec = {
            spring(stiffness = Spring.StiffnessLow)
        }
    ) {
        tabPositions[it].left
    }
    val indicatorRight by transition.animateDp(
        label = "",
        transitionSpec = { spring(stiffness = Spring.StiffnessLow) }
    ) {
        tabPositions[it].right
    }

    Box(
        Modifier
            .tabIndicatorOffset(tabPositions[selectedIndex])
            .padding(4.dp)
            .dropShadow(
                shape = RoundedCornerShape(8.dp),
                offsetX = 1.dp,
                offsetY = 1.dp,
                blur = 4.dp,
                color = SDGColor.Neutral900_a5
            )
            .width(indicatorRight - indicatorLeft)
            .fillMaxHeight()
            .clip(RoundedCornerShape(8.dp))
            .background(SDGColor.Neutral0)
    )
}

enum class SDGSegmentType(val requiredLabelCount: Int) {
    TWO_OPTION(requiredLabelCount = 2),
    THREE_OPTION(requiredLabelCount = 3),
    FOUR_OPTION(requiredLabelCount = 4),
}

enum class SDGSegmentTextLine {
    ONE_LINE,
    TWO_LINE
}

@Preview(name = "4option + 2line")
@Composable
private fun PreviewSDGSegment_FourOption_TwoLine() {
    SDGSegment(
        type = SDGSegmentType.FOUR_OPTION,
        labels = persistentListOf("긴라벨은 말줄임 긴라벨은 말줄임", "짧은라벨2", "짧은라벨3", "짧은 라벨4"),
        line = SDGSegmentTextLine.TWO_LINE,
        selectedIndex = 1,
        backgroundColor = SDGColor.Neutral300,
        onLabelClick = {}
    )
}

@Preview(name = "3option + 2line")
@Composable
private fun PreviewSDGSegment_ThreeOption_TwoLine() {
    SDGSegment(
        type = SDGSegmentType.THREE_OPTION,
        labels = persistentListOf("긴라벨은 말줄임 긴라벨은 말줄임", "짧은라벨2", "짧은라벨3"),
        line = SDGSegmentTextLine.TWO_LINE,
        selectedIndex = 1,
        backgroundColor = SDGColor.Neutral300,
        onLabelClick = {}
    )
}

@Preview(name = "3option + 1line")
@Composable
private fun PreviewSDGSegment_ThreeOption_OneLine() {
    SDGSegment(
        type = SDGSegmentType.THREE_OPTION,
        labels = persistentListOf("긴라벨은 말줄임 긴라벨은 말줄임", "짧은라벨2", "짧은라벨3"),
        line = SDGSegmentTextLine.ONE_LINE,
        selectedIndex = 0,
        backgroundColor = SDGColor.Neutral100,
        onLabelClick = {}
    )
}

@Preview(name = "2option + 2line")
@Composable
private fun PreviewSDGSegment_TwoOption_TwoLine() {
    SDGSegment(
        type = SDGSegmentType.TWO_OPTION,
        labels = persistentListOf("1", "라벨2"),
        line = SDGSegmentTextLine.TWO_LINE,
        selectedIndex = 0,
        backgroundColor = SDGColor.Neutral300,
        onLabelClick = {}
    )
}

@Preview(name = "2option + 1line")
@Composable
private fun PreviewSDGSegment_TwoOption_OneLine() {
    SDGSegment(
        type = SDGSegmentType.TWO_OPTION,
        labels = persistentListOf("라벨1", "라벨2"),
        line = SDGSegmentTextLine.ONE_LINE,
        selectedIndex = 1,
        backgroundColor = SDGColor.Neutral300,
        onLabelClick = {}
    )
}

@Preview(name = "min width")
@Composable
private fun PreviewSDGSegment_TwoOption_OneLine_MinWidth() {
    Box(
        modifier = Modifier
            .width(76.dp)
    ) {
        SDGSegment(
            type = SDGSegmentType.TWO_OPTION,
            labels = persistentListOf("Sim", "Nao"),
            line = SDGSegmentTextLine.ONE_LINE,
            selectedIndex = 1,
            backgroundColor = SDGColor.Neutral300,
            onLabelClick = {}
        )
    }
}