package com.shopl.sdg.component.tab.icon.preview

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import com.shopl.sdg.component.tab.icon.SDGIconTabIcon
import com.shopl.sdg.component.tab.icon.SDGIconTabIconSize
import com.shopl.sdg.component.tab.icon.SDGIconTabOption
import com.shopl.sdg.component.tab.icon.SDGTabItem
import com.shopl.sdg_common.foundation.SDGColor
import com.shopl.sdg_common.foundation.spacing.SDGSpacing.Spacing16
import com.shopl.sdg_common.foundation.spacing.SDGSpacing.Spacing8
import com.shopl.sdg_resource.R

internal class SDGIconTabPreviewParameterProvider :
    PreviewParameterProvider<SDGIconTabPreviewParameter> {

    override val values: Sequence<SDGIconTabPreviewParameter> = sequenceOf(
        기본_Three_옵션(),
        기본_Three_옵션_두번째_탭_선택(),
        기본_Three_옵션_세번째_탭_선택(),
        기본_Four_옵션(),
        기본_Four_옵션_두번째_탭_선택(),
        기본_Four_옵션_세번째_탭_선택(),
        기본_Four_옵션_네번째_탭_선택(),
        기본_Five_옵션(),
        기본_Five_옵션_두번째_탭_선택(),
        기본_Five_옵션_세번째_탭_선택(),
        기본_Five_옵션_네번째_탭_선택(),
        기본_Five_옵션_다섯번째_탭_선택(),
        긴_라벨_말줄임(),
        수치_미노출(),
        선택된_탭_수치_미노출(),
        여백_지정(),
    )

    private fun 기본_Three_옵션() = SDGIconTabPreviewParameter(
        option = SDGIconTabOption.ThreeOption(tabs = tabs(3)),
        selectedTab = 1,
    )

    private fun 기본_Three_옵션_두번째_탭_선택() = SDGIconTabPreviewParameter(
        option = SDGIconTabOption.ThreeOption(tabs = tabs(3)),
        selectedTab = 2,
    )

    private fun 기본_Three_옵션_세번째_탭_선택() = SDGIconTabPreviewParameter(
        option = SDGIconTabOption.ThreeOption(tabs = tabs(3)),
        selectedTab = 3,
    )

    private fun 기본_Four_옵션() = SDGIconTabPreviewParameter(
        option = SDGIconTabOption.FourOption(tabs = tabs(4)),
        selectedTab = 1,
    )

    private fun 기본_Four_옵션_두번째_탭_선택() = SDGIconTabPreviewParameter(
        option = SDGIconTabOption.FourOption(tabs = tabs(4)),
        selectedTab = 2,
    )

    private fun 기본_Four_옵션_세번째_탭_선택() = SDGIconTabPreviewParameter(
        option = SDGIconTabOption.FourOption(tabs = tabs(4)),
        selectedTab = 3,
    )

    private fun 기본_Four_옵션_네번째_탭_선택() = SDGIconTabPreviewParameter(
        option = SDGIconTabOption.FourOption(tabs = tabs(4)),
        selectedTab = 4,
    )

    private fun 기본_Five_옵션() = SDGIconTabPreviewParameter(
        option = SDGIconTabOption.FiveOption(tabs = tabs(5)),
        selectedTab = 1,
    )

    private fun 기본_Five_옵션_두번째_탭_선택() = SDGIconTabPreviewParameter(
        option = SDGIconTabOption.FiveOption(tabs = tabs(5)),
        selectedTab = 2,
    )

    private fun 기본_Five_옵션_세번째_탭_선택() = SDGIconTabPreviewParameter(
        option = SDGIconTabOption.FiveOption(tabs = tabs(5)),
        selectedTab = 3,
    )

    private fun 기본_Five_옵션_네번째_탭_선택() = SDGIconTabPreviewParameter(
        option = SDGIconTabOption.FiveOption(tabs = tabs(5)),
        selectedTab = 4,
    )

    private fun 기본_Five_옵션_다섯번째_탭_선택() = SDGIconTabPreviewParameter(
        option = SDGIconTabOption.FiveOption(tabs = tabs(5)),
        selectedTab = 5,
    )

    private fun 긴_라벨_말줄임() = SDGIconTabPreviewParameter(
        option = SDGIconTabOption.FiveOption(
            tabs = tabs(5).map { it.copy(label = "긴 라벨은 두 줄까지 표시하고 이후 말줄임 처리합니다. 추가 설명입니다.") },
        ),
        selectedTab = 1,
    )

    private fun 수치_미노출() = SDGIconTabPreviewParameter(
        option = SDGIconTabOption.ThreeOption(tabs = tabs(3).map { it.copy(showCount = false) }),
        selectedTab = 1,
    )

    private fun 선택된_탭_수치_미노출() = SDGIconTabPreviewParameter(
        option = SDGIconTabOption.ThreeOption(
            tabs = tabs(3).mapIndexed { index, tab -> tab.copy(showCount = index != 1) },
        ),
        selectedTab = 2,
    )

    private fun 여백_지정() = SDGIconTabPreviewParameter(
        option = SDGIconTabOption.ThreeOption(tabs = tabs(3)),
        selectedTab = 1,
        paddingValues = PaddingValues(horizontal = Spacing16, vertical = Spacing8),
    )

    private fun tabs(optionCount: Int): List<SDGTabItem> =
        List(optionCount) { index ->
            SDGTabItem(
                label = "Label ${index + 1}",
                count = "999+",
                showCount = true,
                iconTabIc = SDGIconTabIcon(
                    icon = R.drawable.ic_common_list,
                    tint = SDGColor.Neutral500,
                    size = SDGIconTabIconSize.entries[index % SDGIconTabIconSize.entries.size],
                ),
            )
        }
}

internal data class SDGIconTabPreviewParameter(
    val option: SDGIconTabOption,
    val selectedTab: Int,
    val paddingValues: PaddingValues = PaddingValues(0.dp),
)
