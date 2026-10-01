package com.shopl.sdg.component.tab.box.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.shopl.sdg.component.tab.box.SDGBoxTabItem
import com.shopl.sdg.component.tab.box.SDGBoxTabOption
import com.shopl.sdg.component.tab.box.SDGBoxTabStyle

internal class SDGBoxTabPreviewParameterProvider :
    PreviewParameterProvider<SDGBoxTabPreviewParameter> {

    override val values: Sequence<SDGBoxTabPreviewParameter> = sequenceOf(
        기본_Two_Solid_첫번째_탭_선택(),
        기본_Two_Solid_두번째_탭_선택(),
        기본_Two_Line_첫번째_탭_선택(),
        기본_Two_Line_두번째_탭_선택(),
        기본_Three_Solid_첫번째_탭_선택(),
        기본_Three_Solid_두번째_탭_선택(),
        기본_Three_Solid_세번째_탭_선택(),
        기본_Three_Line_첫번째_탭_선택(),
        기본_Three_Line_두번째_탭_선택(),
        기본_Three_Line_세번째_탭_선택(),
        이차_깊이_미노출(),
        단일_선택_Count_미노출(),
        긴_선택값_말줄임과_추가_선택_수량(),
    )

    private fun 기본_Two_Solid_첫번째_탭_선택() = twoOption(0, SDGBoxTabStyle.Solid)
    private fun 기본_Two_Solid_두번째_탭_선택() = twoOption(1, SDGBoxTabStyle.Solid)
    private fun 기본_Two_Line_첫번째_탭_선택() = twoOption(0, SDGBoxTabStyle.Line)
    private fun 기본_Two_Line_두번째_탭_선택() = twoOption(1, SDGBoxTabStyle.Line)
    private fun 기본_Three_Solid_첫번째_탭_선택() = threeOption(0, SDGBoxTabStyle.Solid)
    private fun 기본_Three_Solid_두번째_탭_선택() = threeOption(1, SDGBoxTabStyle.Solid)
    private fun 기본_Three_Solid_세번째_탭_선택() = threeOption(2, SDGBoxTabStyle.Solid)
    private fun 기본_Three_Line_첫번째_탭_선택() = threeOption(0, SDGBoxTabStyle.Line)
    private fun 기본_Three_Line_두번째_탭_선택() = threeOption(1, SDGBoxTabStyle.Line)
    private fun 기본_Three_Line_세번째_탭_선택() = threeOption(2, SDGBoxTabStyle.Line)

    private fun 이차_깊이_미노출() = twoOption(
        selectedTab = 0,
        style = SDGBoxTabStyle.Solid,
        selectedTwoDepth = SDGBoxTabItem.ShowTwoDepth.False,
        unselectedTwoDepth = SDGBoxTabItem.ShowTwoDepth.False,
    )

    private fun 단일_선택_Count_미노출() = twoOption(
        selectedTab = 1,
        style = SDGBoxTabStyle.Line,
        selectedTwoDepth = SDGBoxTabItem.ShowTwoDepth.True("선택값", "1"),
    )

    private fun 긴_선택값_말줄임과_추가_선택_수량() = threeOption(
        selectedTab = 2,
        style = SDGBoxTabStyle.Solid,
        selectedTwoDepth = SDGBoxTabItem.ShowTwoDepth.True("매우 긴 선택값은 한 줄에서 말줄임 처리됩니다", "12"),
    )

    private fun twoOption(
        selectedTab: Int,
        style: SDGBoxTabStyle,
        selectedTwoDepth: SDGBoxTabItem.ShowTwoDepth = SDGBoxTabItem.ShowTwoDepth.True("선택값", "3"),
        unselectedTwoDepth: SDGBoxTabItem.ShowTwoDepth = SDGBoxTabItem.ShowTwoDepth.True("-", null),
    ) = SDGBoxTabPreviewParameter(
        option = SDGBoxTabOption.TwoOption(tabs(2, selectedTab, selectedTwoDepth, unselectedTwoDepth)),
        style = style,
        selectedTab = selectedTab,
    )

    private fun threeOption(
        selectedTab: Int,
        style: SDGBoxTabStyle,
        selectedTwoDepth: SDGBoxTabItem.ShowTwoDepth = SDGBoxTabItem.ShowTwoDepth.True("선택값", "3"),
        unselectedTwoDepth: SDGBoxTabItem.ShowTwoDepth = SDGBoxTabItem.ShowTwoDepth.True("텍스트", null),
    ) = SDGBoxTabPreviewParameter(
        option = SDGBoxTabOption.ThreeOption(tabs(3, selectedTab, selectedTwoDepth, unselectedTwoDepth)),
        style = style,
        selectedTab = selectedTab,
    )

    private fun tabs(
        count: Int,
        selectedTab: Int,
        selectedTwoDepth: SDGBoxTabItem.ShowTwoDepth,
        unselectedTwoDepth: SDGBoxTabItem.ShowTwoDepth,
    ): List<SDGBoxTabItem> = List(count) { index ->
        SDGBoxTabItem(
            label = "Label ${index + 1}",
            showTwoDepth = if (index == selectedTab) selectedTwoDepth else unselectedTwoDepth,
        )
    }
}

internal data class SDGBoxTabPreviewParameter(
    val option: SDGBoxTabOption,
    val style: SDGBoxTabStyle,
    val selectedTab: Int,
)
