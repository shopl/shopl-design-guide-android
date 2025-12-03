package com.shopl.sdg.template.list_header.icon

import androidx.compose.ui.graphics.Color
import kotlinx.collections.immutable.PersistentList

data class SDGIconHeaderRightItem(
    val icons: PersistentList<SDGIconHeaderIcon>,
    val isBox: Boolean
) {
    data class SDGIconHeaderIcon(
        val resId: Int,
        val color: Color,
        val onClick: (() -> Unit)? = null
    )
}
