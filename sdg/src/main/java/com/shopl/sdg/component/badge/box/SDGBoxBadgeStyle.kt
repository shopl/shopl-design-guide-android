package com.shopl.sdg.component.badge.box

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

@Immutable
sealed interface SDGBoxBadgeStyle {
    data object Solid : SDGBoxBadgeStyle

    data class Line(
        val lineColor: Color,
    ) : SDGBoxBadgeStyle
}
