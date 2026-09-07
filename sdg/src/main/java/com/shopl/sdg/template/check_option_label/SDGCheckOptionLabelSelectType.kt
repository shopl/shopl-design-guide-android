package com.shopl.sdg.template.check_option_label

import androidx.compose.ui.graphics.Color
import com.shopl.sdg.component.check_option.model.SDGCheckOptionSelectedBackgroundColor
import com.shopl.sdg_common.foundation.SDGColor

enum class SDGCheckOptionLabelSelectType(
    internal val labelColor: Color,
    internal val selectedBackgroundColor: SDGCheckOptionSelectedBackgroundColor,
) {
    Normal(
        labelColor = SDGColor.Neutral700,
        selectedBackgroundColor = SDGCheckOptionSelectedBackgroundColor.NORMAL,
    ),
    Color(
        labelColor = SDGColor.Primary300,
        selectedBackgroundColor = SDGCheckOptionSelectedBackgroundColor.NORMAL,
    ),
    Neutral(
        labelColor = SDGColor.Neutral700,
        selectedBackgroundColor = SDGCheckOptionSelectedBackgroundColor.NEUTRAL,
    ),
}