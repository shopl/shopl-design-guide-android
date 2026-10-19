package com.shopl.sdg.component.navigation.text

import androidx.compose.ui.graphics.Color
import com.shopl.sdg_common.foundation.SDGColor

sealed interface SDGTextNaviBackButton {
    data class True(
        val onClick: () -> Unit,
        val iconColor: Color = SDGColor.Neutral700,
        val contentDescription: String? = null,
    ) : SDGTextNaviBackButton

    data object False : SDGTextNaviBackButton
}
