package com.shopl.sdg.component.radio.model

enum class SDGRadioState {
    DEFAULT,
    SELECTED,
    DISABLED,
    ;

    companion object {
        fun Boolean.toSDGRadioState(): SDGRadioState = if (this) SELECTED else DEFAULT
    }
}
