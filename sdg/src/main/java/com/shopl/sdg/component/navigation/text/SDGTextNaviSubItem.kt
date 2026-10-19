package com.shopl.sdg.component.navigation.text

import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.Color

sealed interface SDGTextNaviSubItem {
    val onClick: () -> Unit

    data class Icon(
        @param:DrawableRes val iconResId: Int,
        override val onClick: () -> Unit,
        val iconColor: Color?,
    ) : SDGTextNaviSubItem

    data class Label(
        val text: String,
        override val onClick: () -> Unit,
    ) : SDGTextNaviSubItem
}
