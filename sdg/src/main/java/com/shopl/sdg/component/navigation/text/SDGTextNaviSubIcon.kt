package com.shopl.sdg.component.navigation.text

import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.Color

data class SDGTextNaviSubIcon(
    @param:DrawableRes val iconResId: Int,
    override val onClick: () -> Unit,
    val iconColor: Color? = null,
) : SDGTextNaviSubItem
