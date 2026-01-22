package com.shopl.sdg.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation3.runtime.NavEntry
import com.google.accompanist.systemuicontroller.rememberSystemUiController
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
import com.shopl.sdg_common.foundation.SDGColor

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
    val systemUiController = rememberSystemUiController()
    LaunchedEffect(Unit) {
        systemUiController.setStatusBarColor(
            color = SDGColor.Transparent,
            darkIcons = false
        )
    }
    OverviewScreen(
        moveToScene = moveToScene
    )
}

@Composable
private fun FoundationScreenRoute(destination: FoundationScene) {
    val systemUiController = rememberSystemUiController()
    LaunchedEffect(destination) {
        systemUiController.setStatusBarColor(
            color = SDGColor.Transparent,
            darkIcons = true
        )
    }
    when (destination) {
        is FoundationScene.Color -> {
            ColorScreen()
        }

        else -> {}
    }
}

@Composable
private fun ComponentScreenRoute(destination: ComponentScene) {
    val systemUiController = rememberSystemUiController()
    LaunchedEffect(destination) {
        systemUiController.setStatusBarColor(
            color = SDGColor.Transparent,
            darkIcons = true
        )
    }
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
