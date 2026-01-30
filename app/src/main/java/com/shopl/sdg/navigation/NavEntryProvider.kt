package com.shopl.sdg.navigation

import androidx.activity.compose.LocalActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.core.view.WindowCompat
import androidx.navigation3.runtime.NavEntry
import com.shopl.sdg.scene.ComponentScene
import com.shopl.sdg.scene.FoundationScene
import com.shopl.sdg.scene.SDGScene
import com.shopl.sdg.ui.screen.OverviewScreen
import com.shopl.sdg.ui.screen.component.AvatarScreen
import com.shopl.sdg.ui.screen.component.button.BottomButtonScreen
import com.shopl.sdg.ui.screen.component.button.BoxButtonScreen
import com.shopl.sdg.ui.screen.component.button.CapsuleButtonScreen
import com.shopl.sdg.ui.screen.component.button.FloatingButtonScreen
import com.shopl.sdg.ui.screen.component.button.GhostButtonScreen
import com.shopl.sdg.ui.screen.foundation.ColorScreen
import com.shopl.sdg.ui.screen.foundation.IconographyScreen
import com.shopl.sdg.ui.screen.foundation.SpacingScreen
import com.shopl.sdg.ui.screen.foundation.TypographScreen

internal fun provideNavEntry(
    destination: SDGScene,
    moveToScene: (SDGScene) -> Unit,
): NavEntry<Any> {
    return NavEntry(destination) {
        when (destination) {
            is SDGScene.Overview -> OverviewScreenRoute(
                moveToScene = moveToScene
            )

            is FoundationScene -> FoundationScreenRoute(
                destination = destination
            )

            is ComponentScene -> ComponentScreenRoute(
                destination = destination
            )

            else -> {}
        }
    }
}

@Composable
private fun OverviewScreenRoute(
    moveToScene: (SDGScene) -> Unit,
) {
    SDGRouteWrapper(
        isDarkIcon = false
    ) {
        OverviewScreen(
            moveToScene = moveToScene
        )
    }
}

@Composable
private fun FoundationScreenRoute(destination: FoundationScene) {
    SDGRouteWrapper {
        when (destination) {
            is FoundationScene.Color -> {
                ColorScreen()
            }

            is FoundationScene.Spacing -> {
                SpacingScreen()
            }

            is FoundationScene.Iconography -> {
                IconographyScreen()
            }

            is FoundationScene.Typograph -> {
                TypographScreen()
            }

            else -> {}
        }
    }
}

@Composable
private fun ComponentScreenRoute(destination: ComponentScene) {
    SDGRouteWrapper {
        when (destination) {
            is ComponentScene.Avatar -> {
                AvatarScreen()
            }

            is ComponentScene.Button -> {
                when (destination) {
                    is ComponentScene.Button.BottomButton -> {
                        BottomButtonScreen()
                    }

                    is ComponentScene.Button.BoxButton -> {
                        BoxButtonScreen()
                    }

                    is ComponentScene.Button.CapsuleButton -> {
                        CapsuleButtonScreen()
                    }

                    is ComponentScene.Button.GhostButton -> {
                        GhostButtonScreen()
                    }

                    is ComponentScene.Button.FloatingButton -> {
                        FloatingButtonScreen()
                    }

                    else -> {}
                }
            }

            else -> {}
        }
    }
}

@Composable
private fun SDGRouteWrapper(
    isDarkIcon: Boolean = true,
    content: @Composable () -> Unit
) {
    val activity = LocalActivity.current

    SideEffect {
        activity?.window?.let { window ->
            val view = window.decorView
            val windowInsetsController = WindowCompat.getInsetsController(window, view)
            windowInsetsController.isAppearanceLightStatusBars = isDarkIcon
        }
    }
    content()
}
