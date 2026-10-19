package com.shopl.sdg.component.navigation.text

import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.Color
import com.shopl.sdg_common.foundation.SDGColor

sealed interface SDGTextNaviSubLabel {
    data class True(
        val text: String,
        val textColor: Color = SDGColor.Neutral700,
    ) : SDGTextNaviSubLabel

    data object False : SDGTextNaviSubLabel
}

sealed interface SDGTextNaviSubIcon {
    data class True(
        @param:DrawableRes val iconResId: Int,
        val iconColor: Color? = null,
        val contentDescription: String? = null,
    ) : SDGTextNaviSubIcon

    data object False : SDGTextNaviSubIcon
}
