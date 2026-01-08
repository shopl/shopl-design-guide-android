package com.shopl.sdg.component.radio.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.shopl.sdg.component.radio.SDGRadioColor
import com.shopl.sdg.component.radio.SDGRadioSize
internal class SDGRadioPreviewParameterProvider :
    PreviewParameterProvider<SDGRadioPreviewParams> {

    override val values: Sequence<SDGRadioPreviewParams> = sequenceOf(
        // Default
        기본_상태_BASIC_MEDIUM(),
        기본_상태_SPECIAL_MEDIUM(),

        // Selected
        선택된_상태_BASIC_MEDIUM(),
        선택된_상태_SPECIAL_MEDIUM(),

        // Disabled
        비활성_상태_BASIC_MEDIUM(),
        비활성_상태_SPECIAL_MEDIUM(),
    )

    private fun 기본_상태_BASIC_MEDIUM() = SDGRadioPreviewParams(
        isSelected = false,
        isEnabled = true,
        color = SDGRadioColor.BASIC,
        size = SDGRadioSize.MEDIUM,
    )

    private fun 기본_상태_SPECIAL_MEDIUM() = SDGRadioPreviewParams(
        isSelected = false,
        isEnabled = true,
        color = SDGRadioColor.SPECIAL,
        size = SDGRadioSize.MEDIUM,
    )

    private fun 선택된_상태_BASIC_MEDIUM() = SDGRadioPreviewParams(
        isSelected = true,
        isEnabled = true,
        color = SDGRadioColor.BASIC,
        size = SDGRadioSize.MEDIUM,
    )

    private fun 선택된_상태_SPECIAL_MEDIUM() = SDGRadioPreviewParams(
        isSelected = true,
        isEnabled = true,
        color = SDGRadioColor.SPECIAL,
        size = SDGRadioSize.MEDIUM,
    )

    private fun 비활성_상태_BASIC_MEDIUM() = SDGRadioPreviewParams(
        isSelected = false,
        isEnabled = false,
        color = SDGRadioColor.BASIC,
        size = SDGRadioSize.MEDIUM,
    )

    private fun 비활성_상태_SPECIAL_MEDIUM() = SDGRadioPreviewParams(
        isSelected = false,
        isEnabled = false,
        color = SDGRadioColor.SPECIAL,
        size = SDGRadioSize.MEDIUM,
    )
}
