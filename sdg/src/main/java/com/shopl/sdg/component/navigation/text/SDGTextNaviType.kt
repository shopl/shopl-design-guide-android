package com.shopl.sdg.component.navigation.text

import androidx.annotation.DrawableRes
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

data class SDGTextNaviRightItem(
    val subLabel: SDGTextNaviSubLabel,
    val subIcon: SDGTextNaviSubIcon,
    val onClick: () -> Unit,
)

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
