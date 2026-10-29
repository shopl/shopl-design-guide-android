package com.shopl.sdg.component.button.bottom

import androidx.compose.ui.graphics.Color
import com.shopl.sdg_common.foundation.SDGColor

enum class SDGBottomButtonStyle(
    val styleName: String,
    val activeColor: Color,
    val pressedColor: Color,
    val disabledColor: Color,
) {
    Primary(
        styleName = "Primary",
        activeColor = SDGColor.Primary300,
        pressedColor = SDGColor.Primary400,
        disabledColor = SDGColor.Primary50,
    ),
    Neutral(
        styleName = "Neutral",
        activeColor = SDGColor.Neutral700,
        pressedColor = SDGColor.Neutral900,
        disabledColor = SDGColor.Neutral300,
    ),
    Point(
        styleName = "Point",
        activeColor = SDGColor.Red300,
        pressedColor = SDGColor.Red400,
        disabledColor = SDGColor.Red50,
    ),
    Normal(
        styleName = "Normal",
        activeColor = SDGColor.Neutral200,
        pressedColor = SDGColor.Neutral300,
        disabledColor = SDGColor.Neutral200,
    ),
}
