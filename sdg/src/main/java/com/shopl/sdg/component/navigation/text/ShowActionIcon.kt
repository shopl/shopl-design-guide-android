package com.shopl.sdg.component.navigation.text

import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.Color

sealed interface ShowActionIcon {
    data class True(
        @param:DrawableRes val iconResId: Int,
        val iconColor: Color?,
    ) : ShowActionIcon

    data object False : ShowActionIcon
}
