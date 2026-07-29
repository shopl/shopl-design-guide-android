package com.shopl.sdg.template.checkbox_label.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.shopl.sdg.template.checkbox_label.SDGCheckboxLabelSelectType
import com.shopl.sdg.template.checkbox_label.SDGCheckboxLabelState

internal class SDGCheckboxLabelPreviewParameterProvider :
    PreviewParameterProvider<SDGCheckboxLabelPreviewParams> {

    override val values = sequenceOf(
        기본_상태(),
        선택_상태_NORMAL(),
        선택_상태_COLOR(),
        선택_상태_NEUTRAL(),
        비활성_상태(),
        긴_라벨_선택_상태(),
    )

    private fun 기본_상태() = SDGCheckboxLabelPreviewParams(
        state = SDGCheckboxLabelState.Default,
        selectType = SDGCheckboxLabelSelectType.Normal,
        label = "옵션명",
    )

    private fun 선택_상태_NORMAL() = SDGCheckboxLabelPreviewParams(
        state = SDGCheckboxLabelState.Selected,
        selectType = SDGCheckboxLabelSelectType.Normal,
        label = "옵션명",
    )

    private fun 선택_상태_COLOR() = SDGCheckboxLabelPreviewParams(
        state = SDGCheckboxLabelState.Selected,
        selectType = SDGCheckboxLabelSelectType.Color,
        label = "옵션명",
    )

    private fun 선택_상태_NEUTRAL() = SDGCheckboxLabelPreviewParams(
        state = SDGCheckboxLabelState.Selected,
        selectType = SDGCheckboxLabelSelectType.Neutral,
        label = "옵션명",
    )

    private fun 비활성_상태() = SDGCheckboxLabelPreviewParams(
        state = SDGCheckboxLabelState.Disabled,
        selectType = SDGCheckboxLabelSelectType.Normal,
        label = "옵션명",
    )

    private fun 긴_라벨_선택_상태() = SDGCheckboxLabelPreviewParams(
        state = SDGCheckboxLabelState.Selected,
        selectType = SDGCheckboxLabelSelectType.Normal,
        label = "여러 줄로 표시되는 긴 체크박스 라벨입니다.",
    )
}
