package com.shopl.sdg.component.progress.linear.preview

import androidx.compose.ui.graphics.Color
import com.shopl.sdg.component.progress.linear.SDGLinearProgressType
import com.shopl.sdg_common.foundation.SDGColor

internal data class SDGLinearProgressPreviewParameter(
    val type: SDGLinearProgressType,
    val label: String? = "업로드 중입니다",
    val labelColor: Color = SDGColor.Neutral700,
)
