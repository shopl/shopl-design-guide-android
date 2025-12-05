package com.shopl.sdg.navigation

import androidx.compose.runtime.Stable
import kotlinx.serialization.Serializable

/**
 * SDG - Foundation
 *
 * @see <a href="https://www.figma.com/design/qWVshatQ9eqoIn4fdEZqWy/SDG?node-id=4739-18081&m=dev">Figma</a>
 */
@Stable
sealed interface FoundationDestination : SDGDestination {
    @Serializable
    data object Color : FoundationDestination

    @Serializable
    data object Typograph : FoundationDestination
}
