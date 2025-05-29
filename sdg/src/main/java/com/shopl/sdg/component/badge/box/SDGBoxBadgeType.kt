package com.shopl.sdg.component.badge.box

import androidx.compose.ui.graphics.Color

/**
 * [SDGBoxBadge] Type
 */
sealed class SDGBoxBadgeType {
    data object Solid : SDGBoxBadgeType()
    data class Line(val lineColor: Color) : SDGBoxBadgeType()
}