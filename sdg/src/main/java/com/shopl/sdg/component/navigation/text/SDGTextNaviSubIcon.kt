package com.shopl.sdg.component.navigation.text

import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.Color

sealed interface SDGTextNaviSubIcon {
    data class True(
        @param:DrawableRes val iconResId: Int,
        val iconColor: Color? = null,
    ) : SDGTextNaviSubIcon

    data object False : SDGTextNaviSubIcon
}
