package com.shopl.sdg.component.navigation.category

import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.Color

data class SDGCategoryNaviIconItem(
    @param:DrawableRes val iconResId: Int,
    val onClick: () -> Unit,
    val iconColor: Color? = null,
    val showDot: Boolean = false,
    val contentDescription: String? = null,
)
