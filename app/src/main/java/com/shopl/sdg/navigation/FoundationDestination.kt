package com.shopl.sdg.navigation

import kotlinx.serialization.Serializable

/**
 * SDG - Foundation
 *
 * @see <a href="https://www.figma.com/design/qWVshatQ9eqoIn4fdEZqWy/SDG?node-id=4739-18081&m=dev">Figma</a>
 */
sealed interface FoundationDestination : SDGDestination {
    @Serializable
    data object Color : FoundationDestination

    @Serializable
    data object Typograph : FoundationDestination

    @Serializable
    data object Iconography : FoundationDestination

    @Serializable
    data object Illustration : FoundationDestination

    @Serializable
    data object Spacing : FoundationDestination

    @Serializable
    data object CornerRadius : FoundationDestination
}
