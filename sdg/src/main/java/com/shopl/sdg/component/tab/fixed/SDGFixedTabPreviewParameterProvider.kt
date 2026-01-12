package com.shopl.sdg.component.tab.fixed

import androidx.compose.ui.tooling.preview.PreviewParameterProvider

internal class SDGFixedTabPreviewParameterProvider :
    PreviewParameterProvider<SDGFixedTabPreviewParameter> {

    override val values: Sequence<SDGFixedTabPreviewParameter> = sequenceOf(
        기본_Two_옵션(),
        기본_Two_옵션_두번째_탭_선택(),
        기본_Three_옵션(),
        기본_Three_옵션_두번째_탭_선택(),
        기본_Three_옵션_세번째_탭_선택(),
    )

    private fun 기본_Two_옵션(): SDGFixedTabPreviewParameter {
        return SDGFixedTabPreviewParameter(
            type = SDGFixedTabType.TwoOption("Label", "Label"),
            onTabClick = {},
            selectedTabIndex = 0
        )
    }

    private fun 기본_Two_옵션_두번째_탭_선택(): SDGFixedTabPreviewParameter {
        return SDGFixedTabPreviewParameter(
            type = SDGFixedTabType.TwoOption("Label", "Label"),
            onTabClick = {},
            selectedTabIndex = 1
        )
    }

    private fun 기본_Three_옵션(): SDGFixedTabPreviewParameter {
        return SDGFixedTabPreviewParameter(
            type = SDGFixedTabType.ThreeOption("Label", "Label", "Label"),
            onTabClick = {},
            selectedTabIndex = 0
        )
    }

    private fun 기본_Three_옵션_두번째_탭_선택(): SDGFixedTabPreviewParameter {
        return SDGFixedTabPreviewParameter(
            type = SDGFixedTabType.ThreeOption("Label", "Label", "Label"),
            onTabClick = {},
            selectedTabIndex = 1
        )
    }

    private fun 기본_Three_옵션_세번째_탭_선택(): SDGFixedTabPreviewParameter {
        return SDGFixedTabPreviewParameter(
            type = SDGFixedTabType.ThreeOption("Label", "Label", "Label"),
            onTabClick = {},
            selectedTabIndex = 2
        )
    }

}

internal data class SDGFixedTabPreviewParameter(
    val type: SDGFixedTabType,
    val onTabClick: (Int) -> Unit,
    val selectedTabIndex: Int = 0,
)