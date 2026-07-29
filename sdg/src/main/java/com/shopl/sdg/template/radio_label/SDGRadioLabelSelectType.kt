package com.shopl.sdg.template.radio_label

import androidx.compose.ui.graphics.Color
import com.shopl.sdg.component.radio.SDGRadioColor
import com.shopl.sdg_common.foundation.SDGColor

enum class SDGRadioLabelSelectType(
    internal val labelColor: Color,
    internal val radioColor: SDGRadioColor,
) {
    Normal(
        labelColor = SDGColor.Neutral700,
        radioColor = SDGRadioColor.BASIC,
    ),
    Color(
        labelColor = SDGColor.Primary300,
        radioColor = SDGRadioColor.BASIC,
    ),
    Neutral(
        labelColor = SDGColor.Neutral700,
        radioColor = SDGRadioColor.SPECIAL,
    ),
    ;

    companion object {
        @Deprecated(
            message = "NORMAL 대신 Normal을 사용하세요.",
            replaceWith = ReplaceWith("Normal"),
        )
        @Suppress("PropertyName")
        val NORMAL: SDGRadioLabelSelectType = Normal

        @Deprecated(
            message = "COLOR 대신 Color를 사용하세요.",
            replaceWith = ReplaceWith("Color"),
        )
        @Suppress("PropertyName")
        val COLOR: SDGRadioLabelSelectType = Color

        @Deprecated(
            message = "NEUTRAL 대신 Neutral을 사용하세요.",
            replaceWith = ReplaceWith("Neutral"),
        )
        @Suppress("PropertyName")
        val NEUTRAL: SDGRadioLabelSelectType = Neutral

        /** 레거시 Label/Radio 색상 조합을 신규 선택 타입으로 변환합니다. */
        @Suppress("DEPRECATION")
        internal fun fromLegacy(
            selectedLabelColor: SDGRadioLabelColor,
            radioColor: SDGRadioColor,
        ): SDGRadioLabelSelectType =
            when {
                selectedLabelColor == SDGRadioLabelColor.COLOR -> Color
                radioColor == SDGRadioColor.SPECIAL -> Neutral
                else -> Normal
            }
    }
}
