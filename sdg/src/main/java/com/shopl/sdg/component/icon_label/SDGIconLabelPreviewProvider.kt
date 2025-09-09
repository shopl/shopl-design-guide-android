package com.shopl.sdg.component.icon_label

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.shopl.sdg_common.foundation.SDGColor
import com.shopl.sdg_resource.R

internal class SDGIconLabelPreviewParameterProvider : PreviewParameterProvider<SDGIconLabelPreviewParam> {
    override val values: Sequence<SDGIconLabelPreviewParam> = sequenceOf(
        SDGIconLabelPreviewParam(
            text = "기본 아이콘 라벨 (텍스트만)",
            textColor = SDGColor.Neutral900,
            size = SDGIconLabelSize.Size14,
            type = SDGIconLabelType.Basic,
            spacing = SDGIconLabelSpacing.Spacing4,
        ),
        SDGIconLabelPreviewParam(
            text = "왼쪽 아이콘 포함", 
            textColor = SDGColor.Neutral900,
            size = SDGIconLabelSize.Size14,
            type = SDGIconLabelType.Empha,
            spacing = SDGIconLabelSpacing.Spacing4,
            leftIconResId = R.drawable.ic_common_help,
            leftIconTint = SDGColor.Primary400,
        ),
        SDGIconLabelPreviewParam(
            text = "오른쪽 아이콘 포함",
            textColor = SDGColor.Neutral700,
            size = SDGIconLabelSize.Size12,
            type = SDGIconLabelType.Basic,
            spacing = SDGIconLabelSpacing.Spacing2,
            rightIconResId = R.drawable.ic_common_next_s,
            rightIconTint = SDGColor.Neutral400,
        ),
        SDGIconLabelPreviewParam(
            text = "양쪽 아이콘 + Empha 16",
            textColor = SDGColor.Secondary400,
            size = SDGIconLabelSize.Size16,
            type = SDGIconLabelType.Empha,
            spacing = SDGIconLabelSpacing.Spacing4,
            leftIconResId = R.drawable.ic_common_prev_s,
            leftIconTint = SDGColor.Secondary300,
            rightIconResId = R.drawable.ic_common_next_s,
            rightIconTint = SDGColor.Secondary300,
            isFillMaxWidth = false,
        ),
        SDGIconLabelPreviewParam(
            text = "아주 긴 텍스트가 들어가는 경우 말줄임표 처리 확인용 텍스트입니다.",
            textColor = SDGColor.Neutral600,
            size = SDGIconLabelSize.Size14,
            type = SDGIconLabelType.Basic,
            spacing = SDGIconLabelSpacing.Spacing4,
            leftIconResId = R.drawable.ic_common_list,
            maxLines = 1,
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
    val isFillMaxWidth: Boolean = true,
)