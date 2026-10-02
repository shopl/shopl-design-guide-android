package com.shopl.sdg.component.tab.fixed

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.shopl.sdg_common.foundation.SDGColor
import com.shopl.sdg_common.foundation.spacing.SDGSpacing.Spacing20

internal class SDGFixedTabPreviewParameterProvider :
    PreviewParameterProvider<SDGFixedTabPreviewParameter> {

    override val values: Sequence<SDGFixedTabPreviewParameter> = sequenceOf(
        SDGFixedTabPreviewParameter(
            option = SDGFixedTabOption.TwoOption(listOf("Label", "Label")),
            onTabClick = {},
            baselineDivider = SDGFixedTabBaselineDivider.Hidden,
            marginValues = PaddingValues(horizontal = Spacing20),
        ),
        기본_Two_옵션(),
        기본_Two_옵션_두번째_탭_선택(),
        기본_Three_옵션(),
        기본_Three_옵션_두번째_탭_선택(),
        기본_Three_옵션_세번째_탭_선택(),
        SDGFixedTabPreviewParameter(
            option = SDGFixedTabOption.TwoOption(listOf("한 줄로 말줄임 처리되는 매우 긴 탭 라벨", "Label")),
            onTabClick = {},
            width = 335.dp,
        ),
        SDGFixedTabPreviewParameter(
            option = SDGFixedTabOption.ThreeOption(listOf("Label", "Label", "선택된 매우 긴 탭 라벨도 한 줄로 말줄임됩니다")),
            onTabClick = {},
            selectedTab = 2,
            width = 335.dp,
        ),
        SDGFixedTabPreviewParameter(
            option = SDGFixedTabOption.TwoOption(listOf("Label", "Label")),
            onTabClick = {},
            width = 360.dp,
            baselineDivider = SDGFixedTabBaselineDivider.Visible(horizontalPadding = Spacing20),
        ),
        SDGFixedTabPreviewParameter(
            option = SDGFixedTabOption.TwoOption(listOf("Label", "Label")),
            onTabClick = {},
            selectedTab = 1,
            width = 412.dp,
            baselineDivider = SDGFixedTabBaselineDivider.Visible(horizontalPadding = Spacing20),
            unselectedTabUnderLineColor = SDGColor.Neutral100,
        ),
    )

    private fun 기본_Two_옵션(): SDGFixedTabPreviewParameter {
        return SDGFixedTabPreviewParameter(
            option = SDGFixedTabOption.TwoOption(listOf("Label", "Label")),
            onTabClick = {},
            selectedTab = 0
        )
    }

    private fun 기본_Two_옵션_두번째_탭_선택(): SDGFixedTabPreviewParameter {
        return SDGFixedTabPreviewParameter(
            option = SDGFixedTabOption.TwoOption(listOf("Label", "Label")),
            onTabClick = {},
            selectedTab = 1
        )
    }

    private fun 기본_Three_옵션(): SDGFixedTabPreviewParameter {
        return SDGFixedTabPreviewParameter(
            option = SDGFixedTabOption.ThreeOption(listOf("Label", "Label", "Label")),
            onTabClick = {},
            selectedTab = 0
        )
    }

    private fun 기본_Three_옵션_두번째_탭_선택(): SDGFixedTabPreviewParameter {
        return SDGFixedTabPreviewParameter(
            option = SDGFixedTabOption.ThreeOption(listOf("Label", "Label", "Label")),
            onTabClick = {},
            selectedTab = 1
        )
    }

    private fun 기본_Three_옵션_세번째_탭_선택(): SDGFixedTabPreviewParameter {
        return SDGFixedTabPreviewParameter(
            option = SDGFixedTabOption.ThreeOption(listOf("Label", "Label", "Label")),
            onTabClick = {},
            selectedTab = 2
        )
    }

}

internal data class SDGFixedTabPreviewParameter(
    val option: SDGFixedTabOption,
    val onTabClick: (Int) -> Unit,
    val selectedTab: Int = 0,
    val width: Dp = 375.dp,
    val baselineDivider: SDGFixedTabBaselineDivider = SDGFixedTabBaselineDivider.Hidden,
    val marginValues: PaddingValues = PaddingValues(),
    val unselectedTabUnderLineColor: Color = SDGColor.Neutral200,
)
