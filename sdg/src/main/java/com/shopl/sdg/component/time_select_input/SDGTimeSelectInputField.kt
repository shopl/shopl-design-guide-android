package com.shopl.sdg.component.time_select_input

import androidx.compose.ui.graphics.Color
import com.shopl.sdg_common.foundation.SDGColor

enum class SDGTimeSelectInputField(
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

    @Deprecated(
        message = "typeName 대신 fieldName을 사용하세요.",
        replaceWith = ReplaceWith("fieldName"),
    )
    val typeName: String
        get() = fieldName

    companion object {
        @Deprecated(
            message = "LIGHT_GRAY 대신 LightGray를 사용하세요.",
            replaceWith = ReplaceWith("LightGray"),
        )
        @Suppress("PropertyName")
        val LIGHT_GRAY: SDGTimeSelectInputField = LightGray

        @Deprecated(
            message = "WHITE 대신 White를 사용하세요.",
            replaceWith = ReplaceWith("White"),
        )
        @Suppress("PropertyName")
        val WHITE: SDGTimeSelectInputField = White

        /** 레거시 Time Select Input의 배경색을 Input Field 유형으로 변환합니다. */
        internal fun fromLegacyBackgroundColor(backgroundColor: Color): SDGTimeSelectInputField =
            if (backgroundColor == SDGColor.Neutral0) {
                White
            } else {
                LightGray
            }
    }
}
