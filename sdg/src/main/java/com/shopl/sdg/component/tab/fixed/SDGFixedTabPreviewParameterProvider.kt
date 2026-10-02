package com.shopl.sdg.component.tab.fixed

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.shopl.sdg_common.foundation.SDGColor
import com.shopl.sdg_common.foundation.spacing.SDGSpacing.Spacing20

internal class SDGFixedTabPreviewParameterProvider :
    PreviewParameterProvider<SDGFixedTabPreviewParameter> {

    override val values: Sequence<SDGFixedTabPreviewParameter> = sequenceOf(
        좌우여백_기준선_숨김_두_탭(),
        기본_두_탭(),
        두_탭_두번째_선택(),
        기본_세_탭(),
        세_탭_두번째_선택(),
        세_탭_세번째_선택(),
        말줄임_두_탭(),
        말줄임_세_탭_세번째_선택(),
        기준선_표시_두_탭(),
        기준선_표시_두_탭_두번째_선택_색상변경(),
    )

    private fun 좌우여백_기준선_숨김_두_탭() = SDGFixedTabPreviewParameter(
        option = SDGFixedTabOption.TwoOption(listOf("Label", "Label")),
        onTabClick = {},
        baselineDivider = SDGFixedTabBaselineDivider.Hidden,
        horizontalPadding = Spacing20,
    )

    private fun 기본_두_탭() = SDGFixedTabPreviewParameter(
        option = SDGFixedTabOption.TwoOption(listOf("Label", "Label")),
        onTabClick = {},
        baselineDivider = SDGFixedTabBaselineDivider.Hidden,
    )

    private fun 두_탭_두번째_선택() = SDGFixedTabPreviewParameter(
        option = SDGFixedTabOption.TwoOption(listOf("Label", "Label")),
        onTabClick = {},
        baselineDivider = SDGFixedTabBaselineDivider.Hidden,
        selectedTab = 1,
    )

    private fun 기본_세_탭() = SDGFixedTabPreviewParameter(
        option = SDGFixedTabOption.ThreeOption(listOf("Label", "Label", "Label")),
        onTabClick = {},
        baselineDivider = SDGFixedTabBaselineDivider.Hidden,
    )

    private fun 세_탭_두번째_선택() = SDGFixedTabPreviewParameter(
        option = SDGFixedTabOption.ThreeOption(listOf("Label", "Label", "Label")),
        onTabClick = {},
        baselineDivider = SDGFixedTabBaselineDivider.Hidden,
        selectedTab = 1,
    )

    private fun 세_탭_세번째_선택() = SDGFixedTabPreviewParameter(
        option = SDGFixedTabOption.ThreeOption(listOf("Label", "Label", "Label")),
        onTabClick = {},
        baselineDivider = SDGFixedTabBaselineDivider.Hidden,
        selectedTab = 2,
    )

    private fun 말줄임_두_탭() = SDGFixedTabPreviewParameter(
        option = SDGFixedTabOption.TwoOption(listOf("한 줄로 말줄임 처리되는 매우 긴 탭 라벨", "Label")),
        onTabClick = {},
        baselineDivider = SDGFixedTabBaselineDivider.Hidden,
        width = 335.dp,
    )

    private fun 말줄임_세_탭_세번째_선택() = SDGFixedTabPreviewParameter(
        option = SDGFixedTabOption.ThreeOption(listOf("Label", "Label", "선택된 매우 긴 탭 라벨도 한 줄로 말줄임됩니다")),
        onTabClick = {},
        baselineDivider = SDGFixedTabBaselineDivider.Hidden,
        selectedTab = 2,
        width = 335.dp,
    )

    private fun 기준선_표시_두_탭() = SDGFixedTabPreviewParameter(
        option = SDGFixedTabOption.TwoOption(listOf("Label", "Label")),
        onTabClick = {},
        width = 360.dp,
        baselineDivider = SDGFixedTabBaselineDivider.Visible(horizontalPadding = Spacing20),
    )

    private fun 기준선_표시_두_탭_두번째_선택_색상변경() = SDGFixedTabPreviewParameter(
        option = SDGFixedTabOption.TwoOption(listOf("Label", "Label")),
        onTabClick = {},
        selectedTab = 1,
        width = 412.dp,
        baselineDivider = SDGFixedTabBaselineDivider.Visible(horizontalPadding = Spacing20),
        unselectedTabUnderLineColor = SDGColor.Neutral100,
    )

}

internal data class SDGFixedTabPreviewParameter(
    val option: SDGFixedTabOption,
    val onTabClick: (Int) -> Unit,
    val selectedTab: Int = 0,
    val width: Dp = 375.dp,
    val baselineDivider: SDGFixedTabBaselineDivider,
    val horizontalPadding: Dp = 0.dp,
    val unselectedTabUnderLineColor: Color = SDGColor.Neutral200,
)
