package com.shopl.sdg.component.button.bottom

/**
 * 기존 Bottom Button API의 유형입니다.
 */
@Deprecated("SDGBottomButtonStyle을 사용하세요.")
enum class SDGBottomButtonType(val typeName: String) {
    POSITIVE("Positive"),
    NEGATIVE("Negative"),
    NORMAL("Normal"),
    NORMAL_DARK("Normal Dark");

    internal fun toStyle(): SDGBottomButtonStyle = when (this) {
        POSITIVE -> SDGBottomButtonStyle.Primary
        NEGATIVE -> SDGBottomButtonStyle.Point
        NORMAL -> SDGBottomButtonStyle.Normal
        NORMAL_DARK -> SDGBottomButtonStyle.Neutral
    }
}
