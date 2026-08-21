package com.shopl.sdg.component.radio.model

import androidx.compose.ui.graphics.Color
import com.shopl.sdg_common.foundation.SDGColor

enum class SDGRadioSelectedBackgroundColor(
    val selectedBackgroundColor: Color,
) {
    NORMAL(selectedBackgroundColor = SDGColor.Primary300),
    NEUTRAL(selectedBackgroundColor = SDGColor.Neutral700),
}
