package com.shopl.sdg.ui.screen.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.shopl.sdg.component.tab.fixed.SDGFixedTab
import com.shopl.sdg.component.tab.fixed.SDGFixedTabBaselineDivider
import com.shopl.sdg.component.tab.fixed.SDGFixedTabOption
import com.shopl.sdg.scene.ComponentScene
import com.shopl.sdg.ui.base.SDGSampleBaseScaffold
import com.shopl.sdg.ui.theme.ShoplDesignGuideTheme
import com.shopl.sdg_common.foundation.SDGColor
import com.shopl.sdg_common.foundation.spacing.SDGSpacing
import com.shopl.sdg_common.foundation.typography.SDGTypography
import com.shopl.sdg_common.ui.components.SDGText

/**
 * SDG Sample App - Component - Fixed Tab
 *
 * @see <a href="https://www.figma.com/design/qWVshatQ9eqoIn4fdEZqWy/SDG?node-id=20900-15091&m=dev">Figma</a>
 */
@Composable
internal fun FixedTabScreen(
    onClickBack: () -> Unit,
    onClickMenu: () -> Unit,
) {
    SDGSampleBaseScaffold(
        name = ComponentScene.Tab.FixedTab.displayLabel,
        description = "고정된 영역 내에서 3개 이하로 분할되며, 페이지 내 유사한 콘텐츠를 그룹화하여 섹션 간 이동 시 사용하는 탭 컴포넌트",
        bodyContent = { FixedTabScreenContent() },
        onClickBack = onClickBack,
        onClickMenu = onClickMenu,
    )
}

@Composable
private fun FixedTabScreenContent() {
    val twoOptions = SDGFixedTabOption.TwoOption(listOf("전체", "진행 중"))
    val threeOptions = SDGFixedTabOption.ThreeOption(listOf("전체", "진행 중", "완료"))

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = SDGSpacing.Spacing16, vertical = SDGSpacing.Spacing24),
        verticalArrangement = Arrangement.spacedBy(SDGSpacing.Spacing24),
    ) {
        FixedTabSection(
            title = "2 Option",
            option = twoOptions,
            baselineDivider = SDGFixedTabBaselineDivider.Hidden,
        )
        FixedTabSection(
            title = "3 Option",
            option = threeOptions,
            baselineDivider = SDGFixedTabBaselineDivider.Hidden,
            initialSelectedTab = 1,
        )
        FixedTabSection(
            title = "Baseline Divider 표시 · 좌우 20dp",
            option = twoOptions,
            baselineDivider = SDGFixedTabBaselineDivider.Visible(horizontalPadding = SDGSpacing.Spacing20),
        )
        FixedTabSection(
            title = "Baseline Divider 숨김 · 좌우 여백 20dp",
            option = twoOptions,
            baselineDivider = SDGFixedTabBaselineDivider.Hidden,
            horizontalPadding = SDGSpacing.Spacing20,
        )
        FixedTabSection(
            title = "미선택 밑줄 · Divider 색상 변경",
            option = threeOptions,
            baselineDivider = SDGFixedTabBaselineDivider.Visible(horizontalPadding = SDGSpacing.Spacing20),
            unselectedTabUnderLineColor = SDGColor.Primary300,
        )
        FixedTabSection(
            title = "긴 라벨 · 한 줄 말줄임",
            option = SDGFixedTabOption.ThreeOption(
                listOf("전체", "가용 영역을 초과하는 매우 긴 탭 라벨", "완료"),
            ),
            baselineDivider = SDGFixedTabBaselineDivider.Hidden,
            initialSelectedTab = 1,
        )
    }
}

@Composable
private fun FixedTabSection(
    title: String,
    option: SDGFixedTabOption,
    baselineDivider: SDGFixedTabBaselineDivider,
    horizontalPadding: Dp = 0.dp,
    unselectedTabUnderLineColor: Color = SDGColor.Neutral200,
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
        Box(modifier = Modifier.fillMaxWidth().padding(horizontal = horizontalPadding)) {
            SDGFixedTab(
                option = option,
                selectedTab = selectedTab,
                onTabClick = { selectedTab = it },
                unselectedTabUnderLineColor = unselectedTabUnderLineColor,
                baselineDivider = baselineDivider,
            )
        }
    }
}

@Preview(widthDp = 360)
@Preview(widthDp = 412)
@Composable
private fun PreviewFixedTabScreen() {
    ShoplDesignGuideTheme {
        FixedTabScreen(onClickBack = {}, onClickMenu = {})
    }
}
