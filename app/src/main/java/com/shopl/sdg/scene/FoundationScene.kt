package com.shopl.sdg.scene

import androidx.compose.runtime.Stable

/**
 * SDG - Foundation
 *
 * @see <a href="https://www.figma.com/design/qWVshatQ9eqoIn4fdEZqWy/SDG?node-id=4739-18081&m=dev">Figma</a>
 */
@Stable
sealed class FoundationScene(
    override val displayLabel: String,
    override val implemented: Boolean,
) : SDGScene(displayLabel, implemented) {
    data object Color : FoundationScene(
        displayLabel = "Color",
        implemented = false
    )

    data object CornerRadius : FoundationScene(
        displayLabel = "Corner Radius",
        implemented = false
    )

    data object Iconography : FoundationScene(
        displayLabel = "Iconography",
        implemented = false
    )

    data object Spacing : FoundationScene(
        displayLabel = "Spacing",
        implemented = false
    )

    data object Typograph : FoundationScene(
        displayLabel = "Typograph",
        implemented = false
    )
}
