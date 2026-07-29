package com.shopl.sdg.component.icon_label

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.shopl.sdg_common.foundation.SDGColor
import com.shopl.sdg_resource.R

internal class SDGIconLabelPreviewParameterProvider : PreviewParameterProvider<SDGIconLabelPreviewParam> {
    override val values: Sequence<SDGIconLabelPreviewParam> = sequenceOf(
        SDGIconLabelPreviewParam(
            text = "Medium Normal",
            textColor = SDGColor.Neutral900,
            size = SDGIconLabelSize.Medium,
            type = SDGIconLabelType.Normal,
            spacing = SDGIconLabelSpacing.Spacing4,
            leftIconResId = R.drawable.ic_common_help,
            leftIconTint = SDGColor.Primary400,
        ),
        SDGIconLabelPreviewParam(
            text = "Medium Bold",
            textColor = SDGColor.Neutral900,
            size = SDGIconLabelSize.Medium,
            type = SDGIconLabelType.Bold,
            spacing = SDGIconLabelSpacing.Spacing4,
            leftIconResId = R.drawable.ic_common_help,
            leftIconTint = SDGColor.Primary400,
            rightIconResId = R.drawable.ic_common_next_s,
            rightIconTint = SDGColor.Neutral700,
        ),
        SDGIconLabelPreviewParam(
            text = "Small Normal",
            textColor = SDGColor.Neutral700,
            size = SDGIconLabelSize.Small,
            type = SDGIconLabelType.Normal,
            spacing = SDGIconLabelSpacing.Spacing2,
            rightIconResId = R.drawable.ic_common_next_s,
            rightIconTint = SDGColor.Neutral400,
        ),
        SDGIconLabelPreviewParam(
            text = "XSmall Bold",
            textColor = SDGColor.Secondary400,
            size = SDGIconLabelSize.XSmall,
            type = SDGIconLabelType.Bold,
            spacing = SDGIconLabelSpacing.Spacing4,
            leftIconResId = R.drawable.ic_common_prev_s,
            leftIconTint = SDGColor.Secondary300,
        ),
        SDGIconLabelPreviewParam(
            text = "아주 긴 텍스트가 들어가는 경우 말줄임표 처리 확인용 텍스트입니다.",
            textColor = SDGColor.Neutral600,
            size = SDGIconLabelSize.Small,
            type = SDGIconLabelType.Normal,
            spacing = SDGIconLabelSpacing.Spacing4,
            leftIconResId = R.drawable.ic_common_list,
            rightIconResId = R.drawable.ic_common_next_s,
            maxLines = 1,
            isFillMaxWidth = true,
        ),
    )
}

internal data class SDGIconLabelPreviewParam(
    val text: String,
    val textColor: Color,
    val size: SDGIconLabelSize,
    val type: SDGIconLabelType,
    val spacing: SDGIconLabelSpacing,
    val leftIconResId: Int? = null,
    val leftIconTint: Color? = null,
    val rightIconResId: Int? = null,
    val rightIconTint: Color? = null,
    val maxLines: Int = Int.MAX_VALUE,
    val isFillMaxWidth: Boolean = false,
)
