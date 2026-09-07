package com.shopl.sdg.template.check_option_label.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.shopl.sdg.template.check_option_label.SDGCheckOptionLabelSelectedType
import com.shopl.sdg.template.check_option_label.SDGCheckOptionLabelSize
import com.shopl.sdg.template.check_option_label.SDGCheckOptionLabelState

internal class SDGCheckOptionLabelPreviewParameterProvider :
    PreviewParameterProvider<SDGCheckOptionLabelPreviewParams> {

    override val values: Sequence<SDGCheckOptionLabelPreviewParams> = sequenceOf(
        기본_상태(),
        선택_상태_NORMAL(),
        선택_상태_COLOR(),
        선택_상태_NEUTRAL(),
        비활성_상태(),
        SMALL_긴_라벨_선택_상태(),
    )

    private fun 기본_상태() = SDGCheckOptionLabelPreviewParams(
        state = SDGCheckOptionLabelState.Default,
        selectType = SDGCheckOptionLabelSelectedType.Normal,
        size = SDGCheckOptionLabelSize.MEDIUM,
        label = "옵션명",
    )

    private fun 선택_상태_NORMAL() = SDGCheckOptionLabelPreviewParams(
        state = SDGCheckOptionLabelState.Selected,
        selectType = SDGCheckOptionLabelSelectedType.Normal,
        size = SDGCheckOptionLabelSize.MEDIUM,
        label = "옵션명",
    )

    private fun 선택_상태_COLOR() = SDGCheckOptionLabelPreviewParams(
        state = SDGCheckOptionLabelState.Selected,
        selectType = SDGCheckOptionLabelSelectedType.Color,
        size = SDGCheckOptionLabelSize.MEDIUM,
        label = "옵션명",
    )

    private fun 선택_상태_NEUTRAL() = SDGCheckOptionLabelPreviewParams(
        state = SDGCheckOptionLabelState.Selected,
        selectType = SDGCheckOptionLabelSelectedType.Neutral,
        size = SDGCheckOptionLabelSize.MEDIUM,
        label = "옵션명",
    )

    private fun 비활성_상태() = SDGCheckOptionLabelPreviewParams(
        state = SDGCheckOptionLabelState.Disabled,
        selectType = SDGCheckOptionLabelSelectedType.Normal,
        size = SDGCheckOptionLabelSize.MEDIUM,
        label = "옵션명",
    )

    private fun SMALL_긴_라벨_선택_상태() = SDGCheckOptionLabelPreviewParams(
        state = SDGCheckOptionLabelState.Selected,
        selectType = SDGCheckOptionLabelSelectedType.Normal,
        size = SDGCheckOptionLabelSize.SMALL,
        label = "옵션명이 길어지면 아이콘과 상단 정렬되고 사용 가능한 영역에서 줄바꿈으로 전체 내용을 노출합니다.",
    )
}
