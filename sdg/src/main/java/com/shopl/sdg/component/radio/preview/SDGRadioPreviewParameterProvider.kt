package com.shopl.sdg.component.radio.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.shopl.sdg.component.radio.SDGRadioColor
import com.shopl.sdg.component.radio.SDGRadioSize

internal class SDGRadioPreviewParameterProvider :
    PreviewParameterProvider<SDGRadioPreviewParams> {

    override val values: Sequence<SDGRadioPreviewParams> = sequenceOf(
        기본_상태_BASIC_MEDIUM(),
        기본_상태_SPECIAL_MEDIUM(),
        선택된_상태_BASIC_MEDIUM(),
        선택된_상태_SPECIAL_MEDIUM(),
    )

    private fun 기본_상태_BASIC_MEDIUM() = SDGRadioPreviewParams(
        isSelected = false,
        color = SDGRadioColor.BASIC,
        size = SDGRadioSize.MEDIUM,
    )

    private fun 기본_상태_SPECIAL_MEDIUM() = SDGRadioPreviewParams(
        isSelected = false,
        color = SDGRadioColor.SPECIAL,
        size = SDGRadioSize.MEDIUM,
    )

    private fun 선택된_상태_BASIC_MEDIUM() = SDGRadioPreviewParams(
        isSelected = true,
        color = SDGRadioColor.BASIC,
        size = SDGRadioSize.MEDIUM,
    )

    private fun 선택된_상태_SPECIAL_MEDIUM() = SDGRadioPreviewParams(
        isSelected = true,
        color = SDGRadioColor.SPECIAL,
        size = SDGRadioSize.MEDIUM,
    )
}
