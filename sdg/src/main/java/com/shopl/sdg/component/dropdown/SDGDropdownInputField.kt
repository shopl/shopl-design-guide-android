package com.shopl.sdg.component.dropdown

import androidx.compose.ui.graphics.Color
import com.shopl.sdg_common.foundation.SDGColor

/** Dropdown의 Figma Input Field 배경 유형입니다. */
enum class SDGDropdownInputField(
    val fieldName: String,
    internal val backgroundColor: Color,
) {
    LightGray(
        fieldName = "LightGray",
        backgroundColor = SDGColor.Neutral50,
    ),
    White(
        fieldName = "White",
        backgroundColor = SDGColor.Neutral0,
    ),
    ;

    internal companion object {
        /** 레거시 Dropdown의 배경색을 Input Field 유형으로 변환합니다. */
        fun fromLegacyBackgroundColor(backgroundColor: Color): SDGDropdownInputField =
            if (backgroundColor == SDGColor.Neutral50) {
                LightGray
            } else {
                White
            }
    }
}
