package com.shopl.sdg.component.navigation.text

import androidx.compose.ui.graphics.Color

sealed interface SDGTextNaviLabel {
    data class True(
        val text: String,
        val textColor: Color,
    ) : SDGTextNaviLabel

    data object False : SDGTextNaviLabel
}
