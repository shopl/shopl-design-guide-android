package com.shopl.sdg.component.checkbox.model

enum class SDGCheckBoxState {
    Default,
    Selected,
    Disabled,
    ;

    companion object {
        fun Boolean.toSDGCheckBoxState(): SDGCheckBoxState = if (this) Selected else Default
    }
}
