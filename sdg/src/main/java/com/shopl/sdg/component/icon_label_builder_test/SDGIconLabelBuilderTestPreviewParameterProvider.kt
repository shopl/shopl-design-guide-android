package com.shopl.sdg.component.icon_label_builder_test

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.shopl.sdg_common.foundation.SDGColor
import com.shopl.sdg_resource.R

internal class SDGIconLabelBuilderTestPreviewParameterProvider : PreviewParameterProvider<SDGIconLabelBuilderTestPreviewParameter> {
    override val values: Sequence<SDGIconLabelBuilderTestPreviewParameter> = sequenceOf(
        왼쪽_Medium_Normal(),
        양쪽_Medium_Bold(),
        오른쪽_Small_Normal(),
        왼쪽_XSmall_Bold(),
        양쪽_Small_전체노출(),
        양쪽_Small_말줄임(),
    )

    private fun 왼쪽_Medium_Normal() = SDGIconLabelBuilderTestPreviewParameter(
        // TODO(resource-owner): "Medium Normal" 전용 문자열 키가 필요합니다. 등록 후 Preview label에 연결합니다.
        label = "",
        labelColor = SDGColor.Neutral900,
        size = SDGIconLabelBuilderTestSize.Medium,
        fontWeight = SDGIconLabelBuilderTestFontWeight.Normal,
        gap = SDGIconLabelBuilderTestGap.Spacing4,
        leftIc = SDGIconLabelBuilderTestIcon(
            resId = R.drawable.ic_common_help,
            tint = SDGColor.Primary400,
        ),
    )

    private fun 양쪽_Medium_Bold() = SDGIconLabelBuilderTestPreviewParameter(
        // TODO(resource-owner): "Medium Bold" 전용 문자열 키가 필요합니다. 등록 후 Preview label에 연결합니다.
        label = "",
        labelColor = SDGColor.Neutral900,
        size = SDGIconLabelBuilderTestSize.Medium,
        fontWeight = SDGIconLabelBuilderTestFontWeight.Bold,
        gap = SDGIconLabelBuilderTestGap.Spacing4,
        leftIc = SDGIconLabelBuilderTestIcon(
            resId = R.drawable.ic_common_help,
            tint = SDGColor.Primary400,
        ),
        rightIc = SDGIconLabelBuilderTestIcon(
            resId = R.drawable.ic_common_next_s,
            tint = SDGColor.Neutral700,
        ),
    )

    private fun 오른쪽_Small_Normal() = SDGIconLabelBuilderTestPreviewParameter(
        // TODO(resource-owner): "Small Normal" 전용 문자열 키가 필요합니다. 등록 후 Preview label에 연결합니다.
        label = "",
        labelColor = SDGColor.Neutral700,
        size = SDGIconLabelBuilderTestSize.Small,
        fontWeight = SDGIconLabelBuilderTestFontWeight.Normal,
        gap = SDGIconLabelBuilderTestGap.Spacing2,
        rightIc = SDGIconLabelBuilderTestIcon(
            resId = R.drawable.ic_common_next_s,
            tint = SDGColor.Neutral400,
        ),
    )

    private fun 왼쪽_XSmall_Bold() = SDGIconLabelBuilderTestPreviewParameter(
        // TODO(resource-owner): "XSmall Bold" 전용 문자열 키가 필요합니다. 등록 후 Preview label에 연결합니다.
        label = "",
        labelColor = SDGColor.Secondary400,
        size = SDGIconLabelBuilderTestSize.XSmall,
        fontWeight = SDGIconLabelBuilderTestFontWeight.Bold,
        gap = SDGIconLabelBuilderTestGap.Spacing4,
        leftIc = SDGIconLabelBuilderTestIcon(
            resId = R.drawable.ic_common_prev_s,
            tint = SDGColor.Secondary300,
        ),
    )

    private fun 양쪽_Small_전체노출() = SDGIconLabelBuilderTestPreviewParameter(
        // TODO(resource-owner): "아주 긴 텍스트가 들어가는 경우 전체 텍스트가 줄바꿈되어 노출됩니다." 전용 문자열 키가 필요합니다. 등록 후 Preview label에 연결합니다.
        label = "",
        labelColor = SDGColor.Neutral600,
        size = SDGIconLabelBuilderTestSize.Small,
        fontWeight = SDGIconLabelBuilderTestFontWeight.Normal,
        gap = SDGIconLabelBuilderTestGap.Spacing4,
        leftIc = SDGIconLabelBuilderTestIcon(resId = R.drawable.ic_common_list),
        rightIc = SDGIconLabelBuilderTestIcon(resId = R.drawable.ic_common_next_s),
        labelOverflow = SDGIconLabelBuilderTestOverflow.Full,
        isFillMaxWidth = true,
    )

    private fun 양쪽_Small_말줄임() = SDGIconLabelBuilderTestPreviewParameter(
        // TODO(resource-owner): "아주 긴 텍스트가 들어가는 경우 말줄임표 처리 확인용 텍스트입니다." 전용 문자열 키가 필요합니다. 등록 후 Preview label에 연결합니다.
        label = "",
        labelColor = SDGColor.Neutral600,
        size = SDGIconLabelBuilderTestSize.Small,
        fontWeight = SDGIconLabelBuilderTestFontWeight.Normal,
        gap = SDGIconLabelBuilderTestGap.Spacing4,
        leftIc = SDGIconLabelBuilderTestIcon(resId = R.drawable.ic_common_list),
        rightIc = SDGIconLabelBuilderTestIcon(resId = R.drawable.ic_common_next_s),
        labelOverflow = SDGIconLabelBuilderTestOverflow.SingleLineEllipsis,
        isFillMaxWidth = true,
    )
}
