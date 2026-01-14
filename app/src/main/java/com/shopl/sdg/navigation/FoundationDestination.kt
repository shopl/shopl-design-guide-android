package com.shopl.sdg.navigation

import androidx.compose.runtime.Stable

/**
 * SDG - Foundation
 *
 * @see <a href="https://www.figma.com/design/qWVshatQ9eqoIn4fdEZqWy/SDG?node-id=4739-18081&m=dev">Figma</a>
 */
@Stable
sealed class FoundationDestination(
    override val displayLabel: String,
    override val implemented: Boolean,
) : SDGDestination(displayLabel, implemented) {
    data object Color : FoundationDestination(
        displayLabel = "Color",
        implemented = false
    )

    data object CornerRadius : FoundationDestination(
        displayLabel = "Corner Radius",
        implemented = false
    )

    data object Iconography : FoundationDestination(
        displayLabel = "Iconography",
        implemented = false
    )

    data object Spacing : FoundationDestination(
        displayLabel = "Spacing",
        implemented = false
    )

    data object Typograph : FoundationDestination(
        displayLabel = "Typograph",
        implemented = false
    )
}
