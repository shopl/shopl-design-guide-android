package com.shopl.sdg.component.navigation.text

import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.Color
import com.shopl.sdg_common.foundation.SDGColor

sealed interface SDGTextNaviSubItem {
    val onClick: () -> Unit

    data class Icon(
        @param:DrawableRes val iconResId: Int,
        override val onClick: () -> Unit,
        val iconColor: Color? = null,
    ) : SDGTextNaviSubItem

    data class Label(
        val text: String,
        override val onClick: () -> Unit,
        val textColor: Color = SDGColor.Neutral700,
    ) : SDGTextNaviSubItem
}
