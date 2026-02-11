package com.shopl.sdg.component.badge.box.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.shopl.sdg.component.badge.box.SDGBoxBadgeSize
import com.shopl.sdg.component.badge.box.SDGBoxBadgeType
import com.shopl.sdg_common.foundation.SDGColor

internal class SDGBoxBadgePreviewParameterProvider :
    PreviewParameterProvider<SDGBoxBadgePreviewParam> {

    override val values: Sequence<SDGBoxBadgePreviewParam> = sequenceOf(
        Solid_기본(),
        Line_기본(),
        Solid_비활성(),
        Solid_말줄임(),
        Solid_오른쪽_아이콘(),
        Solid_오른쪽_아이콘_말줄임(),
    )

    private fun Solid_기본() = SDGBoxBadgePreviewParam(
        size = SDGBoxBadgeSize.XSmall,
        type = SDGBoxBadgeType.Solid,
        label = "Label",
        labelColor = SDGColor.Neutral700,
        backgroundColor = SDGColor.Neutral0,
    )

    private fun Line_기본() = SDGBoxBadgePreviewParam(
        size = SDGBoxBadgeSize.XSmall,
        type = SDGBoxBadgeType.Line(SDGColor.Neutral300),
        label = "Label",
        labelColor = SDGColor.Neutral700,
        backgroundColor = SDGColor.Transparent,
    )

    private fun Solid_비활성() = SDGBoxBadgePreviewParam(
        size = SDGBoxBadgeSize.XSmall,
        type = SDGBoxBadgeType.Solid,
        label = "Label",
        labelColor = SDGColor.Neutral700,
        backgroundColor = SDGColor.Neutral0,
        enable = false
    )

    private fun Solid_말줄임() = SDGBoxBadgePreviewParam(
        size = SDGBoxBadgeSize.XSmall,
        type = SDGBoxBadgeType.Solid,
        label = "Label Label Label Label Label Label Label Label Label Label Label Label Label Label Label Label Label Label",
        labelColor = SDGColor.Neutral700,
        backgroundColor = SDGColor.Neutral0,
    )

    private fun Solid_오른쪽_아이콘() = SDGBoxBadgePreviewParam(
        size = SDGBoxBadgeSize.XSmall,
        type = SDGBoxBadgeType.Solid,
        label = "Label",
        labelColor = SDGColor.Neutral700,
        backgroundColor = SDGColor.Neutral0,
        rightIcon = com.shopl.sdg_resource.R.drawable.ic_common_next_s,
        rightIconTint = SDGColor.Neutral700
    )

    private fun Solid_오른쪽_아이콘_말줄임() = SDGBoxBadgePreviewParam(
        size = SDGBoxBadgeSize.XSmall,
        type = SDGBoxBadgeType.Solid,
        label = "Label Label Label Label Label Label Label Label Label Label Label Label Label Label Label Label Label Label",
        labelColor = SDGColor.Neutral700,
        backgroundColor = SDGColor.Neutral0,
        rightIcon = com.shopl.sdg_resource.R.drawable.ic_common_next_s,
        rightIconTint = SDGColor.Neutral700
    )
}