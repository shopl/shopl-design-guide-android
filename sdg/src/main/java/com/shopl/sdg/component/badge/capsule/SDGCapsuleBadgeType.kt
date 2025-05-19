package com.shopl.sdg.component.badge.capsule

import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.Color

/**
 * [SDGCapsuleBadge] - Badge Type
 */
sealed class SDGCapsuleBadgeType {
    @Stable
    data object Solid : SDGCapsuleBadgeType()

    @Stable
    data class Line(val lineColor: Color) : SDGCapsuleBadgeType()
}