package com.shopl.sdg.template.radio_label.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.shopl.sdg.template.radio_label.SDGRadioLabelSelectType
import com.shopl.sdg.template.radio_label.SDGRadioLabelState

internal class SDGRadioLabelPreviewParameterProvider :
    PreviewParameterProvider<SDGRadioLabelPreviewParams> {

    override val values: Sequence<SDGRadioLabelPreviewParams> = sequenceOf(
        기본_상태(),
        선택_상태_NORMAL(),
        선택_상태_COLOR(),
        선택_상태_NEUTRAL(),
        비활성_상태(),
        긴_라벨_선택_상태(),
    )

    private fun 기본_상태() = SDGRadioLabelPreviewParams(
        state = SDGRadioLabelState.Default,
        selectType = SDGRadioLabelSelectType.Normal,
        label = "옵션명",
    )

    private fun 선택_상태_NORMAL() = SDGRadioLabelPreviewParams(
        state = SDGRadioLabelState.Selected,
        selectType = SDGRadioLabelSelectType.Normal,
        label = "옵션명",
    )

    private fun 선택_상태_COLOR() = SDGRadioLabelPreviewParams(
        state = SDGRadioLabelState.Selected,
        selectType = SDGRadioLabelSelectType.Color,
        label = "옵션명",
    )

    private fun 선택_상태_NEUTRAL() = SDGRadioLabelPreviewParams(
        state = SDGRadioLabelState.Selected,
        selectType = SDGRadioLabelSelectType.Neutral,
        label = "옵션명",
    )

    private fun 비활성_상태() = SDGRadioLabelPreviewParams(
        state = SDGRadioLabelState.Disabled,
        selectType = SDGRadioLabelSelectType.Normal,
        label = "옵션명",
    )

    private fun 긴_라벨_선택_상태() = SDGRadioLabelPreviewParams(
        state = SDGRadioLabelState.Selected,
        selectType = SDGRadioLabelSelectType.Normal,
        label = "옵션명이 길어지면 아이콘과 상단 정렬되고 사용 가능한 영역에서 줄바꿈으로 전체 내용을 노출합니다.",
    )
}
