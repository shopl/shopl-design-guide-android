package com.shopl.sdg.component.icon_label

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.shopl.sdg_common.foundation.SDGColor
import com.shopl.sdg_resource.R

internal class SDGIconLabelPreviewParameterProvider : PreviewParameterProvider<SDGIconLabelPreviewParameter> {
    override val values: Sequence<SDGIconLabelPreviewParameter> = sequenceOf(
        기본_Medium(),
        강조_Medium(),
        기본_Small(),
        강조_Small(),
        기본_XSmall(),
        강조_XSmall(),
        긴라벨_전체노출(),
        긴라벨_한줄말줄임(),
    )

    private fun 기본_Medium() = SDGIconLabelPreviewParameter(
        label = "Medium Normal",
        labelColor = SDGColor.Neutral700,
        size = SDGIconLabelSize.Medium,
        fontWeight = SDGIconLabelFontWeight.Normal,
        gap = SDGIconLabelGap.Spacing4,
        leftIc = SDGIconLabelIcon(R.drawable.ic_common_help, SDGColor.Primary400),
        rightIc = SDGIconLabelIcon(R.drawable.ic_common_next_s, SDGColor.Neutral700),
    )

    private fun 강조_Medium() = 기본_Medium().copy(
        label = "Medium Bold",
        fontWeight = SDGIconLabelFontWeight.Bold,
        gap = SDGIconLabelGap.Spacing2,
    )

    private fun 기본_Small() = 기본_Medium().copy(
        label = "Small Normal",
        size = SDGIconLabelSize.Small,
        leftIc = null,
    )

    private fun 강조_Small() = 강조_Medium().copy(
        label = "Small Bold",
        size = SDGIconLabelSize.Small,
        rightIc = null,
    )

    private fun 기본_XSmall() = 기본_Medium().copy(
        label = "XSmall Normal",
        size = SDGIconLabelSize.XSmall,
        labelColor = SDGColor.Secondary400,
    )

    private fun 강조_XSmall() = 기본_XSmall().copy(
        label = "XSmall Bold",
        fontWeight = SDGIconLabelFontWeight.Bold,
        gap = SDGIconLabelGap.Spacing2,
    )

    private fun 긴라벨_전체노출() = 기본_Small().copy(
        label = "긴 라벨의 전체 노출과 한 줄 말줄임을 같은 너비에서 확인합니다.",
        labelOverflow = SDGIconLabelOverflow.Full,
        isFillMaxWidth = true,
    )

    private fun 긴라벨_한줄말줄임() = 긴라벨_전체노출().copy(
        labelOverflow = SDGIconLabelOverflow.SingleLineEllipsis,
    )
}
