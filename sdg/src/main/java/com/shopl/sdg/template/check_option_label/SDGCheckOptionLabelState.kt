package com.shopl.sdg.template.check_option_label

sealed interface SDGCheckOptionLabelState {
    data object Default : SDGCheckOptionLabelState
    data object Selected : SDGCheckOptionLabelState
    data object Disabled : SDGCheckOptionLabelState
}