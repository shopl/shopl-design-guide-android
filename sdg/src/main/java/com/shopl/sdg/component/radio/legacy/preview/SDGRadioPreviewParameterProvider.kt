package com.shopl.sdg.component.radio.legacy.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.shopl.sdg.component.radio.legacy.model.SDGRadioColor
import com.shopl.sdg.component.radio.legacy.model.SDGRadioSize
import com.shopl.sdg.component.radio.legacy.model.SDGRadioStatus

internal class SDGRadioPreviewParameterProvider :
    PreviewParameterProvider<SDGRadioPreviewParams> {

    override val values: Sequence<SDGRadioPreviewParams> = sequenceOf(
        기본_상태_BASIC_MEDIUM(),
        기본_상태_SPECIAL_MEDIUM(),
        선택된_상태_BASIC_MEDIUM(),
        선택된_상태_SPECIAL_MEDIUM(),
        비활성_상태_BASIC_MEDIUM(),
        비활성_상태_SPECIAL_MEDIUM(),
    )

    private fun 기본_상태_BASIC_MEDIUM() = SDGRadioPreviewParams(
        status = SDGRadioStatus.DEFAULT,
        color = SDGRadioColor.BASIC,
        size = SDGRadioSize.MEDIUM,
    )

    private fun 기본_상태_SPECIAL_MEDIUM() = SDGRadioPreviewParams(
        status = SDGRadioStatus.DEFAULT,
        color = SDGRadioColor.SPECIAL,
        size = SDGRadioSize.MEDIUM,
    )

    private fun 선택된_상태_BASIC_MEDIUM() = SDGRadioPreviewParams(
        status = SDGRadioStatus.SELECTED,
        color = SDGRadioColor.BASIC,
        size = SDGRadioSize.MEDIUM,
    )

    private fun 선택된_상태_SPECIAL_MEDIUM() = SDGRadioPreviewParams(
        status = SDGRadioStatus.SELECTED,
        color = SDGRadioColor.SPECIAL,
        size = SDGRadioSize.MEDIUM,
    )

    private fun 비활성_상태_BASIC_MEDIUM() = SDGRadioPreviewParams(
        status = SDGRadioStatus.DISABLED,
        color = SDGRadioColor.BASIC,
        size = SDGRadioSize.MEDIUM,
    )

    private fun 비활성_상태_SPECIAL_MEDIUM() = SDGRadioPreviewParams(
        status = SDGRadioStatus.DISABLED,
        color = SDGRadioColor.SPECIAL,
        size = SDGRadioSize.MEDIUM,
    )
}
