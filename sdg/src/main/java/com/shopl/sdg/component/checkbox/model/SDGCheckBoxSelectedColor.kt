package com.shopl.sdg.component.checkbox.model

import androidx.compose.ui.graphics.Color
import com.shopl.sdg_common.foundation.SDGColor

enum class SDGCheckBoxSelectedColor(
    val selectedBoxColor: Color,
) {
    Normal(selectedBoxColor = SDGColor.Primary300),
    Neutral(selectedBoxColor = SDGColor.Neutral700),
}
