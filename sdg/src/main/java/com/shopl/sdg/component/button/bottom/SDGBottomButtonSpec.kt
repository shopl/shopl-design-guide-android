package com.shopl.sdg.component.button.bottom

@Deprecated("SDGBottomButtonSize를 사용하세요.")
enum class SDGBottomButtonSpec(val specName: String) {
    FULL("Full Size"),
    ADAPTIVE("Adaptive Size");

    internal fun toSize(): SDGBottomButtonSize = when (this) {
        FULL -> SDGBottomButtonSize.Full
        ADAPTIVE -> SDGBottomButtonSize.Adaptive
    }
}
