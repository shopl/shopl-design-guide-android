package com.shopl.sdg.component.navigation.text

import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.Color

sealed interface SDGTextNaviActionItem {
    data class Icon(
        @param:DrawableRes val iconResId: Int,
        val iconColor: Color?,
    ) : SDGTextNaviActionItem

    data class Label(
        val text: String,
        val textColor: Color,
        val onClick: () -> Unit,
    ) : SDGTextNaviActionItem
}
