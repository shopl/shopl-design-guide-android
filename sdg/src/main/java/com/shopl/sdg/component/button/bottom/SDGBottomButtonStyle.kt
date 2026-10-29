package com.shopl.sdg.component.button.bottom

import androidx.compose.ui.graphics.Color
import com.shopl.sdg_common.foundation.SDGColor

enum class SDGBottomButtonStyle(
    val styleName: String,
    val activeColor: Color,
    val pressedColor: Color,
    val disabledColor: Color,
    val textColor: Color,
    val disabledTextColor: Color,
) {
    Primary(
        styleName = "Primary",
        activeColor = SDGColor.Primary300,
        pressedColor = SDGColor.Primary400,
        disabledColor = SDGColor.Primary50,
        textColor = SDGColor.Neutral0,
        disabledTextColor = SDGColor.Neutral0,
    ),
    Neutral(
        styleName = "Neutral",
        activeColor = SDGColor.Neutral700,
        pressedColor = SDGColor.Neutral900,
        disabledColor = SDGColor.Neutral300,
        textColor = SDGColor.Neutral0,
        disabledTextColor = SDGColor.Neutral0,
    ),
    Point(
        styleName = "Point",
        activeColor = SDGColor.Red300,
        pressedColor = SDGColor.Red400,
        disabledColor = SDGColor.Red50,
        textColor = SDGColor.Neutral0,
        disabledTextColor = SDGColor.Neutral0,
    ),
    Normal(
        styleName = "Normal",
        activeColor = SDGColor.Neutral200,
        pressedColor = SDGColor.Neutral300,
        disabledColor = SDGColor.Neutral200,
        textColor = SDGColor.Neutral700,
        disabledTextColor = SDGColor.Neutral350,
    ),
}
