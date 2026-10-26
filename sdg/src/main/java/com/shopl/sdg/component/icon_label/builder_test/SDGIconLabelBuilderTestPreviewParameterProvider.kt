package com.shopl.sdg.component.icon_label.builder_test

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.shopl.sdg_common.foundation.SDGColor
import com.shopl.sdg_resource.R

internal class SDGIconLabelBuilderTestPreviewParameterProvider :
    PreviewParameterProvider<SDGIconLabelBuilderTestPreviewParameter> {
    override val values: Sequence<SDGIconLabelBuilderTestPreviewParameter> = sequence {
        for (size in SDGIconLabelBuilderTestSize.entries) {
            for (fontWeight in SDGIconLabelBuilderTestFontWeight.entries) {
                yield(양쪽_아이콘(size, fontWeight))
                yield(왼쪽_긴_라벨(size, fontWeight))
                yield(오른쪽_말줄임(size, fontWeight))
            }
        }
    }

    private fun 양쪽_아이콘(
        size: SDGIconLabelBuilderTestSize,
        fontWeight: SDGIconLabelBuilderTestFontWeight,
    ) = SDGIconLabelBuilderTestPreviewParameter(
        label = "Label",
        labelColor = SDGColor.Neutral700,
        size = size,
        fontWeight = fontWeight,
        gap = SDGIconLabelBuilderTestGap.Spacing4,
        labelOverflow = SDGIconLabelBuilderTestOverflow.Full,
        leftIcon = SDGIconLabelBuilderTestIcon(iconResId = R.drawable.ic_common_help),
        rightIcon = SDGIconLabelBuilderTestIcon(iconResId = R.drawable.ic_common_next_s),
    )

    private fun 왼쪽_긴_라벨(
        size: SDGIconLabelBuilderTestSize,
        fontWeight: SDGIconLabelBuilderTestFontWeight,
    ) = 양쪽_아이콘(size, fontWeight).copy(
        label = "아주 긴 텍스트가 들어가는 경우 전체 텍스트가 줄바꿈되어 노출됩니다.",
        gap = SDGIconLabelBuilderTestGap.Spacing2,
        rightIcon = null,
    )

    private fun 오른쪽_말줄임(
        size: SDGIconLabelBuilderTestSize,
        fontWeight: SDGIconLabelBuilderTestFontWeight,
    ) = 양쪽_아이콘(size, fontWeight).copy(
        label = "아주 긴 텍스트가 들어가는 경우 한 줄 말줄임표로 표시됩니다.",
        labelColor = SDGColor.Primary400,
        labelOverflow = SDGIconLabelBuilderTestOverflow.SingleLineEllipsis,
        leftIcon = null,
    )
}
