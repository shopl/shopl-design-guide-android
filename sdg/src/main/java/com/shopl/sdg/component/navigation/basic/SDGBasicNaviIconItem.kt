package com.shopl.sdg.component.navigation.basic

import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.Color

data class SDGBasicNaviIconItem(
    @param:DrawableRes val resId: Int,
    val onClick: () -> Unit,
    val color: Color? = null,
    val showDot: Boolean = false
)