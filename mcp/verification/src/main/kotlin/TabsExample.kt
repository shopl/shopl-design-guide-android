package com.shopl.sdg.mcp.examples

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import com.shopl.sdg.component.tab.fixed.SDGFixedTab
import com.shopl.sdg.component.tab.fixed.SDGFixedTabBaselineDivider
import com.shopl.sdg.component.tab.fixed.SDGFixedTabOption
import com.shopl.sdg_common.foundation.SDGColor

/** 두 탭의 조합 예제입니다. selectedTab은 0 또는 1이며 상태 보존 정책은 호출 화면이 결정합니다. */
@Composable
fun TabsExample(
    firstLabel: String,
    secondLabel: String,
    selectedTab: Int,
    onSelectTab: (Int) -> Unit,
    firstContent: @Composable () -> Unit,
    secondContent: @Composable () -> Unit,
) {
    Column {
        SDGFixedTab(
            option = SDGFixedTabOption.TwoOption(listOf(firstLabel, secondLabel)),
            selectedTab = selectedTab,
            onTabClick = onSelectTab,
            unselectedTabUnderLineColor = SDGColor.Neutral200,
            baselineDivider = SDGFixedTabBaselineDivider.Hidden,
        )
        when (selectedTab) {
            0 -> firstContent()
            1 -> secondContent()
        }
    }
}
