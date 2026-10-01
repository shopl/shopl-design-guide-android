package com.shopl.sdg.ui.screen.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.shopl.sdg.component.tab.box.SDGBoxTab
import com.shopl.sdg.component.tab.box.SDGBoxTabItem
import com.shopl.sdg.component.tab.box.SDGBoxTabOption
import com.shopl.sdg.component.tab.box.SDGBoxTabStyle
import com.shopl.sdg.scene.ComponentScene
import com.shopl.sdg.ui.base.SDGSampleBaseScaffold
import com.shopl.sdg.ui.theme.ShoplDesignGuideTheme
import com.shopl.sdg_common.foundation.SDGColor
import com.shopl.sdg_common.foundation.spacing.SDGSpacing
import com.shopl.sdg_common.foundation.typography.SDGTypography
import com.shopl.sdg_common.ui.components.SDGText

/**
 * SDG Sample App - Component - Box Tab
 *
 * @see <a href="https://www.figma.com/design/qWVshatQ9eqoIn4fdEZqWy/SDG?node-id=20902-19365&m=dev">Figma</a>
 */
@Composable
internal fun BoxTabScreen(
    onClickBack: () -> Unit,
    onClickMenu: () -> Unit,
) {
    SDGSampleBaseScaffold(
        name = ComponentScene.Tab.BoxTab.displayLabel,
        description = "라벨과 세부 선택 값을 조합하여 화면 조건을 유연하게 전환하는 박스형 탭 컴포넌트",
        bodyContent = { BoxTabScreenContent() },
        onClickBack = onClickBack,
        onClickMenu = onClickMenu,
    )
}

@Composable
private fun BoxTabScreenContent() {
    val tabs = listOf(
        SDGBoxTabItem("근무지", SDGBoxTabItem.ShowTwoDepth.True("서울 본사", "1")),
        SDGBoxTabItem("구성원", SDGBoxTabItem.ShowTwoDepth.True("홍길동", "2")),
        SDGBoxTabItem("상태", SDGBoxTabItem.ShowTwoDepth.True("진행 중", "12")),
    )
    val twoTabs = tabs.take(2)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(SDGColor.Neutral50)
            .padding(horizontal = SDGSpacing.Spacing16, vertical = SDGSpacing.Spacing24),
        verticalArrangement = Arrangement.spacedBy(SDGSpacing.Spacing24),
    ) {
        BoxTabSection(
            title = "2 Option · Solid",
            option = SDGBoxTabOption.TwoOption(twoTabs),
            style = SDGBoxTabStyle.Solid,
        )
        BoxTabSection(
            title = "2 Option · Line",
            option = SDGBoxTabOption.TwoOption(twoTabs),
            style = SDGBoxTabStyle.Line,
            initialSelectedTab = 1,
        )
        BoxTabSection(
            title = "3 Option · Solid",
            option = SDGBoxTabOption.ThreeOption(tabs),
            style = SDGBoxTabStyle.Solid,
            initialSelectedTab = 1,
        )
        BoxTabSection(
            title = "3 Option · Line",
            option = SDGBoxTabOption.ThreeOption(tabs),
            style = SDGBoxTabStyle.Line,
            initialSelectedTab = 2,
        )
        BoxTabSection(
            title = "2Depth 미노출",
            option = SDGBoxTabOption.TwoOption(
                twoTabs.map { it.copy(showTwoDepth = SDGBoxTabItem.ShowTwoDepth.False) },
            ),
            style = SDGBoxTabStyle.Solid,
        )
        BoxTabSection(
            title = "2Depth 표시 혼합",
            option = SDGBoxTabOption.ThreeOption(
                tabs.mapIndexed { index, tab ->
                    if (index == 1) tab.copy(showTwoDepth = SDGBoxTabItem.ShowTwoDepth.False) else tab
                },
            ),
            style = SDGBoxTabStyle.Line,
            initialSelectedTab = 1,
        )
        BoxTabSection(
            title = "긴 라벨 · 선택값 · Count 유지",
            option = SDGBoxTabOption.ThreeOption(
                tabs.map { tab ->
                    tab.copy(
                        label = "여러 줄에 걸쳐 표시되는 긴 탭 라벨",
                        showTwoDepth = SDGBoxTabItem.ShowTwoDepth.True("한 줄에서 말줄임 처리되는 매우 긴 선택값", "12"),
                    )
                },
            ),
            style = SDGBoxTabStyle.Solid,
        )
        BoxTabSection(
            title = "Count 없음 · 0 · 1 → 미노출",
            option = SDGBoxTabOption.ThreeOption(
                listOf(null, "0", "1").mapIndexed { index, count ->
                    tabs[index].copy(showTwoDepth = SDGBoxTabItem.ShowTwoDepth.True("선택값", count))
                },
            ),
            style = SDGBoxTabStyle.Line,
        )
    }
}

@Composable
private fun BoxTabSection(
    title: String,
    option: SDGBoxTabOption,
    style: SDGBoxTabStyle,
    initialSelectedTab: Int = 0,
) {
    var selectedTab by rememberSaveable { mutableIntStateOf(initialSelectedTab) }

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(SDGSpacing.Spacing8),
    ) {
        SDGText(
            text = title,
            textColor = SDGColor.Neutral700,
            typography = SDGTypography.Body1SB,
        )
        SDGBoxTab(
            option = option,
            style = style,
            selectedTab = selectedTab,
            onTabClick = { selectedTab = it },
        )
    }
}

@Preview
@Composable
private fun PreviewBoxTabScreen() {
    ShoplDesignGuideTheme {
        BoxTabScreen(onClickBack = {}, onClickMenu = {})
    }
}
