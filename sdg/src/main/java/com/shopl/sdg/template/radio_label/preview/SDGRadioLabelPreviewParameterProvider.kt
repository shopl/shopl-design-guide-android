package com.shopl.sdg.template.radio_label.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.shopl.sdg.template.radio_label.SDGRadioLabelColor

internal class SDGRadioLabelPreviewParameterProvider :
    PreviewParameterProvider<SDGRadioLabelPreviewParams> {

    override val values: Sequence<SDGRadioLabelPreviewParams> = sequenceOf(
        활성_상태_및_선택_상태_LABEL_BASIC_한줄(),
        활성_상태_및_선택_상태_LABEL_COLOR_한줄(),
        활성_상태_및_비선택_상태_LABEL_BASIC_한줄(),
        비활성_상태_및_선택_상태_LABEL_BASIC_한줄(),
        비활성_상태_및_비선택_상태_LABEL_BASIC_한줄(),
        비활성_상태_LABEL_BASIC_여러줄(),
    )

    private fun 활성_상태_및_선택_상태_LABEL_BASIC_한줄() = SDGRadioLabelPreviewParams(
        isSelected = true,
        isEnabled = true,
        labelColor = SDGRadioLabelColor.BASIC,
        label = "옵션",
    )

    private fun 활성_상태_및_선택_상태_LABEL_COLOR_한줄() = SDGRadioLabelPreviewParams(
        isSelected = true,
        isEnabled = true,
        labelColor = SDGRadioLabelColor.COLOR,
        label = "옵션",
    )

    private fun 활성_상태_및_비선택_상태_LABEL_BASIC_한줄() = SDGRadioLabelPreviewParams(
        isSelected = false,
        isEnabled = true,
        labelColor = SDGRadioLabelColor.BASIC,
        label = "옵션",
    )

    private fun 비활성_상태_및_선택_상태_LABEL_BASIC_한줄() = SDGRadioLabelPreviewParams(
        isSelected = true,
        isEnabled = false,
        labelColor = SDGRadioLabelColor.BASIC,
        label = "옵션",
    )

    private fun 비활성_상태_및_비선택_상태_LABEL_BASIC_한줄() = SDGRadioLabelPreviewParams(
        isSelected = false,
        isEnabled = false,
        labelColor = SDGRadioLabelColor.BASIC,
        label = "옵션",
    )

    private fun 비활성_상태_LABEL_BASIC_여러줄() = SDGRadioLabelPreviewParams(
        isSelected = false,
        isEnabled = false,
        labelColor = SDGRadioLabelColor.BASIC,
        label = "옵션이 여러 줄로\n표시되는 경우를\n확인하기 위한 텍스트",
    )
}
