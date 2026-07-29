package com.shopl.sdg.template.checkbox_label

import androidx.compose.ui.graphics.Color
import com.shopl.sdg_common.foundation.SDGColor

enum class SDGCheckboxLabelSelectType(
    internal val labelColor: Color,
    internal val checkedBackgroundColor: Color,
) {
    Normal(
        labelColor = SDGColor.Neutral700,
        checkedBackgroundColor = SDGColor.Primary300,
    ),
    Color(
        labelColor = SDGColor.Primary300,
        checkedBackgroundColor = SDGColor.Primary300,
    ),
    Neutral(
        labelColor = SDGColor.Neutral700,
        checkedBackgroundColor = SDGColor.Neutral700,
    ),
    ;

    companion object {
        @Deprecated(
            message = "NORMAL 대신 Normal을 사용하세요.",
            replaceWith = ReplaceWith("Normal"),
        )
        @Suppress("PropertyName")
        val NORMAL: SDGCheckboxLabelSelectType = Normal

        @Deprecated(
            message = "COLOR 대신 Color를 사용하세요.",
            replaceWith = ReplaceWith("Color"),
        )
        @Suppress("PropertyName")
        val COLOR: SDGCheckboxLabelSelectType = Color

        @Deprecated(
            message = "NEUTRAL 대신 Neutral을 사용하세요.",
            replaceWith = ReplaceWith("Neutral"),
        )
        @Suppress("PropertyName")
        val NEUTRAL: SDGCheckboxLabelSelectType = Neutral
    }
}
