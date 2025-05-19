package com.shopl.sdg.component.badge.capsule

import androidx.compose.ui.graphics.Color

/**
 * [SDGCapsuleBadge] Type
 */
sealed class SDGCapsuleBadgeType {
    data object Solid : SDGCapsuleBadgeType()

    data class Line(val lineColor: Color) : SDGCapsuleBadgeType()
}