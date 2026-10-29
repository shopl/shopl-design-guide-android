package com.shopl.sdg.component.button.bottom

import com.shopl.sdg.component.button.bottom.model.SDGBottomButtonType as NewSDGBottomButtonType

@Deprecated("com.shopl.sdg.component.button.bottom.model.SDGBottomButtonType을 사용하세요.")
enum class SDGBottomButtonType(val typeName: String) {
    POSITIVE("Positive"),
    NEGATIVE("Negative"),
    NORMAL("Normal"),
    NORMAL_DARK("Normal Dark");

    internal fun toType(): NewSDGBottomButtonType = when (this) {
        POSITIVE -> NewSDGBottomButtonType.Primary
        NEGATIVE -> NewSDGBottomButtonType.Point
        NORMAL -> NewSDGBottomButtonType.Normal
        NORMAL_DARK -> NewSDGBottomButtonType.Neutral
    }
}
