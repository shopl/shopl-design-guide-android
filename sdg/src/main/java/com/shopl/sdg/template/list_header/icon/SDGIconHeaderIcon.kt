package com.shopl.sdg.template.list_header.icon

import androidx.compose.ui.graphics.Color

data class SDGIconHeaderIcon(
    val resId: Int,
    val color: Color,
    val onClick: (() -> Unit)? = null
)