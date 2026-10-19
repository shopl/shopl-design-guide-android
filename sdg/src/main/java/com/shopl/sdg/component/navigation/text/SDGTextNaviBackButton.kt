package com.shopl.sdg.component.navigation.text

sealed interface SDGTextNaviBackButton {
    data class True(
        val onClick: () -> Unit,
    ) : SDGTextNaviBackButton

    data object False : SDGTextNaviBackButton
}
