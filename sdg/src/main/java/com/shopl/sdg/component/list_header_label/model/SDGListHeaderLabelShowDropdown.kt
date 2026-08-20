package com.shopl.sdg.component.list_header_label.model

sealed interface SDGListHeaderLabelShowDropdown {
    val enabled: Boolean
    val onClick: () -> Unit

    data class True(
        override val onClick: () -> Unit,
    ) : SDGListHeaderLabelShowDropdown {
        override val enabled: Boolean = true
    }

    data object False : SDGListHeaderLabelShowDropdown {
        override val enabled: Boolean = false
        override val onClick: () -> Unit = {}
    }
}
