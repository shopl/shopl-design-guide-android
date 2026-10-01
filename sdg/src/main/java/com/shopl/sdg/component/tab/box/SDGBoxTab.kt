package com.shopl.sdg.component.tab.box

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement.Center
import androidx.compose.foundation.layout.Arrangement.spacedBy
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.selected
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.shopl.sdg.component.tab.box.preview.SDGBoxTabPreviewParameter
import com.shopl.sdg.component.tab.box.preview.SDGBoxTabPreviewParameterProvider
import com.shopl.sdg_common.ext.clickable
import com.shopl.sdg_common.foundation.SDGColor
import com.shopl.sdg_common.foundation.SDGCornerRadius
import com.shopl.sdg_common.foundation.spacing.SDGSpacing.Spacing16
import com.shopl.sdg_common.foundation.spacing.SDGSpacing.Spacing2
import com.shopl.sdg_common.foundation.spacing.SDGSpacing.Spacing6
import com.shopl.sdg_common.foundation.spacing.SDGSpacing.Spacing8
import com.shopl.sdg_common.foundation.typography.SDGTypography
import com.shopl.sdg_common.ui.components.SDGText

/**
 * SDG - Tab - Box Tab
 *
 * 라벨과 세부 선택 값을 조합하여 화면 조건을 유연하게 전환하는 박스형 탭 컴포넌트
 *
 * @version 2.3.47
 *
 * @param option 2개 또는 3개의 탭 데이터
 * @param selectedTab 0부터 시작하는 필수 선택 인덱스. 0 이상 탭 개수 미만으로 지정합니다.
 * @param onTabClick 클릭한 탭의 인덱스를 전달합니다. 호출부에서 selectedTab을 갱신합니다.
 *
 * @see <a href="https://www.figma.com/design/qWVshatQ9eqoIn4fdEZqWy/SDG?node-id=20902-19365&m=dev">Figma</a>
 */
@Composable
fun SDGBoxTab(
    option: SDGBoxTabOption,
    style: SDGBoxTabStyle,
    selectedTab: Int,
    onTabClick: (tabPosition: Int) -> Unit
) {
    require(selectedTab in option.tabs.indices) {
        "Selected Tab은 0부터 ${option.tabs.lastIndex} 사이의 인덱스여야 합니다. (입력값: $selectedTab)"
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(SDGCornerRadius.BoxRadius.Radius12)
            .background(SDGColor.Neutral0)
            .then(
                if (style == SDGBoxTabStyle.Line) {
                    Modifier.border(1.dp, SDGColor.Neutral200, SDGCornerRadius.BoxRadius.Radius12)
                } else {
                    Modifier
                }
            )
            .padding(horizontal = Spacing6, vertical = Spacing8)
            .selectableGroup(),
        horizontalArrangement = spacedBy(Spacing6),
        verticalAlignment = Alignment.CenterVertically
    ) {
        option.tabs.forEachIndexed { index, item ->
            val isSelected = index == selectedTab
            val state = if (isSelected) SDGBoxTabItemState.Selected else SDGBoxTabItemState.Unselected
            val itemModifier = Modifier
                .weight(1f)
                .semantics { selected = isSelected }
                .clickable(
                    role = Role.Tab,
                    onClick = { onTabClick(index) }
                )

            if (index < option.tabs.lastIndex) {
                Row(
                    modifier = itemModifier,
                    horizontalArrangement = spacedBy(Spacing6),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    BoxTabItem(item = item, state = state, modifier = Modifier.weight(1f))
                    VerticalDivider(
                        modifier = Modifier.height(Spacing16),
                        color = SDGColor.Neutral200
                    )
                }
            } else {
                BoxTabItem(item = item, state = state, modifier = itemModifier)
            }
        }
    }
}

@Composable
private fun BoxTabItem(
    item: SDGBoxTabItem,
    state: SDGBoxTabItemState,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = spacedBy(Spacing2, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        SDGText(
            text = item.label,
            typography = state.labelTypography,
            textColor = state.labelTextColor,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = Spacing2)
        )
        val twoDepth = item.showTwoDepth
        if (twoDepth is SDGBoxTabItem.ShowTwoDepth.True) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Center,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                SDGText(
                    text = twoDepth.text,
                    typography = SDGTypography.Body3R,
                    textColor = state.twoDepthColor,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.weight(1f, fill = false)
                )

                twoDepth.count?.toIntOrNull()?.takeIf { it > 1 }?.let { count ->
                    SDGText(
                        text = "+${count - 1}",
                        typography = SDGTypography.Body3SB,
                        textColor = state.twoDepthColor,
                        maxLines = 1
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 335)
@Composable
private fun PreviewSDGBoxTab(
    @PreviewParameter(SDGBoxTabPreviewParameterProvider::class)
    parameter: SDGBoxTabPreviewParameter
) {
    Box(modifier = Modifier.background(SDGColor.Neutral50)) {
        SDGBoxTab(
            option = parameter.option,
            style = parameter.style,
            selectedTab = parameter.selectedTab,
            onTabClick = {}
        )
    }
}
