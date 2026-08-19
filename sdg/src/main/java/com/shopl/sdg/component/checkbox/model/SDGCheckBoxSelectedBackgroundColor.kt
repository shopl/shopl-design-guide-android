package com.shopl.sdg.component.checkbox.model

import androidx.compose.ui.graphics.Color
import com.shopl.sdg_common.foundation.SDGColor

enum class SDGCheckBoxSelectedBackgroundColor(
    val selectedBackgroundColor: Color,
) {
    Normal(selectedBackgroundColor = SDGColor.Primary300),
    Neutral(selectedBackgroundColor = SDGColor.Neutral700),
}
