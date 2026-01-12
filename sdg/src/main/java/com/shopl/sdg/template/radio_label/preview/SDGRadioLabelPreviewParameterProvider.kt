package com.shopl.sdg.template.radio_label.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.shopl.sdg.template.radio_label.SDGRadioLabelColor
import com.shopl.sdg.template.radio_label.SDGRadioLabelStatus

internal class SDGRadioLabelPreviewParameterProvider :
    PreviewParameterProvider<SDGRadioLabelPreviewParams> {

    override val values: Sequence<SDGRadioLabelPreviewParams> = sequenceOf(
        기본_상태_LABEL_BASIC_한줄(),
        기본_상태_LABEL_COLOR_한줄(),
        기본_상태_LABEL_BASIC_여러줄(),
        선택_상태_LABEL_BASIC_한줄(),
        선택_상태_LABEL_COLOR_한줄(),
        비활성_상태_LABEL_BASIC_한줄(),
        비활성_상태_LABEL_COLOR_한줄(),
    )

    private fun 기본_상태_LABEL_BASIC_한줄() = SDGRadioLabelPreviewParams(
        status = SDGRadioLabelStatus.DEFAULT,
        labelColor = SDGRadioLabelColor.BASIC,
        label = "옵션",
    )

    private fun 기본_상태_LABEL_COLOR_한줄() = SDGRadioLabelPreviewParams(
        status = SDGRadioLabelStatus.DEFAULT,
        labelColor = SDGRadioLabelColor.COLOR,
        label = "옵션",
    )

    private fun 기본_상태_LABEL_BASIC_여러줄() = SDGRadioLabelPreviewParams(
        status = SDGRadioLabelStatus.DEFAULT,
        labelColor = SDGRadioLabelColor.BASIC,
        label = "옵션이 여러 줄로\n표시되는 경우를\n확인하기 위한 텍스트",
    )

    private fun 선택_상태_LABEL_BASIC_한줄() = SDGRadioLabelPreviewParams(
        status = SDGRadioLabelStatus.SELECTED,
        labelColor = SDGRadioLabelColor.BASIC,
        label = "옵션",
    )

    private fun 선택_상태_LABEL_COLOR_한줄() = SDGRadioLabelPreviewParams(
        status = SDGRadioLabelStatus.SELECTED,
        labelColor = SDGRadioLabelColor.COLOR,
        label = "옵션",
    )

    private fun 비활성_상태_LABEL_BASIC_한줄() = SDGRadioLabelPreviewParams(
        status = SDGRadioLabelStatus.DISABLED,
        labelColor = SDGRadioLabelColor.BASIC,
        label = "옵션",
    )

    private fun 비활성_상태_LABEL_COLOR_한줄() = SDGRadioLabelPreviewParams(
        status = SDGRadioLabelStatus.DISABLED,
        labelColor = SDGRadioLabelColor.BASIC,
        label = "옵션",
    )

}
