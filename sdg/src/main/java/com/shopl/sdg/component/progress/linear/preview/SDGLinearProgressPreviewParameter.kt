package com.shopl.sdg.component.progress.linear.preview

import com.shopl.sdg.component.progress.linear.SDGLinearProgressShowLabel
import com.shopl.sdg.component.progress.linear.SDGLinearProgressType
import com.shopl.sdg_common.foundation.SDGColor

internal data class SDGLinearProgressPreviewParameter(
    val type: SDGLinearProgressType,
    val showLabel: SDGLinearProgressShowLabel = SDGLinearProgressShowLabel.True(
        text = "업로드 중입니다",
        textColor = SDGColor.Neutral700,
    ),
)
