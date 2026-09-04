@file:Suppress("DEPRECATION")

package com.shopl.sdg.component.badge.box

import androidx.compose.ui.graphics.Color

/**
 * [SDGBoxBadge] Type
 */
@Deprecated(
    message = "SDGBoxBadgeStyle을 사용하세요.",
)
sealed class SDGBoxBadgeType {
    data object Solid : SDGBoxBadgeType()
    data class Line(val lineColor: Color) : SDGBoxBadgeType()
}

internal fun SDGBoxBadgeType.toStyle(): SDGBoxBadgeStyle =
    when (this) {
        SDGBoxBadgeType.Solid -> SDGBoxBadgeStyle.Solid
        is SDGBoxBadgeType.Line -> SDGBoxBadgeStyle.Line(lineColor = lineColor)
    }
