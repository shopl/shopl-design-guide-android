package com.shopl.sdg.component.navigation.text

import androidx.compose.ui.graphics.Color
import com.shopl.sdg_common.foundation.SDGColor

sealed interface SDGTextNaviLabel {
    data class True(
        val text: String,
        val textColor: Color = SDGColor.Neutral900,
    ) : SDGTextNaviLabel

    data object False : SDGTextNaviLabel
}
