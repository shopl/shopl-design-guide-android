package com.shopl.sdg.component.badge.box

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

@Immutable
sealed interface SDGBoxBadgeStyle {
    val styleName: String

    data object Solid : SDGBoxBadgeStyle {
        override val styleName = "Solid"
    }

    data class Line(
        val lineColor: Color,
    ) : SDGBoxBadgeStyle {
        override val styleName = "Line"
    }
}
