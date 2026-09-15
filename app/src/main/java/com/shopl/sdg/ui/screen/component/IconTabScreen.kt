package com.shopl.sdg.ui.screen.component

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
import com.shopl.sdg.component.tab.icon.SDGIconTab
import com.shopl.sdg.component.tab.icon.SDGIconTabIcon
import com.shopl.sdg.component.tab.icon.SDGIconTabIconSize
import com.shopl.sdg.component.tab.icon.SDGIconTabOption
import com.shopl.sdg.component.tab.icon.SDGTabItem
import com.shopl.sdg.scene.ComponentScene
import com.shopl.sdg.ui.base.SDGSampleBaseScaffold
import com.shopl.sdg.ui.theme.ShoplDesignGuideTheme
import com.shopl.sdg_common.foundation.SDGColor
import com.shopl.sdg_common.foundation.spacing.SDGSpacing
import com.shopl.sdg_common.foundation.typography.SDGTypography
import com.shopl.sdg_common.ui.components.SDGText
import com.shopl.sdg_resource.R

/**
 * SDG Sample App - Component - Icon Tab
 *
 * @see <a href="https://www.figma.com/design/SsBipIZV6kO3kUo0ekFyOR/Icon-Tab?node-id=1-63&m=dev">Figma</a>
 */
@Composable
internal fun IconTabScreen(
    onClickBack: () -> Unit,
    onClickMenu: () -> Unit,
) {
    SDGSampleBaseScaffold(
        name = ComponentScene.Tab.IconTab.displayLabel,
        description = "아이콘과 라벨, 수치를 조합하여 선택 상태에 따라 텍스트와 아이콘이 상호 전환되는 탭 컴포넌트",
        bodyContent = { IconTabScreenContent() },
        onClickBack = onClickBack,
        onClickMenu = onClickMenu,
    )
}

@Composable
private fun IconTabScreenContent() {
    val tabs = SDGIconTabIconSize.entries.mapIndexed { index, size ->
        SDGTabItem(
            label = "Label ${index + 1}",
            count = "${(index + 1) * 12}",
            showCount = true,
            iconTabIc = SDGIconTabIcon(
                resId = R.drawable.ic_common_list,
                size = size,
                tint = SDGColor.Neutral500,
            ),
        )
    }
    val fourTabs = tabs + tabs.last().copy(label = "Label 4", count = "48")
    val fiveTabs = fourTabs + tabs.last().copy(label = "Label 5", count = "999+")

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = SDGSpacing.Spacing16, vertical = SDGSpacing.Spacing24),
        verticalArrangement = Arrangement.spacedBy(SDGSpacing.Spacing24),
    ) {
        IconTabSection(title = "3 Option", option = SDGIconTabOption.ThreeOption(tabs = tabs))
        IconTabSection(title = "4 Option", option = SDGIconTabOption.FourOption(tabs = fourTabs))
        IconTabSection(title = "5 Option", option = SDGIconTabOption.FiveOption(tabs = fiveTabs))
        IconTabSection(
            title = "두 줄 라벨 / 말줄임",
            option = SDGIconTabOption.FiveOption(
                tabs = fiveTabs.map { it.copy(label = "긴 라벨은 두 줄까지 표시하고 이후 말줄임 처리합니다. 추가 설명입니다.") },
            ),
        )
        IconTabSection(
            title = "Show Count: False",
            option = SDGIconTabOption.ThreeOption(tabs = tabs.map { it.copy(showCount = false) }),
        )
        IconTabSection(
            title = "Tab 2 · Show Count: False",
            option = SDGIconTabOption.ThreeOption(
                tabs = tabs.mapIndexed { index, tab -> tab.copy(showCount = index != 1) },
            ),
        )
    }
}

@Composable
private fun IconTabSection(
    title: String,
    option: SDGIconTabOption,
) {
    var selectedTab by rememberSaveable { mutableIntStateOf(0) }

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(SDGSpacing.Spacing8),
    ) {
        SDGText(
            text = title,
            textColor = SDGColor.Neutral700,
            typography = SDGTypography.Body1SB,
        )
        SDGIconTab(
            option = option,
            selectedTab = selectedTab,
            onTabClick = { selectedTab = it },
        )
    }
}

@Preview
@Composable
private fun PreviewIconTabScreen() {
    ShoplDesignGuideTheme {
        IconTabScreen(onClickBack = {}, onClickMenu = {})
    }
}
