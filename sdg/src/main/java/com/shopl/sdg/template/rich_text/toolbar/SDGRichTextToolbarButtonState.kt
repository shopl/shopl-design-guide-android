package com.shopl.sdg.template.rich_text.toolbar

import androidx.compose.ui.graphics.Color
import com.shopl.sdg_common.foundation.SDGColor

enum class SDGRichTextToolbarButtonState(internal val iconColor: Color) {
    Default(SDGColor.Neutral700),
    Selected(SDGColor.Primary300),
    Disabled(SDGColor.Neutral300),
}
