package com.shopl.sdg.component.checkbox.model

enum class SDGCheckBoxState {
    DEFAULT,
    SELECTED,
    DISABLED,
    ;

    companion object {
        fun Boolean.toSDGCheckBoxState(): SDGCheckBoxState = if (this) SELECTED else DEFAULT
    }
}
