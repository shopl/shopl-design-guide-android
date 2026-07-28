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
}
