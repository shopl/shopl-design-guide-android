package com.shopl.sdg.component.tab.icon

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.shopl.sdg.component.tab.icon.preview.SDGIconTabPreviewParameter
import com.shopl.sdg.component.tab.icon.preview.SDGIconTabPreviewParameterProvider
import com.shopl.sdg_common.foundation.spacing.SDGSpacing.Spacing4

private val SDGIconTabItemMinWidth = 50.dp

/**
 * SDG - Tab - Icon Tab
 *
 * 아이콘과 라벨, 수치를 조합하여 선택 상태에 따라 텍스트와 아이콘이 상호 전환되는 탭 컴포넌트
 *
 * @version 2.3.47
 *
 * @param option Option: ThreeOption / FourOption / FiveOption
 * @param selectedTab Selected Tab: 1부터 시작하는 선택 위치. Option의 탭 개수 이내로 지정합니다.
 * @param onTabClick 클릭한 탭의 위치(1부터 시작)를 전달하는 콜백. 호출부에서 selectedTab을 갱신합니다.
 * @param paddingValues 컴포넌트 외부 여백. 여백을 제외한 부모의 가용 너비를 채웁니다.
 *
 * @see <a href="https://www.figma.com/design/SsBipIZV6kO3kUo0ekFyOR/Icon-Tab?node-id=1-63&m=dev">Figma</a>
 */
@Composable
fun SDGIconTab(
    option: SDGIconTabOption,
    selectedTab: Int,
    onTabClick: (Int) -> Unit,
    paddingValues: PaddingValues = PaddingValues(0.dp),
) {
    require(selectedTab in 1..option.tabs.size) {
        "Selected Tab은 1부터 ${option.tabs.size} 사이의 위치여야 합니다. (입력값: $selectedTab)"
    }

    Row(
        modifier = Modifier
            .padding(paddingValues)
            .fillMaxWidth()
            .selectableGroup(),
        horizontalArrangement = Arrangement.spacedBy(Spacing4),
    ) {
        option.tabs.forEachIndexed { index, tab ->
            val isSelected = index + 1 == selectedTab
            SDGIconTabItem(
                state = if (isSelected) SDGIconTabItemState.Selected else SDGIconTabItemState.Unselected,
                label = tab.label,
                count = tab.count,
                showCount = tab.showCount,
                iconTabIc = tab.iconTabIc,
                onClick = { onTabClick(index + 1) },
                modifier = if (isSelected) {
                    Modifier.weight(1f).widthIn(min = SDGIconTabItemMinWidth)
                } else {
                    Modifier.width(SDGIconTabItemMinWidth)
                },
            )
        }
    }
}

@Preview(showBackground = true, widthDp = 335)
@Composable
private fun PreviewSDGIconTab(
    @PreviewParameter(SDGIconTabPreviewParameterProvider::class)
    parameter: SDGIconTabPreviewParameter,
) {
    with(parameter) {
        SDGIconTab(
            option = option,
            selectedTab = selectedTab,
            onTabClick = {},
            paddingValues = paddingValues,
        )
    }
}
