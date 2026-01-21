package com.shopl.sdg.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.navigation3.runtime.NavEntry
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.shopl.sdg.scene.ComponentScene
import com.shopl.sdg.scene.SDGScene
import com.shopl.sdg.ui.screen.OverviewScreen
import com.shopl.sdg.ui.screen.component.AvatarScreen
import com.shopl.sdg.ui.screen.component.button.BottomButtonScreen
import com.shopl.sdg.ui.screen.component.button.BoxButtonScreen
import com.shopl.sdg.ui.screen.component.button.CapsuleButtonScreen
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
    SideEffect {
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
private fun ComponentScreenRoute(destination: ComponentScene) {
    val systemUiController = rememberSystemUiController()
    SideEffect {
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

                else -> {}
            }
        }

        else -> {}
    }
}
