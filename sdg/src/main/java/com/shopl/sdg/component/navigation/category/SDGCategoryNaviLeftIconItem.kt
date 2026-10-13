package com.shopl.sdg.component.navigation.category

import androidx.compose.ui.graphics.Color

data class SDGCategoryNaviLeftIconItem(
    val onClick: () -> Unit,
    val iconColor: Color?,
    val contentDescription: String? = null,
)
