package com.shopl.sdg.component.badge.box.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.shopl.sdg.component.badge.box.SDGBoxBadgeFontWeight
import com.shopl.sdg.component.badge.box.SDGBoxBadgeIcon
import com.shopl.sdg.component.badge.box.SDGBoxBadgeStyle
import com.shopl.sdg_common.foundation.SDGColor
import com.shopl.sdg_resource.R

internal class SDGBoxBadgePreviewParameterProvider :
    PreviewParameterProvider<SDGBoxBadgePreviewParam> {

    override val values: Sequence<SDGBoxBadgePreviewParam> = sequenceOf(
        solidNormal(),
        solidBold(),
        lineNormal(),
        lineBold(),
        ellipsis(),
    )

    private fun solidNormal() = SDGBoxBadgePreviewParam(
        style = SDGBoxBadgeStyle.Solid,
        fontWeight = SDGBoxBadgeFontWeight.Normal,
        label = "Label",
        labelColor = SDGColor.Neutral0,
        backgroundColor = SDGColor.Primary300,
        leftIc = SDGBoxBadgeIcon(
            resId = R.drawable.ic_common_prev_s,
            tint = SDGColor.Neutral0,
        ),
        rightIc = SDGBoxBadgeIcon(
            resId = R.drawable.ic_common_next_s,
            tint = SDGColor.Neutral0,
        ),
    )

    private fun solidBold() = SDGBoxBadgePreviewParam(
        style = SDGBoxBadgeStyle.Solid,
        fontWeight = SDGBoxBadgeFontWeight.Bold,
        label = "Label",
        labelColor = SDGColor.Neutral0,
        backgroundColor = SDGColor.Primary300,
    )

    private fun lineNormal() = SDGBoxBadgePreviewParam(
        style = SDGBoxBadgeStyle.Line(lineColor = SDGColor.Primary300),
        fontWeight = SDGBoxBadgeFontWeight.Normal,
        label = "Label",
        labelColor = SDGColor.Primary300,
        backgroundColor = SDGColor.Neutral0,
        leftIc = SDGBoxBadgeIcon(
            resId = R.drawable.ic_common_prev_s,
            tint = SDGColor.Primary300,
        ),
    )

    private fun lineBold() = SDGBoxBadgePreviewParam(
        style = SDGBoxBadgeStyle.Line(lineColor = SDGColor.Primary300),
        fontWeight = SDGBoxBadgeFontWeight.Bold,
        label = "Label",
        labelColor = SDGColor.Primary300,
        backgroundColor = SDGColor.Transparent,
        rightIc = SDGBoxBadgeIcon(
            resId = R.drawable.ic_common_next_s,
            tint = SDGColor.Primary300,
        ),
    )

    private fun ellipsis() = SDGBoxBadgePreviewParam(
        style = SDGBoxBadgeStyle.Solid,
        fontWeight = SDGBoxBadgeFontWeight.Normal,
        label = "Label Label Label Label Label Label Label Label Label Label",
        labelColor = SDGColor.Neutral0,
        backgroundColor = SDGColor.Primary300,
        isFillMaxWidth = true,
    )
}
