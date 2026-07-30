package com.shopl.sdg.component.icon_label

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.shopl.sdg_common.foundation.SDGColor
import com.shopl.sdg_resource.R

internal class SDGIconLabelPreviewParameterProvider : PreviewParameterProvider<SDGIconLabelPreviewParam> {
    override val values: Sequence<SDGIconLabelPreviewParam> = sequenceOf(
        SDGIconLabelPreviewParam(
            label = "Medium Normal",
            labelColor = SDGColor.Neutral900,
            size = SDGIconLabelSize.Medium,
            fontWeight = SDGIconLabelFontWeight.Normal,
            gap = SDGIconLabelGap.Spacing4,
            leftIc = R.drawable.ic_common_help,
            leftIcTint = SDGColor.Primary400,
        ),
        SDGIconLabelPreviewParam(
            label = "Medium Bold",
            labelColor = SDGColor.Neutral900,
            size = SDGIconLabelSize.Medium,
            fontWeight = SDGIconLabelFontWeight.Bold,
            gap = SDGIconLabelGap.Spacing4,
            leftIc = R.drawable.ic_common_help,
            leftIcTint = SDGColor.Primary400,
            rightIc = R.drawable.ic_common_next_s,
            rightIcTint = SDGColor.Neutral700,
        ),
        SDGIconLabelPreviewParam(
            label = "Small Normal",
            labelColor = SDGColor.Neutral700,
            size = SDGIconLabelSize.Small,
            fontWeight = SDGIconLabelFontWeight.Normal,
            gap = SDGIconLabelGap.Spacing2,
            rightIc = R.drawable.ic_common_next_s,
            rightIcTint = SDGColor.Neutral400,
        ),
        SDGIconLabelPreviewParam(
            label = "XSmall Bold",
            labelColor = SDGColor.Secondary400,
            size = SDGIconLabelSize.XSmall,
            fontWeight = SDGIconLabelFontWeight.Bold,
            gap = SDGIconLabelGap.Spacing4,
            leftIc = R.drawable.ic_common_prev_s,
            leftIcTint = SDGColor.Secondary300,
        ),
        SDGIconLabelPreviewParam(
            label = "아주 긴 텍스트가 들어가는 경우 말줄임표 처리 확인용 텍스트입니다.",
            labelColor = SDGColor.Neutral600,
            size = SDGIconLabelSize.Small,
            fontWeight = SDGIconLabelFontWeight.Normal,
            gap = SDGIconLabelGap.Spacing4,
            leftIc = R.drawable.ic_common_list,
            rightIc = R.drawable.ic_common_next_s,
            labelOverflow = SDGIconLabelOverflow.SingleLineEllipsis,
            isFillMaxWidth = true,
        ),
    )
}

internal data class SDGIconLabelPreviewParam(
    val label: String,
    val labelColor: Color,
    val size: SDGIconLabelSize,
    val fontWeight: SDGIconLabelFontWeight,
    val gap: SDGIconLabelGap,
    val leftIc: Int? = null,
    val leftIcTint: Color? = null,
    val rightIc: Int? = null,
    val rightIcTint: Color? = null,
    val labelOverflow: SDGIconLabelOverflow = SDGIconLabelOverflow.Full,
    val isFillMaxWidth: Boolean = false,
)
