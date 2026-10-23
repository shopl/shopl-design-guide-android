package com.shopl.sdg.component.navigation.text

import androidx.compose.ui.graphics.Color

sealed interface SDGTextNaviActionItem {
    data class Label(
        val text: String,
        val textColor: Color,
        val onClick: () -> Unit,
    ) : SDGTextNaviActionItem
}
