package com.shopl.sdg.component.button.box

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import com.shopl.sdg_common.foundation.SDGColor
import com.shopl.sdg_common.foundation.spacing.SDGSpacing.Spacing16
import com.shopl.sdg_common.foundation.spacing.SDGSpacing.Spacing4
import com.shopl.sdg_common.foundation.spacing.SDGSpacing.Spacing8
import com.shopl.sdg_resource.R

internal class SDGBoxButtonPreviewParameterProvider :
    PreviewParameterProvider<SDGBoxButtonPreviewParameter> {

    override val values: Sequence<SDGBoxButtonPreviewParameter> = sequenceOf(
        SDGBoxButtonPreviewParameter(
            label = "버튼명",
            labelColor = SDGColor.Neutral0,
            backgroundColor = SDGColor.Primary400,
            size = SDGBoxButtonSize.Medium,
            type = SDGBoxButtonType.Solid,
            isFillMaxWidth = true,
            marginValues = PaddingValues(horizontal = Spacing16, vertical = Spacing8),
            leftIcon = R.drawable.ic_common_prev_s,
            leftIconTint = SDGColor.Neutral0,
        ),
        SDGBoxButtonPreviewParameter(
            label = "버튼명",
            labelColor = SDGColor.Primary400,
            backgroundColor = SDGColor.Neutral0,
            size = SDGBoxButtonSize.Small,
            type = SDGBoxButtonType.Line(SDGColor.Primary400),
            rightIcon = R.drawable.ic_common_next_s,
            rightIconTint = SDGColor.Neutral500,
            iconDownSize = true,
            marginValues = PaddingValues(horizontal = Spacing16, vertical = Spacing4),
        ),
        SDGBoxButtonPreviewParameter(
            label = "비활성화 된 BoxButton",
            labelColor = SDGColor.Neutral700,
            backgroundColor = SDGColor.Secondary400,
            size = SDGBoxButtonSize.Medium,
            type = SDGBoxButtonType.Solid,
            enable = false,
            rightIcon = R.drawable.ic_common_next_s,
            rightIconTint = SDGColor.Neutral0,
        ),
        SDGBoxButtonPreviewParameter(
            label = "비활성화 된 흰 색상 라벨 BoxButton",
            labelColor = SDGColor.Neutral0,
            backgroundColor = SDGColor.Secondary400,
            size = SDGBoxButtonSize.Medium,
            type = SDGBoxButtonType.Solid,
            enable = false,
            rightIcon = R.drawable.ic_common_next_s,
            rightIconTint = SDGColor.Neutral0,
        ),
        SDGBoxButtonPreviewParameter(
            label = "위치한 영역 내에서 최대 길이까지 버튼명을 표시했는데 길어진다고 줄바꿈으로 전체를 표시할 수 없습니다.",
            labelColor = SDGColor.Neutral700,
            backgroundColor = SDGColor.Neutral0,
            size = SDGBoxButtonSize.XSmall,
            type = SDGBoxButtonType.Line(SDGColor.Neutral300),
            isFillMaxWidth = false,
        ),
    )
}

internal data class SDGBoxButtonPreviewParameter(
    val size: SDGBoxButtonSize,
    val type: SDGBoxButtonType,
    val label: String,
    val labelColor: Color,
    val backgroundColor: Color,
    val iconDownSize: Boolean = false,
    val enable: Boolean = true,
    val isFillMaxWidth: Boolean = false,
    @DrawableRes val leftIcon: Int? = null,
    val leftIconTint: Color? = null,
    @DrawableRes val rightIcon: Int? = null,
    val rightIconTint: Color? = null,
    val marginValues: PaddingValues = PaddingValues(0.dp),
)