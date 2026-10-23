package com.shopl.sdg.component.navigation.text

import androidx.compose.ui.graphics.Color

sealed interface SDGTextNaviBackButton {
    data class True(
        val onClick: () -> Unit,
        val iconColor: Color?,
    ) : SDGTextNaviBackButton

    data object False : SDGTextNaviBackButton
}
