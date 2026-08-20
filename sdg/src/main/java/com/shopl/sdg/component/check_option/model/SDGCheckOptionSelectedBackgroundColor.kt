package com.shopl.sdg.component.check_option.model

import androidx.compose.ui.graphics.Color
import com.shopl.sdg_common.foundation.SDGColor

enum class SDGCheckOptionSelectedBackgroundColor(
    val selectedBackgroundColor: Color,
) {
    NORMAL(selectedBackgroundColor = SDGColor.Primary300),
    NEUTRAL(selectedBackgroundColor = SDGColor.Neutral700),
}
