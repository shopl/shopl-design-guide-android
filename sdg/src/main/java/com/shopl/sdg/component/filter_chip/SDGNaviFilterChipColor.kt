package com.shopl.sdg.component.filter_chip

import androidx.annotation.ColorRes
import androidx.compose.ui.graphics.Color
import com.shopl.sdg_common.foundation.SDGColor

enum class SDGNaviFilterChipColor(
    @ColorRes val backgroundColor: Color,
) {
    WHITE(backgroundColor = SDGColor.Neutral0),
    LIGHT_GRAY(backgroundColor = SDGColor.Neutral50),
}
