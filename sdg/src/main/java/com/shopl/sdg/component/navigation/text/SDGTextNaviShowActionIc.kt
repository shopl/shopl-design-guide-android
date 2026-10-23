package com.shopl.sdg.component.navigation.text

import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.Color

sealed interface SDGTextNaviShowActionIc {
    data class True(
        @param:DrawableRes val iconResId: Int,
        val iconColor: Color?,
    ) : SDGTextNaviShowActionIc

    data object False : SDGTextNaviShowActionIc
}
