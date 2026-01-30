package com.shopl.sdg.scene

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import com.shopl.sdg.ui.screen.foundation.ColorScreen
import com.shopl.sdg.ui.screen.foundation.IconographyScreen
import com.shopl.sdg.ui.screen.foundation.SpacingScreen
import com.shopl.sdg.ui.screen.foundation.TypographScreen
import kotlinx.collections.immutable.persistentListOf

/**
 * SDG - Foundation
 *
 * @see <a href="https://www.figma.com/design/qWVshatQ9eqoIn4fdEZqWy/SDG?node-id=4739-18081&m=dev">Figma</a>
 */
@Stable
internal sealed class FoundationScene(
    override val displayLabel: String,
    override val implemented: Boolean,
) : SDGScene(displayLabel, implemented) {
    data object Color : FoundationScene(
        displayLabel = "Color",
        implemented = true
    ) {
        override val isDarkIcon: Boolean = true

        @Composable
        override fun Screen(moveToScene: (SDGScene) -> Unit) {
            ColorScreen()
        }
    }

    data object CornerRadius : FoundationScene(
        displayLabel = "Corner Radius",
        implemented = false
    ) {
        override val isDarkIcon: Boolean = true

        @Composable
        override fun Screen(moveToScene: (SDGScene) -> Unit) {
            throw IllegalStateException("Not implemented")
        }
    }

    data object Iconography : FoundationScene(
        displayLabel = "Iconography",
        implemented = true
    ) {
        override val isDarkIcon: Boolean = true

        @Composable
        override fun Screen(moveToScene: (SDGScene) -> Unit) {
            IconographyScreen()
        }
    }

    data object Spacing : FoundationScene(
        displayLabel = "Spacing",
        implemented = true
    ) {
        override val isDarkIcon: Boolean = true

        @Composable
        override fun Screen(moveToScene: (SDGScene) -> Unit) {
            SpacingScreen()
        }
    }

    data object Typograph : FoundationScene(
        displayLabel = "Typograph",
        implemented = true
    ) {
        override val isDarkIcon: Boolean = true

        @Composable
        override fun Screen(moveToScene: (SDGScene) -> Unit) {
            TypographScreen()
        }
    }
}

internal val foundationScenes = persistentListOf(
    FoundationScene.Color,
    FoundationScene.CornerRadius,
    FoundationScene.Iconography,
    FoundationScene.Spacing,
    FoundationScene.Typograph,
)
