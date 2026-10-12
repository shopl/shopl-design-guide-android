package com.shopl.sdg.component.select_input

sealed interface SDGSelectInputShowClearIcon {
    val enabled: Boolean
    val onClick: () -> Unit

    data class True(
        override val onClick: () -> Unit,
    ) : SDGSelectInputShowClearIcon {
        override val enabled: Boolean = true
    }

    data object False : SDGSelectInputShowClearIcon {
        override val enabled: Boolean = false
        override val onClick: () -> Unit = {}
    }
}
