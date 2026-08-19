package com.shopl.sdg.component.checkbox.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.shopl.sdg.component.checkbox.model.SDGCheckBoxSelectedColor
import com.shopl.sdg.component.checkbox.model.SDGCheckBoxSize
import com.shopl.sdg.component.checkbox.model.SDGCheckBoxState

internal class SDGCheckBoxPreviewParameterProvider :
    PreviewParameterProvider<SDGCheckBoxPreviewParams> {

    override val values: Sequence<SDGCheckBoxPreviewParams> = sequenceOf(
        기본_상태_MEDIUM(),
        선택된_상태_NORMAL_MEDIUM(),
        선택된_상태_NEUTRAL_MEDIUM(),
        비활성_상태_MEDIUM(),
        기본_상태_LARGE(),
        선택된_상태_NORMAL_LARGE(),
        선택된_상태_NEUTRAL_LARGE(),
        비활성_상태_LARGE(),
    )

    private fun 기본_상태_MEDIUM() = SDGCheckBoxPreviewParams(
        state = SDGCheckBoxState.Default,
        selectedColor = SDGCheckBoxSelectedColor.Neutral,
        size = SDGCheckBoxSize.Medium,
    )

    private fun 선택된_상태_NORMAL_MEDIUM() = SDGCheckBoxPreviewParams(
        state = SDGCheckBoxState.Selected,
        selectedColor = SDGCheckBoxSelectedColor.Normal,
        size = SDGCheckBoxSize.Medium,
    )

    private fun 선택된_상태_NEUTRAL_MEDIUM() = SDGCheckBoxPreviewParams(
        state = SDGCheckBoxState.Selected,
        selectedColor = SDGCheckBoxSelectedColor.Neutral,
        size = SDGCheckBoxSize.Medium,
    )

    private fun 비활성_상태_MEDIUM() = SDGCheckBoxPreviewParams(
        state = SDGCheckBoxState.Disabled,
        selectedColor = SDGCheckBoxSelectedColor.Neutral,
        size = SDGCheckBoxSize.Medium,
    )

    private fun 기본_상태_LARGE() = SDGCheckBoxPreviewParams(
        state = SDGCheckBoxState.Default,
        selectedColor = SDGCheckBoxSelectedColor.Neutral,
        size = SDGCheckBoxSize.Large,
    )

    private fun 선택된_상태_NORMAL_LARGE() = SDGCheckBoxPreviewParams(
        state = SDGCheckBoxState.Selected,
        selectedColor = SDGCheckBoxSelectedColor.Normal,
        size = SDGCheckBoxSize.Large,
    )

    private fun 선택된_상태_NEUTRAL_LARGE() = SDGCheckBoxPreviewParams(
        state = SDGCheckBoxState.Selected,
        selectedColor = SDGCheckBoxSelectedColor.Neutral,
        size = SDGCheckBoxSize.Large,
    )

    private fun 비활성_상태_LARGE() = SDGCheckBoxPreviewParams(
        state = SDGCheckBoxState.Disabled,
        selectedColor = SDGCheckBoxSelectedColor.Neutral,
        size = SDGCheckBoxSize.Large,
    )
}
