package com.shopl.sdg.component.check_option.model

enum class SDGCheckOptionState {
    DEFAULT,
    SELECTED,
    DISABLED,
    ;

    companion object {
        fun Boolean.toSDGCheckOptionState(): SDGCheckOptionState = if (this) SELECTED else DEFAULT
    }
}
