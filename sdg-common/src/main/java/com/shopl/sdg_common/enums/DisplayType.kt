package com.shopl.sdg_common.enums

import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.Color
import com.shopl.sdg_common.foundation.SDGColor
import com.shopl.sdg_common.ui.components.IOTypeface

@Stable
enum class DisplayType(val typeface: IOTypeface, val subTextColor: Color) {
    NORMAL(IOTypeface.REGULAR, SDGColor.Neutral500), EMPHA(IOTypeface.SEMI_BOLD, SDGColor.Neutral700)
}



