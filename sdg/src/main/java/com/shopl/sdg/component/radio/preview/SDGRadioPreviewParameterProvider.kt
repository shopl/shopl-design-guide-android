package com.shopl.sdg.component.radio.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.shopl.sdg.component.radio.model.SDGRadioSelectedBackgroundColor
import com.shopl.sdg.component.radio.model.SDGRadioSize
import com.shopl.sdg.component.radio.model.SDGRadioState

internal class SDGRadioPreviewParameterProvider :
    PreviewParameterProvider<SDGRadioPreviewParams> {

    override val values: Sequence<SDGRadioPreviewParams> = sequenceOf(
        기본_상태_MEDIUM(),
        선택된_상태_NORMAL_MEDIUM(),
        선택된_상태_NEUTRAL_MEDIUM(),
        비활성_상태_MEDIUM(),
        기본_상태_LARGE(),
        선택된_상태_NORMAL_LARGE(),
        선택된_상태_NEUTRAL_LARGE(),
        비활성_상태_LARGE(),
    )

    private fun 기본_상태_MEDIUM() = SDGRadioPreviewParams(
        state = SDGRadioState.DEFAULT,
        selectedColor = SDGRadioSelectedBackgroundColor.NEUTRAL,
        size = SDGRadioSize.MEDIUM,
    )

    private fun 선택된_상태_NORMAL_MEDIUM() = SDGRadioPreviewParams(
        state = SDGRadioState.SELECTED,
        selectedColor = SDGRadioSelectedBackgroundColor.NORMAL,
        size = SDGRadioSize.MEDIUM,
    )

    private fun 선택된_상태_NEUTRAL_MEDIUM() = SDGRadioPreviewParams(
        state = SDGRadioState.SELECTED,
        selectedColor = SDGRadioSelectedBackgroundColor.NEUTRAL,
        size = SDGRadioSize.MEDIUM,
    )

    private fun 비활성_상태_MEDIUM() = SDGRadioPreviewParams(
        state = SDGRadioState.DISABLED,
        selectedColor = SDGRadioSelectedBackgroundColor.NEUTRAL,
        size = SDGRadioSize.MEDIUM,
    )

    private fun 기본_상태_LARGE() = SDGRadioPreviewParams(
        state = SDGRadioState.DEFAULT,
        selectedColor = SDGRadioSelectedBackgroundColor.NEUTRAL,
        size = SDGRadioSize.LARGE,
    )

    private fun 선택된_상태_NORMAL_LARGE() = SDGRadioPreviewParams(
        state = SDGRadioState.SELECTED,
        selectedColor = SDGRadioSelectedBackgroundColor.NORMAL,
        size = SDGRadioSize.LARGE,
    )

    private fun 선택된_상태_NEUTRAL_LARGE() = SDGRadioPreviewParams(
        state = SDGRadioState.SELECTED,
        selectedColor = SDGRadioSelectedBackgroundColor.NEUTRAL,
        size = SDGRadioSize.LARGE,
    )

    private fun 비활성_상태_LARGE() = SDGRadioPreviewParams(
        state = SDGRadioState.DISABLED,
        selectedColor = SDGRadioSelectedBackgroundColor.NEUTRAL,
        size = SDGRadioSize.LARGE,
    )
}
