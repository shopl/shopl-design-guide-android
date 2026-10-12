package com.shopl.sdg.component.select_input

sealed class SDGSelectInputShowClearIcon(
    open val onClick: (() -> Unit)? = null,
) {
    data class True(
        override val onClick: () -> Unit,
    ) : SDGSelectInputShowClearIcon(onClick = onClick)

    data object False : SDGSelectInputShowClearIcon()
}
