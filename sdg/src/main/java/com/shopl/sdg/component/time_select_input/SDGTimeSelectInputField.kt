package com.shopl.sdg.component.time_select_input

import androidx.compose.ui.graphics.Color
import com.shopl.sdg_common.foundation.SDGColor

/**
 * Time Select Input의 인풋 필드 배경 타입입니다.
 *
 * @property typeName 타입 이름
 */
enum class SDGTimeSelectInputField(
    val typeName: String,
    internal val backgroundColor: Color,
) {
    LIGHT_GRAY(
        typeName = "LightGray",
        backgroundColor = SDGColor.Neutral50,
    ),
    WHITE(
        typeName = "White",
        backgroundColor = SDGColor.Neutral0,
    ),
}
